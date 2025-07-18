package com.lta.cursoapis.curso_introduccion_apis.Controller;

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
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto) throws Exception{
        //guardo la respuesta en nuevoProducto

        Long idCategoria = producto.getCategoria().getIdCategoria();
        Producto nuevoProducto = productoService.registrarProducto(idCategoria,producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> listarPorNombre(@PathVariable String nombre){
        Optional<Producto> producto = productoService.buscarPorNombre(nombre);
        return producto.isPresent()
                //ternario (if-else)
                ? ResponseEntity.ok(producto.get()) //devuelve ok y el producto
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idProducto){
        Optional<Producto> producto = productoService.buscarPorId(idProducto);
        return producto.isPresent()
                //ternario (if-else)
                ? ResponseEntity.ok(producto.get()) //devuelve ok y el producto
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto,@RequestBody Producto producto){
        try{
            Producto productoActualizado = new Producto();
            productoActualizado.setIdProducto(idProducto);
            productoActualizado.setNombreProducto(producto.getNombreProducto());
            productoActualizado.setDescripcion(producto.getDescripcion());
            productoActualizado.setPrecio(producto.getPrecio());
            productoActualizado.setCantidad(producto.getCantidad());
            productoActualizado.setEstadoProducto(producto.getEstadoProducto());

            productoActualizado.setCategoria(producto.getCategoria());


            Producto productoBBDD = productoService.actualizarProducto(idProducto,productoActualizado);
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
            Producto productoActualizado = productoService.cambiarEstadoProducto(idProducto,estadoProducto);
            return ResponseEntity.ok(productoActualizado);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/estado/{estadoProducto}")
    public ResponseEntity<List<Producto>> listarProductosPorEstado(@PathVariable EstadoProducto estadoProducto) {
        List<Producto> productos = productoService.obtenerProductosPorEstado(estadoProducto);
        return ResponseEntity.ok(productos);
    }
}
