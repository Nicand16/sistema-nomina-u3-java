package co.edu.unicartagena.nomina.domain;

import java.math.BigDecimal;

public final class CommissionEmployee extends AbstractEmployee {
    private final BigDecimal monthlyBaseSalary;
    private final BigDecimal sales;
    private final BigDecimal commissionRate;

    public CommissionEmployee(String id, String fullName, BigDecimal monthlyBaseSalary,
                              BigDecimal sales, BigDecimal commissionRate) {
        super(id, fullName);
        this.monthlyBaseSalary = Money.requireNonNegative("salario base", monthlyBaseSalary);
        this.sales = Money.requireNonNegative("ventas", sales);
        this.commissionRate = Money.requireRate("tasa de comision", commissionRate);
    }

    @Override
    public BigDecimal grossPay() {
        BigDecimal commission = Money.percentOf(sales, commissionRate);
        BigDecimal additionalBonus = sales.compareTo(Money.COMMISSION_BONUS_THRESHOLD) > 0
                ? Money.percentOf(sales, Money.COMMISSION_BONUS_RATE)
                : Money.ZERO;
        return Money.round(monthlyBaseSalary.add(commission).add(additionalBonus));
    }

    @Override
    public BigDecimal employerPaidBenefits() {
        return Money.FOOD_BENEFIT;
    }
}
