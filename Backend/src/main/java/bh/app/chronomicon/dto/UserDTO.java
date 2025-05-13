package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;

public record UserDTO(
        String lpna_identifier,
        String full_name,
        String service_name,
        Rank rank,
        boolean supervisor,
        boolean instructor,
        boolean trainee
) {
    public UserDTO(UserEntity user){
        this(user.getLpna_identifier(), user.getFull_name(), user.getService_name(), user.getRank(), user.isSupervisor(),
                user.isInstructor(), user.isTrainee());
    }

}
