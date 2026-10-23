package com.fullStack.expenseTracker.services;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.TransactionRequestDto;
import com.fullStack.expenseTracker.exceptions.CategoryNotFoundException;
import com.fullStack.expenseTracker.exceptions.TransactionNotFoundException;
import com.fullStack.expenseTracker.exceptions.TransactionServiceLogicException;
import com.fullStack.expenseTracker.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {


    ResponseEntity<ApiResponseDto<?>> addTransaction(TransactionRequestDto transactionRequestDto, String userEmail)
            throws UserNotFoundException, CategoryNotFoundException, TransactionServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getTransactionById(Long TransactionId, String userEmail)
            throws TransactionNotFoundException;

    ResponseEntity<ApiResponseDto<?>> updateTransaction(Long transactionId, TransactionRequestDto transactionRequestDto, String userEmail)
            throws TransactionNotFoundException, UserNotFoundException, CategoryNotFoundException, TransactionServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> deleteTransaction(Long transactionId, String userEmail) throws TransactionNotFoundException, TransactionServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getTransactionsByUser(String email, int pageNumber, int pageSize, String searchKey, String sortField, String sortDirec, String transactionType) throws UserNotFoundException, TransactionServiceLogicException;

}
