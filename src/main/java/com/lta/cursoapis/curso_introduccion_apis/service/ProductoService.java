package com.lta.cursoapis.curso_introduccion_apis.service;

import com.lta.cursoapis.curso_introduccion_apis.dto.ProductoDTO;
import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import com.lta.cursoapis.curso_introduccion_apis.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    ProductoDTO registrarProducto(Long idCategoria, ProductoDTO productoDTO) /*throws Exception*/;

    List<ProductoDTO> listarProductos();

    Optional<ProductoDTO> buscarPorNombre(String Nombre);

    Optional<ProductoDTO> buscarPorId(Long idProducto);

    ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoDTO) /*throws Exception*/;

    void eliminarProducto(Long idProducto) /*throws Exception*/;

    ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto estadoProducto) /*throws Exception*/;

    //si quiero obtener todos los productos del estado disponible

    List<ProductoDTO> obtenerProductosPorEstado(EstadoProducto estadoProducto);
}
