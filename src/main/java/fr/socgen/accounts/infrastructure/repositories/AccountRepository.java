package fr.socgen.accounts.infrastructure.repositories;

import fr.socgen.accounts.infrastructure.entities.AccountEntity;
import fr.socgen.accounts.infrastructure.entities.AccountStatementEntity;
import fr.socgen.accounts.infrastructure.entities.OperationEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AccountRepository {

  private final Map<String, AccountEntity> accountEntities = new ConcurrentHashMap<>();

  private static final AccountRepository instance = new AccountRepository();

  private AccountRepository() {}

  public void clear() {
    accountEntities.clear();
  }

  public void addAccount(String accountNumber, String holder, long initialDeposit) {
    requireAccountDoesNotExists(accountNumber);

    accountEntities.put(
        accountNumber, new AccountEntity().setNumber(accountNumber).setHolder(holder));

    if (initialDeposit > 0) {
      addOperation(accountNumber, initialDeposit);
    }
  }

  public void addOperation(String accountNumber, long amount) {
    accountEntities
        .get(accountNumber)
        .addOperation(new OperationEntity().setAmount(amount).setDateTime(LocalDateTime.now()));
  }

  public AccountEntity getAccount(String accountNumber) {
    return accountEntities.get(accountNumber);
  }

  public List<OperationEntity> getOperationsPerformedBeforeDate(
      String accountNumber, LocalDate date) {
    return accountEntities.get(accountNumber).getOperations().stream()
        .filter(operationEntity -> operationEntity.getDateTime().isBefore(date.atStartOfDay()))
        .collect(Collectors.toList());
  }

  public void addAccountStatement(
      String accountNumber, AccountStatementEntity accountStatementEntity) {
    accountEntities.get(accountNumber).addStatement(accountStatementEntity);
  }

  public static AccountRepository getInstance() {
    return instance;
  }

  private void requireAccountDoesNotExists(String accountNumber) {
    if (accountEntities.containsKey(accountNumber)) {
      throw new IllegalArgumentException("Account " + accountNumber + " already exists !");
    }
  }
}
