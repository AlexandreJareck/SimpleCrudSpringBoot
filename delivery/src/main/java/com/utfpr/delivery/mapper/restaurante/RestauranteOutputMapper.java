package com.utfpr.delivery.mapper.restaurante;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utfpr.delivery.dto.restaurante.RestauranteOutputDTO;
import com.utfpr.delivery.entity.Restaurante;

@Component
public class RestauranteOutputMapper {

	@Autowired
	private ModelMapper modelMapper;

	public RestauranteOutputDTO mapearDTO(Restaurante restaurante) {
		RestauranteOutputDTO restauranteOutputDTO = modelMapper.map(restaurante, RestauranteOutputDTO.class);
		
		return restauranteOutputDTO;
	}

	public List<RestauranteOutputDTO> mapearLista(List<Restaurante> restaurantes) {

		return restaurantes.stream().map(restaurante -> mapearDTO(restaurante)).collect(Collectors.toList());
	}

}
