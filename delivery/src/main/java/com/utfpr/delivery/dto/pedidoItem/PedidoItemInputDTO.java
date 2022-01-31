package com.utfpr.delivery.dto.pedidoItem;


import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class PedidoItemInputDTO {
	@NotNull
	private String produto;
	@NotNull
	private Long quantidade;	
}
