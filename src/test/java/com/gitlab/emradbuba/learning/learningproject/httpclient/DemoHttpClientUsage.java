package com.gitlab.emradbuba.learning.learningproject.httpclient;


import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.util.TimeValue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DemoHttpClientUsage {

    public static void main(String[] args) {
        // Demo usage

        try (final CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(createConnectionManager())
                .build()) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

        }

    }

    private static HttpClientConnectionManager createConnectionManager() {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(ConnectionConfig.custom()
                        .setConnectTimeout(5, TimeUnit.SECONDS)
                        .setSocketTimeout(10, TimeUnit.SECONDS)
                        .setValidateAfterInactivity(TimeValue.ofSeconds(15))
                        .setTimeToLive(TimeValue.ofMinutes(30))
                        .build())
                .setDefaultSocketConfig(SocketConfig.DEFAULT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setMaxConnTotal(100)
                .setMaxConnPerRoute()
                .build();
    }

}
