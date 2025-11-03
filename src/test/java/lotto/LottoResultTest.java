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

    @DisplayName("구매 금액과 총 상금을 기반으로 수익률을 계산한다 (소수점 둘째 자리 반올림).")
    @Test
    void calculateProfitRate_should_round_to_first_decimal_place() {
        // [GIVEN] 8000원 구매, 5등 (5000원) 1개 당첨
        int purchaseAmount = 8000;
        lottoResult.add(Rank.FIFTH);

        // [WHEN] 수익률 계산
        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);

        // [THEN] 62.5%
        assertThat(profitRate).isEqualTo(62.5);
    }

    @DisplayName("수익률이 0일 경우 0.0을 반환한다.")
    @Test
    void calculateProfitRate_should_return_zero_when_no_prize() {
        int purchaseAmount = 1000;
        // (당첨 내역 없음)

        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(0.0);
    }
}