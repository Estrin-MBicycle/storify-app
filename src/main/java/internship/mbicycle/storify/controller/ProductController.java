package internship.mbicycle.storify.controller;

import javax.validation.Valid;
import java.security.Principal;
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
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductDTOById(id);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/stores/{id}")
    public List<ProductDTO> getAllProductsFromStore(@PathVariable Long id) {
        return productService.getAllProductsFromStore(id);
    }

    @PostMapping("/{storeId}")
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO,
                                    @PathVariable Long storeId,
                                    @ApiIgnore Principal principal) {
        return productService.saveProduct(productDTO, storeId, principal);
    }

    @PutMapping("/{id}/{storeId}")
    public ProductDTO updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                    @PathVariable Long id,
                                    @PathVariable Long storeId,
                                    @ApiIgnore Principal principal) {
        return productService.updateProduct(productDTO, id, storeId, principal);
    }

    @DeleteMapping("/{storeId}/{productId}")
    public ResponseEntity<?> deleteProductFromStore(@PathVariable Long storeId,
                                                    @PathVariable Long productId,
                                                    @ApiIgnore Principal principal) {
        productService.removeProductByStoreIdAndId(storeId, productId, principal);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteAllProductsFromStore(@PathVariable Long storeId,
                                                        @ApiIgnore Principal principal) {
        productService.removeAllProductsByStoreId(principal, storeId);
        return ResponseEntity.noContent().build();
    }
}
