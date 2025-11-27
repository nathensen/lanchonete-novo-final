package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Vendedor;

public class VendedorRepository implements IVendedorRepository {

    private List<Vendedor> vendedores = new ArrayList<>();

    @Override
    public void salvar(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    @Override
    public Vendedor buscarPorCodigo(int codigo) {
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
