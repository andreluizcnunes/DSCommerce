package com.api.dscommerce.controllers;

import com.api.dscommerce.dto.ProductDTO;
import com.api.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO dto = productService.getProductById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    private ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<ProductDTO> dto = productService.getAllProducts(pageable);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        productDTO = productService.createProduct(productDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productDTO = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
