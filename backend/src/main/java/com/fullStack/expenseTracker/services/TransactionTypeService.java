package com.fullStack.expenseTracker.services;

import com.fullStack.expenseTracker.models.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionTypeService {
    List<TransactionType> getAllTransactions();

}
