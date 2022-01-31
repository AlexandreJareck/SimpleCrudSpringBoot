package com.utfpr.delivery.mapper.pedido;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.pedido.PedidoInputDTO;
import com.utfpr.delivery.entity.Pedido;

@Component
public class PedidoInputDTOMapper {

	@Autowired
	private ModelMapper modelMapper;

public Pedido mapearEntity(PedidoInputDTO pedidoInputDTO) {
		
		return modelMapper.map(pedidoInputDTO, Pedido.class);	
		
	}
	
	public List<Pedido> mapearLista(List<PedidoInputDTO> pedidoInputDTOs) {
		
		return pedidoInputDTOs.stream().map(pedidoItemInputDTO -> mapearEntity(pedidoItemInputDTO)).collect(Collectors.toList());
		
	}

}
