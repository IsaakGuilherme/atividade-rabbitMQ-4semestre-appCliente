package school.sptech.aplicacaoInterna.consumer;

import school.sptech.aplicacaoInterna.RabbitMQConfig;
import school.sptech.aplicacaoCliente.dto.PedidoMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receber(PedidoMessage pedido) {
        System.out.println("\n========== CONSUMER ==========");
        System.out.println("Pedido recebido do RabbitMQ");
        System.out.println("ID: " + pedido.id());
        System.out.println("Cliente: " + pedido.cliente());
        System.out.println("Produto: " + pedido.produto());
        System.out.println("Quantidade: " + pedido.quantidade());
        System.out.println("==============================\n");
    }
}
