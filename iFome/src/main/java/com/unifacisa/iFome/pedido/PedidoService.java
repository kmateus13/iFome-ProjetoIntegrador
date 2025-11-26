package com.unifacisa.iFome.pedido;

import com.unifacisa.iFome.cliente.ClienteRepository;
import com.unifacisa.iFome.produto.Produto;
import com.unifacisa.iFome.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository; 

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Pedido criarPedido(PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

       
        if (request.getClienteId() != null) {
            pedido.setCliente(clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado")));
        }

       
        for (PedidoRequest.ItemRequest itemReq : request.getItens()) {
            Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemReq.getProdutoId()));

            ItemPedido itemPedido = new ItemPedido(produto, itemReq.getQuantidade());
            pedido.adicionarItem(itemPedido);
        }

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    
   
    public List<Pedido> listarFilaCozinha() {
    
        return pedidoRepository.findByStatusNot(StatusPedido.FINALIZADO);
    }
}