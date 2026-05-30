package com.inventario.backend.controller;

import com.inventario.backend.dto.ProductoDto;
import com.inventario.backend.dto.ProductoRequestDto;
import com.inventario.backend.response.ApiResponse;
import com.inventario.backend.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;


    @PostMapping
    public ResponseEntity<ApiResponse<ProductoDto>> registrarProducto(
            @Valid @RequestBody ProductoRequestDto request) {
        ProductoDto producto = productoService.registrarProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Producto registrado correctamente", producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoDto>> editarProducto(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoRequestDto request) {
        ProductoDto producto = productoService.editarProducto(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Producto actualizado correctamente", producto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<ProductoDto>> cambiarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String estado = body.get("estado");
        ProductoDto producto = productoService.cambiarEstado(id, estado);
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado correctamente", producto));
    }

    @PatchMapping("/{id}/stock-minimo")
    public ResponseEntity<ApiResponse<ProductoDto>> actualizarStockMinimo(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        Integer nuevoStockMinimo = body.get("stockMinimo");
        ProductoDto producto = productoService.actualizarStockMinimo(id, nuevoStockMinimo);
        return ResponseEntity.ok(ApiResponse.ok("Stock mínimo actualizado correctamente", producto));
    }

        @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoDto>>> listarProductos() {
        List<ProductoDto> productos = productoService.listarProductos();
        return ResponseEntity.ok(
                ApiResponse.ok("Productos obtenidos correctamente", productos)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoDto>> buscarProducto(@PathVariable Integer id) {
        ProductoDto producto = productoService.buscarPorId(id);
        return ResponseEntity.ok(
                ApiResponse.ok("Producto encontrado", producto)
        );
    }

    @GetMapping("/bajo-stock")
    public ResponseEntity<ApiResponse<List<ProductoDto>>> listarBajoStock() {
        List<ProductoDto> productos = productoService.listarBajoStock();
        return ResponseEntity.ok(
                ApiResponse.ok("Productos con bajo stock", productos)
        );
    }
}
