package com.inventario.backend.repository;

import com.inventario.backend.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoInventarioRepository
        extends JpaRepository<MovimientoInventario, Integer> {

    List<MovimientoInventario> findByProductoId(Integer productoId);

    List<MovimientoInventario> findByTipo(MovimientoInventario.Tipo tipo);

    @Query("SELECT m FROM MovimientoInventario m " +
            "WHERE (:motivo IS NULL OR m.motivo = :motivo) " +
            "AND (:desde IS NULL OR m.fecha >= :desde) " +
            "AND (:hasta IS NULL OR m.fecha <= :hasta)")
    List<MovimientoInventario> filtrarMovimientos(
            @Param("motivo") MovimientoInventario.Motivo motivo,
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta
    );
}