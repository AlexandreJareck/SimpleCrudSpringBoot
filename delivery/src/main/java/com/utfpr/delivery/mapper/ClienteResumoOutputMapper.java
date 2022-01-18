package com.utfpr.delivery.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.ClienteResumoDTO;
import com.utfpr.delivery.dto.ProdutoResumoDTO;
import com.utfpr.delivery.dto.RestauranteResumoDTO;
import com.utfpr.delivery.entity.Cliente;
import com.utfpr.delivery.entity.Produto;
import com.utfpr.delivery.entity.Restaurante;

@Component
public class ClienteResumoOutputMapper {

	@Autowired
	private ModelMapper modelMapper;

	public ClienteResumoDTO mapearDTO(Cliente cliente) {

		ClienteResumoDTO clienteResumoDTO = modelMapper.map(cliente, ClienteResumoDTO.class);
		
		return clienteResumoDTO;
	}

	public List<ClienteResumoDTO> mapearLista(List<Cliente> clientes) {

		return clientes.stream().map(cliente -> mapearDTO(cliente)).collect(Collectors.toList());
	}

}