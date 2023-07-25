package kata.string_calculator;

import java.util.regex.Pattern;

public class StringCalculator {
    public static final String STANDARD_REGEX = ",|\n";
    public static final int MAX_ADDEND = 1000;

    private static final Pattern DELIMITER_SPLIT_REGEX = Pattern.compile("\\]\\[");

    private int calledCount = 0;

    public int getCalledCount() {
        return calledCount;
    }

    /**
     * takes a string of comma-separated numbers, returns their sum
     * 
     * @param numbers numbers separated by commas, with the first line
     *                specifying delimiters
     * 
     * @return sum of {@code numbers}
     * 
     * @throws NumberFormatException    if the string contains characters other
     *                                  than 0-9 and specified delimiters
     * @throws IllegalArgumentException if there is a delimiter specification,
     *                                  but no line break following the
     *                                  delimiter(s), or there are negative
     *                                  numbers
     */
    public int add(String numbers) {
        calledCount++;

        String regex = "";
        if (numbers.startsWith("//")) {
            int endOfFirstLine = numbers.indexOf('\n');
            if (endOfFirstLine < 0) {
                throw new IllegalArgumentException("If specifying delimiters, there needs to be > 1 line");
            }

            String delimitersLine = numbers.substring(0, endOfFirstLine);
            delimitersLine = delimitersLine.substring("//[".length(), delimitersLine.length() - "]".length());

            StringBuilder sb = new StringBuilder();
            for (String delim : delimitersLine.split(DELIMITER_SPLIT_REGEX.pattern())) {
                sb.append(Pattern.quote(delim) + '|');
            }
            regex = sb.substring(0, sb.length() - 1);
            numbers = numbers.substring(endOfFirstLine + 1);
        } else {
            regex = STANDARD_REGEX;
        }

        if ("".equals(numbers)) {
            return 0;
        }

        var stream = numbers.split(regex);
        StringBuilder errorMessage = new StringBuilder();
        int sum = 0;
        for (String str : stream) {
            int n = Integer.parseInt(str);
            if (n < 0) {
                errorMessage.append(n + ", ");
            }
            if (n > MAX_ADDEND) {
                continue;
            }
            sum += n;
        }
        if (!errorMessage.isEmpty()) {
            String err = errorMessage.substring(0, errorMessage.length() - ", ".length());
            throw new IllegalArgumentException("negatives not allwoed: " + err);
        }
        return sum;
    }
}
