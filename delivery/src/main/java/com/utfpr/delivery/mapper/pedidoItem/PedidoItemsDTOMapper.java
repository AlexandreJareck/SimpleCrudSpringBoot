package com.utfpr.delivery.mapper.pedidoItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.pedidoItem.PedidoItemDTO;
import com.utfpr.delivery.entity.PedidoItem;

@Component
public class PedidoItemsDTOMapper {

	@Autowired
	private ModelMapper modelMapper;

	public List<PedidoItemDTO> mapearDTO(List<PedidoItem> pedidoItems) {
		
		List<PedidoItemDTO> pedidoItemsDTO =  new ArrayList<PedidoItemDTO>();
		
		for (PedidoItem item : pedidoItems) {
			PedidoItemDTO pedidoItemDTO = modelMapper.map(item, PedidoItemDTO.class);
			pedidoItemDTO.setValor(item.getValor().multiply(new BigDecimal(item.getQuantidade())));
			pedidoItemsDTO.add(pedidoItemDTO);
		}
		
		return pedidoItemsDTO;
		
	} 

}
