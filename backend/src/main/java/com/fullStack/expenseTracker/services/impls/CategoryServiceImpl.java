package com.fullStack.expenseTracker.services.impls;

import com.fullStack.expenseTracker.services.CategoryService;
import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.enums.ApiResponseStatus;
import com.fullStack.expenseTracker.exceptions.CategoryNotFoundException;
import com.fullStack.expenseTracker.models.Category;
import com.fullStack.expenseTracker.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<ApiResponseDto<?>> getCategories() {
        return ResponseEntity.ok(
                new ApiResponseDto<>(
                        ApiResponseStatus.SUCCESS,
                        HttpStatus.OK,
                        categoryRepository.findAll()
                )
        );
    }

    @Override
    public Category getCategoryById(int id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id" + id));
    }

}
