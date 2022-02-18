package internship.mbicycle.storify.controller;

import java.util.List;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsFromStore(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getAllProductsFromStore(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @DeleteMapping("/{storeId}/{productId}")
    public ResponseEntity<?> deleteProductFromStore(@PathVariable Long storeId,
                                                    @PathVariable Long productId) {
        productService.removeProductByStoreIdAndId(storeId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteAllProductsFromStore(@PathVariable Long storeId) {
        productService.removeAllProductsByStoreId(storeId);
        return ResponseEntity.noContent().build();
    }
}
