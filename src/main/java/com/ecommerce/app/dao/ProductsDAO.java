package com.ecommerce.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.app.entity.Products;
import com.ecommerce.app.repository.ImplementationRepository;
import com.ecommerce.app.repository.ProductRepository;

@Service
public class ProductsDAO implements ImplementationRepository<Products> {

    private final ProductRepository productRepository;

    public ProductsDAO(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void create(Products t) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Products read(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public void update(Products t) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Page<Products> findAll(Pageable pageable) {
        Page<Products> products = this.productRepository.findAll(pageable);
        return products;
    }
}
