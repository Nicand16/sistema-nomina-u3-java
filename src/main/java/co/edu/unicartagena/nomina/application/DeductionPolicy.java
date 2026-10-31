package co.edu.unicartagena.nomina.application;

import java.math.BigDecimal;

public interface DeductionPolicy {
    DeductionBreakdown calculate(BigDecimal grossPay);
}
