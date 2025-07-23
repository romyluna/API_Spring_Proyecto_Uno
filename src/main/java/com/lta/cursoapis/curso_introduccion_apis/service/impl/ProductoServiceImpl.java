package com.lta.cursoapis.curso_introduccion_apis.service.impl;

import com.lta.cursoapis.curso_introduccion_apis.dto.ProductoDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.exceptions.ResourceNotFoundException;
import com.lta.cursoapis.curso_introduccion_apis.repository.CategoriaRepository;
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
     private ProductoRepository productoRepository; //inyecto de repository producto
    @Autowired
    private CategoriaRepository categoriaRepository; //inyecto de repository categoria

    @Override
    public ProductoDTO registrarProducto(Long idCategoria, ProductoDTO productoDTO) /*throws Exception*/ {
        //Producto nuevoProducto = productoRepository.save(producto);
        //return nuevoProducto;
        //return productoRepository.save(producto);

        //voy a buscar primero que la categoria que se pase exista sino existe se lanza una excepcion "no encontrada"
        Categoria categoria = categoriaRepository.findById(idCategoria)
            //.orElseThrow(() -> new Exception("Categoria con ID" + idCategoria + "no encontrada"));
            .orElseThrow(() -> new ResourceNotFoundException("Categoria con ID" + idCategoria + "no encontrada"));
        //si existe la categoria que pasaron
        productoDTO.setCategoria(categoria);
         return productoRepository.save(productoDTO);

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
    public Producto actualizarProducto(Long idProducto, Producto producto) /*throws Exception*/ {
        //chequeo que existe el id
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                //sino existe :
                //.orElseThrow(() -> new Exception("Producto con ID: " + idProducto + " no encontrado"));
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + idProducto + " no encontrado"));
        //si existe:
        productoExistente.setNombreProducto(producto.getNombreProducto());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCantidad(producto.getCantidad());
        productoExistente.setEstadoProducto(producto.getEstadoProducto());

        //Producto productoActualizado = productoRepository.save(productoExistente);
        //return productoActualizado;

        // verifico si el producto que me pasaron
        // ya viene con una categoría cargada, y si es así, confirma que esa categoría realmente exista en la base de datos
        //IMPORTANTE :producto.getCategoria().getIdCategoria() PORQUE Primero accedo al objeto categoria que está dentro
        // del producto, y después accedo al idCategoria que está dentro de ese objeto categoria.
        if(producto.getCategoria() != null && producto.getCategoria().getIdCategoria() !=null){
            //voy a buscar primero que la categoria que se pase exista sino existe se lanza una excepcion "no encontrada"
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                    //.orElseThrow(() -> new Exception("Categoria no encontrada"));
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
            //si existe:

            System.out.println("ID de la categoría recibida: " + producto.getCategoria().getIdCategoria());
            System.out.println("Asignando categoría: " + categoria.getNombreCategoria());


            productoExistente.setCategoria(categoria);
        }

        System.out.println("Producto final antes de guardar: " + productoExistente);

        return productoRepository.save(productoExistente); // directamente
    }

    @Override
    public void eliminarProducto(Long idProducto) /*throws Exception*/ {
        //chequeo que existe el id
         productoRepository.findByIdProducto(idProducto)
                 //sino existe :
                //.orElseThrow(() -> new Exception("Producto con ID: " + idProducto + " no encontrado"));
                 .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + idProducto + " no encontrado"));
        //si existe:
         productoRepository.deleteById(idProducto); //no va con return porque no me devuelve nada
    }

    @Override
    public Producto cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) /*throws Exception*/ {
        //chequeo que existe el id
            Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                    //sino existe :
                    //.orElseThrow(() -> new Exception("Producto con ID: " + idProducto + " no encontrado"));
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + idProducto + " no encontrado"));
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
