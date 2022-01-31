package com.utfpr.delivery.dto.pedidoItem;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PedidoItemsDTO {
	
	@NotNull
	private List<PedidoItemInputDTO> pedidoItems;
	
}
