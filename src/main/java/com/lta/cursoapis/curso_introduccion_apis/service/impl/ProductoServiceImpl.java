package com.lta.cursoapis.curso_introduccion_apis.service.impl;

import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.repository.ProductoRepository;
import com.lta.cursoapis.curso_introduccion_apis.service.ProductoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//implemento todos los metodos de la interface "productoService"
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
     private ProductoRepository productoRepository; //inyecto de repository

    @Override
    public Producto registrarProducto(Producto producto) {
        //Producto nuevoProducto = productoRepository.save(producto);
        //return nuevoProducto;
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {
        //List<Producto> productos = productoRepository.findAll();
        //return productos.stream()
        //     .toList();

        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre) {
        //Optional<Producto> productoOptional = productoRepository.findByNombreProducto(nombre);
        //return productoOptional;
        return productoRepository.findByNombreProducto(nombre);
    }

    @Override
    public Optional<Producto> buscarPorId(Long idProducto) {

        return productoRepository.findByIdProducto(idProducto);
    }

    @Override
    // @SneakyThrows no me anda //es de lombok es como decir throw exception - no vas a colocar un try catch por eso usa este sneaky.
    public Producto actualizarProducto(Long idProducto, Producto producto) throws Exception {
        //chequeo que existe el id
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                //sino existe :
                .orElseThrow(() -> new Exception("Producto con ID: " + idProducto + " no encontrado"));
        //si existe:
        productoExistente.setNombreProducto(producto.getNombreProducto());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCantidad(producto.getCantidad());
        productoExistente.setEstadoProducto(producto.getEstadoProducto());

        //Producto productoActualizado = productoRepository.save(productoExistente);
        //return productoActualizado;

        return productoRepository.save(productoExistente); // directamente
    }

    @Override
    public void eliminarProducto(Long idProducto) throws Exception {
        //chequeo que existe el id
         productoRepository.findByIdProducto(idProducto)
                 //sino existe :
                .orElseThrow(() -> new Exception("Producto con ID: " + idProducto + " no encontrado"));
        //si existe:
         productoRepository.deleteById(idProducto); //no va con return porque no me devuelve nada
    }

    @Override
    public Producto cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) throws Exception {
        //chequeo que existe el id
            Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                    //sino existe :
                    .orElseThrow(() -> new Exception("Producto con ID: " + idProducto + " no encontrado"));
        //si existe:
        productoExistente.setEstadoProducto(nuevoEstadoProducto); // no usa get porque es de un enum ya definido.
        //lo guarda
        return productoRepository.save(productoExistente);
    }

    @Override
    public List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto) {
        return productoRepository.findByEstadoProducto(estadoProducto);
    }
}
