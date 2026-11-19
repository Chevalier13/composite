package padroesestruturais.composite.kits;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Peca implements ItemKit {
    private final String nome;
    private final BigDecimal preco;

    public Peca(String nome, BigDecimal preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da peça é obrigatório.");
        }

        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo ou nulo.");
        }

        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    @Override
    public BigDecimal calcularPreco() {
        return preco;
    }

    @Override
    public String exibir(String indentacao) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );

        return indentacao + "- " + nome + " (" + formato.format(preco) + ")";
    }
}