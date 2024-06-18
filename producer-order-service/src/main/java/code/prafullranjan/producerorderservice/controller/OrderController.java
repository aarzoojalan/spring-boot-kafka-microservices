package code.prafullranjan.producerorderservice.controller;

import code.prafullranjan.baselibs.dto.Order;
import code.prafullranjan.baselibs.dto.OrderEvent;
import code.prafullranjan.producerorderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/order")
    public String placeOrder(@RequestBody Order order) {
        order.setId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setMessage("Order is in pending state");
        orderEvent.setStatus("pending");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);
        return "Order placed successfully...";
    }
}
