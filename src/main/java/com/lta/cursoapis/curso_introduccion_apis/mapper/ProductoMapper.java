package com.lta.cursoapis.curso_introduccion_apis.mapper;

import com.lta.cursoapis.curso_introduccion_apis.dto.ProductoDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    @Autowired
    private ModelMapper modelMapper;

    //convierto un objeto productodto en un objeto producto
    public Producto toEntity(ProductoDTO productoDTO){
        //convierto el dto a un objeto
        return modelMapper.map(productoDTO,Producto.class); //cada atributo que tenga poductoDTO lo va a mapear con producto.class
    }

    //actualiza un producto existente con datos de un dto

    public void toEntity(ProductoDTO productoDTO,Producto productoExistente){
         modelMapper.map(productoDTO,productoExistente);
    }

    public ProductoDTO toDTO(Producto producto){
        //convierto el dto a un objeto
        return modelMapper.map(producto,ProductoDTO.class);
    }

}
