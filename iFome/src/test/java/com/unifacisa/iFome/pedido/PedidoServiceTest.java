package com.unifacisa.iFome.pedido;

import com.unifacisa.iFome.cliente.Cliente;
import com.unifacisa.iFome.cliente.ClienteRepository;
import com.unifacisa.iFome.produto.Produto;
import com.unifacisa.iFome.produto.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository; 

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteRepository clienteRepository; 

    @Test
    @DisplayName("Deve criar pedido com sucesso e calcular valor total corretamente")
    void deveCriarPedidoComSucesso() {
      
        PedidoRequest request = new PedidoRequest();
        request.setClienteId(1L);
        PedidoRequest.ItemRequest itemReq = new PedidoRequest.ItemRequest();
        itemReq.setProdutoId(10L);
        itemReq.setQuantidade(2);
        request.setItens(List.of(itemReq));

      
        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);

        Produto produtoMock = new Produto();
        produtoMock.setId(10L);
        produtoMock.setPreco(new BigDecimal("20.00")); 

        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(produtoRepository.findById(10L)).thenReturn(Optional.of(produtoMock));
        
       
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        
        Pedido pedidoCriado = pedidoService.criarPedido(request);

      
        Assertions.assertNotNull(pedidoCriado);
        
        
        Assertions.assertEquals(StatusPedido.AGUARDANDO_PAGAMENTO, pedidoCriado.getStatus());
        
      
        Assertions.assertEquals(new BigDecimal("40.00"), pedidoCriado.getValorTotal());
        
       
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }
}