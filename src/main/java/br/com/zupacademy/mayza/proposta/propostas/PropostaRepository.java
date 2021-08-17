package br.com.zupacademy.mayza.proposta.propostas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

    @Query("select p from Proposta p left join p.cartao c where p.status = 'ELEGIVEL' AND c.id = null")
    List<Proposta> buscaTodasPropostasElegiveisSemCartao();
}
