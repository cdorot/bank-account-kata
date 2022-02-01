Feature: Withdrawal money from account
  In order to retrieve some or all of my savings
           As a bank client
    I want to make a withdrawal from my account

  Background:
    Given The application is in its initial state

  Scenario Outline: Withdrawal money
    Given a customer "<customer>" has open an account "<account>" with an initial deposit of <initial_balance> €
     When "<customer>" withdraws <amount> € from account "<account>"
     Then the account "<account>" has a balance of <new_balance> €

    Examples:
      | customer | account                     | initial_balance | amount | new_balance |
      | Jean     | FR1402462222101055555M02606 | 100             | 10     | 90          |
