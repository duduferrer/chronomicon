package bh.app.chronomicon.dto;

import jakarta.validation.Valid;

public record CreateUserDTO(
		@Valid
		CoreUserInformationDTO coreUserInformationDTO,
		@Valid
		CreateAtcoDTO createAtcoDTO,
		@Valid
		CreateSystemUserDTO createSystemUserDTO) {
}
