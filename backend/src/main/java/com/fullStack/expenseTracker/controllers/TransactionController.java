package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.exceptions.*;
import com.fullStack.expenseTracker.services.TransactionService;
import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.TransactionRequestDto;
import com.fullStack.expenseTracker.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mywallet/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponseDto<?>> addTransaction(@RequestBody @Valid TransactionRequestDto transactionRequestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, CategoryNotFoundException, TransactionServiceLogicException {
        return transactionService.addTransaction(transactionRequestDto, user.getEmail());
    }

    @GetMapping("/getByUser")
    public ResponseEntity<ApiResponseDto<?>> getTransactionsByUser(@RequestParam(name = "email", required = false) String email,
                                                                   @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                                   @RequestParam(name = "searchKey", defaultValue = "") String searchKey,
                                                                   @RequestParam(name = "sortField", defaultValue = "date") String sortField,
                                                                   @RequestParam(name = "sortDirec", defaultValue = "DESC") String sortDirec,
                                                                   @RequestParam(name = "transactionType", defaultValue = "") String transactionType,
                                                                   @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, TransactionServiceLogicException {
        return transactionService.getTransactionsByUser(user.getEmail(), pageNumber, pageSize, searchKey, sortField, sortDirec, transactionType);
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponseDto<?>> getTransactionById(@RequestParam Long id,
                                                                @AuthenticationPrincipal UserDetailsImpl user)
            throws TransactionNotFoundException {
        return transactionService.getTransactionById(id, user.getEmail());

    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<?>> updateTransaction(@RequestParam Long transactionId,
                                                               @RequestBody @Valid TransactionRequestDto transactionRequestDto,
                                                               @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, CategoryNotFoundException, TransactionNotFoundException, TransactionServiceLogicException {
        return transactionService.updateTransaction(transactionId, transactionRequestDto, user.getEmail());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<?>> deleteTransaction(@RequestParam Long transactionId,
                                                               @AuthenticationPrincipal UserDetailsImpl user)
            throws TransactionNotFoundException, TransactionServiceLogicException {
        return transactionService.deleteTransaction(transactionId, user.getEmail());

    }

}
