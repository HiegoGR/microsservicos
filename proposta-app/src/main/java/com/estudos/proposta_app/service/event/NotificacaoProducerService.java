package com.estudos.proposta_app.service.event;

import com.estudos.proposta_app.dto.PropostaDTO;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducerService {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void notificar(PropostaDTO propostaDTO, String exchage){
        rabbitTemplate.convertAndSend(exchage, "", propostaDTO);
    }

    public void notificarComPrioridade(PropostaDTO propostaDTO, String exchage, MessagePostProcessor messagePostProcessor){
        rabbitTemplate.convertAndSend(exchage, "", propostaDTO, messagePostProcessor);
    }
}
