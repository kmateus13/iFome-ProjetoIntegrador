package com.unifacisa.iFome.cozinha;

import com.unifacisa.iFome.pedido.Pedido;
import com.unifacisa.iFome.pedido.PedidoRepository;
import com.unifacisa.iFome.pedido.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CozinhaServiceTest {

    @InjectMocks
    private CozinhaService cozinhaService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Test
    @DisplayName("Deve atualizar status corretamente (Fluxo Feliz)")
    void deveAtualizarStatusCorretamente() {
        
        Pedido pedidoMock = new Pedido();
        pedidoMock.setId(1L);
        pedidoMock.setStatus(StatusPedido.RECEBIDO);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(i -> i.getArguments()[0]);

       
        Pedido pedidoAtualizado = cozinhaService.atualizarStatus(1L, StatusPedido.EM_PREPARACAO);

        
        Assertions.assertEquals(StatusPedido.EM_PREPARACAO, pedidoAtualizado.getStatus());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar pular etapa (Regra de Negócio)")
    void deveLancarErroAoPularEtapa() {
        
        Pedido pedidoMock = new Pedido();
        pedidoMock.setId(1L);
        pedidoMock.setStatus(StatusPedido.RECEBIDO);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));

       
        Assertions.assertThrows(IllegalStateException.class, () -> {
            cozinhaService.atualizarStatus(1L, StatusPedido.FINALIZADO);
        });
    }
}