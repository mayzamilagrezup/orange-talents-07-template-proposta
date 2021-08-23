package br.com.zupacademy.mayza.proposta.carteiras;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String carteira;

    public CarteiraRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Carteira toCarteira(Cartao cartao) {
        return new Carteira(email, NomeCarteiras.valueOf(carteira.toUpperCase()), cartao);
    }

    public String getCarteira() {
        return carteira;
    }
}
