package com.utfpr.delivery.mapper.produto;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.produto.ProdutoInputDTO;
import com.utfpr.delivery.entity.Produto;

@Component
public class ProdutoInputMapper {

	@Autowired
	private ModelMapper modelMapper;

	public Produto mapearEntity(ProdutoInputDTO produtoInputDTO) {
		
		return modelMapper.map(produtoInputDTO, Produto.class);		
		
	}
	
	public List<Produto> mapearLista(List<ProdutoInputDTO> produtoInputDTOs) {
		
		return produtoInputDTOs.stream().map(produtoInputDTO -> mapearEntity(produtoInputDTO)).collect(Collectors.toList());
		
	}
}
