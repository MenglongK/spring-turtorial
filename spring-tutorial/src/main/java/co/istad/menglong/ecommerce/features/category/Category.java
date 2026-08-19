package co.istad.menglong.ecommerce.features.category;

import co.istad.menglong.ecommerce.features.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// Make POJO = Plain Old Java Object
@Getter
@Setter
@NoArgsConstructor
// Make JPA Entity
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String name; // Default String = 255 characters

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 100)
    private String icon;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}