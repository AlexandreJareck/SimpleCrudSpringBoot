package com.utfpr.delivery.mapper.cliente;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.cliente.ClienteOutputDTO;
import com.utfpr.delivery.entity.Cliente;

@Component
public class ClienteOutputMapper {

	@Autowired
	private ModelMapper modelMapper;

	public ClienteOutputDTO mapearDTO(Cliente cliente) {
		
		return modelMapper.map(cliente, ClienteOutputDTO.class);
		
	}
	
	public List<ClienteOutputDTO> mapearLista(List<Cliente> clientes) {
		
		return clientes.stream().map(cliente -> mapearDTO(cliente)).collect(Collectors.toList());
		
	}
}
