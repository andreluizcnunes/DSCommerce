package com.api.dscommerce.services;

import com.api.dscommerce.dto.CategoryDTO;
import com.api.dscommerce.dto.ProductDTO;
import com.api.dscommerce.dto.ProductMinDTO;
import com.api.dscommerce.entities.Category;
import com.api.dscommerce.entities.Product;
import com.api.dscommerce.repositories.ProductRepository;
import com.api.dscommerce.services.exceptions.DatabaseException;
import com.api.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Optional<Product> result = Optional.ofNullable(productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso nao encotrado")
        ));
        Product product = result.get();
        ProductDTO productDTO = new ProductDTO(product);

        return productDTO;
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> getAllProducts(String name, Pageable pageable) {
        Page<Product> result = productRepository.searchByName(name, pageable);
        return result.map(x -> new ProductMinDTO(x));
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        copyDtoToEntity(productDTO, product);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        try {
            Product product = productRepository.getReferenceById(id);
            copyDtoToEntity(productDTO, product);
            product = productRepository.save(product);
            return new ProductDTO(product);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso nao encotrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso nao encotrado");
        }
        try {
            productRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }


    private void copyDtoToEntity(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());

        product.getCategories().clear();

        for (CategoryDTO categoryDTO : productDTO.getCategories()) {
            Category category = new Category();

            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());

            product.getCategories().add(category);
        }
    }


}
