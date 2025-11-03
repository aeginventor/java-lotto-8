package lotto;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class Application {

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

            // TODO

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
}
