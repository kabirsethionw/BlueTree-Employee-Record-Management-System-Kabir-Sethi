package BlueTree.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {

	public void uploadFile(MultipartFile file);
}
