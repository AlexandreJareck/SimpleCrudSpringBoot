package com.utfpr.delivery.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.ProdutoResumoDTO;
import com.utfpr.delivery.dto.RestauranteResumoDTO;
import com.utfpr.delivery.entity.Produto;
import com.utfpr.delivery.entity.Restaurante;

@Component
public class ProdutoResumoOutputMapper {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoResumoDTO mapearDTO(Produto produto) {

		ProdutoResumoDTO produtoResumoDTO = modelMapper.map(produto, ProdutoResumoDTO.class);
		
		return produtoResumoDTO;
	}

	public List<ProdutoResumoDTO> mapearLista(List<Produto> produtos) {

		return produtos.stream().map(produto -> mapearDTO(produto)).collect(Collectors.toList());
	}

}