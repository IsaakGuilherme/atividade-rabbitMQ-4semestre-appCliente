package school.sptech.aplicacaoInterna.controller;

import school.sptech.aplicacaoCliente.dto.PedidoMessage;
import school.sptech.aplicacaoCliente.producer.PedidoProducer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoProducer producer;

    public PedidoController(PedidoProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@Valid @RequestBody PedidoMessage pedido) {
        // A API não processa o pedido diretamente.
        // Ela entrega a mensagem ao Producer e pode continuar seu fluxo.
        producer.enviar(pedido);

        return ResponseEntity.accepted().body(Map.of(
                "mensagem", "Pedido enviado para processamento assíncrono",
                "pedido", pedido
        ));
    }
}
