package padroesestruturais.composite.kits;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
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

        NumberFormat formato = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );

        System.out.println(kitCompleto.exibir(""));
        System.out.println();
        System.out.println("Valor total do kit completo: " + formato.format(kitCompleto.calcularPreco()));
    }
}