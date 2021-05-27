package BlueTree.services;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import BlueTree.entities.Employee;

@Service
public class FileUploaderServiceImpli implements FileUploaderService {

	
	public List<Employee> invoiceExcelReaderService() {
		return null;
	}
	
    public String uploadDir = "C:/Users/Kabir Sethi/eclipse-workspace/BlueTree-Intership-Kabir/src/main/resources";

    public void uploadFile(MultipartFile file) {

        try {
            Path copyLocation = Paths
                .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
                + ". Please try again!");
        }
    }
}