package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.model.enums.Rank;

public record AtcoDTO(
        String lpna_identifier,
        String full_name,
        String service_name,
        Rank rank,
        short hierarchy,
        boolean supervisor,
        boolean instructor,
        boolean trainee
) {
    public AtcoDTO(AtcoEntity atco){
        this(atco.getLpna_identifier(), atco.getCoreUserInformationEntity().getFullName(), atco.getCoreUserInformationEntity().getService_name(), atco.getCoreUserInformationEntity().getRank(),
                atco.getHierarchy(), atco.isSupervisor(), atco.isInstructor(), atco.isTrainee());
    }

}
