package bh.app.chronomicon.controller;


import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.service.UserService;
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
@RequestMapping(value = "/staff")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Busca Usuários", description = "Retorna uma lista com todos os usuários ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping
    public List<UserDTO> findUsers(){
        return service.findUsers();
    }

    @Operation(summary = "Busca Supervisores", description = "Retorna uma lista com todos os supervisores ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/sups"})
    public List<UserDTO> findSupervisors(){
        return service.findSupervisors();
    }

    @Operation(summary = "Busca Instrutores", description = "Retorna uma lista com todos os instrutores ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/instrutores"})
    public List<UserDTO> findInsts(){
        return service.findInstructors();
    }

    @Operation(summary = "Busca Estagiários", description = "Retorna uma lista com todos os estagiários ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/estagiarios"})
    public List<UserDTO> findTrainees(){
        return service.findTrainees();
    }

    @Operation(summary = "Busca Operadores", description = "Retorna uma lista com todos os usuários ativos que são apenas operadores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com os usuários"),
    })
    @GetMapping(value = {"/operadores"})
    public List<UserDTO> findOps(){
        return service.findOnlyOperators();
    }

    @Operation(summary = "Busca usuário por ID", description = "Recebe um parametro id(numero) e retorna um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um usuário com o ID informado"),
            @ApiResponse(responseCode = "404", description = "Retorna usuário não encontrado")
    })
    @GetMapping(value = {"/{id}"})
    public UserDTO findByID(@PathVariable long id){
        return service.findUserById(id);
    }


    @Operation(summary = "Cadastro Usuário", description = "Cria um novo usuário no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria usuário no banco e retorna"),
            @ApiResponse(responseCode = "409", description = "Falha ao criar usuário"),
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
        UserDTO savedUser = service.createNewUser(user);
        URI location = URI.create(savedUser.lpna_identifier());
        return ResponseEntity.created(location).body(savedUser);
    }

    @Operation(summary = "Busca usuário por LPNA", description = "Recebe um parametro LPNA(texto) e retorna um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um usuário com a LPNA informado"),
            @ApiResponse(responseCode = "404", description = "Retorna usuário não encontrado")
    })
    @GetMapping(value = {"/lpna/{lpna}"})
    public UserDTO findByLPNA(@PathVariable String lpna){
        return service.findUserByLPNA(lpna);
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
}
