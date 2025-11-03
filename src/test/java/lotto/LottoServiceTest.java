package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoServiceTest {

    @DisplayName("구입 금액만큼 로또를 발행한다.")
    @Test
    void issueLottosByAmount() {
        LottoService lottoService = new LottoService();
        int purchaseAmount = 8000;

        List<Lotto> lottos = lottoService.issueLottos(purchaseAmount);

        assertThat(lottos).hasSize(8);
    }

    @DisplayName("당첨 번호와 로또 목록을 비교하여 정확한 통계(LottoResult)를 반환한다.")
    @Test
    void calculateResults() {
        LottoService lottoService = new LottoService();

        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        List<Lotto> userLottos = Arrays.asList(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)), // 1등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)), // 2등
                new Lotto(List.of(1, 2, 3, 4, 5, 8)), // 3등
                new Lotto(List.of(1, 2, 3, 4, 8, 9)), // 4등
                new Lotto(List.of(1, 2, 3, 8, 9, 10)),// 5등
                new Lotto(List.of(10, 11, 12, 13, 14, 15)) // 꽝(MISS)
        );

        LottoResult result = lottoService.calculateResults(userLottos, winningLotto, bonusNumber);

        assertThat(result.getCount(Rank.FIRST)).isEqualTo(1);
        assertThat(result.getCount(Rank.SECOND)).isEqualTo(1);
        assertThat(result.getCount(Rank.THIRD)).isEqualTo(1);
        assertThat(result.getCount(Rank.FOURTH)).isEqualTo(1);
        assertThat(result.getCount(Rank.FIFTH)).isEqualTo(1);
        assertThat(result.getCount(Rank.MISS)).isEqualTo(1);
    }
}