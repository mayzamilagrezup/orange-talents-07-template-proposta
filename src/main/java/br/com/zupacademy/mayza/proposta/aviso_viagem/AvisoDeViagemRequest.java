package br.com.zupacademy.mayza.proposta.aviso_viagem;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoDeViagemRequest {

    @NotBlank
    private String destinoViagem;

    @NotNull
    @Future
    private LocalDate dataTermino;

    public AvisoDeViagemRequest(String destinoViagem, @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING) LocalDate dataTermino) {
        this.destinoViagem = destinoViagem;
        this.dataTermino = dataTermino;
    }

    public AvisoDeViagem toAvisoDeViagem(Cartao cartao, String ipCliente, String userAgent) {
        return new AvisoDeViagem(cartao, destinoViagem, dataTermino, ipCliente, userAgent);
    }

}
