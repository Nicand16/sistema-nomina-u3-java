package co.edu.unicartagena.nomina.application;

import co.edu.unicartagena.nomina.domain.Money;
import java.math.BigDecimal;

public record PayrollResult(
        String employeeId,
        String employeeName,
        BigDecimal grossPay,
        BigDecimal employerPaidBenefits,
        BigDecimal socialSecurityAndPension,
        BigDecimal arl,
        BigDecimal savingsContribution,
        BigDecimal netPay) {

    public PayrollResult {
        grossPay = Money.round(grossPay);
        employerPaidBenefits = Money.round(employerPaidBenefits);
        socialSecurityAndPension = Money.round(socialSecurityAndPension);
        arl = Money.round(arl);
        savingsContribution = Money.round(savingsContribution);
        netPay = Money.round(netPay);
    }

    public BigDecimal totalDeductions() {
        return Money.round(socialSecurityAndPension.add(arl).add(savingsContribution));
    }
}
