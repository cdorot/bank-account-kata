package fr.socgen.accounts.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class AccountStatementEntity {

  private LocalDate date;
  private final List<OperationEntity> operations = new ArrayList<>();
  private long balance = 0L;

  public AccountStatementEntity setOperations(List<OperationEntity> operations) {
    this.operations.clear();
    this.operations.addAll(operations);
    return this;
  }
}
