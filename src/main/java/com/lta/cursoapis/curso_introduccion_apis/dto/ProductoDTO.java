package com.lta.cursoapis.curso_introduccion_apis.dto;


import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
import com.lta.cursoapis.curso_introduccion_apis.entity.EstadoProducto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private Long idProducto;

    @NotBlank(message = "el nombre del producto no puede estar vacio")
    @Size(max = 100, message = "El nombre del producto no debe exceder los 100 caracteres")
    private String nombreProducto;

    @Size(max = 255, message = "La descricion no debe exceder los 255 caracteres")
    private String descripcion;

    @NotBlank(message = "el precio es obligatorio")
    @Min( value = 0 , message = "el precio debe ser mayor o igual a 0")
    private double precio;

    @NotNull(message = "la cantidad es obligatoria")
    @Min( value = 1 , message = "la cantidad debe ser al menos 1")
    private int cantidad;

    @NotNull(message = "el estado del producto es obligatorio")
    private EstadoProducto estadoProducto;

    private Categoria categoria;


}
