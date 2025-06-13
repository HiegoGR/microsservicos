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

    // Injeção das exchanges definidas no application.properties
    @Value("${rabbit.propostapendente.exchange}")
    private String exchangePropostaPendente;

    @Value("${rabbit.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    // ------------------- CONVERSOR JSON -------------------
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configura o RabbitTemplate com suporte a JSON
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    // ------------------- CONFIGURAÇÃO ADMIN -------------------
    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // Inicializa as filas/exchanges/bindings ao subir a aplicação
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdm(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    // ------------------- FILAS PRINCIPAIS -------------------

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta() {
        return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao() {
        return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito() {
        return QueueBuilder
                .durable("proposta-pendente.ms-analise-credito")
                //.maxLength(2L) pode colocar o maximo de mensagem na fila, caso tiver mais alguma vai para DLQ
                //.ttl(10000) // dados da fila seja consumida em ate 10segundos, caso contrario vai para DLQ
                .deadLetterExchange("proposta-pendente-dlx.ex") // liga a DLX
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao() {
        return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
    }

    // ------------------- FILA E EXCHANGE DLQ -------------------

    @Bean
    public Queue criarFilaDlq() {
        return QueueBuilder.durable("proposta-pendente.dlq").build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange("proposta-pendente-dlx.ex").build();
    }

    @Bean
    public Binding bindingDlq() {
        return BindingBuilder.bind(criarFilaDlq()).to(deadLetterExchange());
    }

    // ------------------- EXCHANGES PRINCIPAIS -------------------

    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaPendente).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaConcluida() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaConcluida).build();
    }

    // ------------------- BINDINGS -------------------

    @Bean
    public Binding bindingPropostaPendenteAnaliseCredito() {
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding bindingPropostaPendenteNotificacao() {
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding bindingPropostaConcluidaProposta() {
        return BindingBuilder
                .bind(criarFilaPropostaConcluidaMsProposta())
                .to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding bindingPropostaConcluidaNotificacao() {
        return BindingBuilder
                .bind(criarFilaPropostaConcluidaMsNotificacao())
                .to(criarFanoutExchangePropostaConcluida());
    }
}
