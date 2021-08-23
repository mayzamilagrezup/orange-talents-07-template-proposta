package br.com.zupacademy.mayza.proposta.aviso_viagem;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoDeViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cartao cartao;

    @Column(nullable = false)
    private String destinoViagem;

    @Column(nullable = false)
    private LocalDate dataTermino;

    private LocalDateTime instanteAviso;

    @Column(nullable = false)
    private String ipCliente;

    @Column(nullable = false)
    private String userAgent;

    public AvisoDeViagem(Cartao cartao, String destinoViagem, LocalDate dataTermino, String ipCliente, String userAgent) {
        this.cartao = cartao;
        this.destinoViagem = destinoViagem;
        this.dataTermino = dataTermino;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.instanteAviso = LocalDateTime.now();
    }

    private AvisoDeViagem() {
    }
}
