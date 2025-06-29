package bh.app.chronomicon.mapper;
import bh.app.chronomicon.dto.UpdateShiftDTO;
import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServiceShiftMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateShiftFromDTO(UpdateShiftDTO dto, @MappingTarget ServiceShiftEntity entity);
}

