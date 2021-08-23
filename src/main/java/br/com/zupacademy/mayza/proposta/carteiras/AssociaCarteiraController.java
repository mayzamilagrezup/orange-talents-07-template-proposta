package br.com.zupacademy.mayza.proposta.carteiras;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.cartoes.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class AssociaCarteiraController {

    private CartaoRepository cartaoRepository;
    private AssociadorDeCarteiras associadorDeCarteiras;
    @PersistenceContext
    private EntityManager manager;
    private final Logger log = LoggerFactory.getLogger(AssociaCarteiraController.class);

    public AssociaCarteiraController(CartaoRepository cartaoRepository, AssociadorDeCarteiras associadorDeCarteiras) {
        this.cartaoRepository = cartaoRepository;
        this.associadorDeCarteiras = associadorDeCarteiras;
    }

    @PostMapping("/{id}/carteiras")
    @Transactional
    public ResponseEntity<?> associarCarteira(@PathVariable Long id, @Valid @RequestBody CarteiraRequest request,
                                              UriComponentsBuilder builder) {

        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);
        if (optionalCartao.isEmpty()) {
            log.error("O cartão de id {} não foi encontrado", id);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = optionalCartao.get();
        Carteira carteira = request.toCarteira(cartao);

        if (cartao.possuiCarteiraAssociada(carteira)) {
            log.info("Tentativa falhou. Carteira {} já associada ao cartão {}", request.getCarteira(), cartao.getNumeroDoCartao());
            return ResponseEntity.unprocessableEntity().build();
        }

        associadorDeCarteiras.associaCarteira(cartao, request);
        manager.persist(carteira);

        URI uri = builder.path("/cartoes/{id}/carteiras/{carteiraId}").build(id, carteira.getId());
        return ResponseEntity.created(uri).build();

    }
}
