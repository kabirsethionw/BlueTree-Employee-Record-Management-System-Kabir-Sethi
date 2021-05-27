package BlueTree.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BlueTree.entities.Employee;
import BlueTree.entities.Employee.Status;
import BlueTree.repositories.EmployeeRepository;
import BlueTree.utils.UpdateBody;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllEmployees(){
		try {
			Iterable<Employee> empIter = employeeRepository.findAll();
			List<Employee> empList = new ArrayList<Employee>();
			empIter.forEach(empList::add); // iterable to list
			System.out.println("res: "+empList.get(0));
			return new ResponseEntity<>(empList, HttpStatus.CREATED);
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addEmployee(@RequestBody Employee emp){
		try {
		
			if(emp.getDob().getYear()-LocalDate.now().getYear()<18) {
				System.out.println("hello");
				emp.setStatus(Status.NOT_WORKING);
				employeeRepository.save(emp);
				String message = "User of age 18 or under are not allowed.";
				return new ResponseEntity<>(message, HttpStatus.CONFLICT);
			}
			else {
				System.out.println("YEs :"+emp.getAddress());
				employeeRepository.save(emp);
				return new ResponseEntity<>(emp, HttpStatus.CREATED);
			}
		
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@GetMapping("/find")
	public ResponseEntity<?> findEmployee(@RequestParam String value, @RequestParam String field){
		System.out.println(field);
		try {
		if(field.equals("id")) {
			Optional<Employee> emp = employeeRepository.findById(Long.parseLong(value));
			return new ResponseEntity<>(emp, HttpStatus.OK);
		}else {
			Optional<List<Employee>> empList = employeeRepository.findByName(value);
			return new ResponseEntity<>(empList, HttpStatus.OK);
		}
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@PutMapping("/update")
	private ResponseEntity<?> updateEmployee(@RequestBody UpdateBody body){
		try {
			if(body.getField() == "dob" && Integer.parseInt(body.getValue().split("-")[0])-LocalDate.now().getYear()<18) {
				String statement = "Update employee set "+body.getField()+" = ?, updated_at = CAST(? AS DATE), status = 'NOT_WORKING' where id = ?";
				System.out.println("Here: "+body.getValue());
				jdbcTemplate.update(statement, body.getValue(), LocalDate.now(), Integer.parseInt(body.getId()));
				String message = "User of age 18 or under are not allowed.";
				return new ResponseEntity<>(message, HttpStatus.CONFLICT);
			}
			else {
				String statement = "Update employee set "+body.getField()+" = ?, updated_at = CAST(? AS DATE) where id = ?";
				System.out.println("Here: "+body.getValue());
				int rows = jdbcTemplate.update(statement, body.getValue(), LocalDate.now(), Integer.parseInt(body.getId()));
				System.out.println(rows);
				Optional<Employee> result = employeeRepository.findById(Long.parseLong(body.getId()));
				return new ResponseEntity<>(result.get(), HttpStatus.OK);
			}
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<?> removeEmploy(@RequestParam String id, @RequestParam String status){
		try {
		Optional<Employee> result = employeeRepository.findById(Long.parseLong(id));
		result.get().setStatus(Status.valueOf(status));
		Employee emp= employeeRepository.save(result.get());
		return new ResponseEntity<Object>(emp, HttpStatus.CREATED);
		}catch(Exception e) {
			if(e instanceof ConstraintViolationException) {
				throw e;
			}else {
				String message = "Internal Server Error, response status: 500";
				return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	public EmployeeController() {
		// TODO Auto-generated constructor stub
		
	}

}
