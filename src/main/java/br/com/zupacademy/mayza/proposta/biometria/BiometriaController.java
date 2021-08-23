package br.com.zupacademy.mayza.proposta.biometria;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.cartoes.CartaoRepository;
import org.apache.commons.codec.binary.Base64;
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
public class BiometriaController {

    private CartaoRepository cartaoRepository;
    @PersistenceContext
    private EntityManager manager;
    private final Logger log = LoggerFactory.getLogger(BiometriaController.class);

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    @PostMapping("/{id}/biometria")
    public ResponseEntity<?> criar(@PathVariable Long id, @Valid @RequestBody BiometriaRequest request, UriComponentsBuilder builder) {

        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty()) {
            log.error("O cartão de id {} não foi encontrado", id);
            return ResponseEntity.notFound().build();
        }

        Biometria biometria = request.toBiometria(cartao.get());

        if(!Base64.isBase64(request.getDigital())) {
            return ResponseEntity.badRequest().body("Digital em formato inválido");
        }

        manager.persist(biometria);
        log.info("Biometria criada para o cartão {}", cartao.get().getId());

        URI uri = builder.path("/cartoes/{id}/biometria/{biometriaId}").build(id, biometria.getId());
        return ResponseEntity.created(uri).build();

    }
}
