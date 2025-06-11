package com.estudos.notificao.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonSnsConfiguration {

    @Value("${aws.acessKey}")
    private String acessKey;

    @Value("${aws.secreKey}")
    private String secreKey;

    //Autenticação basica na aws
    @Bean
    public AWSCredentials awsCredentials(){
        return new BasicAWSCredentials(acessKey,secreKey);
    }

    //todas operacoes de SMS estao contidas dentro do amazon SNS
    // usando configuração standard pssando a credencial e a região
    @Bean
    public AmazonSNS amazonSNS(){
        return AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
