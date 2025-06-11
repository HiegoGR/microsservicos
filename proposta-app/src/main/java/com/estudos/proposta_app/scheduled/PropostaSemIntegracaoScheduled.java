package com.estudos.proposta_app.scheduled;

import com.estudos.proposta_app.service.PropostaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@EnableScheduling
public class PropostaSemIntegracaoScheduled {

    private final PropostaService propostaService;


    public PropostaSemIntegracaoScheduled(PropostaService propostaService) {
        this.propostaService = propostaService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void agendamentoPropostaSemIntegracao(){
        log.info("agendamento Proposta Sem Integracao a cada x horas");
        propostaService.propostaSemIntegracao();
    }
}
