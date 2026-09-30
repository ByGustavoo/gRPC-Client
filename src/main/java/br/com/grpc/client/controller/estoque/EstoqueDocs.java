package br.com.grpc.client.controller.estoque;

import br.com.grpc.client.exceptions.dto.ErrorResponseDTO;
import br.com.grpc.client.model.dto.EstoqueDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Estoque", description = "Endpoints relacionados à consulta de Estoque no gRPCServer")
public interface EstoqueDocs {

    @Operation(
            summary = "Consulta o estoque de um produto",
            description = """
                    Consulta o estoque do produto informado chamando o serviço gRPC **ConsultarEstoque** \
                    do gRPCServer. Retorna a descrição, a quantidade e se o produto está disponível.""")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estoque retornado com sucesso!"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(
                    responseCode = "503",
                    description = "Servidor de Estoque indisponível!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{idProduto}")
    ResponseEntity<EstoqueDTO> buscarEstoque(
            @Parameter(description = "Id do produto", example = "1")
            @PathVariable @Positive(message = "O ID do Produto deve ser maior que zero!") Integer idProduto);
}