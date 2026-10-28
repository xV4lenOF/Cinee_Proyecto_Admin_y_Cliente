package pe.cinee.admin.model;

import java.util.List;

public record SaleDetail(SaleRecord sale, List<DetailLine> lines) { }
