package com.lta.cursoapis.curso_introduccion_apis.service.impl;

import com.lta.cursoapis.curso_introduccion_apis.dto.ProductoDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.exceptions.BadRequestException;
import com.lta.cursoapis.curso_introduccion_apis.exceptions.ResourceNotFoundException;
import com.lta.cursoapis.curso_introduccion_apis.mapper.ProductoMapper;
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
    @Autowired
    private ProductoMapper productoMapper;//inyecto de mapper - categoriaMapper/productoMapper

    @Override
    public ProductoDTO registrarProducto(Long idCategoria, ProductoDTO productoDTO) /*throws Exception*/ {


        //Busca la categoría por id - sino existe se lanza una excepcion "no encontrada"
        Categoria categoria = categoriaRepository.findById(idCategoria)
            //.orElseThrow(() -> new Exception("Categoria con ID" + idCategoria + "no encontrada"));
            .orElseThrow(() -> new ResourceNotFoundException("Categoria con ID" + idCategoria + "no encontrada"));

        //Valida que el precio sea mayor que cero- no se puede usar null con los double
        if(productoDTO.getPrecio() <= 0){
            throw new BadRequestException("El precio del producto debe ser mayor que cero");
        }

        //Convierte el DTO a entidad Producto
        Producto producto = productoMapper.toEntity(productoDTO);

        //Setea la categoría encontrada en el producto
        producto.setCategoria(categoria);

        //Guarda el producto en la base de datos
        Producto productoGuardado = productoRepository.save(producto);

        //Convierte el producto guardado a DTO y lo retorna
        return productoMapper.toDTO(productoGuardado);



    }

    @Override
    public List<ProductoDTO> listarProductos() {
        //List<Producto> productos = productoRepository.findAll();
        //return productos.stream()
        //     .toList();

        //return productoRepository.findAll();

        List<Producto> productos = productoRepository.findAll();
        //convierte cada Producto en un ProductoDTO
        return productos.stream()
                .map(productoMapper::toDTO)//seria como hacer esto .map(producto -> productoMapper.toDTO(producto)) para cada producto
                .toList();

    }

    @Override
    public Optional<ProductoDTO> buscarPorNombre(String nombre) {
        //Optional<Producto> productoOptional = productoRepository.findByNombreProducto(nombre);
        //return productoOptional;

        //return productoRepository.findByNombreProducto(nombre);

        Optional<Producto> producto = productoRepository.findByNombreProducto(nombre);
        return producto.map(productoMapper::toDTO);

    }

    @Override
    public Optional<ProductoDTO> buscarPorId(Long idProducto) {

        //return productoRepository.findByIdProducto(idProducto);

        Optional<Producto> producto = productoRepository.findByIdProducto(idProducto);
        return producto.map(productoMapper::toDTO);

    }

    @Override
    // @SneakyThrows no me anda //es de lombok es como decir throw exception - no vas a colocar un try catch por eso usa este sneaky.
    public ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoDTO) /*throws Exception*/ {

        //chequeo que existe el id // es una entidad(PRODUCTO): es lo que ya estaba guardado en la base por eso no uso productoDTO sino producto
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                //sino existe :

                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + idProducto + " no encontrado"));

        //si existe:  copio los datos del DTO (lo que me mandó el usuario) al objeto Producto real que tengo en la base.
        productoExistente.setNombreProducto(productoDTO.getNombreProducto());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setCantidad(productoDTO.getCantidad());
        productoExistente.setEstadoProducto(productoDTO.getEstadoProducto());


        // verifico si el producto que me pasaron
        // ya viene con una categoría cargada, y si es así, confirma que esa categoría realmente exista en la base de datos
        //IMPORTANTE :producto.getCategoria().getIdCategoria() PORQUE Primero accedo al objeto categoria que está dentro
        // del producto, y después accedo al idCategoria que está dentro de ese objeto categoria.
        if(productoDTO.getCategoria() != null && productoDTO.getCategoria().getIdCategoria() !=null){
            //voy a buscar primero que la categoria que se pase exista sino existe se lanza una excepcion "no encontrada"
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoria().getIdCategoria())

                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
            //si existe:

            System.out.println("ID de la categoría recibida: " + productoDTO.getCategoria().getIdCategoria());
            System.out.println("Asignando categoría: " + categoria.getNombreCategoria());


            productoExistente.setCategoria(categoria);
        }

        System.out.println("Producto final antes de guardar: " + productoExistente);

        //guardando una entidad Producto en la base de datos.
        Producto productoActualizado = productoRepository.save(productoExistente);
        //devuelvo un dto
        return productoMapper.toDTO(productoActualizado);


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
    public ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) /*throws Exception*/ {
        //chequeo que existe el id
            Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                    //sino existe :
                    //.orElseThrow(() -> new Exception("Producto con ID: " + idProducto + " no encontrado"));
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + idProducto + " no encontrado"));
        //si existe:
        productoExistente.setEstadoProducto(nuevoEstadoProducto); // no usa get porque es de un enum ya definido.

        //lo guardo en la BD
        Producto productoActualizado = productoRepository.save(productoExistente);

       //Devuelvo en dto
        return productoMapper.toDTO(productoActualizado);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorEstado(EstadoProducto estadoProducto) {
        //obtengo los productos de la base los guardo en la lista de productos (como objeto producto)
        List<Producto> productos = productoRepository.findByEstadoProducto(estadoProducto);
        //recorro cada producto y los convierto en dto y los guardo en una lista
        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }
}
