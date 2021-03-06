package com.utfpr.delivery.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.delivery.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	public Cliente findByUuid(String uuid);
	
}
