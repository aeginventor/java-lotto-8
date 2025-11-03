package lotto;

public enum Rank {
    FIRST(6, 2_000_000_000L),
    SECOND(5, 30_000_000L),
    THIRD(5, 1_500_000L),
    FOURTH(4, 50_000L),
    FIFTH(3, 5_000L),
    MISS(0, 0L);

    private final int matchCount;
    private final long prizeMoney;

    Rank(int matchCount, long prizeMoney) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public static Rank valueOf(int matchCount, boolean bonusMatch) {
        // 6개 일치 (1등)
        if (matchCount == 6) {
            return FIRST;
        }

        // 5개 일치 (2등 또는 3등)
        if (matchCount == 5) {
            if (bonusMatch) {
                return SECOND;
            }
            return THIRD;
        }

        // 4개 일치 (4등)
        if (matchCount == 4) {
            return FOURTH;
        }

        // 3개 일치 (5등)
        if (matchCount == 3) {
            return FIFTH;
        }

        // 꽝
        return MISS;
    }
}