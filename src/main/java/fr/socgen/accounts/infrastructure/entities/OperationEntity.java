package fr.socgen.accounts.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class OperationEntity {

  private LocalDateTime dateTime;
  private long amount;
}
