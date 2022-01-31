package fr.socgen.accounts.infrastructure.services;

import fr.socgen.accounts.domain.spi.AccountSpi;
import fr.socgen.accounts.infrastructure.entities.OperationEntity;
import fr.socgen.accounts.infrastructure.repositories.AccountRepository;

public class AccountSpiImpl implements AccountSpi {

  private final AccountRepository accountRepository = AccountRepository.getInstance();

  @Override
  public long computeBalance(String account) {
    return accountRepository.getAccount(account).getOperations().stream()
        .mapToLong(OperationEntity::getAmount)
        .sum();
  }

  @Override
  public void deposit(String customer, String account, int amount) {
    accountRepository.addOperation(account, amount);
  }
}
