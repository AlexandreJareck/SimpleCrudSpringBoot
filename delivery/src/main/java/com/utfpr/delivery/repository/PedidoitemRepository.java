package com.utfpr.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.delivery.entity.Pedido;
import com.utfpr.delivery.entity.PedidoItem;

public interface PedidoitemRepository extends JpaRepository<PedidoItem, Long> {
	
	public PedidoItem findByUuid(String uuid);	
	
	List<PedidoItem> findByPedido(Pedido pedido);	
	
}
