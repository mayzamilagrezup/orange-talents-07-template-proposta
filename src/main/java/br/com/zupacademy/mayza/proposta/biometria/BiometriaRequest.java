package br.com.zupacademy.mayza.proposta.biometria;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private String digital;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BiometriaRequest(String digital) {
        this.digital = digital;
    }

    public Biometria toBiometria(Cartao cartao) {
        return new Biometria(digital,cartao);
    }

    public String getDigital() {
        return digital;
    }
}
