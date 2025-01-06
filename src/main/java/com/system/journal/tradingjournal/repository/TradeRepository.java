package com.system.journal.tradingjournal.repository;

import com.system.journal.tradingjournal.model.Trade;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade,Long> {
    List<Trade> findAllByUserId(Long id, PageRequest pageRequest);
}
