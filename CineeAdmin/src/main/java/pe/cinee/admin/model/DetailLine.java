package pe.cinee.admin.model;

import java.math.BigDecimal;

public record DetailLine(String productCode, String productName, String category,
                         int quantity, BigDecimal unitPrice, BigDecimal subtotal) { }
