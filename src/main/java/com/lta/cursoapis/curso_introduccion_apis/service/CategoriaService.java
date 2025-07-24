package com.lta.cursoapis.curso_introduccion_apis.service;

import com.lta.cursoapis.curso_introduccion_apis.dto.CategoriaDTO;


import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    CategoriaDTO crearCategoria(CategoriaDTO categoria) /*throws BadRequestException*/;

    List<CategoriaDTO> listarCategoria ();

    Optional<CategoriaDTO> categoriaPorId(Long idCategoria);

    CategoriaDTO actualizarCategoria(Long idCategoria, CategoriaDTO categoriaDTO) /*throws Exception*/;

    void eliminarCategoria(Long idCategoria);
}
