package com.utfpr.delivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.utfpr.delivery.entity.Cliente;
import com.utfpr.delivery.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente obterClientePorId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			return cliente.get();
		}

		return null;
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente alterar(Long id, Cliente cliente) {

		Cliente clienteAtual = this.obterClientePorId(id);

		if (clienteAtual != null) {
			BeanUtils.copyProperties(cliente, clienteAtual, "id");

			return clienteRepository.save(clienteAtual);
		}

		return null;
	}

	public boolean deletar(Long id) {

		Cliente cliente = this.obterClientePorId(id);

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
