package fr.socgen.accounts.domain.services;

import fr.socgen.accounts.domain.spi.AccountSpi;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountService {

  private final AccountSpi accountSpi;

  public long computeBalance(String account) {
    return accountSpi.computeBalance(account);
  }

  public void deposit(String customer, String account, int amount) {
    requirePositiveNumber(amount);

    accountSpi.deposit(customer, account, amount);
  }

  public void withdraw(String customer, String account, int amount) {
    requirePositiveNumber(amount);

    accountSpi.withdraw(customer, account, amount);
  }

  private void requirePositiveNumber(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("The amount to deposit must be positive !");
    }
  }
}
