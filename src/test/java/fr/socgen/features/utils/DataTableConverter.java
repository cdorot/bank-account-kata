package fr.socgen.features.utils;

import io.cucumber.datatable.DataTable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public final class DataTableConverter {

  private DataTableConverter() {}

  public static List<AccountStatementOperationData> convertToAccountStatementOperationDataList(
      DataTable dataTable) {
    return dataTable.entries().stream()
        .map(DataTableConverter::mapToAccountStatementOperationData)
        .toList();
  }

  private static AccountStatementOperationData mapToAccountStatementOperationData(
      Map<String, String> map) {
    final LocalDateTime dateTime =
        IsoLocalDateConverter.getDateTimeFromIsoLocalDateString(map.get("date"));
    final String operation = map.get("operation");
    final long amount = Long.parseLong(map.get("amount"));

    return new AccountStatementOperationData(dateTime, operation, amount);
  }
}
