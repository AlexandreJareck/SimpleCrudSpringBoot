package com.utfpr.delivery.dto.pedidoItem;

import java.math.BigDecimal;

import com.utfpr.delivery.dto.produto.ProdutoDTO;

import lombok.Data;

@Data
public class PedidoItemDTO {
	
	private String uuid;
	private ProdutoDTO produto;
	private Long quantidade;
	private BigDecimal valor;
	
}
