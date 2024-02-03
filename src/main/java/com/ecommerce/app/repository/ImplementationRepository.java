package com.ecommerce.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImplementationRepository<T> {
    
    void create(T t);

    T read(int id);
    
    void update(T t);
    
    void delete(int id);

    Page<T> findAll(Pageable pageable);
}
