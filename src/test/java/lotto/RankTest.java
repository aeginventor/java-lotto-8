package lotto;

import lotto.domain.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("각 등수별로 형식에 맞는 출력 메시지를 반환한다.")
    @Test
    void getMessage() {
        String expectedFifthMessage = "3개 일치 (5,000원)";
        String expectedSecondMessage = "5개 일치, 보너스 볼 일치 (30,000,000원)";

        assertThat(Rank.FIFTH.getMessage()).isEqualTo(expectedFifthMessage);
        assertThat(Rank.SECOND.getMessage()).isEqualTo(expectedSecondMessage);
    }

    @DisplayName("MISS 등급은 메시지 반환 시 null이나 빈 값이 아니다 (처리 대상이 아님).")
    @Test
    void getMessage_MISS() {
        // MISS 등급은 출력하지 않을 것이므로, null만 아니면 됨
        assertThat(Rank.MISS.getMessage()).isNotNull();
    }
}