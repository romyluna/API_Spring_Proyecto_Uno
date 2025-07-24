package com.lta.cursoapis.curso_introduccion_apis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    private Long idCategoria;

    @NotBlank(message = "el nombre de la categoria es obligatoria")
    @Size(min = 3 , max = 50, message = "El nombre de la categoria debe tener entre 3 y 50 caracteres")
    private String nombreCategoria;


}
