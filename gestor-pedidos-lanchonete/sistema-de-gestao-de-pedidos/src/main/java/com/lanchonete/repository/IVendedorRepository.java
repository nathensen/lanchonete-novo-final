/**
 * IVendedorRepository
 * --------------------
 * O que ele altera no programa:
 * - Permite que o Service e o Controller acessem vendedores sem
 *   conhecer detalhes de como os dados são armazenados.
 * - Facilita manutenção, testes e futuras trocas de implementação
 *   (ex: trocar ArrayList por banco SQL).
 */

package com.lanchonete.repository;

import java.util.List;

import com.lanchonete.model.Vendedor;

public interface IVendedorRepository {

    void salvar(Vendedor vendedor);

    void remover(Vendedor vendedor);

    Vendedor buscarPorCodigo(int codigo); // <-- Agora INT, correto!

    List<Vendedor> listarTodos();
}
