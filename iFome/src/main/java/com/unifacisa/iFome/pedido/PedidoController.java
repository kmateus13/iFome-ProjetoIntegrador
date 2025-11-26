package com.unifacisa.iFome.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> checkout(@RequestBody PedidoRequest request) {
        return ResponseEntity.ok(pedidoService.criarPedido(request));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }
    
    @GetMapping("/cozinha")
    public ResponseEntity<List<Pedido>> filaCozinha() {
        return ResponseEntity.ok(pedidoService.listarFilaCozinha());
    }
}