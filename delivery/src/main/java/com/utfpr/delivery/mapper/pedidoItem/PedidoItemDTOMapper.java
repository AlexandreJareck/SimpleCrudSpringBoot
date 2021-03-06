package com.utfpr.delivery.mapper.pedidoItem;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.pedidoItem.PedidoItemDTO;
import com.utfpr.delivery.entity.PedidoItem;

@Component
public class PedidoItemDTOMapper {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoItemDTO mapearDTO(PedidoItem pedido) {

		PedidoItemDTO  pedidoDTO = modelMapper.map(pedido, PedidoItemDTO .class);
		
		return pedidoDTO;
	}

	public List<PedidoItemDTO> mapearLista(List<PedidoItem> pedidos) {
		
		return pedidos.stream().map(pedido -> mapearDTO(pedido)).collect(Collectors.toList());
	}

}
