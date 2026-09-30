package br.com.grpc.client.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("gRPCClient")
                        .version("1.0.0")
                        .description("""
                                API REST que consulta o estoque dos produtos no gRPCServer. Cada chamada \
                                recebida aqui é repassada ao servidor através do serviço gRPC \
                                **EstoqueService**, e a resposta volta convertida para JSON.

                                ### Convenções

                                * **Servidor gRPC:** endereço definido pelas variáveis GRPC_SERVER_IP e \
                                GRPC_SERVER_PORT.
                                * **Indisponibilidade:** quando o gRPCServer não responde, a API devolve **503**.
                                * **Erros:** toda falha segue o modelo ErrorResponseDTO.
                                """));
    }
}