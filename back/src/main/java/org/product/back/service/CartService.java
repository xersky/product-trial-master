package org.product.back.service;

import org.product.back.entity.Cart;
import org.product.back.entity.CartProduct;
import org.product.back.entity.Product;
import org.product.back.repository.CartRepository;
import org.product.back.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getOrCreateCart(String email) {
        return cartRepository.findByEmail(email)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setEmail(email);
                    return cartRepository.save(newCart);
                });
    }

    public Cart addToCart(String email, Long productId, int quantity) {
        Cart cart = getOrCreateCart(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartProduct> existingProduct = cart.getItems().stream()
                .filter(cartProduct -> cartProduct.getProduct().getId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            CartProduct cartProduct = existingProduct.get();
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
        } else {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setCart(cart);
            cartProduct.setProduct(product);
            cartProduct.setQuantity(quantity);
            cart.getItems().add(cartProduct);
        }

        return cartRepository.save(cart);
    }

    public Cart removeFromCart(String email, Long productId) {
        Cart cart = getOrCreateCart(email);
        cart.getItems().removeIf(cartProduct -> cartProduct.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }

    public Cart viewCart(String email) {
        return getOrCreateCart(email);
    }
}
