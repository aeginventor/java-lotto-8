package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {

    private LottoResult lottoResult;

    @BeforeEach
    void setUp() {
        lottoResult = new LottoResult();
    }

    @DisplayName("LottoResult 생성 시 모든 등수의 횟수는 0으로 초기화된다.")
    @Test
    void lottoResult_should_be_initialized_with_zero_counts() {
        for (Rank rank : Rank.values()) {
            assertThat(lottoResult.getCount(rank)).isZero();
        }
    }

    @DisplayName("add() 메서드는 해당 등수의 횟수를 1 증가시킨다.")
    @Test
    void add_should_increment_rank_count() {
        lottoResult.add(Rank.FIFTH);
        lottoResult.add(Rank.FIFTH);
        lottoResult.add(Rank.FOURTH);

        assertThat(lottoResult.getCount(Rank.FIFTH)).isEqualTo(2);
        assertThat(lottoResult.getCount(Rank.FOURTH)).isEqualTo(1);
        assertThat(lottoResult.getCount(Rank.FIRST)).isZero();
    }
}