/**
 * VendedorRepository
 * -------------------
 * O que ela altera no programa:
 * - Controla onde e como os vendedores são guardados.
 * - Permite que o restante do sistema trabalhe com vendedores
 *   sem se preocupar com armazenamento.
 */

package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Vendedor;

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
