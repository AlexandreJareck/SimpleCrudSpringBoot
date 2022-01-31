package com.utfpr.delivery.dto.produto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProdutoInputDTO {
	@NotNull
	private String nome;
	@NotNull
	private BigDecimal preco;
	private String descricao;	
}
