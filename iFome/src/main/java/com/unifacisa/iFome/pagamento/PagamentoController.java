package com.unifacisa.iFome.pagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Pagamento> pagar(@RequestBody PagamentoRequest request) {
        try {
            return ResponseEntity.ok(pagamentoService.processarPagamento(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}