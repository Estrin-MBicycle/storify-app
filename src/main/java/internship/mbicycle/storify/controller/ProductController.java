package internship.mbicycle.storify.controller;

import internship.mbicycle.storify.dto.ProductDTO;
import internship.mbicycle.storify.service.impl.ProductServiceImpl;
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

import java.util.List;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productService.findById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String name) {
        return ResponseEntity.status(200).body(productService.findByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductFromStore(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productService.findAllProductsFromStore(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(200).body(productService.saveProduct(productDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(201).body(productService.saveProduct(productDTO));
    }

    @DeleteMapping("/delete/{storeId}/{productId}")
    public ResponseEntity<Void> deleteProductFromStore(@PathVariable Long storeId,
                                                       @PathVariable Long productId) {
        productService.removeProductByStoreIdAndId(storeId, productId);
        return ResponseEntity.status(204).build();
    }
}
