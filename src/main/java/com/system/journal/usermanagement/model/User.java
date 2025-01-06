package com.system.journal.usermanagement.model;

import com.system.journal.tradingjournal.model.Trade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "user_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "user",cascade = CascadeType.ALL)
    private List<Trade> tradeList = new ArrayList<>();

    public void addTradeInUserList(Trade trade){
        this.tradeList.add(trade);
        trade.setUser(this);
    }

    public void removeTradeFromUser(Trade trade){
        this.tradeList.remove(trade);
        trade.setUser(null);
    }


}
