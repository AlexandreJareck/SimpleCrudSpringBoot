package com.utfpr.delivery.mapper.pedidoItem;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.pedidoItem.PedidoItemInputDTO;
import com.utfpr.delivery.entity.PedidoItem;

@Component
public class PedidoItemInputDTOMapper {

	@Autowired
	private ModelMapper modelMapper;

public PedidoItem mapearEntity(PedidoItemInputDTO pedidoItemInputDTO) {
		
	PedidoItem pedidoItem = modelMapper.map(pedidoItemInputDTO, PedidoItem.class);
		
		return pedidoItem;
		
	}
	
	public List<PedidoItem> mapearLista(List<PedidoItemInputDTO> pedidoItemInputDTOs) {
		
		return pedidoItemInputDTOs.stream()
				.map(orderItemInputDTO -> mapearEntity(orderItemInputDTO))
				.collect(Collectors.toList());
		
	}

}
