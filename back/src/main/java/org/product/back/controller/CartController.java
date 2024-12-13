package org.product.back.controller;

import org.product.back.entity.Account;
import org.product.back.entity.Cart;
import org.product.back.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> viewCart(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Cart cart = cartService.viewCart(account.getEmail());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long productId, @RequestParam int quantity, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Cart updatedCart = cartService.addToCart(account.getEmail(), productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestParam Long productId, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Cart updatedCart = cartService.removeFromCart(account.getEmail(), productId);
        return ResponseEntity.ok(updatedCart);
    }


}