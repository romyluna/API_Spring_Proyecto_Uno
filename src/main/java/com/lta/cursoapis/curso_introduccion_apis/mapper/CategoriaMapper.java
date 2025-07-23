package com.lta.cursoapis.curso_introduccion_apis.mapper;


import com.lta.cursoapis.curso_introduccion_apis.dto.CategoriaDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaDTO toDTO(Categoria categoria){
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public Categoria toEntity(CategoriaDTO categoriaDTO){
        return modelMapper.map(categoriaDTO, Categoria.class);
    }

}
