package tecdes.pagamento;

import javax.swing.SwingUtilities;

import tecdes.pagamento.view.FormPagamento;

public class AppPagamento {
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new FormPagamento().setVisible(true));
    }
}