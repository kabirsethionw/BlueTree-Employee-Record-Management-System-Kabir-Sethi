package BlueTree.services;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import BlueTree.utils.JwtUtil;

@Component
public class Filter extends OncePerRequestFilter {

	@Autowired
    private JwtUtil jwtUtil;
   
	@Autowired
	private CustomUserDetailsService service;
	
	public Filter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 String authorizationHeader = request.getHeader("Authorization");
		 System.out.println("autho "+authorizationHeader);
	        String token = null;
	        String userName = null;
	        
	        Cookie[] arr = null;
	        arr = request.getCookies();
	        Cookie ptr = null;
	        if(arr != null) {
	        	for(Cookie elem: arr) {
		        	if(elem.getName().equals("token")) {
		        		ptr = elem;
		        	}
		        }
	        }
	        
	        if(ptr != null ) {
	        	token = ptr.getValue();
	        	userName = jwtUtil.extractUsername(token); 
	        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = service.loadUserByUsername(userName);

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        response.addHeader("Authentication", "Bearer "+token);
        filterChain.doFilter(request, response);
		
	}

}
