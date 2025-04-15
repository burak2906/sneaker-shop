package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.service.CartService;
import com.mobylab.springbackend.service.dto.CartDto;
import com.mobylab.springbackend.service.dto.CartDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController implements SecuredRestController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<CartDtoResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CartDtoResponse> addToCart(@RequestBody CartDto request) {
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CartDtoResponse> removeFromCart(@RequestBody CartDto request) {
        return ResponseEntity.ok(cartService.removeFromCart(request));
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CartDtoResponse> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }
}