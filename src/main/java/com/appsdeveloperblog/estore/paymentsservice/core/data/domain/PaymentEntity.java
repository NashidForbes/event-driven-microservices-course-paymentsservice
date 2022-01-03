package com.appsdeveloperblog.estore.paymentsservice.core.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    private String paymentId;
    @Column
    private String orderId;
}
