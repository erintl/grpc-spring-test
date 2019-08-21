package com.github.erintl.grpcspringtestclient;

import com.github.erintl.cache.Item;
import com.github.erintl.cache.ItemReply;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.erintl.cache.CacheGrpc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Log
@Component
public class CacheClient
{
    @Value("${grpc.host}")
    private String host;
    @Value("${grpc.port}")
    private int port;

    private ManagedChannel channel;
    private CacheGrpc.CacheBlockingStub cacheStub;

    @PostConstruct
    private void postConstruct()
    {
        log.info("Creating gRPC channel and stub");
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        cacheStub = CacheGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    private void preDestroy()
    {
        channel.shutdown();
    }

    public void setCache()
    {
        Item item = Item.newBuilder().setItemId(45).setItemName("Test Item").build();
        ItemReply itemReply = cacheStub.setItem(item);
    }
}
