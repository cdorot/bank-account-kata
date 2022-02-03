package fr.socgen.accounts.infrastructure.services;

import fr.socgen.accounts.domain.model.AccountStatement;
import fr.socgen.accounts.domain.model.Operation;
import fr.socgen.accounts.domain.spi.AccountSpi;
import fr.socgen.accounts.infrastructure.entities.OperationEntity;
import fr.socgen.accounts.infrastructure.mappers.AccountStatementEntityMapper;
import fr.socgen.accounts.infrastructure.mappers.OperationEntityMapper;
import fr.socgen.accounts.infrastructure.repositories.AccountRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AccountSpiImpl implements AccountSpi {

  private final AccountRepository accountRepository = AccountRepository.getInstance();

  @Override
  public long computeBalance(String account) {
    return accountRepository.getAccount(account).getOperations().stream()
        .mapToLong(OperationEntity::getAmount)
        .sum();
  }

  @Override
  public void deposit(String customer, String account, long amount) {
    accountRepository.addOperation(account, amount);
  }

  @Override
  public void withdraw(String customer, String account, long amount) {
    accountRepository.addOperation(account, -amount);
  }

  @Override
  public List<Operation> getOperationsPerformedBeforeDate(String accountNumber, LocalDate date) {
    return accountRepository.getOperationsPerformedBeforeDate(accountNumber, date).stream()
        .map(OperationEntityMapper.INSTANCE::mapToOperation)
        .collect(Collectors.toList());
  }

  @Override
  public void addAccountStatement(String accountNumber, AccountStatement accountStatement) {
    accountRepository.addAccountStatement(
        accountNumber,
        AccountStatementEntityMapper.INSTANCE.mapToAccountStatementEntity(accountStatement));
  }
}
