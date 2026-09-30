package br.com.grpc.client.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa o estoque de um Produto retornado pelo gRPCServer.")
public record EstoqueDTO(

        @Schema(description = "Id do produto.", example = "1")
        Integer produtoId,

        @Schema(description = "Descrição do produto.", example = "ARROZ TIO JOAO 1KG")
        String descricao,

        @Schema(description = "Quantidade disponível em estoque.", example = "50")
        Integer quantidade,

        @Schema(description = "Indica se o produto está disponível para venda.", example = "true")
        Boolean disponivel

) {}