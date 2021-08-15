package br.com.zupacademy.mayza.proposta.propostas;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

    private String cep;
    private String logradouro;
    private String numero;
    private  String bairro;
    private String localidade;
    private String uf;

    public Endereco(String cep, String logradouro, String numero, String bairro, String localidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }

    private Endereco() {
    }
}
