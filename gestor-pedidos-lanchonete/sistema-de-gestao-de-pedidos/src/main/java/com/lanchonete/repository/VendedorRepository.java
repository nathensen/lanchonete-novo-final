package com.lanchonete.repository;

import com.lanchonete.model.Vendedor;
import java.util.ArrayList;
import java.util.List;

public class VendedorRepository implements IVendedorRepository {
    private final List<Vendedor> vendedores = new ArrayList<>();

    @Override
    public void salvar(Vendedor vendedor) {
        if (vendedor != null && !vendedores.contains(vendedor)) {
            vendedores.add(vendedor);
        }
    }

    @Override
    public void remover(Vendedor vendedor) {
        vendedores.remove(vendedor);
    }

    @Override
    public Vendedor buscarPorCodigo(double codigo) {
        return vendedores.stream()
                .filter(v -> v.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Vendedor> listarTodos() {
        return new ArrayList<>(vendedores);
    }
}
