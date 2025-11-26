package tecdes.pagamento.model;

public class CartaoPagamento implements Pagamento{

    @Override
    public String processar(double valor) {
        return "Pagameto de R$ " + valor + " processado via cartao";
    }

}
