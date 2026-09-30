package br.com.grpc.client.exceptions.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo utilizado para representar erros de validação de campos da requisição")
public record MethodArgumentNotValidResponseDTO(

        @Schema(description = "Campo que causou o erro de validação.", example = "idProduto")
        String campo,

        @Schema(description = "Mensagem de erro de validação.", example = "O ID do Produto deve ser maior que zero!")
        String mensagem

) {}