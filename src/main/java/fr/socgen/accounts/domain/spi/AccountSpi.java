package fr.socgen.accounts.domain.spi;

public interface AccountSpi {

  long computeBalance(String account);

  void deposit(String customer, String account, int amount);

  void withdraw(String customer, String account, int amount);
}
