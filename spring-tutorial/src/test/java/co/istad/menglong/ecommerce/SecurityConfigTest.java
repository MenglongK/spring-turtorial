package co.istad.menglong.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProductsWithoutTrailingSlash() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
               .andExpect(status().isOk()); // or whatever status except 401
    }

    @Test
    public void testProductsWithTrailingSlash() throws Exception {
        mockMvc.perform(get("/api/v1/products/"))
               .andExpect(status().isNotFound());
    }
}
