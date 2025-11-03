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

            // TODO: 당첨 번호 입력 구현

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
}
