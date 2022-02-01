Feature: Request an account statement
  In order to check my operations
           As a bank client
    I want to see the history (operation, date, amount, balance) of my operations

  Background:
    Given The application is in its initial state
      And The date of the day is the "2022-02-01"

  Scenario: Check operations
    Given a customer "Jean" has open an account "FR1402462222101055555M02606" without initial deposit
      And "Jean" has made a deposit of 30 € on "2022-01-02" in account "FR1402462222101055555M02606"
      And "Jean" has made a withdrawal of 15 € on "2022-01-07" in account "FR1402462222101055555M02606"
     When "Jean" requests a statement for account "FR1402462222101055555M02606"
     Then the account "FR1402462222101055555M02606" has a balance of 15 €
      And a statement is generated for account "FR1402462222101055555M02606"
      And the account statement generated for account "FR1402462222101055555M02606" is dated "2022-02-01"
      And the account statement generated for account "FR1402462222101055555M02606" has a balance equal to 15 €
      And the following operations are listed on the statement of account "FR1402462222101055555M02606" :
            | date        | operation    | amount |
            | 2022-01-07  | WITHDRAWAL   | 15     |
            | 2022-01-02  | DEPOSIT      | 30     |
