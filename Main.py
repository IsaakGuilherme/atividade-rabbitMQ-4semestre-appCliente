import pika
import time

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

mensagem = "Pedido 101 criado"
if channel.basic_publish(
    exchange="pedidos.exchange",
    routing_key="pedidos.routingkey",
    body=mensagem,
    properties=pika.BasicProperties(
        delivery_mode=2,
        content_type="text/plain",
        content_encoding="utf-8"
    )
):
    print("Mensagem enviada:", mensagem)
else:
    print("Falha ao enviar a mensagem:", mensagem)

connection.close()