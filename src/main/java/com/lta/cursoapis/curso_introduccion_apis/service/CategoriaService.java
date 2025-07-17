package com.lta.cursoapis.curso_introduccion_apis.service;

import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    Categoria crearCategoria(Categoria categoria) throws BadRequestException;

    List<Categoria> listarCategoria ();

    Optional<Categoria> categoriaPorId(Long idCategoria);

    Categoria actualizarCategoria(Long idCategoria, Categoria categoria) throws Exception;

    void eliminarCategoria(Long idCategoria) throws Exception;
}
