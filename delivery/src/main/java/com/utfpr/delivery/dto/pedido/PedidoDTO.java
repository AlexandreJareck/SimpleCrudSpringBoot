package com.utfpr.delivery.dto.pedido;


import java.util.List;

import com.utfpr.delivery.dto.cliente.ClienteDTO;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemDTO;
import com.utfpr.delivery.dto.restaurante.RestauranteDTO;

import lombok.Data;

@Data
public class PedidoDTO {
	private String uuid;	
	private ClienteDTO cliente;
	private RestauranteDTO restaurante;
	
	private List<PedidoItemDTO> pedidoItems;
}
