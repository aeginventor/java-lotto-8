package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class LottoService {

    private static final int LOTTO_PRICE = 1000;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

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
                MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER, LOTTO_NUMBER_COUNT
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