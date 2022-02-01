package fr.socgen.features.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class IsoLocalDateConverter {

  private IsoLocalDateConverter() {}

  public static LocalDate getDateFromIsoLocalDateString(String strDate) {
    return LocalDate.parse(strDate, DateTimeFormatter.ISO_LOCAL_DATE);
  }

  public static LocalDateTime getDateTimeFromIsoLocalDateString(String strDate) {
    return getDateFromIsoLocalDateString(strDate).atStartOfDay();
  }
}
