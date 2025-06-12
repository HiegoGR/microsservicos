package com.estudos.analisecredito.listener;

import com.estudos.analisecredito.domain.Proposta;
import com.estudos.notificao.domain.Proposta;
import com.estudos.notificao.domain.Usuario;
import com.estudos.notificao.service.NotificacaoSnsService;
import com.estudos.notificao.service.UsuarioService;
import com.estudos.notificao.utils.SmsConstante;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    @Autowired
    private NotificacaoSnsService notificacaoSnsService;

    @Autowired
    private UsuarioService usuarioService;


    // configurando um ouvite, tudo que esta entrando nessa fila, ele fica ouvindo
    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta){
        Usuario usuario = usuarioService.buscarUsuarioPorId(proposta.getUsuarioId());

        String mensagem = String.format(SmsConstante.PROPOSTA_EM_ANALISE, usuario.getNome());

        notificacaoSnsService.notificar(usuario.getTelefone(), mensagem);
    }

}
