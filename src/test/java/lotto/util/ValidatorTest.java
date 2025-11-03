package lotto.util;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatorTest {

    @DisplayName("입력값이 null이거나 비어있으면 예외가 발생한다.")
    @Test
    void validateInput_NullOrEmpty() {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 입력값이 비어있습니다.");

        assertThatThrownBy(() -> Validator.validatePurchaseAmount(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 입력값이 비어있습니다.");
    }

    @DisplayName("구입 금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void validatePurchaseAmount_NotNumeric() {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount("1000j"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 숫자여야 합니다.");
    }

    @DisplayName("구입 금액이 Integer 범위를 초과하면 예외가 발생한다.")
    @Test
    void validatePurchaseAmount_Overflow() {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount("999999999999"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액의 숫자가 너무 큽니다 (Integer 범위 초과).");
    }

    @DisplayName("구입 금액에 앞뒤 공백이 있어도 숫자로 변환되어야 한다.")
    @Test
    void validatePurchaseAmount_WithSpaces() {
        // 이 테스트는 예외가 발생하지 않아야 통과
        Validator.validatePurchaseAmount(" 1000");
        Validator.validatePurchaseAmount("1000 ");
    }

    @DisplayName("구입 금액이 1,000원 단위가 아니면 예외가 발생한다.")
    @Test
    void validatePurchaseAmount_NotMultipleOf1000() {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount("1500"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
    }

    @DisplayName("구입 금액이 1,000원 미만이면 예외가 발생한다.")
    @Test
    void validatePurchaseAmount_LessThan1000() {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount("0"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 1,000원 이상이어야 합니다.");

        assertThatThrownBy(() -> Validator.validatePurchaseAmount("500"))
                .isInstanceOf(IllegalArgumentException.class); // 1000원 단위 에러가 먼저 발생
    }

    @DisplayName("당첨 번호 입력이 쉼표(,)로 끝나면 예외가 발생한다.")
    @Test
    void parseWinningNumbers_EndsWithDelimiter() {
        assertThatThrownBy(() -> Validator.parseWinningNumbers("1,2,3,4,5,"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("당첨 번호에 쉼표(,) 외의 구분자가 포함되면 예외가 발생한다.")
    @Test
    void parseWinningNumbers_InvalidDelimiters() {
        String expectedMessage = "[ERROR] 당첨 번호 형식이 올바르지 않습니다.";

        // 케이스 1: 다른 구분자 사용
        assertThatThrownBy(() -> Validator.parseWinningNumbers("1.2.3.4.5.6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);

        // 케이스 2: 쉼표와 다른 구분자 혼용
        assertThatThrownBy(() -> Validator.parseWinningNumbers("1,2,3;4,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    @DisplayName("당첨 번호에 숫자 형식이 아닌 값이 포함되면 예외가 발생한다.")
    @Test
    void parseWinningNumbers_NotNumeric() {
        assertThatThrownBy(() -> Validator.parseWinningNumbers("1,2,3,4,5,a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("당첨 번호가 Integer 범위를 초과하면 예외가 발생한다.")
    @Test
    void parseWinningNumbers_Overflow() {
        assertThatThrownBy(() -> Validator.parseWinningNumbers("1,2,3,4,5,999999999999"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호의 숫자가 너무 큽니다 (Integer 범위 초과).");
    }

    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다.")
    @Test
    void validateBonusNumber_NotNumeric() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> Validator.validateBonusNumber("a", winningLotto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 숫자여야 합니다.");
    }

    @DisplayName("보너스 번호가 Integer 범위를 초과하면 예외가 발생한다.")
    @Test
    void validateBonusNumber_Overflow() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> Validator.validateBonusNumber("999999999999", winningLotto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호의 숫자가 너무 큽니다 (Integer 범위 초과).");
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void validateBonusNumber_OutOfRange() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> Validator.validateBonusNumber("46", winningLotto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");

        assertThatThrownBy(() -> Validator.validateBonusNumber("0", winningLotto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void validateBonusNumber_DuplicateWithWinningNumbers() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> Validator.validateBonusNumber("6", winningLotto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }
}