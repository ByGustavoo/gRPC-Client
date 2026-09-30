package br.com.grpc.client.exceptions.handler;

import br.com.grpc.client.exceptions.ProdutoNaoEncontradoException;
import br.com.grpc.client.exceptions.ServidorGrpcIndisponivelException;
import br.com.grpc.client.exceptions.dto.ErrorResponseDTO;
import br.com.grpc.client.exceptions.dto.MethodArgumentNotValidResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest pHttpServletRequest) {

        var errors = ex.getFieldErrors()
                .stream()
                .map(fieldError -> new MethodArgumentNotValidResponseDTO(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .toList();

        var response = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/validation-error",
                "A requisição contém dados inválidos!",
                errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpServletRequest pHttpServletRequest) {

        var errors = ex.getParameterValidationResults()
                .stream()
                .flatMap(parameterValidationResult -> parameterValidationResult.getResolvableErrors()
                        .stream()
                        .map(resolvableError -> new MethodArgumentNotValidResponseDTO(
                                parameterValidationResult.getMethodParameter().getParameterName(),
                                resolvableError.getDefaultMessage())))
                .toList();

        var response = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/validation-error",
                "A requisição contém dados inválidos!",
                errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest pHttpServletRequest) {

        var response = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição Inválida!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/unreadable-message",
                "O corpo da requisição não pôde ser lido!",
                ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest pHttpServletRequest) {

        var response = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Parâmetro Inválido!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/argument-type-mismatch",
                "O parâmetro '" + ex.getName() + "' possui um formato inválido!",
                ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest pHttpServletRequest) {

        var response = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição Inválida!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/illegal-argument",
                "A requisição contém dados inválidos!",
                ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleProdutoNaoEncontradoException(ProdutoNaoEncontradoException ex, HttpServletRequest pHttpServletRequest) {

        var response = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Produto não encontrado!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/produto-not-found",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest pHttpServletRequest) {

        var response = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/resource-not-found",
                "O endpoint informado não existe!",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ServidorGrpcIndisponivelException.class)
    public ResponseEntity<ErrorResponseDTO> handleServidorGrpcIndisponivelException(ServidorGrpcIndisponivelException ex, HttpServletRequest pHttpServletRequest) {

        var response = new ErrorResponseDTO(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Servidor de Estoque Indisponível!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/servidor-grpc-indisponivel",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleInternalServerErrorException(Exception ex, HttpServletRequest pHttpServletRequest) {

        log.error("Erro interno não tratado! - Endpoint: [{}]", pHttpServletRequest.getRequestURI(), ex);

        var response = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno no Servidor!",
                pHttpServletRequest.getRequestURI(),
                "/gRPCClient/problems/internal-server-error",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}