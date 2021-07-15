package BlueTree.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BlueTree.entities.Administrator;
import BlueTree.repositories.AdministratorRepository;

@RestController
@RequestMapping("/admin")
public class AdministratorController {
	
	@Autowired
	private AdministratorRepository adminRespository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private BlueTree.utils.JwtUtil jwtUtil; 
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createAdmin(@RequestBody Administrator admin) {
		try {
				admin.setPassword(passwordEncoder.encode(admin.getPassword()));
				System.out.println(admin.getPassword());
				adminRespository.save(admin);
				return new ResponseEntity<>("Admin created successfully!", HttpStatus.CREATED);
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
		
	//For Testing
	@PostMapping("/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody Administrator authRequest, HttpServletResponse http) throws Exception {
		try{
			System.out.println(authRequest.getUsername()+" here "+ authRequest.getPassword());
			authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
				);
			}catch(Exception e) {
				throw new Exception("Invalid username/password");
			}
		String token = jwtUtil.generateToken(authRequest.getUsername());
		http.addCookie(new Cookie("token", token));
		return new ResponseEntity<>(token , HttpStatus.ACCEPTED);
	}
		
	@DeleteMapping("/delete")
	public ResponseEntity<?> removeAdmin(@RequestParam String employee){
		try {
			Optional<Administrator> entry = adminRespository.findByUsername(employee);
			if(entry.isPresent()) {
				adminRespository.delete(entry.get());
				return new ResponseEntity<>("Account deleted successfully!", HttpStatus.ACCEPTED);
			}return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
			
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllAdmins(){
		try {
			String Statement = "SELECT username From administrator;";
			ArrayList<String> list = new ArrayList<String>();
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(Statement);
			for(Map row : rows) {
				String name = (String)row.get("username");
				list.add(name);
			}
			return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/first")
	public ResponseEntity<?> first(@RequestBody Administrator admin){
		try {
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			Administrator res = adminRespository.save(admin);
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}
			else if(e instanceof DataIntegrityViolationException) {
				return new ResponseEntity<>("Username already taken.", HttpStatus.NOT_ACCEPTABLE);
			}
			else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
	}
	
	public AdministratorController() {
		// TODO Auto-generated constructor stub
	}
}
