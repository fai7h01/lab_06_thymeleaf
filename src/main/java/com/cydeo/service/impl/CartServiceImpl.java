package com.cydeo.service.impl;

import com.cydeo.model.Cart;
import com.cydeo.model.CartItem;
import com.cydeo.model.Product;
import com.cydeo.service.CartService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    public static Cart CART = new Cart(BigDecimal.ZERO, new ArrayList<>());

    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Cart addToCart(UUID productId, Integer quantity) {
        //todo find product based on productId
        Product product = productService.findProductById(productId);
        //todo initialise cart item using the found product
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        BigDecimal totalAmount = product.getPrice().multiply(new BigDecimal(quantity));
        cartItem.setTotalAmount(totalAmount);
        //todo calculate cart total amount
        BigDecimal cartTotalAmount = CART.getCartItemList().stream()
                .map(CartItem::getTotalAmount)
                .reduce(new BigDecimal("0"), BigDecimal::add);
        //todo add to cart
        CART.setCartTotalAmount(cartTotalAmount);
        return CART;
    }

    @Override
    public boolean deleteFromCart(UUID productId) {
        //todo delete product object from cart using stream
        return CART.getCartItemList().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
    }
}