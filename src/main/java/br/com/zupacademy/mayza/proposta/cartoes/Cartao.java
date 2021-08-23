package br.com.zupacademy.mayza.proposta.cartoes;

import br.com.zupacademy.mayza.proposta.aviso_viagem.AvisoDeViagem;
import br.com.zupacademy.mayza.proposta.biometria.Biometria;
import br.com.zupacademy.mayza.proposta.bloqueio.Bloqueio;
import br.com.zupacademy.mayza.proposta.propostas.Proposta;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroDoCartao;
    private LocalDateTime emitidoEm;
    private BigDecimal limite;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @OneToMany(mappedBy = "cartao")
    private List<Bloqueio> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Biometria> biometrias = new HashSet<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<AvisoDeViagem> avisos = new HashSet<>();

    @OneToOne
    private Proposta proposta;

    public Cartao(CartaoPropostaResponse response, Proposta proposta) {
        this.numeroDoCartao = response.getNumeroDoCartao();
        this.emitidoEm = response.getEmitidoEm();
        this.limite = response.getLimite();
        this.proposta = proposta;
        this.statusCartao = StatusCartao.DESBLOQUEADO;
    }

    private Cartao() {
    }

    public Long getId() {
        return id;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public boolean isBloqueado() {
        return this.statusCartao.equals(StatusCartao.BLOQUEADO);
    }

    public void setStatusCartao(StatusCartao statusCartao) {
        this.statusCartao = statusCartao;
    }

    public void adicionaAvisoViagem(AvisoDeViagem avisoDeViagem) {
        this.avisos.add(avisoDeViagem);
    }
}
