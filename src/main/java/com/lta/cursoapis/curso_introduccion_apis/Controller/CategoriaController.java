package com.lta.cursoapis.curso_introduccion_apis.Controller;

import com.lta.cursoapis.curso_introduccion_apis.dto.CategoriaDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.exceptions.ResourceNotFoundException;
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
    public ResponseEntity<CategoriaDTO> crearCategoria (@RequestBody CategoriaDTO categoriaDTO) throws BadRequestException {
        CategoriaDTO nuevaCategoria = categoriaService.crearCategoria(categoriaDTO);
            return ResponseEntity.status((HttpStatus.CREATED)).body(nuevaCategoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategoria(){
        List<CategoriaDTO> listaCategoriaDTO = categoriaService.listarCategoria();
        return ResponseEntity.ok(listaCategoriaDTO);
    }


    @GetMapping("/buscar/categoria/{idCategoria}")
    public ResponseEntity<?> categoriaPorId(@PathVariable Long idCategoria){
        Optional<CategoriaDTO> categoriaDTO = categoriaService.categoriaPorId(idCategoria);
        return categoriaDTO.isPresent()
                //ternario (if-else)
                ? ResponseEntity.ok(categoriaDTO.get()) //devuelve ok y la categoria
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria no encontrada");
    }

    @PutMapping("/actualizar/{idCategoria}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long idCategoria,@RequestBody CategoriaDTO categoriaDTO){
        try{
            CategoriaDTO categoriaActualizada = categoriaService.actualizarCategoria(idCategoria, categoriaDTO);
            if(categoriaActualizada != null){
                return new ResponseEntity<>(categoriaActualizada,HttpStatus.OK);
            }else {
                throw new ResourceNotFoundException("categoria no encontrada para actualizar");
                  }
         }catch(Exception exception) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long idCategoria){

        categoriaService.eliminarCategoria(idCategoria);
        //return ResponseEntity.noContent().build(); //ok se elimino va sin cuerpo de mensaje se usa para eliminar
        return ResponseEntity.ok("Categoría eliminada correctamente");
     /*
        try{
            categoriaService.eliminarCategoria(idCategoria);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception exception){
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
     */

    }

}
