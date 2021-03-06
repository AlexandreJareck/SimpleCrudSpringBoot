package com.utfpr.delivery.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.utfpr.delivery.dto.pedido.PedidoDTO;
import com.utfpr.delivery.dto.pedido.PedidoInputDTO;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemDTO;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemInputDTO;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemsDTO;
import com.utfpr.delivery.entity.Pedido;
import com.utfpr.delivery.service.PedidoItemService;
import com.utfpr.delivery.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoItemService pedidoItemService;

	@GetMapping
	@ResponseBody
	public List<PedidoDTO> obterPedidos() {
		return pedidoService.listar();		
	}

	@GetMapping("/{uuid}")
	@ResponseBody
	public PedidoDTO obterPedidoPorUuid(@PathVariable String uuid) {

		return pedidoService.obterProdutoDTOPorUuid(uuid);
	}
	
	@GetMapping("/{uuid}/pedidoItems")
	@ResponseBody
	public List<PedidoItemDTO> obterPedidoItems(@PathVariable(value = "uuid") String uuid) {

		return pedidoItemService.listarItems(uuid);

	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private PedidoDTO adicionar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {		
		return pedidoService.salvar(pedidoInputDTO);
	}
	
	@PostMapping("/{uuid}/pedidoItems")
	@ResponseStatus(code = HttpStatus.CREATED)
	private List<PedidoItemDTO> adicionarItemNoPedido(@RequestBody @Valid PedidoItemsDTO pedidoItemsDTO,@PathVariable(value = "uuid") String uuid) {

		return pedidoService.adicionarItemNoPedido(pedidoItemsDTO, uuid);
	}
	
	@PutMapping("/{pedidoUuid}/pedidoItems/{uuid}")
	@ResponseBody
	private PedidoItemDTO alterarItem(@PathVariable String pedidoUuid, @PathVariable String uuid, @Valid @RequestBody PedidoItemInputDTO pedidoItemInputDTO) {
		
		return pedidoService.alterarItem(pedidoUuid, uuid, pedidoItemInputDTO);
		
	}

	@PutMapping("/{uuid}")
	@ResponseStatus(code = HttpStatus.OK)
	private PedidoDTO alterar(@PathVariable String uuid, @RequestBody PedidoInputDTO pedidoInputDTO) {
		return pedidoService.alterar(uuid, pedidoInputDTO);		
	}

	@DeleteMapping("/{uuid}")
	private ResponseEntity<Pedido> deletar(@PathVariable String uuid) {

		if (pedidoService.deletar(uuid)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().build();
	}

	
}
