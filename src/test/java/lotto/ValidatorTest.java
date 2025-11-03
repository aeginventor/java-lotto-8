package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatorTest {

    @DisplayName("구입 금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void validatePurchaseAmount_NotNumeric() {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount("1000j"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 숫자여야 합니다.");
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

    @DisplayName("당첨 번호에 숫자가 아닌 값이 포함되면 예외가 발생한다.")
    @Test
    void parseWinningNumbers_NotNumeric() {
        assertThatThrownBy(() -> Validator.parseWinningNumbers("1,2,3,4,5,a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 숫자여야 합니다.");
    }
}