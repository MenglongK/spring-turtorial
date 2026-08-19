package co.istad.menglong.ecommerce.features.fileupload;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "files")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false, length = 6)
    private String extension;
    @Column(nullable = false, length = 15)
    private String mediaType;
    @Column(nullable = false)
    private Long size;
}
