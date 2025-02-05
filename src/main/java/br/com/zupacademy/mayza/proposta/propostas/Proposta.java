package br.com.zupacademy.mayza.proposta.propostas;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.cartoes.CartaoPropostaResponse;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @OneToOne(mappedBy = "proposta", cascade = CascadeType.MERGE)
    private Cartao cartao;

    public Proposta(String documento, String email, String nome, Endereco endereco, BigDecimal salario) {
        this.documento = Criptografia.criptografar(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = StatusProposta.PENDENTE;
    }

    private Proposta() {
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return Criptografia.descriptografar(documento);
    }

    public String getNome() {
        return nome;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void atualizaStatusProposta(StatusProposta statusProposta) {
        this.status = statusProposta;
    }

    public void associaCartao(CartaoPropostaResponse response) {
        this.cartao = new Cartao(response, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return documento.equals(proposta.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }
}
