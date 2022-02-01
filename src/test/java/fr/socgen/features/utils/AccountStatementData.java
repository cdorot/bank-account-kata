package fr.socgen.features.utils;

import java.time.LocalDateTime;

public record AccountStatementData(LocalDateTime dateTime, String operation, long amount) {
}
