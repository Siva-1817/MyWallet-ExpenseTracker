package com.fullStack.expenseTracker.services;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.exceptions.CategoryNotFoundException;
import com.fullStack.expenseTracker.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    ResponseEntity<ApiResponseDto<?>> getCategories();

    Category getCategoryById(int id) throws CategoryNotFoundException;

}
