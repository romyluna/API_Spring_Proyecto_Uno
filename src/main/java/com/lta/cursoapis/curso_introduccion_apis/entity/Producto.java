package com.lta.cursoapis.curso_introduccion_apis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_producto")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // 👈 importante para que en el swagger no me lo pida poner
    private Long idProducto;

    @Column(name = "nombre_producto",nullable = false,length = 100)
    private String nombreProducto;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio",nullable = false)
    private double precio;

    @Column(name = "cantidad",nullable = false)
    private int cantidad;

    @Enumerated(EnumType.STRING) //quiero guardar el enum en la base como un string
    @Column(name = "estado_producto",nullable = false)
    private EstadoProducto estadoProducto; //ENUM

    //relacion con la tabla categoria

    @ManyToOne
    @JoinColumn(name="id_categoria",referencedColumnName = "id_categoria")
    private Categoria categoria;

}
