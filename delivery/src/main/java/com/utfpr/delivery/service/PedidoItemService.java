package com.utfpr.delivery.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemDTO;
import com.utfpr.delivery.entity.PedidoItem;
import com.utfpr.delivery.exception.NotFoundException;
import com.utfpr.delivery.mapper.pedidoItem.PedidoItemDTOMapper;
import com.utfpr.delivery.repository.PedidoRepository;
import com.utfpr.delivery.repository.PedidoitemRepository;

@Service
public class PedidoItemService {

	@Autowired
	private PedidoItemDTOMapper pedidoItemDTOMapper;

	@Autowired
	private PedidoitemRepository pedidoItemRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	ObjectMapper objectMapper;

	public List<PedidoItemDTO> listar() {

		List<PedidoItem> pedidoItems = pedidoItemRepository.findAll();
		
		return pedidoItemDTOMapper.mapearLista(pedidoItems);

	}

	public List<PedidoItemDTO> listarItems(String uuid) {		
		
		List<PedidoItem> pedidoItems = pedidoItemRepository.findByPedido(pedidoRepository.findByUuid(uuid));
		
		return pedidoItemDTOMapper.mapearLista(pedidoItems);			
	}

	public PedidoItem obterPedidoPorUuid(String uuid) {

		PedidoItem pedidoItem = pedidoItemRepository.findByUuid(uuid);

		if (pedidoItem == null) {
			throw new NotFoundException("Pedido não encontrado");
		}

		return pedidoItem;

	}

	public PedidoItemDTO obterProdutoDTOPorUuid(String uuid) {
		return pedidoItemDTOMapper.mapearDTO(obterPedidoPorUuid(uuid));
	}

	public PedidoItem salvar(PedidoItem pedidoItem) {

		return pedidoItemRepository.save(pedidoItem);
	}

	public PedidoItem adicionar(String uuid, PedidoItem pedidoItem) {

		PedidoItem pedidoItemAtual = this.obterPedidoPorUuid(uuid);

		BeanUtils.copyProperties(pedidoItem, pedidoItemAtual, "id", "uuid");

		return pedidoItemRepository.save(pedidoItemAtual);

	}

	public PedidoItemDTO alterar(String uuid, PedidoItem pedidoItem) {

		PedidoItem pedidoItemAtual = this.obterPedidoPorUuid(uuid);

		if (pedidoItemAtual != null) {
			BeanUtils.copyProperties(pedidoItem, pedidoItemAtual, "id", "uuid");

			pedidoItem = pedidoItemRepository.save(pedidoItemAtual);

			return pedidoItemDTOMapper.mapearDTO(pedidoItem);
		}

		return null;
	}

	public boolean deletar(String uuid) {

		PedidoItem pedidoItem = this.obterPedidoPorUuid(uuid);

		if (pedidoItem != null) {

			try {
				pedidoItemRepository.delete(pedidoItem);
				return true;
			} catch (EmptyResultDataAccessException ex) {
				System.out.println(ex.getMessage());
			}

		}

		return false;
	}
}
