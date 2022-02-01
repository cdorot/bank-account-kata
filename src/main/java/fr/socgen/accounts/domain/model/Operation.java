package fr.socgen.accounts.domain.model;

import java.time.LocalDateTime;

public record Operation(LocalDateTime dateTime, OperationType type, long amount) { }
