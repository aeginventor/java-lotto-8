package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class LottoService {

    private static final int LOTTO_PRICE = 1000;

    public List<Lotto> issueLottos(int purchaseAmount) {
        int count = calculateLottoCount(purchaseAmount);
        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            lottos.add(generateLotto());
        }
        return lottos;
    }

    private int calculateLottoCount(int purchaseAmount) {
        return purchaseAmount / LOTTO_PRICE;
    }

    private Lotto generateLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                Lotto.MIN_LOTTO_NUMBER, Lotto.MAX_LOTTO_NUMBER, Lotto.LOTTO_NUMBER_COUNT
        );
        return new Lotto(numbers);
    }

    public LottoResult calculateResults(List<Lotto> userLottos, Lotto winningLotto, int bonusNumber) {
        LottoResult lottoResult = new LottoResult();

        for (Lotto userLotto : userLottos) {
            Rank rank = determineRank(userLotto, winningLotto, bonusNumber);
            lottoResult.add(rank);
        }
        return lottoResult;
    }

    private Rank determineRank(Lotto userLotto, Lotto winningLotto, int bonusNumber) {
        int matchCount = userLotto.countMatch(winningLotto);
        boolean bonusMatch = userLotto.contains(bonusNumber);
        return Rank.valueOf(matchCount, bonusMatch);
    }
}