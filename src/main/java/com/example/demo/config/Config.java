package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( "two-sum-config" )
public record Config( String algorithm, String url ) {}
