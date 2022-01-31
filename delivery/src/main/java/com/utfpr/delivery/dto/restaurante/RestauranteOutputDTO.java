package com.utfpr.delivery.dto.restaurante;



import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteOutputDTO {
	
	private String uuid;
	private String nome;
	private BigDecimal taxaFrete;
}

