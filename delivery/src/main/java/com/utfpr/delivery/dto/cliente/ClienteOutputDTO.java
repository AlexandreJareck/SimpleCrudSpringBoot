package com.utfpr.delivery.dto.cliente;

import lombok.Data;

@Data
public class ClienteOutputDTO {
	
	private String uuid;	
	private String nome;
	private String email;
	private String telefone;
	
}