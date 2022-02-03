package fr.socgen.accounts.domain.spi;

import fr.socgen.accounts.domain.model.AccountStatement;
import fr.socgen.accounts.domain.model.Operation;

import java.time.LocalDate;
import java.util.List;

public interface AccountSpi {

  long computeBalance(String account);

  void deposit(String customer, String account, long amount);

  void withdraw(String customer, String account, long amount);

  List<Operation> getOperationsPerformedBeforeDate(String accountNumber, LocalDate date);

  void addAccountStatement(String accountNumber, AccountStatement accountStatement);
}
