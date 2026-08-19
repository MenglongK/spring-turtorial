package co.istad.menglong.ecommerce.features.fileupload;

import co.istad.menglong.ecommerce.features.fileupload.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping
    public FileResponse upload(
            @RequestPart MultipartFile file) {
        return fileUploadService.upload(file);
    }

    @PostMapping("/multiple")
    public List<FileResponse> uploadMultiple(
            @RequestPart List<MultipartFile> files
    ) {
        return fileUploadService.uploadMultiple(files);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public FileResponse delete(
            @PathVariable String name) {
        return fileUploadService.delete(name);
    }

    @GetMapping
    public Page<FileResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        return fileUploadService.findAll(page, size);
    }
}
