package com.tienda.inventario.service;

import com.tienda.inventario.model.EstadisticasDTO;
import com.tienda.inventario.model.Producto;
import com.tienda.inventario.model.Venta;
import com.tienda.inventario.repository.ProductoRepository;
import com.tienda.inventario.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public Venta venderProducto(Long idProducto, int cantidad) {
        Producto producto = productoRepository.findById(idProducto).orElseThrow();

        if (producto.getCantidadDisponible() < cantidad) {
            throw new IllegalArgumentException("Inventario insuficiente");
        }

        producto.setCantidadDisponible(producto.getCantidadDisponible() - cantidad);
        productoRepository.save(producto);

        double precioFinal = calcularPrecioConIVA(producto);

        Venta venta = new Venta();
        venta.setProducto(producto);
        venta.setCantidadVendida(cantidad);
        venta.setPrecioUnitarioConIVA(precioFinal);
        venta.setFecha(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    private double calcularPrecioConIVA(Producto p) {
        double iva = switch (p.getTipo()) {
            case PAPELERIA -> 0.16;
            case SUPERMERCADO -> 0.04;
            case DROGUERIA -> 0.12;
        };
        return p.getPrecioBase() * (1 + iva);
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
    
    public EstadisticasDTO obtenerEstadisticas() {
        List<Venta> ventas = ventaRepository.findAll();

        if (ventas.isEmpty()) {
            return new EstadisticasDTO("N/A", "N/A", 0.0, 0.0);
        }

        Map<String, Integer> ventasPorProducto = new HashMap<>();
        double totalDinero = 0.0;
        int totalUnidades = 0;

        for (Venta venta : ventas) {
            String nombre = venta.getProducto().getNombre();
            int cantidad = venta.getCantidadVendida();
            ventasPorProducto.put(nombre, ventasPorProducto.getOrDefault(nombre, 0) + cantidad);

            totalDinero += venta.getPrecioUnitarioConIVA() * cantidad;
            totalUnidades += cantidad;
        }

        String masVendido = Collections.max(ventasPorProducto.entrySet(), Map.Entry.comparingByValue()).getKey();
        String menosVendido = Collections.min(ventasPorProducto.entrySet(), Map.Entry.comparingByValue()).getKey();
        double promedio = totalUnidades > 0 ? totalDinero / totalUnidades : 0.0;

        return new EstadisticasDTO(masVendido, menosVendido, totalDinero, promedio);
    }

    public Producto pedirProducto(Long idProducto, int cantidad) {
        Producto producto = productoRepository.findById(idProducto).orElseThrow();

        producto.setCantidadDisponible(producto.getCantidadDisponible() + cantidad);
        return productoRepository.save(producto);
        
    }
    
}
