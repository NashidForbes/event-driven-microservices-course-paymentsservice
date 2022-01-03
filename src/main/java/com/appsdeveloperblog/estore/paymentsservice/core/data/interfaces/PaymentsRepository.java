package com.appsdeveloperblog.estore.paymentsservice.core.data.interfaces;

import com.appsdeveloperblog.estore.paymentsservice.core.data.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<PaymentEntity, String> {
}
