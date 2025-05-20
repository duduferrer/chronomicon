package bh.app.chronomicon.controller;


import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/staff")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> findUsers(){
        return service.findUsers();
    }

    @GetMapping(value = {"/sups"})
    public List<UserDTO> findSupervisors(){
        return service.findSupervisors();
    }

    @GetMapping(value = {"/instrutores"})
    public List<UserDTO> findInsts(){
        return service.findInstructors();
    }

    @GetMapping(value = {"/estagiarios"})
    public List<UserDTO> findTrainees(){
        return service.findTrainees();
    }

    @GetMapping(value = {"/operadores"})
    public List<UserDTO> findOps(){
        return service.findOnlyOperators();
    }

    @GetMapping(value = {"/{id}"})
    public UserDTO findByID(@PathVariable long id){
        return service.findUserById(id);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
        UserDTO savedUser = service.createNewUser(user);
        URI location = URI.create(savedUser.lpna_identifier());
        return ResponseEntity.created(location).body(savedUser);
    }

    @GetMapping(value = {"/lpna/{lpna}"})
    public UserDTO findByLPNA(@PathVariable String lpna){
        return service.findUserByLPNA(lpna);
    }
}
