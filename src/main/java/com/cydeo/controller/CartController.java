package com.cydeo.controller;

import com.cydeo.model.Cart;
import com.cydeo.model.CartItem;
import com.cydeo.service.CartService;
import com.cydeo.service.impl.CartServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.cydeo.service.impl.CartServiceImpl.*;

@Controller
@AllArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping("/cart")
    public String showCart(Model model) {

        model.addAttribute("cart", CART);

        return "/cart/show-cart";
    }

    @GetMapping("/addToCart/{id}/{quantity}")
    public String addToCart(@PathVariable UUID id, @PathVariable Integer quantity) {

        cartService.addToCart(id, quantity);

        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable UUID id){

        cartService.deleteFromCart(id);

        return "redirect:/cart";
    }



}
