package co.edu.unicartagena.nomina.application;

import co.edu.unicartagena.nomina.domain.Money;
import java.math.BigDecimal;
import java.util.Objects;

/** The ARL rate is injected because the assignment does not provide one. */
public final class PercentageDeductionPolicy implements DeductionPolicy {
    private final BigDecimal arlRate;

    public PercentageDeductionPolicy(BigDecimal arlRate) {
        this.arlRate = Money.requireRate("tasa ARL configurable", Objects.requireNonNull(arlRate, "arlRate"));
    }

    @Override
    public DeductionBreakdown calculate(BigDecimal grossPay) {
        BigDecimal gross = Money.requireNonNegative("salario bruto", grossPay);
        BigDecimal socialAndPension = Money.percentOf(gross, Money.SOCIAL_PENSION_RATE);
        BigDecimal arl = Money.percentOf(gross, arlRate);
        return new DeductionBreakdown(socialAndPension, arl);
    }

    public BigDecimal arlRate() {
        return arlRate;
    }
}
