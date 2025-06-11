package com.estudos.proposta_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private ConnectionFactory connectionFactory;

    @Value("${rabbit.propostapendente.exchange}")
    private String exchange;

    public RabbitMQConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

//RabbitTemplate naop tem o conversor, entao foi preciso criar para que o mesmo possa converter
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }


    //classe responsavel pela aplicaçao tenha permissao para realizar operaçoes no RabbitMq
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdm(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }


    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta(){
        return QueueBuilder
                //não perde os dados caso aplicação caia, caso cai as filas estarao salvas
                .durable("proposta-concluida.ms-proposta")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao(){
        return QueueBuilder
                //não perde os dados caso aplicação caia, caso cai as filas estarao salvas
                .durable("proposta-concluida.ms-notificacao")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito(){
        return QueueBuilder
                //não perde os dados caso aplicação caia, caso cai as filas estarao salvas
                .durable("proposta-pendente.ms-analise-credito")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao(){
        return QueueBuilder
                //não perde os dados caso aplicação caia, caso cai as filas estarao salvas
                .durable("proposta-pendente.ms-notificacao")
                .build();
    }


    /*Criando as Exchanges e o Binding(junção da fila da Exchange)*/
    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente(){
        return ExchangeBuilder
                .fanoutExchange(exchange)
                .build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsAnaliseCredito(){
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }


    @Bean
    public Binding criarBindingPropostaPendenteMsNotificacao(){
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }

}
