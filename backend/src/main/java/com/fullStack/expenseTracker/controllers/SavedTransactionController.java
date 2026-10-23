package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.SavedTransactionRequestDto;
import com.fullStack.expenseTracker.exceptions.TransactionNotFoundException;
import com.fullStack.expenseTracker.exceptions.UserNotFoundException;
import com.fullStack.expenseTracker.exceptions.UserServiceLogicException;
import com.fullStack.expenseTracker.services.SavedTransactionService;
import com.fullStack.expenseTracker.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mywallet/saved")
public class SavedTransactionController {

    @Autowired
    private SavedTransactionService savedTransactionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<?>> createSavedTransaction(@RequestBody SavedTransactionRequestDto requestDto,
                                                                     @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException, UserNotFoundException{
        return savedTransactionService.createSavedTransaction(requestDto, user.getId());
    }

    @GetMapping("/add")
    public ResponseEntity<ApiResponseDto<?>> addSavedTransaction(@RequestParam("id") long id,
                                                                  @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException, TransactionNotFoundException {
        return savedTransactionService.addSavedTransaction(id, user.getId());
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponseDto<?>> editSavedTransaction(@RequestParam("id") long id, @RequestBody SavedTransactionRequestDto requestDto,
                                                                   @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException, TransactionNotFoundException {
        return savedTransactionService.editSavedTransaction(id, requestDto, user.getId());
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponseDto<?>> deleteSavedTransaction(@RequestParam("id") long id,
                                                                     @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException, TransactionNotFoundException {
        return savedTransactionService.deleteSavedTransaction(id, user.getId());
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponseDto<?>> getAllTransactionsByUser(@RequestParam(required = false) Long id,
                                                                      @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException, UserNotFoundException {
        return savedTransactionService.getAllTransactionsByUser(user.getId());
    }

    @GetMapping("/month")
    public ResponseEntity<ApiResponseDto<?>> getAllTransactionsByUserAndMonth(@RequestParam(required = false) Long id,
                                                                              @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException, UserNotFoundException {
        return savedTransactionService.getAllTransactionsByUserAndMonth(user.getId());
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponseDto<?>> getAllTransactionsById(@RequestParam("id") long id,
                                                                    @AuthenticationPrincipal UserDetailsImpl user)
            throws UserServiceLogicException, TransactionNotFoundException {
        return savedTransactionService.getSavedTransactionById(id, user.getId());
    }

    @GetMapping("/skip")
    public ResponseEntity<ApiResponseDto<?>> skipSavedTransaction(@RequestParam("id") long id,
                                                                  @AuthenticationPrincipal UserDetailsImpl user)
            throws TransactionNotFoundException, UserServiceLogicException {
        return savedTransactionService.skipSavedTransaction(id, user.getId());
    }
}
