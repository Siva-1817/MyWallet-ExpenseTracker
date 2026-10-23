package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.BudgetRequest;
import com.fullStack.expenseTracker.exceptions.UserNotFoundException;
import com.fullStack.expenseTracker.exceptions.UserServiceLogicException;
import com.fullStack.expenseTracker.services.BudgetService;
import com.fullStack.expenseTracker.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mywallet/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<?>> createBudget(@RequestBody BudgetRequest budgetRequest,
                                                            @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, UserServiceLogicException {
        budgetRequest.setUserId(user.getId());
        return budgetService.createBudget(budgetRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponseDto<?>> getBudgetByMonth(@RequestParam int month,
                                                              @RequestParam long year,
                                                              @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException {
        return budgetService.getBudgetByMonth(user.getId(), month, year);
    }
}
