package com.system.journal.tradingjournal.service;

import com.system.journal.common.exception.CustomResponseException;
import com.system.journal.tradingjournal.model.Trade;
import com.system.journal.tradingjournal.model.TradeRegistrationDTO;
import com.system.journal.tradingjournal.model.TradeUpdationDTO;
import com.system.journal.tradingjournal.repository.TradeRepository;
import com.system.journal.usermanagement.model.User;
import com.system.journal.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;


    public TradeService(TradeRepository tradeRepository, UserRepository userRepository) {
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
    }

    public TradeRegistrationDTO registerTrade(TradeRegistrationDTO tradeRegistrationDTO, String userName){

        Optional<User> userOP = userRepository.findByUserName(userName);

        if(userOP.isEmpty()){
            throw new CustomResponseException("User Not found", HttpStatus.BAD_REQUEST);
        }

        User user = userOP.get();

        Trade trade = Trade.builder()
                .tradeDescription(tradeRegistrationDTO.getTradeDescription())
                .entryPrice(tradeRegistrationDTO.getEntryPrice())
                .tradeEntryTime(LocalDateTime.now())
                .tradeExitTime(tradeRegistrationDTO.isCompleted() ? LocalDateTime.now() : null)
                .entryTimeStopLossPrice(tradeRegistrationDTO.getEntryTimeStopLossPrice())
                .exitPrice(tradeRegistrationDTO.getExitPrice())
                .isWon(tradeRegistrationDTO.isWon())
                .tradeInitiationReason(tradeRegistrationDTO.getTradeInitiationReason())
                .isCompleted(tradeRegistrationDTO.isCompleted())
                .stockExchange(tradeRegistrationDTO.getStockExchange())
                .stockName(tradeRegistrationDTO.getStockName())
        .build();

        Trade trade1 = tradeRepository.save(trade);
        tradeRegistrationDTO.setId(trade.getId());

        user.addTradeInUserList(trade1);

        return tradeRegistrationDTO;
    }


    public TradeUpdationDTO updateTrade(TradeUpdationDTO tradeUpdationDTO, String userName){
        Optional<Trade> tradeOP = tradeRepository.findById(tradeUpdationDTO.getId());
        if(tradeOP.isEmpty()){
            String errMsg =
                    String.format("No Trade Present with Id %d for user %s",tradeUpdationDTO.getId(),userName);
            throw new CustomResponseException(errMsg,HttpStatus.BAD_REQUEST);
        }

        Trade trade = tradeOP.get();



        trade.setTradeDescription(
                (tradeUpdationDTO.getTradeDescription() != null && !tradeUpdationDTO.getTradeDescription().isEmpty())
                        ? tradeUpdationDTO.getTradeDescription()
                        : trade.getTradeDescription());
        trade.setCompleted(tradeUpdationDTO.isCompleted() ? true : trade.isCompleted());
        trade.setTradeExitTime(tradeUpdationDTO.isCompleted() ? LocalDateTime.now() : trade.getTradeExitTime());
        trade.setExitPrice((tradeUpdationDTO.getExitPrice() != null && tradeUpdationDTO.getExitPrice() != 0)
                ? tradeUpdationDTO.getExitPrice() : trade.getExitPrice());
        trade.setWon(tradeUpdationDTO.isWon() ? true : trade.isWon());

        return  tradeUpdationDTO;
    }

    public List<Trade> getAllTrades(PageRequest pageRequest,String userName){

        Optional<User> userOP = userRepository.findByUserName(userName);

        if(userOP.isEmpty()){
            throw new CustomResponseException("User Not found", HttpStatus.BAD_REQUEST);
        }

        User user = userOP.get();



       return tradeRepository.findAllByUserId(user.getId(),pageRequest);
    }


}
