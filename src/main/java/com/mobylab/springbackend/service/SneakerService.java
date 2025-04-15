package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Sneaker;
import com.mobylab.springbackend.exception.ResourceNotFoundException;
import com.mobylab.springbackend.repository.SneakerRepository;
import com.mobylab.springbackend.service.dto.SneakerDto;
import com.mobylab.springbackend.service.dto.SneakerRequestDto;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SneakerService {

    private final SneakerRepository sneakerRepository;
    private final EmailService emailService;

    public SneakerService(SneakerRepository sneakerRepository, EmailService emailService) {
        this.sneakerRepository = sneakerRepository;
        this.emailService = emailService;
    }

    public List<SneakerDto> getAllSneakers() {
        return sneakerRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SneakerDto getSneakerById(Long id) {
        Sneaker s = sneakerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sneaker not found with id: " + id));
        return mapToDto(s);
    }

    public Sneaker addSneaker(SneakerRequestDto dto){
        Sneaker s = new Sneaker();
        s.setName(dto.getName());
        s.setSize(dto.getSize());
        s.setPrice(dto.getPrice());
        Sneaker saved = sneakerRepository.save(s);
        emailService.sendEmail(
                "test@example.com",
                "Sneaker adaugat",
                "A fost adaugat sneakerul: " + saved.getName()
        );
        return sneakerRepository.save(s);
    }

    public SneakerDto updateSneaker(Long id, SneakerRequestDto dto) {
        Sneaker s = sneakerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sneaker not found with id: " + id));
        s.setName(dto.getName());
        s.setSize(dto.getSize());
        s.setPrice(dto.getPrice());
        return mapToDto(sneakerRepository.save(s));
    }

    public void deleteSneaker(Long id) {
        if (!sneakerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sneaker not found with id: " + id);
        }
        sneakerRepository.deleteById(id);
    }

    private SneakerDto mapToDto(Sneaker s) {
        return new SneakerDto()
                .setId(s.getId())
                .setName(s.getName())
                .setSize(s.getSize())
                .setPrice(s.getPrice());
    }
}
