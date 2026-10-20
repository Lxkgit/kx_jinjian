package com.kx.system.config.bean;



import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@RefreshScope
public class ElasticsearchConfig {

    @Value("${elasticsearch.address}")
    private String address;
    @Value("${elasticsearch.scheme}")
    private String scheme;
    @Value("${elasticsearch.connectTimeOut}")
    private int connectTimeOut;
    @Value("${elasticsearch.socketTimeOut}")
    private int socketTimeOut;
    @Value("${elasticsearch.connectionRequestTimeOut}")
    private int connectionRequestTimeOut;
    @Value("${elasticsearch.maxConnectNum}")
    private int maxConnectNum;
    @Value("${elasticsearch.maxConnectNumPerRoute}")
    private int maxConnectNumPerRoute;

    @Bean(name="restHighLevelClient")
    @Primary
    public RestHighLevelClient restHighLevelClient(){
        RestClientBuilder clientBuilder= RestClient.builder(getEsHost());
        clientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
            return requestConfigBuilder;
        });

        clientBuilder.setHttpClientConfigCallback(httpClientConfig ->{
            httpClientConfig.setMaxConnTotal(maxConnectNum);
            httpClientConfig.setMaxConnPerRoute(maxConnectNumPerRoute);
            return httpClientConfig;

        });
        return new RestHighLevelClient(clientBuilder);
    }



    private HttpHost[] getEsHost(){


        List<HttpHost> httpHosts = new ArrayList<>();
        String[] hostList = address.split(",");
        for (String s : hostList) {
            String[] split = s.split(":");
            httpHosts.add(new HttpHost(split[0], Integer.parseInt(split[1]), scheme));
        }
        return httpHosts.toArray(new HttpHost[]{});
    }

}
