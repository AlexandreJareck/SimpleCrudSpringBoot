package com.utfpr.delivery.dto.cliente;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ClienteInputDTO {
	
	@NotNull
	private String nome;
	@NotNull
	private String email;
	@NotNull
	private String telefone;
	
}