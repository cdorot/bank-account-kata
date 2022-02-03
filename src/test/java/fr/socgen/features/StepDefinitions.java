package fr.socgen.features;

import fr.socgen.accounts.exposition.AccountApplication;
import fr.socgen.accounts.infrastructure.entities.OperationEntity;
import fr.socgen.accounts.infrastructure.repositories.AccountRepository;
import fr.socgen.features.utils.AccountStatementOperationData;
import fr.socgen.features.utils.DataTableConverter;
import fr.socgen.features.utils.IsoLocalDateConverter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

  private final AccountApplication accountApplication = AccountApplication.newAccountApplication();
  private final AccountRepository accountRepository = AccountRepository.getInstance();

  private LocalDate dateOfTheDay;

  // -- Given steps

  @Given("The application is in its initial state")
  public void given_the_application_is_in_its_initial_state() {
    resetTestContext();
  }

  @Given("The date of the day is the {string}")
  public void given_the_date_of_the_day_is_the(String strDate) {
    dateOfTheDay = IsoLocalDateConverter.getDateFromIsoLocalDateString(strDate);
  }

  @Given("a customer {string} has open an account {string} with an initial deposit of {long} €")
  public void given_a_customer_has_open_an_account_with_an_initial_deposit(
      String customer, String accountNumber, long initialDeposit) {
    accountRepository.addAccount(accountNumber, customer, initialDeposit);
  }

  @Given("a customer {string} has open an account {string} without initial deposit")
  public void given_a_customer_has_open_an_account_without_initial_deposit(
      String customer, String accountNumber) {
    accountRepository.addAccount(accountNumber, customer, 0);
  }

  @Given("{string} has made a deposit of {long} € on {string} in account {string}")
  public void given_customer_has_made_a_deposit_of_money_on(
      String customer, long amount, String strDate, String accountNumber) {
    addOperation(accountNumber, amount, strDate);
  }

  @Given("{string} has made a withdrawal of {long} € on {string} in account {string}")
  public void given_customer_has_made_a_withdrawal_of_money_on(
      String customer, long amount, String strDate, String accountNumber) {
    addOperation(accountNumber, -amount, strDate);
  }

  private void addOperation(String accountNumber, long amount, String strDate) {
    final LocalDateTime dateTime = IsoLocalDateConverter.getDateTimeFromIsoLocalDateString(strDate);

    accountRepository
        .getAccount(accountNumber)
        .addOperation(new OperationEntity().setDateTime(dateTime).setAmount(amount));
  }

  // -- When steps

  @When("{string} deposits {long} € in account {string}")
  public void when_customer_deposits_money_on_account(
      String customer, long amount, String account) {
    accountApplication.deposit(customer, account, amount);
  }

  @When("{string} withdraws {long} € from account {string}")
  public void when_customers_withdraws_money_from_account(
      String customer, long amount, String account) {
    accountApplication.withdraw(customer, account, amount);
  }

  @When("{string} requests a statement for account {string}")
  public void when_customer_requests_a_statement_for_account(
      String customer, String accountNumber) {
    accountApplication.requestAccountStatement(customer, accountNumber, dateOfTheDay);
  }

  // -- Then steps

  @Then("the account {string} has a balance of {long} €")
  public void then_the_balance_of_account_is_equal_to(String accountNumber, long balance) {
    assertEquals(
        balance,
        accountApplication.computeBalance(accountNumber),
        "The account " + accountNumber + " should have a balance equals to " + balance);
  }

  @Then("a statement is generated for account {string}")
  public void then_a_statement_is_generated_for_account(String accountNumber) {
    assertTrue(
        accountRepository.getAccount(accountNumber).getStatements().containsKey(dateOfTheDay),
        "An accournt statement dated ["
            + dateOfTheDay.toString()
            + "] should exists for account ["
            + accountNumber
            + "]");
  }

  @Then("the account statement generated for account {string} is dated {string}")
  public void then_the_account_statement_is_dated(String accountNumber, String strDate) {
    assertEquals(
        IsoLocalDateConverter.getDateFromIsoLocalDateString(strDate),
        accountRepository.getAccount(accountNumber).getStatements().get(dateOfTheDay).getDate(),
        "The date of the generated account statement should be [" + strDate + "]");
  }

  @Then("the account statement generated for account {string} has a balance equal to {long} €")
  public void then_the_balance_on_statement_is_equal_to(String accountNumber, long amount) {
    assertEquals(
        amount,
        accountRepository.getAccount(accountNumber).getStatements().get(dateOfTheDay).getBalance(),
        "The balance of the generated account statement should be equal to [" + amount + " €]");
  }

  @Then("the following operations are listed on the statement of account {string} :")
  public void then_following_operations_are_listed_on_the_statement_of_account(
      String accountNumber, DataTable dataTable) {
    final List<AccountStatementOperationData> expected =
        DataTableConverter.convertToAccountStatementOperationDataList(dataTable);

    final List<AccountStatementOperationData> actual =
        accountRepository
            .getAccount(accountNumber)
            .getStatements()
            .get(dateOfTheDay)
            .getOperations()
            .stream()
            .map(this::convertToAccountStatementData)
            .toList();

    assertArrayEquals(expected.toArray(), actual.toArray());
  }

  private AccountStatementOperationData convertToAccountStatementData(
      OperationEntity operationEntity) {
    String operationLabel = operationEntity.getAmount() > 0 ? "DEPOSIT" : "WITHDRAWAL";
    return new AccountStatementOperationData(
        operationEntity.getDateTime(), operationLabel, Math.abs(operationEntity.getAmount()));
  }

  private void resetTestContext() {
    accountRepository.clear();
    dateOfTheDay = null;
  }
}
