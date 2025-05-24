package bh.app.chronomicon.controller;


import bh.app.chronomicon.dto.CreateOperatorDTO;
import bh.app.chronomicon.model.enums.ShiftType;
import bh.app.chronomicon.service.OperatorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/shift-operator")
@Tag(name = "Operadores", description = "Operações relacionadas a operadores do turno")
public class OperatorController {

    @Autowired
    OperatorService operatorService;

    @PostMapping(value = {"/{lpna}/{shift}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atualiza o status ativo do usuário de acordo com parametro da url"),
            @ApiResponse(responseCode = "404", description = "Retorna usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Falha ao criar usuário"),
    })
    public ResponseEntity<CreateOperatorDTO> createOperator(@PathVariable String lpna, @PathVariable ShiftType shift,
                                                            @RequestParam(defaultValue = "false") boolean sup,
                                                            @RequestParam(defaultValue = "false") boolean est,
                                                            @RequestParam(defaultValue = "false") boolean inst){
        CreateOperatorDTO operatorDTO = operatorService.createOperator (lpna, shift, sup, est, inst);
        URI location = URI.create(operatorDTO.id ());
        return ResponseEntity.created(location).body (operatorDTO);
    }


}
