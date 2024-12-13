package org.product.back.controller;

import org.product.back.entity.Account;
import org.product.back.entity.Wishlist;
import org.product.back.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<Wishlist> viewWishlist(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Wishlist wishlist = wishlistService.viewWishlist(account.getEmail());
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam Long productId, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Wishlist updatedWishlist = wishlistService.addToWishlist(account.getEmail(), productId);
        return ResponseEntity.ok(updatedWishlist);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Wishlist> removeFromWishlist(@RequestParam Long productId, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Wishlist updatedWishlist = wishlistService.removeFromWishlist(account.getEmail(), productId);
        return ResponseEntity.ok(updatedWishlist);
    }


}
