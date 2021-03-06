package com.utfpr.delivery.mapper.produto;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.produto.ProdutoDTO;
import com.utfpr.delivery.entity.Produto;

@Component
public class ProdutoDTOMapper {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoDTO mapearDTO(Produto produto) {
		
		return modelMapper.map(produto, ProdutoDTO.class);
		
	}
	
	public List<ProdutoDTO> mapearLista(List<Produto> produtos) {
		
		return produtos.stream().map(produto-> mapearDTO(produto)).collect(Collectors.toList());
		
	}
}
