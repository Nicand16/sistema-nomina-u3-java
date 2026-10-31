package co.edu.unicartagena.nomina.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {
    public static final BigDecimal ZERO = new BigDecimal("0.00");
    public static final BigDecimal SOCIAL_PENSION_RATE = new BigDecimal("0.04");
    public static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");
    public static final BigDecimal FOOD_BENEFIT = new BigDecimal("1000000.00");
    public static final BigDecimal HOURLY_SAVINGS_RATE = new BigDecimal("0.02");
    public static final BigDecimal COMMISSION_BONUS_RATE = new BigDecimal("0.03");
    public static final BigDecimal COMMISSION_BONUS_THRESHOLD = new BigDecimal("20000000.00");

    private Money() { }

    public static BigDecimal round(BigDecimal amount) {
        return Objects.requireNonNull(amount, "amount").setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal percentOf(BigDecimal amount, BigDecimal rate) {
        return round(amount.multiply(rate));
    }

    public static BigDecimal requireNonNegative(String field, BigDecimal value) {
        Objects.requireNonNull(value, field);
        if (value.signum() < 0) {
            throw new IllegalArgumentException(field + " no puede ser negativo");
        }
        return round(value);
    }

    public static BigDecimal requireRate(String field, BigDecimal rate) {
        Objects.requireNonNull(rate, field);
        if (rate.signum() < 0 || rate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException(field + " debe estar entre 0 y 1 (inclusive)");
        }
        return rate;
    }
}
