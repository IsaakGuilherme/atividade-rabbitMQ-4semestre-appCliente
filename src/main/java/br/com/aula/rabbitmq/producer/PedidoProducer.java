package br.com.aula.rabbitmq.producer;

import br.com.aula.rabbitmq.config.RabbitMQConfig;
import br.com.aula.rabbitmq.dto.PedidoMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(PedidoMessage pedido) {
        // O Producer publica a mensagem no Exchange.
        // A routing key determina para qual fila a mensagem será encaminhada.
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                pedido
        );

        System.out.println("[PRODUCER] Pedido enviado: " + pedido);
    }
}
