package com.cydeo.service.impl;

import com.cydeo.model.Cart;
import com.cydeo.model.CartItem;
import com.cydeo.model.Product;
import com.cydeo.service.CartService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    public static Cart CART = new Cart(BigDecimal.ZERO, new ArrayList<>());

    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Cart addToCart(UUID productId, Integer quantity) {
        List<CartItem> list = CART.getCartItemList();
        //todo find product based on productId
        Product product = productService.findProductById(productId);
        //todo initialise cart item using the found product
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        cartItem.setTotalAmount(totalAmount);
        list.add(cartItem);
        //todo calculate cart total amount
        BigDecimal cartTotalAmount = list.stream()
                .map(CartItem::getTotalAmount)
                .reduce(new BigDecimal("0"), BigDecimal::add);
        //todo add to cart
        CART.setCartTotalAmount(cartTotalAmount);
        CART.setCartItemList(list);
        return CART;
    }

    @Override
    public boolean deleteFromCart(UUID productId) {
        //todo delete product object from cart using stream
        CartItem item = CART.getCartItemList().stream()
                .filter( cartItem -> cartItem.getProduct().getId().equals(productId))
                .limit(1)
                .findFirst().get();
        BigDecimal amount = CART.getCartTotalAmount().subtract(item.getTotalAmount());
        CART.setCartTotalAmount(amount);
        return CART.getCartItemList().remove(item);
    }
}