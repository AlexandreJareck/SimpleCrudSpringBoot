package com.utfpr.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.delivery.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
