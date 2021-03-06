package com.utfpr.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.delivery.dto.cliente.ClienteDTO;
import com.utfpr.delivery.dto.cliente.ClienteInputDTO;
import com.utfpr.delivery.dto.cliente.ClienteOutputDTO;
import com.utfpr.delivery.entity.Cliente;
import com.utfpr.delivery.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;


	@GetMapping
	@ResponseBody
	public List<ClienteOutputDTO> obterClientes() {
		return clienteService.obterClientes();		
	}
	
	@GetMapping("/{uuid}")
	@ResponseBody
	public ClienteDTO obterClientePorUuid(@PathVariable String uuid) {		
		return clienteService.obterClienteDTOPorUuid(uuid);				
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private ClienteDTO adicionar(@RequestBody ClienteInputDTO clienteInputDTO) {
		
		return clienteService.salvar(clienteInputDTO);
	}

	@PutMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	private ClienteDTO alterar(@PathVariable String uuid, @RequestBody ClienteInputDTO clienteInputDTO) {		
		return clienteService.atualizar(uuid, clienteInputDTO);			
	}

	@DeleteMapping("/{uuid}")
	private ResponseEntity<Cliente> deletar(@PathVariable String uuid) {

		if (clienteService.deletar(uuid)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().build();
	}
}
