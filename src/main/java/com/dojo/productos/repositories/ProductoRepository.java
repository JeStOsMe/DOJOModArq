package com.dojo.productos.repositories;

import java.util.List;

import com.dojo.productos.models.Producto;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto, String> {

    List<Producto> findByPrecio(Long precio);
    List<Producto> findByNombre(String nombre);

    
} 
