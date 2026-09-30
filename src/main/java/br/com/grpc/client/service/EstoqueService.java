package br.com.grpc.client.service;

import br.com.grpc.client.exceptions.ProdutoNaoEncontradoException;
import br.com.grpc.client.exceptions.ServidorGrpcIndisponivelException;
import br.com.grpc.client.model.dto.EstoqueDTO;
import br.com.grpc.client.model.proto.estoque.ConsultarEstoqueRequest;
import br.com.grpc.client.model.proto.estoque.EstoqueServiceGrpc.EstoqueServiceBlockingStub;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueServiceBlockingStub estoqueServiceBlockingStub;

    public EstoqueDTO consultarEstoqueByProdutoId(Integer produtoId) {
        log.info("Consultando Estoque do Produto no gRPC-Server... - ID: [{}]", produtoId);

        var request = ConsultarEstoqueRequest.newBuilder()
                .setIdProduto(produtoId)
                .build();

        try {
            var response = estoqueServiceBlockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .consultarEstoque(request);

            log.info("Estoque do Produto recebido com sucesso! - ID: [{}] - Quantidade: [{}] - Disponível: [{}]",
                    response.getIdProduto(), response.getQuantidade(), response.getDisponivel());

            return new EstoqueDTO(
                    response.getIdProduto(),
                    response.getDescricao(),
                    response.getQuantidade(),
                    response.getDisponivel());
        } catch (StatusRuntimeException ex) {
            throw tratarErroGrpc(ex, produtoId);
        }
    }

    private RuntimeException tratarErroGrpc(StatusRuntimeException ex, Integer produtoId) {
        return switch (ex.getStatus().getCode()) {
            case NOT_FOUND -> {
                log.warn("O Produto solicitado não foi encontrado no gRPC-Server! - ID: [{}]", produtoId);
                yield new ProdutoNaoEncontradoException("O Produto solicitado não foi encontrado!");
            }
            case UNAVAILABLE, DEADLINE_EXCEEDED -> {
                log.error("O gRPC-Server não respondeu à consulta de Estoque! - ID: [{}] - Status: [{}]", produtoId, ex.getStatus().getCode());
                yield new ServidorGrpcIndisponivelException("O Servidor de Estoque está indisponível no momento!");
            }
            default -> {
                log.error("Erro inesperado ao consultar o Estoque no gRPC-Server! - ID: [{}] - Status: [{}]", produtoId, ex.getStatus(), ex);
                yield ex;
            }
        };
    }
}