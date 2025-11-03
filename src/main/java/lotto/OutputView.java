package lotto;

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

    public void printStatistics(LottoResult result, String formattedProfitRate) {
        System.out.println("\n당첨 통계");
        System.out.println("---");

        for (Rank rank : DISPLAY_RANKS) {
            System.out.println(
                    rank.getMessage() + " - " + result.getCount(rank) + "개"
            );
        }
        System.out.println("총 수익률은 " + formattedProfitRate + "%입니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }
}