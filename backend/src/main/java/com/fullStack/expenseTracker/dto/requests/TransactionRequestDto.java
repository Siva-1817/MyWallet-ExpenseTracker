package com.fullStack.expenseTracker.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class TransactionRequestDto {

    private String userEmail;

    @Positive(message = "Category id must be positive!")
    private int categoryId;

    @NotBlank(message = "Description is required!")
    @Size(max = 50, message = "Description can have atmost 50 characters!")
    private String description;

    @NotNull(message = "Amount is required!")
    @Positive(message = "Amount must be greater than zero!")
    private Double amount;

    @NotNull(message = "Date is required!")
    private LocalDate date;

}
