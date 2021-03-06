package com.utfpr.delivery.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.delivery.dto.restaurante.RestauranteDTO;
import com.utfpr.delivery.dto.restaurante.RestauranteInputDTO;
import com.utfpr.delivery.dto.restaurante.RestauranteOutputDTO;
import com.utfpr.delivery.entity.Restaurante;
import com.utfpr.delivery.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;	

	@GetMapping
	@ResponseBody
	public List<RestauranteDTO> obterRestaurantes() {	
		
		return restauranteService.listar();		 
	}

	@GetMapping("/{uuid}")
	@ResponseBody
	public RestauranteDTO obterRestaurantePorUuid(@PathVariable String uuid) {			
		return restauranteService.obterRestauranteDTOPorUuid(uuid);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private RestauranteOutputDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		
		return restauranteService.salvar(restauranteInputDTO);

	}

	@PutMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	private RestauranteDTO alterar(@PathVariable String uuid, @RequestBody Restaurante restaurante) {
		return restauranteService.alterar(uuid, restaurante);			
	}
	
	@PatchMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	public @ResponseBody RestauranteDTO alterarParcial(@PathVariable String uuid, HttpServletRequest request) throws IOException {				
		return restauranteService.alterarParcial(uuid, request);
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<Restaurante> deletar(@PathVariable String uuid) {

		if (restauranteService.deletar(uuid)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().build();
	}
}
