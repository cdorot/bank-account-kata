package fr.socgen.accounts.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Accessors(chain = true)
public class AccountEntity {

  private String number;
  private String holder;
  private final List<OperationEntity> operations = Collections.synchronizedList(new ArrayList<>());
  private final Map<LocalDate, AccountStatementEntity> statements = new ConcurrentHashMap<>();

  public AccountEntity addOperation(OperationEntity operationEntity) {
    operations.add(operationEntity);
    return this;
  }

  public AccountEntity addStatement(AccountStatementEntity accountStatementEntity) {
    statements.put(accountStatementEntity.getDate(), accountStatementEntity);
    return this;
  }
}
