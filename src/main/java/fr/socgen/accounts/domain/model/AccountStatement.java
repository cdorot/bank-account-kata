package fr.socgen.accounts.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class AccountStatement {

  private LocalDate date;
  private long balance = 0L;
  private final List<Operation> operations = new ArrayList<>();

  public AccountStatement setOperation(List<Operation> operations) {
    this.operations.clear();
    this.operations.addAll(operations);
    return this;
  }
}
