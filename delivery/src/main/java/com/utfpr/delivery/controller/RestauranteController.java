package com.utfpr.delivery.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.delivery.dto.RestauranteResumoDTO;
import com.utfpr.delivery.entity.Restaurante;
import com.utfpr.delivery.mapper.RestauranteResumoOutputMapper;
import com.utfpr.delivery.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteResumoOutputMapper restauranteResumoOutputMapper;

	@GetMapping
	@ResponseBody
	public List<RestauranteResumoDTO> obterRestaurantes() {	
		
		return restauranteResumoOutputMapper.mapearLista(restauranteService.listar());		 
	}

	@GetMapping("/{id}")
	@ResponseBody
	public Restaurante obterRestaurantePorId(@PathVariable Long id) {

		return restauranteService.obterRestaurantePorId(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private Restaurante adicionar(@RequestBody Restaurante restaurante) {

		return restauranteService.salvar(restaurante);

	}

	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	private Restaurante alterar(@PathVariable Long id, @RequestBody Restaurante restaurante) {

		return restauranteService.alterar(id, restaurante);
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public @ResponseBody Restaurante alterarParcial(@PathVariable Long id, HttpServletRequest request) throws IOException
	{
		return restauranteService.alterarParcial(id, request);	    
	}

	@DeleteMapping("/{id}")
	private ResponseEntity deletar(@PathVariable Long id) {

		if (restauranteService.deletar(id)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().build();
	}
}
