package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Rank;

import java.text.DecimalFormat;
import java.util.List;

public class OutputView {

    private static final List<Rank> DISPLAY_RANKS = List.of(
            Rank.FIFTH,
            Rank.FOURTH,
            Rank.THIRD,
            Rank.SECOND,
            Rank.FIRST
    );

    public void printLottos(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            System.out.println(lotto.toString());
        }
    }

    public void printStatistics(LottoResult result, int purchaseAmount) {
        System.out.println("\n당첨 통계");
        System.out.println("---");

        for (Rank rank : DISPLAY_RANKS) {
            System.out.println(
                    rank.getMessage() + " - " + result.getCount(rank) + "개"
            );
        }

        double profitRate = result.calculateProfitRate(purchaseAmount);
        String profitRateFormat = ",##0.0";
        DecimalFormat formatter = new DecimalFormat(profitRateFormat);
        String formattedProfitRate = formatter.format(profitRate);

        System.out.println("총 수익률은 " + formattedProfitRate + "%입니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }
}