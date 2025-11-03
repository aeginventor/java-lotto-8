package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}