package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.entity.Sneaker;
import com.mobylab.springbackend.service.SneakerService;
import com.mobylab.springbackend.service.dto.SneakerDto;
import com.mobylab.springbackend.service.dto.SneakerRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sneakers")
public class SneakerController implements SecuredRestController {

    private final SneakerService sneakerService;

    public SneakerController(SneakerService sneakerService){
        this.sneakerService = sneakerService;
    }

    // USER + ADMIN pot vedea sneakers
    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<SneakerDto>> getAllSneakers(){
        return ResponseEntity.ok(sneakerService.getAllSneakers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<SneakerDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(sneakerService.getSneakerById(id));
    }

    // Doar ADMIN poate adauga sneakers
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Sneaker> addSneaker(@RequestBody SneakerRequestDto dto){
        return new ResponseEntity<>(sneakerService.addSneaker(dto), HttpStatus.CREATED);
    }

    // Doar ADMIN poate edita sneakers
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SneakerDto> update(@PathVariable Long id, @RequestBody SneakerRequestDto dto){
        return ResponseEntity.ok(sneakerService.updateSneaker(id, dto));
    }

    // Doar ADMIN poate șterge sneakers
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        sneakerService.deleteSneaker(id);
        return ResponseEntity.noContent().build();
    }
}
