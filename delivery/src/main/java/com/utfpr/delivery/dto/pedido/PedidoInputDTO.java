package com.utfpr.delivery.dto.pedido;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoInputDTO {
	private String restaurante;
	private String cliente;
	private BigDecimal valorTotal;
}
