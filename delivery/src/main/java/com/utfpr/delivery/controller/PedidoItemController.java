package com.utfpr.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.delivery.dto.pedidoItem.PedidoItemDTO;
import com.utfpr.delivery.entity.Pedido;
import com.utfpr.delivery.entity.PedidoItem;
import com.utfpr.delivery.service.PedidoItemService;

@RestController
@RequestMapping("/pedidoItems")
public class PedidoItemController {

	@Autowired
	private PedidoItemService pedidoItemService;

	@GetMapping
	@ResponseBody
	public List<PedidoItemDTO> obterPedidoItems() {

		return pedidoItemService.listar();
	}

	@GetMapping("/{uuid}")
	@ResponseBody
	public PedidoItemDTO obterPedidoPorUuid(@PathVariable String uuid) {
		return pedidoItemService.obterProdutoDTOPorUuid(uuid);
	}

	@PutMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	private PedidoItemDTO alterar(@PathVariable String uuid, @RequestBody PedidoItem pedidoItem) {
		return pedidoItemService.alterar(uuid, pedidoItem);
	}

	@DeleteMapping("/{uuid}")
	private ResponseEntity<Pedido> deletar(@PathVariable String uuid) {

		if (pedidoItemService.deletar(uuid)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().build();
	}
}
