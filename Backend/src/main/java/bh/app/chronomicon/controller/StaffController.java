package bh.app.chronomicon.controller;


import bh.app.chronomicon.model.entities.StaffEntity;
import bh.app.chronomicon.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/staff")
public class StaffController {

    @Autowired
    private StaffRepository repository;

    @GetMapping
    public List<StaffEntity> findall(){
        return repository.findAll(Sort.by("hierarchy").ascending());
    }

    @GetMapping(value = {"/sups"})
    public List<StaffEntity> findSups(){
        return repository.findAll(Sort.by("hierarchy").ascending());
    }

    @GetMapping(value = {"/insts"})
    public List<StaffEntity> findInsts(){
        return repository.findAll(Sort.by("hierarchy").descending());
    }

    @GetMapping(value = {"/estagiarios"})
    public List<StaffEntity> findTrainees(){
        return repository.findAll(Sort.by("hierarchy").descending());
    }

    @GetMapping(value = {"/operadores"})
    public List<StaffEntity> findOps(){
        return repository.findAll(Sort.by("hierarchy").descending());
    }

    @GetMapping(value = {"/{id}"})
    public StaffEntity findByID(@PathVariable long id){
        return repository.findById(id).get();
    }

    @PostMapping
    public StaffEntity insert(@RequestBody StaffEntity staff){
        return repository.save(staff);
    }
}
