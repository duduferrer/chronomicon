package bh.app.chronomicon.controller;


import bh.app.chronomicon.dto.CoreUserInformationDTO;
import bh.app.chronomicon.dto.CreateUserDTO;
import bh.app.chronomicon.dto.UpdateUserDTO;
import bh.app.chronomicon.dto.AtcoDTO;
import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.service.AtcoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/staff")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
public class UserController {

    @Autowired
    private AtcoService service;

    @Operation(summary = "Busca Usuários", description = "Retorna uma lista com todos os usuários ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping
    public List<AtcoDTO> findUsers(){
        return service.findUsers();
    }

    @Operation(summary = "Busca Supervisores", description = "Retorna uma lista com todos os supervisores ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/sups"})
    public List<AtcoDTO> findSupervisors(){
        return service.findSupervisors();
    }

    @Operation(summary = "Busca Instrutores", description = "Retorna uma lista com todos os instrutores ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/instrutores"})
    public List<AtcoDTO> findInsts(){
        return service.findInstructors();
    }

    @Operation(summary = "Busca Estagiários", description = "Retorna uma lista com todos os estagiários ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/estagiarios"})
    public List<AtcoDTO> findTrainees(){
        return service.findTrainees();
    }

    @Operation(summary = "Busca Operadores", description = "Retorna uma lista com todos os usuários ativos que são apenas operadores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/operadores"})
    public List<AtcoDTO> findOps(){
        return service.findOnlyOperators();
    }

    @Operation(summary = "Busca usuário por ID", description = "Recebe um parametro id(numero) e retorna um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um usuário com o ID informado"),
            @ApiResponse(responseCode = "404", description = "Retorna usuário não encontrado")
    })
    @GetMapping(value = {"/{id}"})
    public AtcoDTO findByID(@PathVariable long id){
        return service.findUserById(id);
    }


    @Operation(summary = "Cadastro Usuário", description = "Cria um novo usuário no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria usuário no banco e retorna"),
            @ApiResponse(responseCode = "409", description = "Falha ao criar usuário"),
    })
    @PostMapping
    public ResponseEntity<AtcoDTO> createUser(@RequestBody CreateUserDTO user){
        AtcoEntity savedUser = service.createNewUser(user);
        AtcoDTO atcoDTO = new AtcoDTO(savedUser);
        URI location = URI.create(atcoDTO.lpna_identifier());
        return ResponseEntity.created(location).body(atcoDTO);
    }

    @Operation(summary = "Busca usuário por LPNA", description = "Recebe um parametro LPNA(texto) e retorna um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um usuário com a LPNA informado"),
            @ApiResponse(responseCode = "404", description = "Retorna usuário não encontrado")
    })
    @GetMapping(value = {"/lpna/{lpna}"})
    public AtcoDTO findByLPNA(@PathVariable String lpna){
        return service.findUserByLPNAReturnDTO (lpna);
    }

    @Operation(summary = "Atualiza Status ativo/inativo", description = "Muda o status ativo ou inativo de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atualiza o status ativo do usuário de acordo com parametro da url"),
            @ApiResponse(responseCode = "404", description = "Retorna usuário não encontrado")
    })
    @PutMapping(value = {"/{lpna}/status"})
    public ResponseEntity<Void> updateUserStatus(@PathVariable String lpna, @RequestParam boolean active){
        if(active){
            service.activateUser (lpna);
        }else{
            service.deactivateUser (lpna);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza Usuario", description = "Recebe alguns campos com novos dados e pode atualizar usuarios no BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atualiza dados do usuário"),
            @ApiResponse(responseCode = "404", description = "Retorna usuário não encontrado")
    })
    @PatchMapping(value = {"/{lpna}"})
    public ResponseEntity<Void> updateUserStatus(@PathVariable String lpna, @RequestBody UpdateUserDTO user){
        service.updateUser (lpna, user);
        return ResponseEntity.noContent().build();
    }
}
