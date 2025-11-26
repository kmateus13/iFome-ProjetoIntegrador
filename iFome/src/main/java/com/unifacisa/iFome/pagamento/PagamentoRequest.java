package com.unifacisa.iFome.pagamento;

import lombok.Data;

@Data
public class PagamentoRequest {
    private Long pedidoId;
    private TipoPagamento tipoPagamento;
}