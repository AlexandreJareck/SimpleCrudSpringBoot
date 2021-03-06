package com.utfpr.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.delivery.dto.produto.ProdutoDTO;
import com.utfpr.delivery.dto.produto.ProdutoInputDTO;
import com.utfpr.delivery.dto.produto.ProdutoOutputDTO;
import com.utfpr.delivery.entity.Produto;
import com.utfpr.delivery.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	
	@GetMapping
	@ResponseBody
	public List<ProdutoOutputDTO> obterProdutos() {
		return produtoService.obterProdutos();
	}

	@GetMapping("/{uuid}")
	@ResponseBody
	public ProdutoDTO obterProdutoPorUuid(@PathVariable String uuid) {
		return produtoService.obterProdutoDTOPorUuid(uuid);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private ProdutoDTO adicionar(@RequestBody ProdutoInputDTO produtoInputDTO) {		
		return produtoService.salvar(produtoInputDTO);
	}

	@PutMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	private ProdutoDTO alterar(@PathVariable String uuid, @RequestBody ProdutoInputDTO produtoInputDTO) {		
		return produtoService.atualizar(uuid, produtoInputDTO);
	}

	@DeleteMapping("/{uuid}")
	private ResponseEntity<Produto> deletar(@PathVariable String uuid) {

		if (produtoService.deletar(uuid)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().build();
	}
}
