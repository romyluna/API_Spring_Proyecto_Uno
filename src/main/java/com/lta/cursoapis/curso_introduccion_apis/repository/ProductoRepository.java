package com.lta.cursoapis.curso_introduccion_apis.repository;

import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    //como puse que extiende de JpaRepository ya trae en automatico los metodos del crud
    Optional<Producto> findByNombreProducto(String nombreProducto);
    Optional<Producto> findByIdProducto(Long idProducto);

    List<Producto> findByEstadoProducto(EstadoProducto estadoProducto);
}
