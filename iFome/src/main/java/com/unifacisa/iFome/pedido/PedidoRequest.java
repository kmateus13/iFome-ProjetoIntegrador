package com.unifacisa.iFome.pedido;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequest {
    private Long clienteId;
    private List<ItemRequest> itens;

    @Data
    public static class ItemRequest {
        private Long produtoId;
        private Integer quantidade;
    }
}