package bh.app.chronomicon.mapper;

import bh.app.chronomicon.dto.CloseShiftEventDTO;
import bh.app.chronomicon.dto.UpdateShiftEventDTO;
import bh.app.chronomicon.model.entities.ShiftEventsEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShiftEventMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void closeShiftEventFromDTO(CloseShiftEventDTO dto, @MappingTarget ShiftEventsEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateShiftEventFromDTO(UpdateShiftEventDTO dto, @MappingTarget ShiftEventsEntity entity);
}
