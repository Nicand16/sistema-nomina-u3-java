package co.edu.unicartagena.nomina.domain;

import java.math.BigDecimal;

public final class TemporaryEmployee extends AbstractEmployee {
    private final BigDecimal monthlySalary;
    private final int contractDurationMonths;

    public TemporaryEmployee(String id, String fullName, BigDecimal monthlySalary, int contractDurationMonths) {
        super(id, fullName);
        if (contractDurationMonths <= 0) {
            throw new IllegalArgumentException("La duracion del contrato debe ser mayor que cero");
        }
        this.monthlySalary = Money.requireNonNegative("salario mensual", monthlySalary);
        this.contractDurationMonths = contractDurationMonths;
    }

    @Override
    public BigDecimal grossPay() {
        return monthlySalary;
    }

    @Override
    public BigDecimal employerPaidBenefits() {
        return Money.ZERO;
    }

    public int contractDurationMonths() {
        return contractDurationMonths;
    }
}
