package co.edu.unicartagena.nomina.application;

import co.edu.unicartagena.nomina.domain.Money;
import java.math.BigDecimal;

public record DeductionBreakdown(BigDecimal socialSecurityAndPension, BigDecimal arl) {
    public DeductionBreakdown {
        socialSecurityAndPension = Money.round(socialSecurityAndPension);
        arl = Money.round(arl);
    }

    public BigDecimal total() {
        return Money.round(socialSecurityAndPension.add(arl));
    }
}
