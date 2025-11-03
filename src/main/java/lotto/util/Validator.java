package lotto.util;

import lotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {

    private static final int LOTTO_PRICE = 1000;
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String WINNING_NUMBER_DELIMITER = ",";
    private static final String WINNING_NUMBER_FORMAT_REGEX = "^[0-9]+(,[0-9]+)*$";
    private static final Pattern WINNING_NUMBER_PATTERN = Pattern.compile(WINNING_NUMBER_FORMAT_REGEX);

    private static void validateNotEmpty(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ERROR_PREFIX + "입력값이 비어있습니다.");
        }
    }

    public static void validatePurchaseAmount(String input) {
        validateNotEmpty(input);
        int amount = validateNumeric(input);
        validateMinAmount(amount);
        validateAmountUnit(amount);
    }

    private static int validateNumeric(String input) {
        try {
            return Integer.parseInt(input.trim());
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
        validateNotEmpty(input);

        validateWinningNumberFormat(input);

        String[] numberStrings = input.split(WINNING_NUMBER_DELIMITER);
        return convertToNumbers(numberStrings);
    }

    private static void validateWinningNumberFormat(String input) {
        if (!WINNING_NUMBER_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(ERROR_PREFIX + "당첨 번호 형식이 올바르지 않습니다.");
        }
    }

    private static List<Integer> convertToNumbers(String[] numberStrings) {
        List<Integer> numbers = new ArrayList<>();
        for (String numberString : numberStrings) {
            try {
                String trimmedString = numberString.trim();
                numbers.add(Integer.parseInt(trimmedString));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(ERROR_PREFIX + "당첨 번호는 숫자여야 합니다.");
            }
        }
        return numbers;
    }

    public static int validateBonusNumber(String input, Lotto winningLotto) {
        validateNotEmpty(input);
        int bonusNumber = validateNumericBonus(input);
        validateRange(bonusNumber);
        validateDuplicateWithWinningLotto(bonusNumber, winningLotto);
        return bonusNumber;
    }

    private static int validateNumericBonus(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_PREFIX + "보너스 번호는 숫자여야 합니다.");
        }
    }

    private static void validateRange(int number) {
        Lotto.validateNumberRange(number);
    }

    private static void validateDuplicateWithWinningLotto(int bonusNumber, Lotto winningLotto) {
        if (winningLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException(ERROR_PREFIX + "보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}