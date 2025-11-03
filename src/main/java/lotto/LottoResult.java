package lotto;

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
}