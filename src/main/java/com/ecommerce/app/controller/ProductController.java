package com.ecommerce.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.app.dao.ProductsDAO;
import com.ecommerce.app.entity.Products;



@Controller
@ResponseBody
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductsDAO productsDAO;

    public ProductController(final ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }
    
    @GetMapping("/{page}")
    public Page<Products> findAll(@PathVariable int page) {
        PageRequest pageRequest = PageRequest.of(page, 8, Sort.by("id"));
        Page<Products> products = this.productsDAO.findAll(pageRequest);
        return products;
    }
}
