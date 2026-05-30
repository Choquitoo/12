package com.inventario.backend.controller;

import com.inventario.backend.dto.MovimientoRequestDto;
import com.inventario.backend.dto.MovimientoResponseDto;
import com.inventario.backend.dto.ReporteResponseDto;
import com.inventario.backend.response.ApiResponse;
import com.inventario.backend.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping("/entrada")
    public ResponseEntity<ApiResponse<MovimientoResponseDto>> registrarEntrada( @RequestBody MovimientoRequestDto  request) {

        MovimientoResponseDto movimiento = movimientoService.registrarEntrada(request);
        return ResponseEntity.ok(
                ApiResponse.ok("Entrada registrada correctamente", movimiento)
        );
    }

    @PostMapping("/salida")
    public ResponseEntity<ApiResponse<MovimientoResponseDto>> registrarSalida( @RequestBody MovimientoRequestDto request) {  // ← RequestDTO no ResponseDTO

        MovimientoResponseDto movimiento = movimientoService.registrarSalida(request);
        return ResponseEntity.ok(
                ApiResponse.ok("Salida registrada correctamente", movimiento)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovimientoResponseDto>>> listarMovimientos() {
        List<MovimientoResponseDto> movimientos = movimientoService.listarMovimientos();
        return ResponseEntity.ok(
                ApiResponse.ok("Movimientos obtenidos correctamente", movimientos)
        );
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ApiResponse<List<MovimientoResponseDto>>> listarPorProducto(@PathVariable Integer id) {

        List<MovimientoResponseDto> movimientos = movimientoService.listarPorProducto(id);
        return ResponseEntity.ok(
                ApiResponse.ok("Movimientos del producto obtenidos", movimientos)
        );
    }

     @GetMapping("/reporte")
    public ResponseEntity<ApiResponse<ReporteResponseDto>> obtenerReporte(
            @RequestParam(required = false) String motivo,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        ReporteResponseDto reporte =
                movimientoService.listarConFiltros(motivo, desde, hasta);
        return ResponseEntity.ok(
                ApiResponse.ok("Reporte generado correctamente", reporte)
        );
    }
}
