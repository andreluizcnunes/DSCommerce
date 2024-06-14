package com.api.dscommerce.controllers;

import com.api.dscommerce.dto.CategoryDTO;
import com.api.dscommerce.dto.ProductDTO;
import com.api.dscommerce.dto.ProductMinDTO;
import com.api.dscommerce.services.CategoryService;
import com.api.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllProducts() {
        List<CategoryDTO> dto = categoryService.getAllCategory();
        return ResponseEntity.ok(dto);
    }
}
