package com.utfpr.delivery.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.utfpr.delivery.dto.produto.ProdutoDTO;
import com.utfpr.delivery.dto.produto.ProdutoInputDTO;
import com.utfpr.delivery.dto.produto.ProdutoOutputDTO;
import com.utfpr.delivery.entity.Produto;
import com.utfpr.delivery.exception.NotFoundException;
import com.utfpr.delivery.mapper.produto.ProdutoDTOMapper;
import com.utfpr.delivery.mapper.produto.ProdutoInputMapper;
import com.utfpr.delivery.mapper.produto.ProdutoOutputMapper;
import com.utfpr.delivery.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoOutputMapper produtoOutputMapper;
	@Autowired
	private ProdutoDTOMapper produtoDTOMapper;
	@Autowired
	private ProdutoInputMapper produtoInputMapper;

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<ProdutoOutputDTO> obterProdutos() {

		List<Produto> produtos = produtoRepository.findAll();

		return produtoOutputMapper.mapearLista(produtos);
	}

	public Produto obterProdutoPorUuid(String uuid) {

		Produto produto = produtoRepository.findByUuid(uuid);

		if (produto == null) {
			throw new NotFoundException("Produto não encontrado");
		}

		return produto;

	}

	public ProdutoDTO obterProdutoDTOPorUuid(String uuid) {
		return produtoDTOMapper.mapearDTO(obterProdutoPorUuid(uuid));
	}

	public ProdutoDTO salvar(ProdutoInputDTO produtoInputDTO) {
		Produto produto = produtoInputMapper.mapearEntity(produtoInputDTO);
		produto = produtoRepository.save(produto);

		return produtoDTOMapper.mapearDTO(produto);
	}

	public ProdutoDTO atualizar(String uuid, ProdutoInputDTO produtoInputDTO) {

		Produto produto = produtoInputMapper.mapearEntity(produtoInputDTO);
		Produto produtoAtual = this.obterProdutoPorUuid(uuid);
		BeanUtils.copyProperties(produto, produtoAtual, "id", "uuid");

		produto = produtoRepository.save(produtoAtual);
		
		return produtoDTOMapper.mapearDTO(produto);
	}

	public boolean deletar(String uuid) {

		Produto produto = this.obterProdutoPorUuid(uuid);

		if (produto != null) {

			try {
				produtoRepository.delete(produto);
				return true;
			} catch (EmptyResultDataAccessException ex) {
				System.out.println(ex.getMessage());
			}

		}

		return false;
	}
}
