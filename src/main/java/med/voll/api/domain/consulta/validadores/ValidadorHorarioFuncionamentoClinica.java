package med.voll.api.domain.consulta.validadores;

import med.voll.api.config.exception.ValidationExceptionMessage;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){

        LocalDateTime dataConsulta = dados.data();

        boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean aberturaClinica = dataConsulta.getHour() < 7;
        boolean encerramento = dataConsulta.getHour() > 18;

        if(domingo || aberturaClinica || encerramento){
            throw new ValidationExceptionMessage("Consulta fora do horário de funcionamento da clinica");
        }

    }

}
