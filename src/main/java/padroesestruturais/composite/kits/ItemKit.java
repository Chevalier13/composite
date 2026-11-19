package padroesestruturais.composite.kits;

import java.math.BigDecimal;

public interface ItemKit {
    String getNome();

    BigDecimal calcularPreco();

    String exibir(String indentacao);
}