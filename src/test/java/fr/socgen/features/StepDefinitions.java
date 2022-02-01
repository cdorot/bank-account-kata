package fr.socgen.features;

import fr.socgen.accounts.exposition.AccountApplication;
import fr.socgen.accounts.infrastructure.repositories.AccountRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

  private final AccountApplication accountApplication = AccountApplication.newAccountApplication();
  private final AccountRepository accountRepository = AccountRepository.getInstance();

  @Given("The application is in its initial state")
  public void the_application_is_in_its_initial_state() {
    accountRepository.clear();
  }

  @Given("a customer {string} has open an account {string} with an initial deposit of {int} €")
  public void given_a_customer_has_open_an_account_with_an_initial_deposit(
      String customer, String accountNumber, int initialDeposit) {
    accountRepository.addAccount(accountNumber, customer, initialDeposit);
  }

  @When("{string} deposits {int} € in account {string}")
  public void when_customer_deposits_money_on_account(String customer, int amount, String account) {
    accountApplication.deposit(customer, account, amount);
  }

  @When("{string} withdraws {int} € from account {string}")
  public void when_customers_withdraws_money_from_account(
      String customer, int amount, String account) {
    accountApplication.withdraw(customer, account, amount);
  }

  @Then("the account {string} has a balance of {int} €")
  public void then_the_balance_of_account_is_equal_to(String accountNumber, int balance) {
    assertEquals(
        balance,
        accountApplication.computeBalance(accountNumber),
        "The account " + accountNumber + " should have a balance equals to " + balance);
  }
}
