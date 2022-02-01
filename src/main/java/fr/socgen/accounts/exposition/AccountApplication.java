package fr.socgen.accounts.exposition;

import fr.socgen.accounts.domain.model.AccountStatement;
import fr.socgen.accounts.domain.services.AccountService;
import fr.socgen.accounts.infrastructure.services.AccountSpiImpl;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class AccountApplication {

  private final AccountService accountService;

  public long computeBalance(String account) {
    return accountService.computeBalance(account);
  }

  public void deposit(String customer, String account, long amount) {
    accountService.deposit(customer, account, amount);
  }

  public void withdraw(String customer, String account, long amount) {
    accountService.withdraw(customer, account, amount);
  }

  public AccountStatement requestAccountStatement(
      String customer, String accountNumber, LocalDate date) {
    return accountService.requestAccountStatement(accountNumber, date);
  }

  public static AccountApplication newAccountApplication() {
    return new AccountApplication(new AccountService(new AccountSpiImpl()));
  }
}
