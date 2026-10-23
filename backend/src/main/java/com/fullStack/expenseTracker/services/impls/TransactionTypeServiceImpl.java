package com.fullStack.expenseTracker.services.impls;

import com.fullStack.expenseTracker.services.TransactionTypeService;
import com.fullStack.expenseTracker.models.TransactionType;
import com.fullStack.expenseTracker.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionTypeServiceImpl implements TransactionTypeService {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Override
    public List<TransactionType> getAllTransactions() {
        return transactionTypeRepository.findAll();
    }

}
