package co.edu.unicartagena.nomina.domain;

import java.math.BigDecimal;

public final class SalariedEmployee extends AbstractEmployee {
    private final BigDecimal monthlySalary;
    private final int yearsOfService;

    public SalariedEmployee(String id, String fullName, BigDecimal monthlySalary, int yearsOfService) {
        super(id, fullName);
        if (yearsOfService < 0) throw new IllegalArgumentException("La antiguedad no puede ser negativa");
        this.monthlySalary = Money.requireNonNegative("salario mensual", monthlySalary);
        this.yearsOfService = yearsOfService;
    }

    @Override
    public BigDecimal grossPay() {
        BigDecimal bonus = yearsOfService > 5
                ? Money.percentOf(monthlySalary, new BigDecimal("0.10"))
                : Money.ZERO;
        return Money.round(monthlySalary.add(bonus));
    }

    @Override
    public BigDecimal employerPaidBenefits() {
        return Money.FOOD_BENEFIT;
    }
}
