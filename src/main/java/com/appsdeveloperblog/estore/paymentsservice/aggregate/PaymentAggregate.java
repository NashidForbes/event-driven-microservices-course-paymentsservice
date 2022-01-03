package com.appsdeveloperblog.estore.paymentsservice.aggregate;

import com.appsdeveloperblog.estore.sagacoreapi.commands.ProcessPaymentCommand;
import com.appsdeveloperblog.estore.sagacoreapi.events.PaymentProcessedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.security.InvalidParameterException;

@Aggregate
public class PaymentAggregate {

    // associate the dispatch command e.g. ProcessPaymentCommand class
    // with the right aggregate
    // via the id (target id -> AggregateIdentifier)
    @AggregateIdentifier
    private String paymentId;
    private String orderId;


    public PaymentAggregate() {
    }

    @CommandHandler
    public void handle(ProcessPaymentCommand processPaymentCommand){

        if(processPaymentCommand.getOrderId() == null){
            throw new InvalidParameterException("Order Id is null");
        }

        if(processPaymentCommand.getPaymentId() == null){
            throw new InvalidParameterException("Payment Id is null");
        }

        if(processPaymentCommand.getPaymentDetails().getName() == null ||
                processPaymentCommand.getPaymentDetails().getName().isBlank()){
            throw new InvalidParameterException("Payment details name is null or blank");
        }

        if(processPaymentCommand.getPaymentDetails().getCvv() == null ||
                processPaymentCommand.getPaymentDetails().getCvv().isBlank()){
            throw new InvalidParameterException("Payment details name is null or blank");
        }

        if(processPaymentCommand.getPaymentDetails().getCvv().length() != 3 ||
                processPaymentCommand.getPaymentDetails().getCvv().isBlank()){
            throw new IllegalArgumentException("Payment details cvv is not a value");
        }
        
        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                .paymentId(processPaymentCommand.getPaymentId())
                .orderId(processPaymentCommand.getOrderId())
                .build();

        // apply "stages" event for publish, if no exceptions,
        // staged object is published update the ProductAggregate
        // state with the latest values
        AggregateLifecycle.apply(paymentProcessedEvent);

    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent){
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}
