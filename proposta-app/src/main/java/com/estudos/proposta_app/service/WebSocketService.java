package com.estudos.proposta_app.service;


import com.estudos.proposta_app.dto.PropostaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notificar(PropostaDTO propostaDTO){
        simpMessagingTemplate.convertAndSend("/proposta",propostaDTO);
    }
}
