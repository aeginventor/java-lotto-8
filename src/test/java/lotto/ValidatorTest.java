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
}