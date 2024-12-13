package org.product.back.service;

import org.product.back.entity.Product;
import org.product.back.entity.Wishlist;
import org.product.back.repository.ProductRepository;
import org.product.back.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public Wishlist getOrCreateWishlist(String email) {
        return wishlistRepository.findByEmail(email)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setEmail(email);
                    return wishlistRepository.save(newWishlist);
                });
    }

    public Wishlist addToWishlist(String email, Long productId) {
        Wishlist wishlist = getOrCreateWishlist(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Add product to the wishlist if not already present
        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);
            wishlistRepository.save(wishlist);
        }

        return wishlist;
    }

    public Wishlist removeFromWishlist(String email, Long productId) {
        Wishlist wishlist = getOrCreateWishlist(email);
        wishlist.getProducts().removeIf(product -> product.getId().equals(productId));
        return wishlistRepository.save(wishlist);
    }

    public Wishlist viewWishlist(String email) {
        return getOrCreateWishlist(email);
    }
}
