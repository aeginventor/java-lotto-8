package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @DisplayName("일치 개수와 보너스 번호 일치 여부에 따라 정확한 등수를 반환한다.")
    @ParameterizedTest(name = "{0}개 일치, 보너스: {1} -> {2}")
    @CsvSource({
            "6, false, FIRST",   // 1등 (보너스 여부 상관없음)
            "5, true, SECOND",  // 2등
            "5, false, THIRD",  // 3등
            "4, false, FOURTH", // 4등 (보너스 여부 상관없음)
            "3, false, FIFTH",  // 5등 (보너스 여부 상관없음)
            "2, false, MISS",   // 꽝
            "1, false, MISS",   // 꽝
            "0, false, MISS"    // 꽝
    })
    void valueOf(int matchCount, boolean bonusMatch, Rank expectedRank) {
        assertThat(Rank.valueOf(matchCount, bonusMatch)).isEqualTo(expectedRank);
    }
}