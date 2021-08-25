package br.com.zupacademy.mayza.proposta.propostas;

import org.springframework.security.crypto.encrypt.Encryptors;

public class Criptografia {

    private static String salt = "aec14fdb76aa";
    private static String password = "b20582be74374f8a";

    public static String criptografar(String text) {
        return Encryptors.queryableText(password, salt).encrypt(text);
    }

    public static String descriptografar(String text) {
        return Encryptors.queryableText(password, salt).decrypt(text);
    }
}
