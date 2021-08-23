package br.com.zupacademy.mayza.proposta.bloqueio;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime instante;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private  String ipCliente;

    @ManyToOne
    private Cartao cartao;

    public Bloqueio(String userAgent, String ipCliente, Cartao cartao) {
        this.userAgent = userAgent;
        this.ipCliente = ipCliente;
        this.cartao = cartao;
        this.instante = LocalDateTime.now();
    }

    private Bloqueio() {
    }

}
