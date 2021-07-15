package BlueTree.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import BlueTree.entities.Employee;
import BlueTree.repositories.EmployeeRepository;
import BlueTree.services.ExcelHelper;


@RestController
@RequestMapping("/excelUpload")
public class ExcelFileUploadController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostMapping(value="/save", consumes={"multipart/form-data"})
	public ResponseEntity<?> saveExcelFile(@RequestParam("file") MultipartFile file) {
		System.out.println("Here, Find me! File size: "+file.getSize());
		if(ExcelHelper.hasExcelFormat(file)) {
			try {
				
				System.out.println(file.getName());
				List<Employee> employees = ExcelHelper.excelToEmloyees(file);
				employeeRepository.saveAll(employees);
				System.out.println("File uploaded successfully.");
				return new ResponseEntity<>(employees, HttpStatus.CREATED);
			}catch(Exception e) {
				return new ResponseEntity<> ("Failed to store excel file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<> ("Invalid file format", HttpStatus.BAD_REQUEST);
		}
		
	}
	

    
}