package com.lta.cursoapis.curso_introduccion_apis.Controller;

import com.lta.cursoapis.curso_introduccion_apis.dto.ProductoDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/registrar")
    //ResonseEntity:Se usa cuando no se o no me el tipo exacto del dato que va a devolver el ResponseEntity.
    public ResponseEntity<?> registrarProducto(@RequestBody ProductoDTO productoDTO) throws Exception{
        //guardo la respuesta en nuevoProducto

        Long idCategoria = productoDTO.getCategoria().getIdCategoria();
        ProductoDTO nuevoProducto = productoService.registrarProducto(idCategoria,productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos(){
        List<ProductoDTO> productosDTO = productoService.listarProductos();
        return ResponseEntity.ok(productosDTO);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> listarPorNombre(@PathVariable String nombre){
        Optional<ProductoDTO> productoDTO = productoService.buscarPorNombre(nombre);
        return productoDTO.isPresent()
                //ternario (if-else)
                ? ResponseEntity.ok(productoDTO.get()) //devuelve ok y el producto
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idProducto){
        Optional<ProductoDTO> productoDTO = productoService.buscarPorId(idProducto);
        return productoDTO.isPresent()
                //ternario (if-else)
                ? ResponseEntity.ok(productoDTO.get()) //devuelve ok y el producto
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto,@RequestBody ProductoDTO productoDTO){
        try{
            ProductoDTO productoActualizado = new ProductoDTO();
            productoActualizado.setIdProducto(idProducto);
            productoActualizado.setNombreProducto(productoDTO.getNombreProducto());
            productoActualizado.setDescripcion(productoDTO.getDescripcion());
            productoActualizado.setPrecio(productoDTO.getPrecio());
            productoActualizado.setCantidad(productoDTO.getCantidad());
            productoActualizado.setEstadoProducto(productoDTO.getEstadoProducto());

            productoActualizado.setCategoria(productoDTO.getCategoria());


            ProductoDTO productoBBDD = productoService.actualizarProducto(idProducto,productoActualizado);
            return ResponseEntity.ok(productoActualizado);

        }catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long idProducto){
        try{
            productoService.eliminarProducto(idProducto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping("/estado/{idProducto}")
    public ResponseEntity<?> cambiarEstadoProducto(@PathVariable Long idProducto,@RequestBody EstadoProducto estadoProducto){
        try{
            ProductoDTO productoActualizado = productoService.cambiarEstadoProducto(idProducto,estadoProducto);
            return ResponseEntity.ok(productoActualizado);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/estado/{estadoProducto}")
    public ResponseEntity<List<ProductoDTO>> listarProductosPorEstado(@PathVariable EstadoProducto estadoProducto) {
        List<ProductoDTO> productoDTO = productoService.obtenerProductosPorEstado(estadoProducto);
        return ResponseEntity.ok(productoDTO);
    }
}
