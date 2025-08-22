package med.voll.api.domain.medico;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);


    @Query("""
            SELECT m FROM medicos m
            WHERE m.ativo = true AND m.especialidade = :especialidade
            
            AND m.id NOT IN(    
              SELECT c.medico.id FROM consultas c
              WHERE c.data = :data
            )
            
            ORDER BY RAND()
            LIMIT 1
            
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, @NotNull @Future LocalDateTime data);


    @Query("""
            select m.ativo
            from medicos m
            where
            m.id = :id
            """)
    Boolean findAtivoById(@Param("id") Long id);

}
