import pika
import time
import json

class PedidoMessage:
    def __init__(self, id, cliente, produto, quantidade):
        self.id = id
        self.cliente = cliente
        self.produto = produto
        self.quantidade = quantidade

while True:
    try:
        connection = pika.BlockingConnection(
            pika.ConnectionParameters(
                host="rabbitmq",
                credentials=pika.PlainCredentials(
                    "app", "app123"
                )
            )
        )
        break
    except pika.exceptions.AMQPConnectionError:
        print("RabbitMQ indisponível. Tentando novamente...")
        time.sleep(3)

channel = connection.channel()

channel.exchange_declare(
    exchange="pedidos.exchange",
    exchange_type="direct",
    durable=True
)

channel.queue_declare(
    queue="pedidos.queue",
    durable=True
)

channel.queue_bind(
    queue="pedidos.queue",
    exchange="pedidos.exchange",
    routing_key="pedidos.routingkey"
)

channel.confirm_delivery()

pedidoMensagem = PedidoMessage(5, "Isaak Guilherme", "Controle Gamer", 1)
mensagem_json = json.dumps(pedidoMensagem.__dict__)

try:
    channel.basic_publish(
        exchange="pedidos.exchange",
        routing_key="pedidos.routingkey",
        body=mensagem_json,
        properties=pika.BasicProperties(
            delivery_mode=2,
            content_type="application/json",
            content_encoding="utf-8"
        )
    )
    print("Mensagem enviada:", mensagem_json)
except pika.exceptions.AMQPError as e:
    print("Falha ao enviar a mensagem:", mensagem_json, e)

connection.close()