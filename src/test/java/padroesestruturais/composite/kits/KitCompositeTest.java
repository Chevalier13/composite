package padroesestruturais.composite.kits;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KitCompositeTest {

    @Test
    void deveCalcularPrecoDePecaIndividual() {
        Peca ssd = new Peca("SSD NVMe 1TB", new BigDecimal("450.00"));

        assertEquals("SSD NVMe 1TB", ssd.getNome());
        assertEquals(new BigDecimal("450.00"), ssd.calcularPreco());
    }

    @Test
    void deveRetornarZeroParaKitVazio() {
        Kit kitVazio = new Kit("Kit Vazio");

        assertEquals(0, BigDecimal.ZERO.compareTo(kitVazio.calcularPreco()));
        assertTrue(kitVazio.getItens().isEmpty());
    }

    @Test
    void deveCalcularPrecoDeKitComPecas() {
        Kit kitArmazenamento = new Kit("Kit de Armazenamento");
        kitArmazenamento.adicionar(new Peca("SSD NVMe 1TB", new BigDecimal("450.00")));
        kitArmazenamento.adicionar(new Peca("HD 2TB SATA", new BigDecimal("380.00")));
        kitArmazenamento.adicionar(new Peca("Cabo SATA Blindado", new BigDecimal("30.00")));

        assertEquals(new BigDecimal("860.00"), kitArmazenamento.calcularPreco());
        assertEquals(3, kitArmazenamento.getItens().size());
    }

    @Test
    void deveCalcularPrecoRecursivamenteParaKitCompleto() {
        Kit kitProcessamento = new Kit("Kit de Processamento");
        kitProcessamento.adicionar(new Peca("Processador AMD Ryzen 7 5700X", new BigDecimal("1250.00")));
        kitProcessamento.adicionar(new Peca("Placa-mãe B550M", new BigDecimal("780.00")));
        kitProcessamento.adicionar(new Peca("Memória RAM 16GB DDR4", new BigDecimal("320.00")));
        kitProcessamento.adicionar(new Peca("Pasta Térmica de Alta Condutividade", new BigDecimal("50.00")));

        Kit kitArmazenamento = new Kit("Kit de Armazenamento");
        kitArmazenamento.adicionar(new Peca("SSD NVMe 1TB", new BigDecimal("450.00")));
        kitArmazenamento.adicionar(new Peca("HD 2TB SATA", new BigDecimal("380.00")));
        kitArmazenamento.adicionar(new Peca("Cabo SATA Blindado", new BigDecimal("30.00")));

        Kit kitPerifericos = new Kit("Kit de Periféricos");
        kitPerifericos.adicionar(new Peca("Teclado Mecânico", new BigDecimal("350.00")));
        kitPerifericos.adicionar(new Peca("Mouse Óptico", new BigDecimal("220.00")));
        kitPerifericos.adicionar(new Peca("Headset com Microfone", new BigDecimal("280.00")));
        kitPerifericos.adicionar(new Peca("Mousepad Estendido", new BigDecimal("70.00")));

        Kit kitCompleto = new Kit("Kit Completo de Manutenção");
        kitCompleto.adicionar(kitProcessamento);
        kitCompleto.adicionar(kitArmazenamento);
        kitCompleto.adicionar(kitPerifericos);
        kitCompleto.adicionar(new Peca("Fonte 650W 80 Plus", new BigDecimal("450.00")));
        kitCompleto.adicionar(new Peca("Gabinete Mid Tower", new BigDecimal("300.00")));

        assertEquals(new BigDecimal("4930.00"), kitCompleto.calcularPreco());
    }

    @Test
    void deveRemoverItemDoKitEAtualizarPreco() {
        Kit kit = new Kit("Kit Básico");
        Peca teclado = new Peca("Teclado Mecânico", new BigDecimal("350.00"));
        Peca mouse = new Peca("Mouse Óptico", new BigDecimal("220.00"));

        kit.adicionar(teclado);
        kit.adicionar(mouse);

        assertTrue(kit.remover(mouse));
        assertFalse(kit.remover(mouse));
        assertEquals(new BigDecimal("350.00"), kit.calcularPreco());
        assertEquals(1, kit.getItens().size());
    }

    @Test
    void deveExibirHierarquiaDeKitsEPecasComIndentacao() {
        Kit subkit = new Kit("Kit de Armazenamento");
        subkit.adicionar(new Peca("SSD NVMe 1TB", new BigDecimal("450.00")));

        Kit kitPrincipal = new Kit("Kit Principal");
        kitPrincipal.adicionar(subkit);
        kitPrincipal.adicionar(new Peca("Fonte 650W", new BigDecimal("450.00")));

        String saida = kitPrincipal.exibir("");

        assertTrue(saida.contains("+ Kit Principal"));
        assertTrue(saida.contains("900,00"));
        assertTrue(saida.contains("\n  + Kit de Armazenamento"));
        assertTrue(saida.contains("\n    - SSD NVMe 1TB"));
        assertTrue(saida.contains("\n  - Fonte 650W"));
    }

    @Test
    void deveLancarExcecaoParaPecaComDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Peca(null, new BigDecimal("100.00")));
        assertThrows(IllegalArgumentException.class, () -> new Peca("   ", new BigDecimal("100.00")));
        assertThrows(IllegalArgumentException.class, () -> new Peca("SSD", null));
        assertThrows(IllegalArgumentException.class, () -> new Peca("SSD", new BigDecimal("-10.00")));
    }

    @Test
    void deveLancarExcecaoParaKitComNomeInvalidoOuItemNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Kit(null));
        assertThrows(IllegalArgumentException.class, () -> new Kit(""));

        Kit kit = new Kit("Kit Teste");
        assertThrows(IllegalArgumentException.class, () -> kit.adicionar(null));
    }

    @Test
    void deveImpedirAutoReferenciaNoKit() {
        Kit kit = new Kit("Kit Principal");

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> kit.adicionar(kit)
        );

        assertEquals("Um kit não pode conter a si mesmo.", excecao.getMessage());
    }

    @Test
    void deveImpedirCicloIndiretoEntreKits() {
        Kit kitA = new Kit("Kit A");
        Kit kitB = new Kit("Kit B");
        Kit kitC = new Kit("Kit C");

        kitA.adicionar(kitB);
        kitB.adicionar(kitC);

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> kitC.adicionar(kitA)
        );

        assertEquals("Não é permitido criar ciclos entre kits.", excecao.getMessage());
    }
}