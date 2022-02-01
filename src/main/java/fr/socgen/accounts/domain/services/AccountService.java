package fr.socgen.accounts.domain.services;

import fr.socgen.accounts.domain.model.AccountStatement;
import fr.socgen.accounts.domain.model.Operation;
import fr.socgen.accounts.domain.spi.AccountSpi;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class AccountService {

  private final AccountSpi accountSpi;

  public long computeBalance(String account) {
    return accountSpi.computeBalance(account);
  }

  public void deposit(String customer, String account, long amount) {
    requirePositiveNumber(amount);

    accountSpi.deposit(customer, account, amount);
  }

  public void withdraw(String customer, String account, long amount) {
    requirePositiveNumber(amount);

    accountSpi.withdraw(customer, account, amount);
  }

  public AccountStatement requestAccountStatement(String accountNumber, LocalDate date) {
    requireDateTodayOrInThePast(date);

    final List<Operation> operations =
        accountSpi.getOperationsPerformedBeforeDate(accountNumber, date).stream()
            .sorted(Comparator.comparing(Operation::dateTime).reversed())
            .toList();
    final long balance = operations.stream().mapToLong(Operation::amount).sum();

    final AccountStatement accountStatement =
        new AccountStatement().setDate(date).setBalance(balance).setOperation(operations);

    accountSpi.addAcountStatement(accountNumber, accountStatement);

    return accountStatement;
  }

  private static void requireDateTodayOrInThePast(LocalDate date) {
    if (null == date || date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("The date should be filled with a date in the past !");
    }
  }

  private static void requirePositiveNumber(long amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("The amount to deposit must be positive !");
    }
  }
}
