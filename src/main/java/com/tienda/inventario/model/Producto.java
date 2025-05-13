package com.tienda.inventario.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;

    private int cantidadDisponible;

    private int cantidadPedido;
    
    private int cantidad;
    
    private int cantidadMinima;

    private double precioBase;
    
    private double iva;
    
    private double precioFinal;
    
    private boolean isPedido; 
}
