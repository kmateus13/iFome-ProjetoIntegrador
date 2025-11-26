package com.unifacisa.iFome.cozinha;

import com.unifacisa.iFome.pedido.Pedido;
import com.unifacisa.iFome.pedido.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cozinha")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

   
    @GetMapping("/fila")
    public ResponseEntity<List<Pedido>> listarFila() {
        return ResponseEntity.ok(cozinhaService.listarFilaDeProducao());
    }

    
    @PatchMapping("/{id}/preparar")
    public ResponseEntity<Pedido> iniciarPreparo(@PathVariable Long id) {
        return ResponseEntity.ok(cozinhaService.atualizarStatus(id, StatusPedido.EM_PREPARACAO));
    }

   
    @PatchMapping("/{id}/pronto")
    public ResponseEntity<Pedido> marcarComoPronto(@PathVariable Long id) {
        return ResponseEntity.ok(cozinhaService.atualizarStatus(id, StatusPedido.PRONTO));
    }

   
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Pedido> finalizarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(cozinhaService.atualizarStatus(id, StatusPedido.FINALIZADO));
    }
}