package med.voll.api.domain.consulta;

import med.voll.api.config.exception.ValidationExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelaConsultasService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void cancelar(DadosCancelmanetoConsulta dados){

        if(!(consultaRepository.existsById(dados.idConsulta()))){
            throw new ValidationExceptionMessage("consulta não existe");
        }

        if(dados.motivo() == null){
            throw new ValidationExceptionMessage("Você precisa de um motivo para cancelar a consulta");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        LocalDateTime agora = LocalDateTime.now();

        if(consulta.getData().isBefore(agora.plusHours(24))){
            throw new ValidationExceptionMessage("Consulta só pode ser cancelada com 24h de antecedência");
        }

        consultaRepository.delete(consulta);

    }

}
