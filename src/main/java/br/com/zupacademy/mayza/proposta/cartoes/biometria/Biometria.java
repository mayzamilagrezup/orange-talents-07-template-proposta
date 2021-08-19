package br.com.zupacademy.mayza.proposta.cartoes.biometria;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String digital;

    @ManyToOne
    private Cartao cartao;

    private LocalDateTime instante;

    public Biometria(String digital, Cartao cartao) {
        this.digital = digital;
        this.cartao = cartao;
        this.instante = LocalDateTime.now();
    }

    private Biometria() {
    }

    public Long getId() {
        return id;
    }

    public String getDigital() {
        return digital;
    }

}
