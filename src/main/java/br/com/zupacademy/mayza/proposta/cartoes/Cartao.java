package br.com.zupacademy.mayza.proposta.cartoes;

import br.com.zupacademy.mayza.proposta.propostas.Proposta;

import javax.persistence.*;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Proposta proposta;

    private String numero;

    public Cartao(Proposta proposta, String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }

    private Cartao() {
    }

    public Long getId() {
        return id;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public String getNumero() {
        return numero;
    }
}
