package com.system.journal.tradingjournal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeRegistrationDTO {

    private Long id;

    private String stockName;

    private String stockExchange;

    private String tradeDescription;

    private String tradeInitiationReason;

    private Double entryPrice;

    private Double exitPrice;

    private Double entryTimeStopLossPrice;

    private boolean isCompleted;

    private boolean isWon;

}
