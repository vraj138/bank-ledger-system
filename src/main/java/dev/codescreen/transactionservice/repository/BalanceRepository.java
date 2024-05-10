package dev.codescreen.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.codescreen.transactionservice.entity.BalanceTable;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceTable, Long>{
    BalanceTable findByUserId(String userId);
}

