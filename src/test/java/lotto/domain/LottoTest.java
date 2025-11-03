package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 1~45 범위 밖의 숫자면 예외가 발생한다.")
    @Test
    void 로또_번호의_범위_검사() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Lotto(List.of(0, 1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 오름차순으로 정렬되어야 한다.")
    @Test
    void lottoNumbersShouldBeSorted() {
        Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));

        assertThat(lotto.toString()).isEqualTo("[1, 2, 3, 4, 5, 6]");
    }

    @DisplayName("로또 번호가 특정 숫자를 포함하고 있는지 확인할 수 있다.")
    @Test
    void containsNumber() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.contains(6)).isTrue();

        assertThat(lotto.contains(7)).isFalse();
    }

    @DisplayName("다른 로또와 몇 개의 번호가 일치하는지 계산한다.")
    @Test
    void countMatch() {
        Lotto myLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto winningLotto = new Lotto(List.of(1, 2, 7, 8, 9, 10));

        assertThat(myLotto.countMatch(winningLotto)).isEqualTo(2);
    }

    @DisplayName("일치하는 번호가 없으면 0을 반환한다.")
    @Test
    void countMatch_Zero() {
        Lotto myLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto winningLotto = new Lotto(List.of(7, 8, 9, 10, 11, 12));

        assertThat(myLotto.countMatch(winningLotto)).isEqualTo(0);
    }
}
