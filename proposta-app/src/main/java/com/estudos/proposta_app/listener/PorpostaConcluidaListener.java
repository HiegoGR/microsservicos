package com.estudos.proposta_app.listener;

import com.estudos.proposta_app.dto.PropostaDTO;
import com.estudos.proposta_app.entity.Proposta;
import com.estudos.proposta_app.mapper.PropostaMapper;
import com.estudos.proposta_app.repository.PropostaRepository;
import com.estudos.proposta_app.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class PorpostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private PropostaMapper propostaMapper;

    @Autowired
    private WebSocketService webSocketService;


    //consome tudo que esta vindo dessa fila e salva na base de dados
    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(PropostaDTO propostaDto){
        Proposta proposta = propostaMapper.toEntity(propostaDto);
        propostaRepository.save(proposta);
        webSocketService.notificar(propostaMapper.toDTO(proposta));
    }
}
