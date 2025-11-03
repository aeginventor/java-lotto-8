package lotto;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        LottoService lottoService = new LottoService();

        LottoGameController lottoGameController = new LottoGameController(
                inputView, outputView, lottoService
        );

        lottoGameController.run();
    }
}