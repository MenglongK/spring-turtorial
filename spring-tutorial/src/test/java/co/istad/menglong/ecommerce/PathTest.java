package co.istad.menglong.ecommerce;

import org.springframework.web.util.pattern.PathPatternParser;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.http.server.PathContainer;

public class PathTest {
    public static void main(String[] args) {
        PathPatternParser parser = new PathPatternParser();
        PathPattern pattern = parser.parse("/api/v1/products/**");
        
        System.out.println("Matches /api/v1/products: " + pattern.matches(PathContainer.parsePath("/api/v1/products")));
        System.out.println("Matches /api/v1/products/: " + pattern.matches(PathContainer.parsePath("/api/v1/products/")));
        System.out.println("Matches /api/v1/products/123: " + pattern.matches(PathContainer.parsePath("/api/v1/products/123")));
    }
}
