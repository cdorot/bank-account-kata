package fr.socgen.accounts.domain.model;

import lombok.Getter;

@Getter
public class Account {

  private final String number;
  private final String holder;

  public Account(String number, String holder) {
    this.number = number;
    this.holder = holder;
  }
}
