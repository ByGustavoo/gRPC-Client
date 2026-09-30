package br.com.grpc.client.controller.estoque;

import br.com.grpc.client.config.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EstoqueControllerTest extends AbstractControllerTest {

    @Test
    void buscarEstoqueTest() throws Exception {
        testGet("/v1/estoques/1");
    }
}