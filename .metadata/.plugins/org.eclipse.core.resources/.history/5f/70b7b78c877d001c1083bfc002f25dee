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
import com.utfpr.delivery.exception.NotFoundException;
import com.utfpr.delivery.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	ObjectMapper objectMapper;

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

	public Restaurante obterRestaurantePorUuid(String uuid) {

		Restaurante restaurante = restauranteRepository.findByUuid(uuid);
		
		if(restaurante == null) {
			throw new NotFoundException("Restaurante não encontrado");
		}
		
		return restaurante;

	}

	public Restaurante salvar(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}

	public Restaurante alterar(String uuid, Restaurante restaurante) {

		Restaurante restauranteAtual = this.obterRestaurantePorUuid(uuid);

		if (restauranteAtual != null) {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "uuid");

			return restauranteRepository.save(restauranteAtual);
		}

		return null;
	}

	public Restaurante alterarParcial(String uuid, HttpServletRequest request) throws IOException {
		Restaurante restauranteAtual = this.obterRestaurantePorUuid(uuid);

		Restaurante restaurante = objectMapper.readerForUpdating(restauranteAtual).readValue(request.getReader());

		if (restaurante != null) {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

			return restauranteRepository.save(restaurante);
		}

		return null;
	}

	public boolean deletar(String uuid) {

		Restaurante restaurante = this.obterRestaurantePorUuid(uuid);

		if (restaurante != null) {

			try {
				restauranteRepository.delete(restaurante);
				return true;
			} catch (EmptyResultDataAccessException ex) {
				System.out.println(ex.getMessage());
			}

		}

		return false;
	}
}
