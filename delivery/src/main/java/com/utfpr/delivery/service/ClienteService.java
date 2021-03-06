package com.utfpr.delivery.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.utfpr.delivery.dto.cliente.ClienteDTO;
import com.utfpr.delivery.dto.cliente.ClienteInputDTO;
import com.utfpr.delivery.dto.cliente.ClienteOutputDTO;
import com.utfpr.delivery.entity.Cliente;
import com.utfpr.delivery.exception.NotFoundException;
import com.utfpr.delivery.mapper.cliente.ClienteDTOMapper;
import com.utfpr.delivery.mapper.cliente.ClienteInputMapper;
import com.utfpr.delivery.mapper.cliente.ClienteOutputMapper;
import com.utfpr.delivery.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteOutputMapper clienteOutputMapper;
	@Autowired
	private ClienteDTOMapper clienteDTOMapper;
	@Autowired
	private ClienteInputMapper clienteInputMapper;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<ClienteOutputDTO> obterClientes() {
		
		List<Cliente> clientes = clienteRepository.findAll();	
		List<ClienteOutputDTO> clienteOutputDTOs = clienteOutputMapper.mapearLista(clientes);
		
		return clienteOutputDTOs;
	}	

	public Cliente obterClientePorUuid(String uuid) {
		
		Cliente cliente = clienteRepository.findByUuid(uuid);		

		if (cliente == null) {
			throw new NotFoundException("Cliente não encontrado");
		}

		return cliente;

	}
	
	public ClienteDTO obterClienteDTOPorUuid(String uuid) {

		return clienteDTOMapper.mapearDTO(obterClientePorUuid(uuid));

	}
	
	public ClienteDTO salvar(ClienteInputDTO clienteInputDTO) {

		Cliente cliente = clienteInputMapper.mapearEntity(clienteInputDTO);		
		cliente = clienteRepository.save(cliente);		
		
		return clienteDTOMapper.mapearDTO(cliente);
	}
	
	public ClienteDTO atualizar(String uuid, ClienteInputDTO clienteInputDTO) {		 
		
		Cliente cliente = clienteInputMapper.mapearEntity(clienteInputDTO);		
		Cliente clienteAtual = this.obterClientePorUuid(uuid);
		BeanUtils.copyProperties(cliente, clienteAtual, "id", "uuid");
		
		cliente = clienteRepository.save(clienteAtual);	
		
		return clienteDTOMapper.mapearDTO(cliente);		
	}
	
	public boolean deletar(String uuid) {

		Cliente cliente = this.obterClientePorUuid(uuid);

		if (cliente != null) {

			try {
				clienteRepository.delete(cliente);
				return true;
			} catch (EmptyResultDataAccessException ex) {
				System.out.println(ex.getMessage());
			}

		}

		return false;
	}
}
