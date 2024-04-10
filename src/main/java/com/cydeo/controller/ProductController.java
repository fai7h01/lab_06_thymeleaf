package com.cydeo.controller;

import com.cydeo.model.Product;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/list")
    public String getProductList(Model model){

        model.addAttribute("productList",productService.listProducts());

        return "/product/list";
    }

    @GetMapping("/create-form")
    public String productCreatePage(Model model){

        model.addAttribute("product", new Product());

        return "product/create-product";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute("product") Product product){

        product.setId(UUID.randomUUID());

        productRepository.save(product);

        return "redirect:/list";
    }

}
