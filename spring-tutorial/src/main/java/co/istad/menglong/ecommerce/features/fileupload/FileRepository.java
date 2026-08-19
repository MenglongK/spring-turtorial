package co.istad.menglong.ecommerce.features.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileUpload, Integer> {
    Optional<FileUpload> findByName(String name);
}
