package com.fatema.sample.entity;

import com.fatema.sample.repository.AccountRepository;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Table(name = "statement")
@Data
public class Statement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "datefield", nullable = false)
    private String dateField;

    @Column(name = "amount", nullable = false)
    private String amount;
}
