package com.dojo.productos.controllers;

import java.util.List;
import java.util.Optional;

import com.dojo.productos.models.Producto;
import com.dojo.productos.repositories.ProductoRepository;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    ProductoRepository repo;

    @RequestMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos() {
        try {
            List<Producto> productos = repo.findAll();

            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/productos/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("id") String id){
        Optional<Producto> _producto = repo.findById(id);

        if (_producto.isPresent()){
            return new ResponseEntity<>(_producto.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping("/productos/{precio}")
    public ResponseEntity<List<Producto>> getProductoByPrecio(@PathVariable("precio") Long precio){
        List<Producto> productos = repo.findByPrecio(precio);

        if (productos.size() > 0){
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/productos/{nombre}")
    public ResponseEntity<List<Producto>> getProductoByPrecio(@PathVariable("nombre") String nombre){
        List<Producto> productos = repo.findByNombre(nombre);

        if (productos.size() > 0){
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/productos")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto){

        try {
            Producto _producto = repo.save(new Producto(producto.getId(), producto.getNombre(), producto.getPrecio()));
            return new ResponseEntity<>(_producto, HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") String id, @RequestBody Producto producto){
        Optional<Producto> data = repo.findById(id);

        if (data.isPresent()){
            Producto _producto = data.get();
            _producto.setId(producto.getId());
            _producto.setNombre(producto.getNombre());
            _producto.setPrecio(producto.getPrecio());

            return new ResponseEntity<>(repo.save(_producto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<HttpStatus> deleteProductoById(@PathVariable("id") String id){
        try {
            repo.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/productos")
    public ResponseEntity<HttpStatus> deleteAllProductos(){
        try {
            repo.deleteAll();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
