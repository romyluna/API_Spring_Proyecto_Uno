package com.lta.cursoapis.curso_introduccion_apis.Controller;

import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.service.CategoriaService;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired

    private CategoriaService categoriaService;

    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria (@RequestBody Categoria categoria) throws BadRequestException {
            Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
            return ResponseEntity.status((HttpStatus.CREATED)).body(nuevaCategoria);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategoria(){
        List<Categoria> listaCategoria = categoriaService.listarCategoria();
        return ResponseEntity.ok(listaCategoria);
    }


    @GetMapping("/buscar/categoria/{idCategoria}")
    public ResponseEntity<?> categoriaPorId(@PathVariable Long idCategoria){
        Optional<Categoria> categoria = categoriaService.categoriaPorId(idCategoria);
        return categoria.isPresent()
                //ternario (if-else)
                ? ResponseEntity.ok(categoria.get()) //devuelve ok y la categoria
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria no encontrada");
    }

    @PutMapping("/actualizar/{idCategoria}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long idCategoria,@RequestBody Categoria categoria){
        try{
            Categoria categoriaActualizada = new Categoria();
            categoriaActualizada.setIdCategoria(idCategoria);
            categoriaActualizada.setNombreCategoria(categoria.getNombreCategoria());

            Categoria productoBBDD = categoriaService.actualizarCategoria(idCategoria,categoriaActualizada);
            return ResponseEntity.ok(categoriaActualizada);

        }catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long idCategoria){
        try{
            categoriaService.eliminarCategoria(idCategoria);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

}
