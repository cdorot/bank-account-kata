package fr.socgen.accounts.infrastructure.mappers;

import fr.socgen.accounts.domain.model.AccountStatement;
import fr.socgen.accounts.infrastructure.entities.AccountStatementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = OperationEntityMapper.class)
public interface AccountStatementEntityMapper {

  AccountStatementEntityMapper INSTANCE = Mappers.getMapper(AccountStatementEntityMapper.class);

  AccountStatementEntity mapToAccountStatementEntity(AccountStatement accountStatement);
}
