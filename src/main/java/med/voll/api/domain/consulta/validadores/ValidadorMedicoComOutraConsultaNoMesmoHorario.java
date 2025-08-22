package med.voll.api.domain.consulta.validadores;

import med.voll.api.config.exception.ValidationExceptionMessage;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){

        boolean medicoPossuiConsultaNoHorário = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(medicoPossuiConsultaNoHorário){
            throw new ValidationExceptionMessage("Médico já possui uma consulta agendada nesse horário");
        }

    }

}
