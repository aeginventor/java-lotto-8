package lotto;

import java.text.DecimalFormat;
import java.util.List;

public class LottoGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public LottoGameController(InputView inputView, OutputView outputView, LottoService lottoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
    }

    public void run() {
        try {
            int purchaseAmount = getPurchaseAmountWithValidation();
            List<Lotto> lottos = lottoService.issueLottos(purchaseAmount);
            outputView.printLottos(lottos);

            Lotto winningLotto = getWinningLottoWithValidation();
            int bonusNumber = getBonusNumberWithValidation(winningLotto);

            LottoResult result = lottoService.calculateResults(lottos, winningLotto, bonusNumber);
            printStatistics(result, purchaseAmount);

        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private int getPurchaseAmountWithValidation() {
        while (true) {
            String input = inputView.readPurchaseAmount();
            try {
                Validator.validatePurchaseAmount(input);
                return Integer.parseInt(input);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Lotto getWinningLottoWithValidation() {
        while (true) {
            String input = inputView.readWinningNumbers();
            try {
                List<Integer> numbers = Validator.parseWinningNumbers(input);
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private int getBonusNumberWithValidation(Lotto winningLotto) {
        while (true) {
            String input = inputView.readBonusNumber();
            try {
                return Validator.validateBonusNumber(input, winningLotto);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printStatistics(LottoResult result, int purchaseAmount) {
        double profitRate = result.calculateProfitRate(purchaseAmount);

        String profitRateFormat = ",##0.0";
        DecimalFormat decimalFormat = new DecimalFormat(profitRateFormat);
        String formattedProfitRate = decimalFormat.format(profitRate);

        outputView.printStatistics(result, formattedProfitRate);
    }
}