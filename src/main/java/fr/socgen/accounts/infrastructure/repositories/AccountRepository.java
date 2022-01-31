package fr.socgen.accounts.infrastructure.repositories;

import fr.socgen.accounts.infrastructure.entities.AccountEntity;
import fr.socgen.accounts.infrastructure.entities.OperationEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

  private final Map<String, AccountEntity> accountEntities = new ConcurrentHashMap<>();

  private static final AccountRepository instance = new AccountRepository();

  private AccountRepository() {}

  public void clear() {
    accountEntities.clear();
  }

  public void addAccount(String accountNumber, String holder, int initialDeposit) {
    if (accountEntities.containsKey(accountNumber)) {
      throw new IllegalArgumentException("Account " + accountNumber + " already exists !");
    }

    final AccountEntity accountEntity =
        new AccountEntity().setNumber(accountNumber).setHolder(holder);

    if (initialDeposit > 0) {
      accountEntity.addOperation(
          new OperationEntity().setAmount(initialDeposit).setDateTime(LocalDateTime.now()));
    }

    accountEntities.put(accountNumber, accountEntity);
  }

  public void addOperation(String accountNumber, int amount) {
    accountEntities
        .get(accountNumber)
        .addOperation(new OperationEntity().setAmount(amount).setDateTime(LocalDateTime.now()));
  }

  public AccountEntity getAccount(String accountNumber) {
    return accountEntities.get(accountNumber);
  }

  public static AccountRepository getInstance() {
    return instance;
  }
}
