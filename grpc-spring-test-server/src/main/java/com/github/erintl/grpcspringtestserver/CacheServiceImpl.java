package com.github.erintl.grpcspringtestserver;

import com.github.erintl.cache.CacheGrpc;
import com.github.erintl.cache.Item;
import com.github.erintl.cache.ItemReply;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class CacheServiceImpl extends CacheGrpc.CacheImplBase
{
    @Override
    public void setItem(Item request, StreamObserver<ItemReply> responseObserver)
    {
        String requestInfo = String.format("Id: %d, description: %s",
                request.getItemId(), request.getItemName());
        System.out.println(requestInfo);

        ItemReply reply = ItemReply.newBuilder().setResult(1).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
