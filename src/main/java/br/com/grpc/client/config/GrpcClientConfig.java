package br.com.grpc.client.config;

import br.com.grpc.client.model.proto.estoque.EstoqueServiceGrpc.EstoqueServiceBlockingStub;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.ImportGrpcClients;

@Configuration
@ImportGrpcClients(target = "estoque", types = EstoqueServiceBlockingStub.class)
public class GrpcClientConfig {

}