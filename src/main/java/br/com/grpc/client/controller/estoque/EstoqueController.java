package br.com.grpc.client.controller.estoque;

import br.com.grpc.client.model.dto.EstoqueDTO;
import br.com.grpc.client.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/estoques")
public class EstoqueController implements EstoqueDocs {

    private final EstoqueService estoqueService;

    @Override
    public ResponseEntity<EstoqueDTO> buscarEstoque(Integer idProduto) {
        return ResponseEntity.ok(estoqueService.consultarEstoqueByProdutoId(idProduto));
    }
}