package lotto.domain;

import java.text.DecimalFormat;
import java.util.Arrays;

public enum Rank {
    FIRST(6, 2_000_000_000L, "6개 일치 (%s원)"),
    SECOND(5, 30_000_000L, "5개 일치, 보너스 볼 일치 (%s원)"),
    THIRD(5, 1_500_000L, "5개 일치 (%s원)"),
    FOURTH(4, 50_000L, "4개 일치 (%s원)"),
    FIFTH(3, 5_000L, "3개 일치 (%s원)"),
    MISS(0, 0L, "");

    private static final String PRIZE_FORMAT = ",##0";

    private final int matchCount;
    private final long prizeMoney;
    private final String messageFormat;

    Rank(int matchCount, long prizeMoney, String messageFormat) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
        this.messageFormat = messageFormat;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public static Rank valueOf(int matchCount, boolean bonusMatch) {
        // 2등/3등 분기 로직
        if (matchCount == 5) {
            if (bonusMatch) {
                return SECOND;
            }
            return THIRD;
        }

        return Arrays.stream(Rank.values())
                .filter(rank -> rank.matchCount == matchCount)
                .filter(rank -> rank != SECOND && rank != THIRD)
                .findFirst()
                .orElse(MISS);
    }

    public String getMessage() {
        if (this == MISS) {
            return "MISS";
        }
        DecimalFormat formatter = new DecimalFormat(PRIZE_FORMAT);
        String formattedPrize = formatter.format(prizeMoney);

        return String.format(messageFormat, formattedPrize);
    }
}