package lotto;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    private static final int LOTTO_PRICE = 1000;
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String WINNING_NUMBER_DELIMITER = ",";

    public static void validatePurchaseAmount(String input) {
        int amount = validateNumeric(input);
        validateMinAmount(amount);
        validateAmountUnit(amount);
    }

    private static int validateNumeric(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_PREFIX + "구입 금액은 숫자여야 합니다.");
        }
    }

    private static void validateMinAmount(int amount) {
        if (amount < LOTTO_PRICE) {
            throw new IllegalArgumentException(ERROR_PREFIX + "구입 금액은 1,000원 이상이어야 합니다.");
        }
    }

    private static void validateAmountUnit(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(ERROR_PREFIX + "구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    public static List<Integer> parseWinningNumbers(String input) {
        String[] numberStrings = input.split(WINNING_NUMBER_DELIMITER);
        return convertToNumbers(numberStrings);
    }

    private static List<Integer> convertToNumbers(String[] numberStrings) {
        List<Integer> numbers = new ArrayList<>();
        for (String numberString : numberStrings) {
            try {
                numbers.add(Integer.parseInt(numberString.trim()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(ERROR_PREFIX + "당첨 번호는 숫자여야 합니다.");
            }
        }
        return numbers;
    }

    public static int validateBonusNumber(String input, Lotto winningLotto) {
        int bonusNumber;

        try {
            bonusNumber = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_PREFIX + "보너스 번호는 숫자여야 합니다.");
        }

        int MIN_LOTTO_NUMBER = 1;
        int MAX_LOTTO_NUMBER = 45;
        if (bonusNumber < MIN_LOTTO_NUMBER || bonusNumber > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException(
                    ERROR_PREFIX + "보너스 번호는 " + MIN_LOTTO_NUMBER + "부터 " + MAX_LOTTO_NUMBER + " 사이의 숫자여야 합니다.");
        }

        if (winningLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException(ERROR_PREFIX + "보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }

        return bonusNumber;
    }
}