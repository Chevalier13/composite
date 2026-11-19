package padroesestruturais.composite.kits;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Kit implements ItemKit {
    private final String nome;
    private final List<ItemKit> itens;

    public Kit(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do kit é obrigatório.");
        }

        this.nome = nome;
        this.itens = new ArrayList<>();
    }

    public void adicionar(ItemKit item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
        }

        if (item == this) {
            throw new IllegalArgumentException("Um kit não pode conter a si mesmo.");
        }

        if (item instanceof Kit && ((Kit) item).contem(this)) {
            throw new IllegalArgumentException("Não é permitido criar ciclos entre kits.");
        }

        itens.add(item);
    }

    public boolean remover(ItemKit item) {
        return itens.remove(item);
    }

    public List<ItemKit> getItens() {
        return Collections.unmodifiableList(itens);
    }

    private boolean contem(ItemKit alvo) {
        for (ItemKit item : itens) {
            if (item == alvo) {
                return true;
            }

            if (item instanceof Kit && ((Kit) item).contem(alvo)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public BigDecimal calcularPreco() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemKit item : itens) {
            total = total.add(item.calcularPreco());
        }

        return total;
    }

    @Override
    public String exibir(String indentacao) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );

        StringBuilder builder = new StringBuilder();
        builder.append(indentacao)
                .append("+ ")
                .append(nome)
                .append(" (Total: ")
                .append(formato.format(calcularPreco()))
                .append(")");

        for (ItemKit item : itens) {
            builder.append("\n").append(item.exibir(indentacao + "  "));
        }

        return builder.toString();
    }
}