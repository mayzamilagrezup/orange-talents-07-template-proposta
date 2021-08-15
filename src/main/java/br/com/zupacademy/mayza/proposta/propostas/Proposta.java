package br.com.zupacademy.mayza.proposta.propostas;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false)
    private BigDecimal salario;

    public Proposta(String documento, String email, String nome, Endereco endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    private Proposta() {
    }

    public Long getId() {
        return id;
    }
}
