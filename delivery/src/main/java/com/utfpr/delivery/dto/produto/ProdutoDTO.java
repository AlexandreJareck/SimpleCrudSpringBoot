package com.utfpr.delivery.dto.produto;

import java.math.BigDecimal;


import lombok.Data;

@Data
public class ProdutoDTO {	
	private String uuid;
	private String nome;	
	private BigDecimal preco;
	private String descricao;	
}
