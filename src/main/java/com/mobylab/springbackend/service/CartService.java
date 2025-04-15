package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Cart;
import com.mobylab.springbackend.entity.Sneaker;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.exception.ResourceNotFoundException;
import com.mobylab.springbackend.repository.CartRepository;
import com.mobylab.springbackend.repository.SneakerRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.CartDto;
import com.mobylab.springbackend.service.dto.CartDtoResponse;
import com.mobylab.springbackend.service.dto.SneakerDto;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final SneakerRepository sneakerRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, SneakerRepository sneakerRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.sneakerRepository = sneakerRepository;
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            System.out.println("User details: " + userDetails.getUsername());
            return userRepository.findUserByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("User not authenticated");
    }

    private Cart getOrCreateCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setSneakers(List.of());
            return cartRepository.save(cart);
        }
        return cart;
    }

    public CartDtoResponse getCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        return mapToDto(cart);
    }

    public CartDtoResponse addToCart(CartDto dto) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        List<Sneaker> sneakers = sneakerRepository.findAllById(dto.getSneakerIds());
        cart.getSneakers().addAll(sneakers);
        return mapToDto(cartRepository.save(cart));
    }

    public CartDtoResponse removeFromCart(CartDto dto) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        boolean anyToRemove = cart.getSneakers().stream()
                .anyMatch(s -> dto.getSneakerIds().contains(s.getId()));

        if (!anyToRemove) {
            throw new ResourceNotFoundException("None of the selected sneakers are in the cart.");
        }

        cart.getSneakers().removeIf(s -> dto.getSneakerIds().contains(s.getId()));
        return mapToDto(cartRepository.save(cart));
    }

    public CartDtoResponse clearCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        if (cart.getSneakers().isEmpty()) {
            throw new BadRequestException("Cart is already empty.");
        }

        cart.getSneakers().clear();
        return mapToDto(cartRepository.save(cart));
    }

    private CartDtoResponse mapToDto(Cart cart) {
        List<SneakerDto> sneakerDtos = cart.getSneakers().stream().map(s -> {
            SneakerDto dto = new SneakerDto();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setSize(s.getSize());
            dto.setPrice(s.getPrice());
            return dto;
        }).collect(Collectors.toList());

        CartDtoResponse response = new CartDtoResponse();
        response.setUserId(cart.getUser().getId());
        response.setSneakers(sneakerDtos);
        return response;
    }
}