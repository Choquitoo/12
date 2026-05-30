package com.inventario.backend.repository;

import com.inventario.backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo")
    List<Producto> findProductosBajoStock();

    List<Producto> findByEstado(Producto.Estado estado);

    @Query("SELECT COUNT(p) > 0 FROM Producto p WHERE p.codigoSerie = :codigo")
    boolean yaExisteCodigoSerie(@Param("codigo") String codigo);

    @Query("SELECT COUNT(p) > 0 FROM Producto p WHERE p.codigoSerie = :codigo AND p.id <> :id")
    boolean otroProductoTieneElCodigoSerie(@Param("codigo") String codigo, @Param("id") Integer id);
}