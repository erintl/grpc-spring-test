package com.github.erintl.grpcspringtestclient;

import com.github.erintl.cache.CacheGrpc;
import com.github.erintl.cache.Item;
import com.github.erintl.cache.ItemReply;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLException;
import java.io.File;
import java.util.logging.Level;

@Log
@Component
public class CacheClient
{
    @Value("${grpc.host}")
    private String host;
    @Value("${grpc.port}")
    private int port;
    @Value("${grpc.ca-certificate}")
    private String caCertificateName;

    private ManagedChannel channel;
    private CacheGrpc.CacheBlockingStub cacheStub;

    @PostConstruct
    private void postConstruct()
    {
        log.info("Creating gRPC channel and stub");
        try
        {
            channel = NettyChannelBuilder.forAddress(host, port)
                    .sslContext(GrpcSslContexts.forClient().trustManager(new File(caCertificateName)).build()).build();
            cacheStub = CacheGrpc.newBlockingStub(channel);
        }
        catch (SSLException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @PreDestroy
    private void preDestroy()
    {
        channel.shutdown();
    }

    public void setCache()
    {
        Item item = Item.newBuilder().setItemId(45).setItemName("Test Item").build();
        try
        {
            ItemReply itemReply = cacheStub.setItem(item);
        }
        catch (StatusRuntimeException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
