package BlueTree.controllers;

import java.net.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import BlueTree.entities.Administrator;


@Controller
public class HomeController {
	@GetMapping("/home")
	public String home() {
		return "BlueTree-Home";
	}
	
	@Autowired
	private BlueTree.utils.JwtUtil jwtUtil; 

	@Autowired
	private AuthenticationManager authManager;
	
	@GetMapping("/")
		public ResponseEntity<?> welcome() {
			return new ResponseEntity<>("Success!", HttpStatus.ACCEPTED);
		}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody Administrator authRequest, HttpServletResponse http) throws Exception {
		try{
			System.out.println(authRequest.getUsername()+" here "+ authRequest.getPassword());
			authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
				);
			}catch(Exception e) {
				return new ResponseEntity<>("Invalid username/password", HttpStatus.BAD_REQUEST);
			}
		String token = jwtUtil.generateToken(authRequest.getUsername());
		http.addCookie(new Cookie("token", token));
		http.addCookie(new Cookie("name", authRequest.getUsername()));
		return new ResponseEntity<>(token , HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/login")
	public String login() {
		return "Bluetree-Admin-Login2";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "Bluetree-Admin-Dashboard";
	}
	
	@GetMapping("/create")
	public String createAdmin() {
		return "Bluetree-Admin-Create";
	}
	
	@GetMapping("/delete")
	public String deleteAdmin() {
		return "Bluetree-Admin-Delete";
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
}