package lotto;

public class Validator {

    private static final int LOTTO_PRICE = 1000;
    private static final String ERROR_PREFIX = "[ERROR] ";

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
}