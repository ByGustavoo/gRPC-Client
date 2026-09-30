package br.com.grpc.client.exceptions;

public class ServidorGrpcIndisponivelException extends RuntimeException {

    public ServidorGrpcIndisponivelException(String message) {
        super(message);
    }
}