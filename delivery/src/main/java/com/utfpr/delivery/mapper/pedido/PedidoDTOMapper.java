package com.utfpr.delivery.mapper.pedido;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.pedido.PedidoDTO;
import com.utfpr.delivery.entity.Pedido;

@Component
public class PedidoDTOMapper {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoDTO mapearDTO(Pedido pedido) {
		
		PedidoDTO  pedidoDTO = modelMapper.map(pedido, PedidoDTO .class);
		
		return pedidoDTO;
	}

	public List<PedidoDTO> mapearLista(List<Pedido> pedidos) {
		
		return pedidos.stream().map(pedido -> mapearDTO(pedido)).collect(Collectors.toList());
	}

}
