package com.utfpr.delivery.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "uuid", length = 36)
	private String uuid;
	
	private BigDecimal valorTotal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurante")
	private Restaurante restaurante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente")
	private Cliente cliente;
	
	@PrePersist
	private void gerarUUID() {
		setUuid(UUID.randomUUID().toString());
	}	
}
