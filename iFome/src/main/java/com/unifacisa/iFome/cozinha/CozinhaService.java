package com.unifacisa.iFome.cozinha;

import com.unifacisa.iFome.pedido.Pedido;
import com.unifacisa.iFome.pedido.PedidoRepository;
import com.unifacisa.iFome.pedido.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    @Autowired
    private PedidoRepository pedidoRepository;

   
    public List<Pedido> listarFilaDeProducao() {
        return pedidoRepository.findByStatusNot(StatusPedido.FINALIZADO).stream()
                .filter(p -> p.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) 
                .toList();
    }

    
    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

       
        validarTransicao(pedido.getStatus(), novoStatus);

        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    private void validarTransicao(StatusPedido atual, StatusPedido novo) {
      
        if (atual == StatusPedido.FINALIZADO) {
            throw new IllegalStateException("Pedido já foi finalizado.");
        }
        
      
        if (atual == StatusPedido.RECEBIDO && novo == StatusPedido.FINALIZADO) {
            throw new IllegalStateException("O pedido precisa ser preparado antes de finalizar.");
        }
    }
}