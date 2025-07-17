package com.lta.cursoapis.curso_introduccion_apis.service.impl;

import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.repository.CategoriaRepository;
import com.lta.cursoapis.curso_introduccion_apis.service.CategoriaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria crearCategoria(Categoria categoria) throws BadRequestException {
        if(categoriaRepository.existsByNombreCategoria(categoria.getNombreCategoria())){
            throw new BadRequestException("Ya existe una categoria con ese nombre");
        }
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> listarCategoria() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> categoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Override
    public Categoria actualizarCategoria(Long idCategoria, Categoria categoria) throws Exception {
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
        //sino existe :
                .orElseThrow(() -> new Exception("Producto con ID: " + idCategoria + " no encontrado"));
        //si existe:
        categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
        return categoriaRepository.save(categoriaExistente);
   }

    @Override
    public void eliminarCategoria(Long idCategoria) throws Exception{
        //chequeo que existe el id
        categoriaRepository.findById(idCategoria)
                //sino existe :
                .orElseThrow(() -> new Exception("Producto con ID: " + idCategoria + " no encontrado"));
        //si existe:
        categoriaRepository.deleteById(idCategoria);
    }
}
