package com.utfpr.delivery.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utfpr.delivery.dto.pedido.PedidoDTO;
import com.utfpr.delivery.dto.pedido.PedidoInputDTO;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemDTO;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemInputDTO;
import com.utfpr.delivery.dto.pedidoItem.PedidoItemsDTO;
import com.utfpr.delivery.entity.Cliente;
import com.utfpr.delivery.entity.Pedido;
import com.utfpr.delivery.entity.PedidoItem;
import com.utfpr.delivery.entity.Produto;
import com.utfpr.delivery.entity.Restaurante;
import com.utfpr.delivery.exception.NotFoundException;
import com.utfpr.delivery.mapper.pedido.PedidoDTOMapper;
import com.utfpr.delivery.mapper.pedido.PedidoInputDTOMapper;
import com.utfpr.delivery.mapper.pedidoItem.PedidoItemDTOMapper;
import com.utfpr.delivery.mapper.pedidoItem.PedidoItemInputDTOMapper;
import com.utfpr.delivery.mapper.pedidoItem.PedidoItemsDTOMapper;
import com.utfpr.delivery.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private PedidoItemService pedidoItemService;

	@Autowired
	private PedidoDTOMapper pedidoDTOMapper;
	@Autowired
	private PedidoInputDTOMapper pedidoInputDTOMapper;
	@Autowired
	private PedidoItemInputDTOMapper pedidoItemInputDTOMapper;
	@Autowired
	private PedidoItemDTOMapper pedidoItemDTOMapper;
	@Autowired
	private PedidoItemsDTOMapper pedidoItemsDTOMapper;

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	ObjectMapper objectMapper;

	public List<PedidoDTO> listar() {
		List<Pedido> pedidos = pedidoRepository.findAll();		
		
		List<PedidoDTO> pedidoDTO = pedidoDTOMapper.mapearLista(pedidos);
		
		if(pedidoDTO != null) {
			for (PedidoDTO pedido : pedidoDTO) {
				pedido.setPedidoItems(pedidoItemService.listarItems(pedido.getUuid()));
			}
		}
		
		return pedidoDTO;
	}

	public Pedido obterPedidoPorId(Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);

		if (pedido.isPresent()) {
			return pedido.get();
		}

		return null;
	}

	public Pedido obterPedidoPorUuid(String uuid) {

		Pedido pedido = pedidoRepository.findByUuid(uuid);

		if (pedido == null) {
			throw new NotFoundException("Pedido n??o encontrado");
		}

		return pedido;

	}

	public PedidoDTO obterProdutoDTOPorUuid(String uuid) {
		return pedidoDTOMapper.mapearDTO(obterPedidoPorUuid(uuid));
	}

	public PedidoDTO salvar(PedidoInputDTO pedidoInputDTO) {

		Pedido pedido = pedidoInputDTOMapper.mapearEntity(pedidoInputDTO);

		Cliente cliente = clienteService.obterClientePorUuid(pedidoInputDTO.getCliente());
		pedido.setCliente(cliente);

		Restaurante restaurante = restauranteService.obterRestaurantePorUuid(pedidoInputDTO.getRestaurante());
		pedido.setRestaurante(restaurante);

		pedido = pedidoRepository.save(pedido);

		return pedidoDTOMapper.mapearDTO(pedido);
	}

	public PedidoDTO alterar(String uuid, PedidoInputDTO pedidoInputDTO) {

		Pedido pedido = pedidoInputDTOMapper.mapearEntity(pedidoInputDTO);

		Restaurante restaurante = restauranteService.obterRestaurantePorUuid(pedidoInputDTO.getRestaurante());
		pedido.setRestaurante(restaurante);

		Cliente cliente = clienteService.obterClientePorUuid(pedidoInputDTO.getCliente());
		pedido.setCliente(cliente);

		Pedido pedidoAtual = this.obterPedidoPorUuid(uuid);
		BeanUtils.copyProperties(pedido, pedidoAtual, "id", "uuid");
		
		pedido = pedidoRepository.save(pedidoAtual);

		return pedidoDTOMapper.mapearDTO(pedido);				

	}

	public List<PedidoItemDTO> adicionarItemNoPedido(PedidoItemsDTO pedidoItemsDTO, String uuid) {
		Pedido pedido = this.obterPedidoPorUuid(uuid);

		List<PedidoItem> pedidoItems = new ArrayList<PedidoItem>();

		for (PedidoItemInputDTO item : pedidoItemsDTO.getPedidoItems()) {

			PedidoItem pedidoItem = pedidoItemInputDTOMapper.mapearEntity(item);

			Produto produto = produtoService.obterProdutoPorUuid(item.getProduto());
			pedidoItem.setProduto(produto);
			pedidoItem.setPedido(pedido);
			pedidoItem.setQuantidade(item.getQuantidade());
			pedidoItem.setValor(produto.getPreco().multiply(new BigDecimal(item.getQuantidade())));
			pedidoItem = pedidoItemService.salvar(pedidoItem);

			pedidoItems.add(pedidoItem);
		}

		return pedidoItemsDTOMapper.mapearDTO(pedidoItems);
	}

	public PedidoItemDTO alterarItem(String pedidoUuid, String uuid, PedidoItemInputDTO pedidoItemInputDTO) {
		Pedido pedido = this.obterPedidoPorUuid(pedidoUuid);		
		PedidoItem pedidoItem = pedidoItemInputDTOMapper.mapearEntity(pedidoItemInputDTO);		
		Produto produto = produtoService.obterProdutoPorUuid(pedidoItemInputDTO.getProduto());
		
		pedidoItem.setProduto(produto);
		pedidoItem.setPedido(pedido);		
		pedidoItem = pedidoItemService.adicionar(uuid, pedidoItem);

		return pedidoItemDTOMapper.mapearDTO(pedidoItem);
	}

	public boolean deletar(String uuid) {

		Pedido pedido = this.obterPedidoPorUuid(uuid);

		if (pedido != null) {

			try {
				pedidoRepository.delete(pedido);
				return true;
			} catch (EmptyResultDataAccessException ex) {
				System.out.println(ex.getMessage());
			}

		}

		return false;
	}
}
