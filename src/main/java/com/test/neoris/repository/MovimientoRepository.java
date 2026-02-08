package com.test.neoris.repository;

import com.test.neoris.dto.ReporteInterface;
import com.test.neoris.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
        @Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = :clienteId " +
                        "AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
        List<Movimiento> generarReporte(
                        @Param("clienteId") Long clienteId,
                        @Param("fechaInicio") LocalDateTime fechaInicio,
                        @Param("fechaFin") LocalDateTime fechaFin);

        @Query(value = "CALL sp_generar_reporte(:clienteId, :fechaInicio, :fechaFin)", nativeQuery = true)
        List<ReporteInterface> generarReporteSp(
                        @Param("clienteId") Long clienteId,
                        @Param("fechaInicio") LocalDateTime fechaInicio,
                        @Param("fechaFin") LocalDateTime fechaFin);
}
