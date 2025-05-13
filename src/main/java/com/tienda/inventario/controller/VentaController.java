package com.tienda.inventario.controller;

import com.tienda.inventario.model.EstadisticasDTO;
import com.tienda.inventario.model.Producto;
import com.tienda.inventario.model.Venta;
import com.tienda.inventario.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping("/vender")
    public Venta venderProducto(@RequestBody Map<String, Object> datos) {
        Long idProducto = Long.valueOf(datos.get("idProducto").toString());
        int cantidad = Integer.parseInt(datos.get("cantidad").toString());

        return ventaService.venderProducto(idProducto, cantidad);
    }

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }
    
    @GetMapping("/estadisticas")
    public EstadisticasDTO obtenerEstadisticas() {
        return ventaService.obtenerEstadisticas();
    }
    
    @PostMapping("/pedir")
    public Producto pedirProducto(@RequestBody Map<String, Object> datos) {
        Long idProducto = Long.valueOf(datos.get("idProducto").toString());
        int cantidad = Integer.parseInt(datos.get("cantidad").toString());

        return ventaService.pedirProducto(idProducto, cantidad);
    }
}
