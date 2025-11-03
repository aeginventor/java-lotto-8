package lotto.domain;

import java.util.EnumMap;
import java.util.Map;

public class LottoResult {

    private final Map<Rank, Integer> results;

    public LottoResult() {
        this.results = new EnumMap<>(Rank.class);
        initialize();
    }

    private void initialize() {
        for (Rank rank : Rank.values()) {
            results.put(rank, 0);
        }
    }

    public void add(Rank rank) {
        results.put(rank, results.get(rank) + 1);
    }

    public int getCount(Rank rank) {
        return results.get(rank);
    }

    private long calculateTotalPrize() {
        return results.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
    }

    public double calculateProfitRate(int purchaseAmount) {
        long totalPrize = calculateTotalPrize();

        if (purchaseAmount == 0) {
            return 0.0; // 0으로 나누기 방지
        }

        double rate = (double) totalPrize / purchaseAmount * 100.0;

        // 소수점 둘째 자리에서 반올림 (첫째 자리까지 표시)
        return Math.round(rate * 10) / 10.0;
    }
}