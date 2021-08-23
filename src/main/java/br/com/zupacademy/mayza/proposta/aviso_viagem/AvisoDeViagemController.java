package br.com.zupacademy.mayza.proposta.aviso_viagem;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.cartoes.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class AvisoDeViagemController {

    private CartaoRepository cartaoRepository;
    private NotificadorDeViagem notificadorDeViagem;
    private final Logger log = LoggerFactory.getLogger(AvisoDeViagemController.class);

    public AvisoDeViagemController(CartaoRepository cartaoRepository, NotificadorDeViagem notificadorDeViagem) {
        this.cartaoRepository = cartaoRepository;
        this.notificadorDeViagem = notificadorDeViagem;
    }

    @PostMapping("/{id}/aviso-de-viagem")
    @Transactional
    public ResponseEntity<?> avisar(@PathVariable Long id, @Valid @RequestBody AvisoDeViagemRequest request,
                                    HttpServletRequest httpRequest) {

        String ipCliente = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);

        if (ipCliente.isBlank() || userAgent.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);
        if (optionalCartao.isEmpty()) {
            log.error("O cartão de id {} não foi encontrado", id);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = optionalCartao.get();
        notificadorDeViagem.notificaViagem(cartao,request);

        AvisoDeViagem avisoDeViagem = request.toAvisoDeViagem(cartao, ipCliente, userAgent);
        cartao.adicionaAvisoViagem(avisoDeViagem);
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().build();
    }
}
