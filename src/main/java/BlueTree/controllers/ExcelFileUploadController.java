package BlueTree.controllers;


import java.util.List;

import org.apache.poi.poifs.crypt.dsig.services.RevocationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import BlueTree.entities.Employee;
import BlueTree.repositories.EmployeeRepository;
import BlueTree.services.ExcelDataService;
import BlueTree.services.FileUploaderService;

@Controller
public class ExcelFileUploadController {
	
	@Autowired
	FileUploaderService fileService;
	
	@Autowired
	ExcelDataService excelservice;
	
	@Autowired
	EmployeeRepository repo;
	
	@GetMapping("/")
    public String index() {
        return "uploadPage";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    	try {
    		 fileService.uploadFile(file);

    	        redirectAttributes.addFlashAttribute("message",
    	            "You have successfully uploaded '"+ file.getOriginalFilename()+"' !");
    	        
    	        List<Employee> excelDataAsList = excelservice.getExcelDataAsList();
    	    	int noOfRecords = excelservice.saveExcelData(excelDataAsList);
    	    	return "BlueTree-success";
    	}catch(Exception e) {
    		e.printStackTrace();
    		return e.getMessage();
    	}
        
    }
    
}