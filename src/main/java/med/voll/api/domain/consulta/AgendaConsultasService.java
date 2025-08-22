package med.voll.api.domain.consulta;

import med.voll.api.config.exception.ValidationExceptionMessage;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultasService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if(!(pacienteRepository.existsById(dados.idPaciente()))){
            throw new ValidationExceptionMessage("Id do paciente não existe");
        }

        if(dados.idMedico() != null && !(medicoRepository.existsById(dados.idMedico()))){
            throw new ValidationExceptionMessage("Id do médico não existe");
        }

        validadores.forEach(v -> v.validar(dados));

        //Optional<Medico> (boolean ) ==> findByID ==> .get() -- pega o objeto em si
        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        Medico medico = escolherMedico(dados);

        if(medico == null){
            throw new ValidationExceptionMessage("Não existe medico disponivel nessa data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados){
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidationExceptionMessage("ERROR: especialidade é obrigatória");
        }

        //carregando medico aleatorio do banco de dados atraves do repository
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

}
