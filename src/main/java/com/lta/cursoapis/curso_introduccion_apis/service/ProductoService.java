package com.lta.cursoapis.curso_introduccion_apis.service;

import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto registrarProducto(Long idCategoria,Producto producto) /*throws Exception*/;

    List<Producto> listarProductos();

    Optional<Producto> buscarPorNombre(String Nombre);

    Optional<Producto> buscarPorId(Long idProducto);

    Producto actualizarProducto(Long idProducto, Producto producto) /*throws Exception*/;

    void eliminarProducto(Long idProducto) /*throws Exception*/;

    Producto cambiarEstadoProducto(Long idProducto, EstadoProducto estadoProducto) /*throws Exception*/;

    //si quiero obtener todos los productos del estado disponible

    List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto);
}
