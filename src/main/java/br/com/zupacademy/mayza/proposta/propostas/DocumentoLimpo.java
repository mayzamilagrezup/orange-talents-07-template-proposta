package br.com.zupacademy.mayza.proposta.propostas;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DocumentoLimpo {

    private String documento;

    public DocumentoLimpo(String documento) {
        this.documento = documento;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(documento);
    }
}
