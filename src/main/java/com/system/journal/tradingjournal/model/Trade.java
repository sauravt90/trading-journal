package com.system.journal.tradingjournal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system.journal.usermanagement.model.User;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "trades")
@AllArgsConstructor
@Builder
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String stockName;

    private String stockExchange;

    private String tradeDescription;

    private String tradeInitiationReason;

    private LocalDateTime tradeEntryTime;

    private LocalDateTime tradeExitTime;

    private Double entryPrice;

    private Double exitPrice;

    private Double entryTimeStopLossPrice;

    private boolean isCompleted;

    private boolean isWon;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
