package com.lta.cursoapis.curso_introduccion_apis.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;


    @Column(name = "nombre_categoria",nullable = false,length = 50)
    private String nombreCategoria;


}
