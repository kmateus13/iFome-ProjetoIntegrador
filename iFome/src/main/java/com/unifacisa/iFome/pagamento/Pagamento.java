package com.unifacisa.iFome.pagamento;

import com.unifacisa.iFome.pedido.Pedido;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;

    private BigDecimal valor;

    private LocalDateTime dataPagamento = LocalDateTime.now();
    
    private boolean aprovado;
}