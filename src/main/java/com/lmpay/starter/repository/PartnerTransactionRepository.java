package com.lmpay.starter.repository;

import com.lmpay.starter.model.PartnerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerTransactionRepository extends JpaRepository<PartnerTransaction, Integer> {
    PartnerTransaction findByTransactionNo(String transactionNo);
}
