package dev.codescreen.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.codescreen.transactionservice.entity.EventsTable;


@Repository
public interface EventsTableRepository extends JpaRepository<EventsTable, Long>{
    // EventsTable findByUserId(BalanceTable userid);
}
