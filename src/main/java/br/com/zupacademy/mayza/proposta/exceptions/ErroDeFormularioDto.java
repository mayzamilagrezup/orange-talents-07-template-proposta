package br.com.zupacademy.mayza.proposta.exceptions;

public class ErroDeFormularioDto {

    private String campo;
    private String erro;

    public ErroDeFormularioDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public ErroDeFormularioDto() {
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
