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
import com.utfpr.delivery.dto.restaurante.RestauranteDTO;
import com.utfpr.delivery.dto.restaurante.RestauranteInputDTO;
import com.utfpr.delivery.dto.restaurante.RestauranteOutputDTO;
import com.utfpr.delivery.entity.Restaurante;
import com.utfpr.delivery.exception.NotFoundException;
import com.utfpr.delivery.mapper.restaurante.RestauranteDTOMapper;
import com.utfpr.delivery.mapper.restaurante.RestauranteInputMapper;
import com.utfpr.delivery.mapper.restaurante.RestauranteOutputMapper;
import com.utfpr.delivery.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteInputMapper restauranteInputMapper;

	@Autowired
	private RestauranteDTOMapper restauranteDTOMapper;

	@Autowired
	private RestauranteOutputMapper restauranteOutputMapper;

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private RestauranteOutputMapper restauranteResumoOutputMapper;

	public List<RestauranteDTO> listar() {

		List<Restaurante> restaurantes = restauranteRepository.findAll();

		return restauranteDTOMapper.mapearLista(restaurantes);
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

		if (restaurante == null) {
			throw new NotFoundException("Restaurante n??o encontrado");
		}

		return restaurante;

	}

	public RestauranteDTO obterRestauranteDTOPorUuid(String uuid) {

		return restauranteDTOMapper.mapearDTO(obterRestaurantePorUuid(uuid));

	}

	public RestauranteOutputDTO obterRestauranteResumo(Long id) {

		Optional<Restaurante> restaurante = restauranteRepository.findById(id);

		if (!restaurante.isPresent()) {
			throw new NotFoundException("Restaurante n??o encontrado");
		}

		RestauranteOutputDTO restauranteDTO = restauranteResumoOutputMapper.mapearDTO(restaurante.get());

		return restauranteDTO;

	}

	public RestauranteOutputDTO salvar(RestauranteInputDTO restauranteInputDTO) {

		Restaurante restaurante = restauranteInputMapper.mapearEntity(restauranteInputDTO);

		restaurante = restauranteRepository.save(restaurante);

		RestauranteOutputDTO restauranteDTO = restauranteOutputMapper.mapearDTO(restaurante);

		return restauranteDTO;
	}

	public RestauranteDTO alterar(String uuid, Restaurante restaurante) {
		
		Restaurante restauranteAtual = this.obterRestaurantePorUuid(uuid);

		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "uuid");

		restaurante = restauranteRepository.save(restauranteAtual);

		return restauranteDTOMapper.mapearDTO(restaurante);
	}

	public RestauranteDTO alterarParcial(String uuid, HttpServletRequest request) throws IOException {				
		
		Restaurante restauranteAtual = this.obterRestaurantePorUuid(uuid);

		Restaurante restaurante = objectMapper.readerForUpdating(restauranteAtual).readValue(request.getReader());

		if (restaurante != null) {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

			restaurante = restauranteRepository.save(restaurante);
			
			return restauranteDTOMapper.mapearDTO(restaurante);				
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
