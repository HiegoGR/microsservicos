package com.estudos.proposta_app.service;

import com.estudos.proposta_app.dto.PropostaDTO;
import com.estudos.proposta_app.entity.Proposta;
import com.estudos.proposta_app.mapper.PropostaMapper;
import com.estudos.proposta_app.repository.PropostaRepository;
import com.estudos.proposta_app.service.event.NotificacaoProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private PropostaMapper propostaMapper;

    @Autowired
    private NotificacaoProducerService notificacao;

    @Value("${rabbit.propostapendente.exchange}")
    private String exchange;

    @Transactional
    public PropostaDTO salvar(PropostaDTO propostaDTO) {
        Proposta proposta = propostaMapper.toEntity(propostaDTO);
        proposta = propostaRepository.save(proposta);

        PropostaDTO response = propostaMapper.toDTO(proposta);
        notificarFilaRabbitMq(response);

        return response;
    }

    private void notificarFilaRabbitMq(PropostaDTO response){
        try {
            notificacao.notificar(response,exchange);
        }catch (RuntimeException ex){
            response.setIntegrada(false);
            propostaRepository.save(propostaMapper.toEntity(response));
        }

    }

    public List<PropostaDTO> listar(){
        return propostaRepository.findAll().stream().map(propostaMapper::toDTO).toList();
    }

    public PropostaDTO buscarPorId(Long id) {
        return propostaRepository.findById(id)
                .map(propostaMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrado"));
    }

    @Transactional
    public void propostaSemIntegracao() {
        propostaRepository.findAllByIntegradaIsFalse().stream()
                .map(propostaMapper::toDTO)
                .forEach(proposta -> {
                    boolean integradaComSucesso = false;

                    try {
                        notificacao.notificar(proposta, exchange);
                        integradaComSucesso = true;
                    } catch (Exception e) {
                        log.error("Erro ao notificar proposta {} para o RabbitMQ: {}", proposta.getId(), e.getMessage());
                    }

                    proposta.setIntegrada(integradaComSucesso);
                    propostaRepository.save(propostaMapper.toEntity(proposta));
                });
    }

}
