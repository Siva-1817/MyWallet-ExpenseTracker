package com.fullStack.expenseTracker.dataSeeders;

import com.fullStack.expenseTracker.enums.ETransactionType;
import com.fullStack.expenseTracker.models.Category;
import com.fullStack.expenseTracker.models.TransactionType;
import com.fullStack.expenseTracker.repository.CategoryRepository;
import com.fullStack.expenseTracker.repository.TransactionTypeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CategoryDataSeeder implements ApplicationRunner {
    private final CategoryRepository categoryRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    public CategoryDataSeeder(CategoryRepository categoryRepository,
                              TransactionTypeRepository transactionTypeRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedCategories(ETransactionType.TYPE_EXPENSE, List.of(
                "Food", "Transportation", "Housing", "Utilities", "Health",
                "Shopping", "Entertainment", "Other Expense"
        ));
        seedCategories(ETransactionType.TYPE_INCOME, List.of(
                "Salary", "Freelance", "Investment", "Gift", "Other Income"
        ));
    }

    private void seedCategories(ETransactionType type, List<String> names) {
        TransactionType transactionType = transactionTypeRepository.findByTransactionTypeName(type);
        if (transactionType == null) {
            throw new IllegalStateException("Transaction type has not been seeded: " + type);
        }

        for (String name : names) {
            if (!categoryRepository.existsByCategoryNameAndTransactionType(name, transactionType)) {
                categoryRepository.save(new Category(name, transactionType, true));
            }
        }
    }
}
