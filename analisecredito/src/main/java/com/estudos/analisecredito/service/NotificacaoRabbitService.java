package com.estudos.analisecredito.service;


import com.estudos.analisecredito.domain.Proposta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoRabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notificar(String exchage, Proposta proposta){
        rabbitTemplate.convertAndSend(exchage,"",proposta);
    }
}
