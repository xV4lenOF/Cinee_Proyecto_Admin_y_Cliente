package pe.cinee.admin.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleRecord(
        String id,
        LocalDateTime date,
        String customer,
        String document,
        String email,
        String phone,
        String movie,
        String functionDate,
        String functionTime,
        String roomCode,
        String roomNumber,
        String seats,
        String payment,
        BigDecimal total,
        String state,
        String ticketCode,
        String qrCode,
        String functionCode) { }
