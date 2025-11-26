package com.unifacisa.iFome.pagamento;

import com.unifacisa.iFome.pedido.Pedido;
import com.unifacisa.iFome.pedido.PedidoRepository;
import com.unifacisa.iFome.pedido.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository; 

    @Transactional
    public Pagamento processarPagamento(PagamentoRequest request) {
     
        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

       
        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Este pedido não está aguardando pagamento ou já foi pago.");
        }

        
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setTipo(request.getTipoPagamento());
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setAprovado(true); 
        
        pagamentoRepository.save(pagamento);

     
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedidoRepository.save(pedido);

        return pagamento;
    }
}