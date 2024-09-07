package com.example.shoppingcart.controllers;

import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.services.ProductService;
import com.example.shoppingcart.utility.Categories;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findByPrice(@RequestParam(value = "price1", required = false) String price1Str, @RequestParam(value = "price2", required = false) String price2Str) {

        if (price1Str == null & price2Str == null) {
            return productService.returnAll();
        }
        if (price1Str == null) {
            return productService.findBelow(Integer.parseInt(price2Str));
        }
        if (price2Str == null) {
            return productService.findAbove(Integer.parseInt(price1Str));
        }


        return productService.findByPriceRange(Integer.parseInt(price1Str), Integer.parseInt(price2Str));

    }

    @PostMapping
    public void add(@RequestBody Product product) {
        productService.create(product);
    }


    @GetMapping("/category")
    public Map<Categories, Integer> findByCategory() {
        List<Categories> categories = Arrays.asList(Categories.class.getEnumConstants());
        Map<Categories, Integer> map = new TreeMap<>();
        for (Categories cat :
                categories) {
            map.put(cat, productService.findByCategory(cat));
        }
        return map;
    }
}

