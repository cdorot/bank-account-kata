package fr.socgen.accounts.infrastructure.mappers;

import fr.socgen.accounts.domain.model.Operation;
import fr.socgen.accounts.infrastructure.entities.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationEntityMapper {

  OperationEntityMapper INSTANCE = Mappers.getMapper(OperationEntityMapper.class);

  OperationEntity mapToOperationEntity(Operation operation);

  Operation mapToOperation(OperationEntity operationEntity);
}
