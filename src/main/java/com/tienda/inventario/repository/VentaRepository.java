package com.tienda.inventario.repository;

import com.tienda.inventario.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}