package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.services.ReportService;
import com.fullStack.expenseTracker.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mywallet/report")
public class ReportController {

    @Autowired
    ReportService reportService;


    @GetMapping("/getTotalIncomeOrExpense")
    public ResponseEntity<ApiResponseDto<?>> getTotalIncomeOrExpense(@RequestParam int transactionTypeId,
                                                                     @RequestParam int month,
                                                                     @RequestParam int year,
                                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        return reportService.getTotalByTransactionTypeAndUser(user.getId(), transactionTypeId, month, year);
    }

    @GetMapping("/getTotalNoOfTransactions")
    public ResponseEntity<ApiResponseDto<?>> getTotalNoOfTransactions(@RequestParam int month,
                                                                      @RequestParam int year,
                                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return reportService.getTotalNoOfTransactionsByUser(user.getId(), month, year);
    }

    @GetMapping("/getTotalByCategory")
    public ResponseEntity<ApiResponseDto<?>> getTotalByCategory(@RequestParam int categoryId,
                                                                @RequestParam int month,
                                                                @RequestParam int year,
                                                                @AuthenticationPrincipal UserDetailsImpl user) {
        return reportService.getTotalExpenseByCategoryAndUser(user.getEmail(), categoryId, month, year);
    }

    @GetMapping("/getMonthlySummaryByUser")
    public ResponseEntity<ApiResponseDto<?>> getMonthlySummaryByUser(@AuthenticationPrincipal UserDetailsImpl user) {
        return reportService.getMonthlySummaryByUser(user.getEmail());
    }

}
