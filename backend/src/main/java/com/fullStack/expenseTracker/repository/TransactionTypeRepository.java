package com.fullStack.expenseTracker.repository;

import com.fullStack.expenseTracker.enums.ETransactionType;
import com.fullStack.expenseTracker.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
    boolean existsByTransactionTypeName(ETransactionType transactionTypeName);

    TransactionType findByTransactionTypeName(ETransactionType transactionTypeName);
}
