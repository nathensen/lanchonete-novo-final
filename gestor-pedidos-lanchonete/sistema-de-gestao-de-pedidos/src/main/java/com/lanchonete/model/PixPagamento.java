package tecdes.pagamento.model;

public class PixPagamento implements Pagamento{

    @Override
    public String processar(double valor) {
        return "Pagamento de R$ " + valor + " realizado via pix";
    }

}
