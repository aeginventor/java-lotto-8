package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    @DisplayName("구입 금액 입력 엣지 케이스 (재입력 테스트)")
    void 예외_테스트_구입금액() {
        assertSimpleTest(() -> {
            run("1000j", "1500", "0", "999999999999", "", "1000", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    "[ERROR] 구입 금액은 숫자여야 합니다.", // "1000j"
                    "[ERROR] 구입 금액은 1,000원 단위여야 합니다.", // "1500"
                    "[ERROR] 구입 금액은 1,000원 이상이어야 합니다.", // "0"
                    "[ERROR] 구입 금액의 숫자가 너무 큽니다 (Integer 범위 초과).", // "999999999999"
                    "[ERROR] 입력값이 비어있습니다.", // ""
                    "1개를 구매했습니다." // "1000" (복구 성공)
            );
        });
    }

    @Test
    @DisplayName("당첨 번호 입력 엣지 케이스 (재입력 테스트)")
    void 예외_테스트_당첨번호() {
        assertSimpleTest(() -> {
            run("1000",
                    "1,2,3,4,5,a", "1.2.3.4.5.6", "1,2,3,4,5,", "1,2,3,4,5,5", "1,2,3,4,5,46", "1,2,3,4,5,999999999999",
                    "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    "1개를 구매했습니다.",
                    "[ERROR] 당첨 번호 형식이 올바르지 않습니다.", // "a", "1.2.3", "1,2,3,"
                    "[ERROR] 로또 번호에 중복된 숫자가 있습니다.", // "1,2,3,4,5,5"
                    "[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.", // "1,2,3,4,5,46"
                    "[ERROR] 당첨 번호의 숫자가 너무 큽니다 (Integer 범위 초과).", // "999999999999"
                    "보너스 번호를 입력해 주세요." // "1,2,3,4,5,6" (복구 성공)
            );
        });
    }

    @Test
    @DisplayName("보너스 번호 입력 엣지 케이스 (재입력 테스트)")
    void 예외_테스트_보너스번호() {
        assertSimpleTest(() -> {
            run("1000", "1,2,3,4,5,6",
                    "a", "999999999999", "46", "6", "",
                    "7");
            assertThat(output()).contains(
                    "당첨 번호를 입력해 주세요.",
                    "[ERROR] 보너스 번호는 숫자여야 합니다.", // "a" (이전 단계에서 수정한 메시지)
                    "[ERROR] 보너스 번호의 숫자가 너무 큽니다 (Integer 범위 초과).", // "999999999999"
                    "[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.", // "46"
                    "[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.", // "6"
                    "[ERROR] 입력값이 비어있습니다.", // ""
                    "당첨 통계" // "7" (복구 성공)
            );
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
