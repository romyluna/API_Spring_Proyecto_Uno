package com.lta.cursoapis.curso_introduccion_apis.service.impl;

import com.lta.cursoapis.curso_introduccion_apis.dto.CategoriaDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import com.lta.cursoapis.curso_introduccion_apis.exceptions.BadRequestException;
import com.lta.cursoapis.curso_introduccion_apis.exceptions.ResourceNotFoundException;
import com.lta.cursoapis.curso_introduccion_apis.mapper.CategoriaMapper;
import com.lta.cursoapis.curso_introduccion_apis.mapper.ProductoMapper;
import com.lta.cursoapis.curso_introduccion_apis.repository.CategoriaRepository;
import com.lta.cursoapis.curso_introduccion_apis.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;//inyecto de mapper - categoriaMapper/productoMapper

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) /*throws BadRequestException*/ {
        if(categoriaRepository.existsByNombreCategoria(categoriaDTO.getNombreCategoria())){
            throw new BadRequestException("Ya existe una categoria con ese nombre");
        }

        //convierto lo que viene como dto en una entidad CATEGORIA
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);

        //para que la entidad que creamos arriba se guarde en la base de datos
        Categoria nuevaCategoria = categoriaRepository.save(categoria);

        //la nuevaCategoria la tranformo en DTO para mostrarla como respuesta
        return categoriaMapper.toDTO(nuevaCategoria);


    }

    @Override
    public List<CategoriaDTO> listarCategoria() {
        //return categoriaRepository.findAll();

        List<Categoria> categoria = categoriaRepository.findAll();
        //convierte cada categoria en un categoriaDTO
        return categoria.stream()
                .map(categoriaMapper::toDTO)//seria como hacer esto .map(categoria -> categoriaMapper.toDTO(categoria)) para cada categoria
                .toList();

    }

    @Override
    public Optional<CategoriaDTO> categoriaPorId(Long idCategoria) {
        //return categoriaRepository.findById(idCategoria);

        Optional<Categoria> categoria= categoriaRepository.findById(idCategoria);
        return categoria.map(categoriaMapper::toDTO);

    }

    @Override
    public CategoriaDTO actualizarCategoria(Long idCategoria, CategoriaDTO categoriaDTO) /*throws Exception*/ {

        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
        //sino existe :
                //.orElseThrow(() -> new Exception("Producto con ID: " + idCategoria + " no encontrado"));
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + idCategoria + " no encontrado"));
        //si existe:
        categoriaExistente.setNombreCategoria(categoriaDTO.getNombreCategoria());

        //guardando la entidad Categoria en la base de datos.
        Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
        //devuelvo un dto
        return categoriaMapper.toDTO(categoriaActualizada);

   }

    @Override
    public void eliminarCategoria(Long idCategoria){
        //chequeo que existe el id
        categoriaRepository.findById(idCategoria)
                //sino existe :
                //.orElseThrow(() -> new Exception("Producto con ID: " + idCategoria + " no encontrado"));
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + idCategoria + " no encontrado"));
        //si existe:
        categoriaRepository.deleteById(idCategoria);
    }
}
