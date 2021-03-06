package com.utfpr.delivery.mapper.produto;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.produto.ProdutoOutputDTO;
import com.utfpr.delivery.entity.Produto;

@Component
public class ProdutoOutputMapper {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoOutputDTO mapearDTO(Produto produto) {
		
		return modelMapper.map(produto, ProdutoOutputDTO.class);
		
	}
	
	public List<ProdutoOutputDTO> mapearLista(List<Produto> produtos) {
		
		return produtos.stream().map(produto-> mapearDTO(produto)).collect(Collectors.toList());
		
	}
}
