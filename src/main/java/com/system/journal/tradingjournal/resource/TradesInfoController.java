package com.system.journal.tradingjournal.resource;

import com.system.journal.tradingjournal.model.Trade;
import com.system.journal.tradingjournal.model.TradeRegistrationDTO;
import com.system.journal.tradingjournal.model.TradeUpdationDTO;
import com.system.journal.tradingjournal.service.TradeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradesInfoController {

    private final TradeService tradeService;

    public TradesInfoController(TradeService tradeService) {
        this.tradeService = tradeService;
    }


    @PostMapping("/register")
    public ResponseEntity<TradeRegistrationDTO> createTrade(@RequestBody TradeRegistrationDTO tradeRegistrationDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        TradeRegistrationDTO tradeRegistrationDTO1 = tradeService.registerTrade(tradeRegistrationDTO,userName);
        return new  ResponseEntity<>(tradeRegistrationDTO1, HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<TradeUpdationDTO> updateTrade(@RequestBody TradeUpdationDTO tradeUpdationDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        TradeUpdationDTO tradeUpdationDTO1 = tradeService.updateTrade(tradeUpdationDTO,userName);
        return new ResponseEntity<>(tradeUpdationDTO1,HttpStatus.OK);
    }

    @GetMapping("/getAllTrades")
    public ResponseEntity<List<Trade>> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size,
                                              @RequestParam(defaultValue = "tradeEntryTime") String sortBy,
                                              @RequestParam(defaultValue = "true") boolean ascending){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return new ResponseEntity<>(tradeService.getAllTrades(pageable,userName),HttpStatus.OK);
    }


}
