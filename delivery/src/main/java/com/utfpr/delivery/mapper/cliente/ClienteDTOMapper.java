package com.utfpr.delivery.mapper.cliente;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.cliente.ClienteDTO;
import com.utfpr.delivery.entity.Cliente;

@Component
public class ClienteDTOMapper {

	@Autowired
	private ModelMapper modelMapper;

	public ClienteDTO mapearDTO(Cliente cliente) {
		
		return modelMapper.map(cliente, ClienteDTO.class);
		
	}
	
	public List<ClienteDTO> mapearLista(List<Cliente> clientes) {
		
		return clientes.stream().map(cliente -> mapearDTO(cliente)).collect(Collectors.toList());
		
	}
}
