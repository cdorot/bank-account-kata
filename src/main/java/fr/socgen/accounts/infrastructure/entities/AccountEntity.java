package fr.socgen.accounts.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class AccountEntity {

  private String number;
  private String holder;
  private final List<OperationEntity> operations = Collections.synchronizedList(new ArrayList<>());

  public AccountEntity addOperation(OperationEntity operationEntity) {
    operations.add(operationEntity);
    return this;
  }
}
