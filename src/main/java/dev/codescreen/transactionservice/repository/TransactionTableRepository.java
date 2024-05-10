package dev.codescreen.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.codescreen.transactionservice.entity.TransactionTable;

@Repository
public interface TransactionTableRepository extends JpaRepository<TransactionTable, Long>{
    
}