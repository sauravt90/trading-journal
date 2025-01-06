package com.system.journal.tradingjournal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeUpdationDTO {

    private Long id;

    private String tradeDescription;

    private Double exitPrice;

    private boolean isCompleted;

    private boolean isWon;

}
