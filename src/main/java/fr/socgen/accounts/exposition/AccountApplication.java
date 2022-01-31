package fr.socgen.accounts.exposition;

import fr.socgen.accounts.domain.services.AccountService;
import fr.socgen.accounts.infrastructure.services.AccountSpiImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountApplication {

  private final AccountService accountService;

  public static AccountApplication newAccountApplication() {
    return new AccountApplication(new AccountService(new AccountSpiImpl()));
  }

  public long computeBalance(String account) {
    return accountService.computeBalance(account);
  }

  public void deposit(String customer, String account, int amount) {
    accountService.deposit(customer, account, amount);
  }
}
