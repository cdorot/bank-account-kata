package fr.socgen.features.utils;

import java.time.LocalDateTime;

public record AccountStatementOperationData(LocalDateTime dateTime, String operation, long amount) {
}
