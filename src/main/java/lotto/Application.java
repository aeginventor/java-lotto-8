package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.text.DecimalFormat;
import java.util.List;

public class Application {

    private static final List<Rank> DISPLAY_RANKS = List.of(
            Rank.FIFTH,
            Rank.FOURTH,
            Rank.THIRD,
            Rank.SECOND,
            Rank.FIRST
    );

    private final LottoService lottoService = new LottoService();

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        try {
            int purchaseAmount = getPurchaseAmountWithValidation();

            List<Lotto> lottos = lottoService.issueLottos(purchaseAmount);

            printLottos(lottos);

            Lotto winningLotto = getWinningLottoWithValidation();

            int bonusNumber = getBonusNumberWithValidation(winningLotto);

            LottoResult result = lottoService.calculateResults(lottos, winningLotto, bonusNumber);

            printStatistics(result, purchaseAmount);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getPurchaseAmountWithValidation() {
        while (true) {
            System.out.println("구입금액을 입력해 주세요.");
            String input = Console.readLine();

            try {
                Validator.validatePurchaseAmount(input);
                return Integer.parseInt(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printLottos(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");

        for (Lotto lotto : lottos) {
            System.out.println(lotto.toString());
        }
    }

    private Lotto getWinningLottoWithValidation() {
        while (true) {
            System.out.println("\n당첨 번호를 입력해 주세요.");
            String input = Console.readLine();
            try {
                List<Integer> numbers = Validator.parseWinningNumbers(input);

                return new Lotto(numbers);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getBonusNumberWithValidation(Lotto winningLotto) {
        while (true) {
            System.out.println("\n보너스 번호를 입력해 주세요.");
            String input = Console.readLine();
            try {
                return Validator.validateBonusNumber(input, winningLotto);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printStatistics(LottoResult result, int purchaseAmount) {
        double profitRate = result.calculateProfitRate(purchaseAmount);
        String profitRateFormat = ",##0.0";
        DecimalFormat decimalFormat = new DecimalFormat(profitRateFormat);
        String formattedProfitRate = decimalFormat.format(profitRate);

        System.out.println("\n당첨 통계");
        System.out.println("---");

        for (Rank rank : DISPLAY_RANKS) {
            System.out.println(
                    rank.getMessage() + " - " + result.getCount(rank) + "개"
            );
        }

        System.out.println("총 수익률은 " + formattedProfitRate + "%입니다.");
    }
}
