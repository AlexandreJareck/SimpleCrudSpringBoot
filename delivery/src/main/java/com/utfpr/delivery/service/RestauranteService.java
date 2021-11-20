package com.utfpr.delivery.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utfpr.delivery.entity.Restaurante;
import com.utfpr.delivery.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired ObjectMapper objectMapper;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante obterRestaurantePorId(Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);

		if (restaurante.isPresent()) {
			return restaurante.get();
		}

		return null;
	}

	public Restaurante salvar(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}

	public Restaurante alterar(Long id, Restaurante restaurante) {

		Restaurante restauranteAtual = this.obterRestaurantePorId(id);

		if (restauranteAtual != null) {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

			return restauranteRepository.save(restauranteAtual);
		}

		return null;
	}
	
	public Restaurante alterarParcial( Long id, HttpServletRequest request) throws IOException {
		Restaurante restauranteAtual = this.obterRestaurantePorId(id);
		Restaurante restaurante = objectMapper.readerForUpdating(restauranteAtual).readValue(request.getReader());

		if (restauranteAtual != null) {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

			return restauranteRepository.save(restaurante);
		}

		return null;
	}

	public boolean deletar(Long id) {

		Restaurante restaurante = this.obterRestaurantePorId(id);

		if (restaurante != null) {

			try {
				restauranteRepository.delete(restaurante);
				return true;
			} 
			catch (EmptyResultDataAccessException ex) {
				System.out.println(ex.getMessage());
			}

		}

		return false;
	}
}
