Feature: Deposit money in an account
  In order to save money
           As a bank client
    I want to make a deposit in my account

  Background:
    Given The application is in its initial state

  Scenario Outline: Deposit money
    Given a customer "<customer>" has open an account "<account>" with an initial deposit of <initial_balance> €
     When "<customer>" deposits <amount> € in account "<account>"
     Then the account "<account>" has a balance of <new_balance> €

    Examples:
      | customer | account                     | initial_balance | amount | new_balance |
      | Jean     | FR1402462222101055555M02606 | 10              | 100    | 110         |
