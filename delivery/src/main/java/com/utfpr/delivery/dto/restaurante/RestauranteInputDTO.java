package com.utfpr.delivery.dto.restaurante;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RestauranteInputDTO {
	
	@NotNull
	private String nome;
	
	@NotNull
	@DecimalMin(value = "0.00", inclusive = false)
	@Digits(integer = 15, fraction = 2)
	private BigDecimal taxaFrete;
	
}
