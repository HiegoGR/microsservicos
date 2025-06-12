package com.estudos.analisecredito.service.strategy;

import com.estudos.analisecredito.domain.Proposta;
import com.estudos.analisecredito.service.NotificacaoRabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnaliseCreditoService {

    @Autowired
    private List<CalculoPonto> calculoPontoList;

    @Autowired
    private NotificacaoRabbitService notificacaoRabbitService;

    @Value("${rabbitmq.queue.proposta.concluida}")
    private String exchangePropostaConcluida;

    public void analisar(Proposta proposta) {
        try {
            //intera sobra as estrategias e soma os pontos.
            int pontuacao = calculoPontoList
                    .stream()
                    .mapToInt(impl -> impl.calcular(proposta))
                    .sum();

            proposta.setAprovada(pontuacao > 350);

        } catch (ResponseStatusException ex){
            proposta.setAprovada(false);
            proposta.setObservacao(ex.getMessage());
        }

        notificacaoRabbitService.notificar(exchangePropostaConcluida, proposta);
    }

}
