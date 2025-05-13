package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;

public record CreateUserDTO(
        String lpna_identifier,
        String full_name,
        String service_name,
        Rank rank,
        short hierarchy,
        boolean supervisor,
        boolean instructor,
        boolean trainee
) {
    public CreateUserDTO(UserEntity user){
        this(user.getLpna_identifier(), user.getFull_name(), user.getService_name(), user.getRank(),
                user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
    }

}
