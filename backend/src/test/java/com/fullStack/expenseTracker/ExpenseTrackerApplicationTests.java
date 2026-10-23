package com.fullStack.expenseTracker;

import com.fullStack.expenseTracker.enums.ETransactionType;
import com.fullStack.expenseTracker.dto.requests.LocalPasswordResetRequestDto;
import com.fullStack.expenseTracker.dto.requests.TransactionRequestDto;
import com.fullStack.expenseTracker.models.Category;
import com.fullStack.expenseTracker.models.User;
import com.fullStack.expenseTracker.repository.CategoryRepository;
import com.fullStack.expenseTracker.repository.TransactionRepository;
import com.fullStack.expenseTracker.repository.UserRepository;
import com.fullStack.expenseTracker.services.TransactionService;
import com.fullStack.expenseTracker.services.AuthService;
import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class ExpenseTrackerApplicationTests {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void seededCategoriesAllowAuthenticatedIncomeAndExpenseTransactionPersistence() throws Exception {
		Category expenseCategory = categoryRepository.findAll().stream()
				.filter(category -> category.isEnabled()
						&& category.getTransactionType().getTransactionTypeName() == ETransactionType.TYPE_EXPENSE)
				.findFirst()
				.orElseThrow();
		Category incomeCategory = categoryRepository.findAll().stream()
				.filter(category -> category.isEnabled()
						&& category.getTransactionType().getTransactionTypeName() == ETransactionType.TYPE_INCOME)
				.findFirst()
				.orElseThrow();
		User user = userRepository.saveAndFlush(new User(
				"wallet-" + UUID.randomUUID().toString().substring(0, 8),
				"wallet-" + UUID.randomUUID() + "@example.com",
				"bcrypt-test-hash",
				true
		));

		ApiResponseDto<?> expenseResponse = transactionService.addTransaction(new TransactionRequestDto(
				"ignored-client@example.com", expenseCategory.getCategoryId(), "Test expense", 12.50, LocalDate.now()
		), user.getEmail()).getBody();
		ApiResponseDto<?> incomeResponse = transactionService.addTransaction(new TransactionRequestDto(
				"ignored-client@example.com", incomeCategory.getCategoryId(), "Test income", 25.00, LocalDate.now()
		), user.getEmail()).getBody();

		Assertions.assertEquals("SUCCESS", expenseResponse.getStatus().name());
		Assertions.assertEquals("SUCCESS", incomeResponse.getStatus().name());
		Assertions.assertEquals(2, transactionRepository.findByUser(user.getEmail(),
				org.springframework.data.domain.PageRequest.of(0, 10),
				"",
				"").getTotalElements());
	}

	@Test
	@Transactional
	void localDemoPasswordResetUpdatesOnlyTheAccountMatchingTheRegisteredEmail() throws Exception {
		String email = "reset-" + UUID.randomUUID() + "@example.com";
		User targetUser = userRepository.saveAndFlush(new User(
				"reset-" + UUID.randomUUID().toString().substring(0, 8),
				email,
				passwordEncoder.encode("old-password"),
				true
		));
		User otherUser = userRepository.saveAndFlush(new User(
				"other-" + UUID.randomUUID().toString().substring(0, 8),
				"other-" + UUID.randomUUID() + "@example.com",
				passwordEncoder.encode("other-password"),
				true
		));
		LocalPasswordResetRequestDto request = new LocalPasswordResetRequestDto();
		request.setEmail(email);
		request.setNewPassword("new-password");
		request.setConfirmPassword("new-password");

		ApiResponseDto<?> response = authService.resetPasswordForLocalDemo(request).getBody();

		User updatedUser = userRepository.findByEmail(email).orElseThrow();
		User unchangedUser = userRepository.findByEmail(otherUser.getEmail()).orElseThrow();
		Assertions.assertEquals("SUCCESS", response.getStatus().name());
		Assertions.assertTrue(passwordEncoder.matches("new-password", updatedUser.getPassword()));
		Assertions.assertFalse(passwordEncoder.matches("new-password", unchangedUser.getPassword()));
		Assertions.assertFalse(response.getResponse().toString().contains(updatedUser.getPassword()));
		Assertions.assertNotEquals(targetUser.getPassword(), "new-password");
	}
}
