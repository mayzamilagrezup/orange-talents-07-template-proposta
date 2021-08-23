package br.com.zupacademy.mayza.proposta.carteiras;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private NomeCarteiras nomeCarteiras;

    @ManyToOne
    private Cartao cartao;

    public Carteira(String email, NomeCarteiras nomeCarteiras, Cartao cartao) {
        this.email = email;
        this.nomeCarteiras = nomeCarteiras;
        this.cartao = cartao;
    }

    private Carteira() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira = (Carteira) o;
        return nomeCarteiras == carteira.nomeCarteiras;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeCarteiras);
    }
}
