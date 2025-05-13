package com.tienda.inventario.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadisticasDTO {
    private String productoMasVendido;
    private String productoMenosVendido;
    private double totalDineroVentas;
    private double promedioVentaPorUnidad;
}
