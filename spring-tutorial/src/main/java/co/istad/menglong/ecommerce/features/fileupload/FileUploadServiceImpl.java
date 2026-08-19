package co.istad.menglong.ecommerce.features.fileupload;

import co.istad.menglong.ecommerce.features.fileupload.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileRepository fileRepository;

    @Value("${file-upload.server-path}")
    private String serverPath;

    @Value("${file-upload.base-uri}")
    private String baseUri;


    @Override
    public List<FileResponse> uploadMultiple(List<MultipartFile> files) {
        return files.stream()
                .map(this::saveFile)
                .collect(Collectors.toList());
    }

    @Override
    public FileResponse delete(String name) {
        FileUpload fileUpload = fileRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));
        fileRepository.delete(fileUpload);
        return FileResponse.builder()
                .name(fileUpload.getName())
//                .extension(fileUpload.getExtension())
//                .mediaType(fileUpload.getMediaType())
//                .size(fileUpload.getSize())
//                .uri(baseUri + "/" + fileUpload.getName() + "." + fileUpload.getExtension())
                .build();
    }


    @Override
    public FileResponse upload(MultipartFile file) {
        return saveFile(file);
    }

    @Override
    public Page<FileResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        Page<FileUpload> fileUploads = fileRepository.findAll(pageable);
        return fileUploads.map(fileUpload -> FileResponse.builder()
                .name(fileUpload.getName())
                .extension(fileUpload.getExtension())
                .mediaType(fileUpload.getMediaType())
                .size(fileUpload.getSize())
                .uri(baseUri + "/" + fileUpload.getName() + "." + fileUpload.getExtension())
                .build());
    }


    private FileResponse saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        String fileExt = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        // Create path object
        Path path = Paths.get(String.format("%s%s.%s", serverPath, fileName, fileExt));
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(fileName);
        fileUpload.setExtension(fileExt);
        fileUpload.setMediaType(file.getContentType());
        fileUpload.setSize(file.getSize());

        fileRepository.save(fileUpload);

        return FileResponse.builder()
                .name(fileUpload.getName())
                .extension(fileUpload.getExtension())
                .mediaType(fileUpload.getMediaType())
                .size(fileUpload.getSize())
                .uri(baseUri + "/" + fileUpload.getName() + "." + fileUpload.getExtension())
                .build();
    }

}