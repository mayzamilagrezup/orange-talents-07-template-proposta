package br.com.zupacademy.mayza.proposta.cartoes.bloqueio;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.cartoes.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class SolicitaBloqueioController {

    private CartaoRepository cartaoRepository;
    private NotificadorBloqueio notificadorBloqueio;
    private final Logger log = LoggerFactory.getLogger(SolicitaBloqueioController.class);


    public SolicitaBloqueioController(CartaoRepository cartaoRepository, NotificadorBloqueio notificadorBloqueio) {
        this.cartaoRepository = cartaoRepository;
        this.notificadorBloqueio = notificadorBloqueio;
    }

    @PostMapping("/{id}/bloqueio")
    @Transactional
    public ResponseEntity<?> bloquear(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        String ipCliente = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        if (ipCliente.isBlank() || userAgent.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);
        if (optionalCartao.isEmpty()) {
            log.error("O cartão de id {} não foi encontrado", id);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = optionalCartao.get();

        if (cartao.isBloqueado()) {
            log.info("Tentativa falhou, pois o cartão {} já está bloqueado", cartao.getNumeroDoCartao());
            return ResponseEntity.unprocessableEntity().build();
        }

        notificadorBloqueio.notificaBloqueio(cartao, ipCliente, userAgent);
        log.info("Cartão {} bloqueado!", cartao.getNumeroDoCartao());

        return ResponseEntity.ok().build();
    }

}
