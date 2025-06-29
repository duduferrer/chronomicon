package bh.app.chronomicon.mapper;

import bh.app.chronomicon.dto.UpdateOperatorDTO;
import bh.app.chronomicon.model.entities.OperatorEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OperatorMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOperatorFromDTO(UpdateOperatorDTO dto, @MappingTarget OperatorEntity entity);
}

