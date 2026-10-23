package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.services.CategoryService;
import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mywallet/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponseDto<?>> getAllCategories() {
        return categoryService.getCategories();
    }
}

