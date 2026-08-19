package co.istad.menglong.ecommerce.features.fileupload;

import co.istad.menglong.ecommerce.features.fileupload.dto.FileResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    FileResponse upload(MultipartFile file);

    Page<FileResponse> findAll(int page, int size);

    List<FileResponse> uploadMultiple(List<MultipartFile> files);

    FileResponse delete(String name);
}
