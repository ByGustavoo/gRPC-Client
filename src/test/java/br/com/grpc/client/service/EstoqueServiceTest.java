package br.com.grpc.client.service;

import br.com.grpc.client.config.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EstoqueServiceTest extends AbstractTest {

    @Autowired
    private EstoqueService estoqueService;

    @Test
    void consultarEstoqueByProdutoIdTest() {
        var estoque = Assertions.assertDoesNotThrow(() -> estoqueService.consultarEstoqueByProdutoId(1));
        Assertions.assertNotNull(estoque);
    }
}