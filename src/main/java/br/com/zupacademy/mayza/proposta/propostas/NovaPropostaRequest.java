package br.com.zupacademy.mayza.proposta.propostas;

import br.com.zupacademy.mayza.proposta.validacao.CpfOuCnpj;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    @CpfOuCnpj
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String cep;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String numero;

    @NotBlank
    private  String bairro;

    @NotBlank
    private String localidade;

    @NotBlank
    private String uf;

    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaRequest(String documento, String email, String nome, String cep, String logradouro, String numero,
                               String bairro, String localidade, String uf, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.salario = salario;
    }

    public Proposta toProposta() {
        Endereco endereco = new Endereco(cep, logradouro, numero, bairro, localidade, uf);
        return new Proposta(documento, email, nome, endereco, salario);
    }

    public String getDocumento() {
        return documento;
    }
}
