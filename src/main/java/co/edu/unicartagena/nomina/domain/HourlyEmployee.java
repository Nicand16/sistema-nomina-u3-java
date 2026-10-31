package co.edu.unicartagena.nomina.domain;

import java.math.BigDecimal;

public final class HourlyEmployee extends AbstractEmployee implements SavingsFundParticipant {
    private static final BigDecimal STANDARD_HOURS = new BigDecimal("40");

    private final BigDecimal hourlyRate;
    private final BigDecimal hoursWorked;
    private final int yearsOfService;
    private final boolean acceptsSavingsFund;

    public HourlyEmployee(String id, String fullName, BigDecimal hourlyRate, BigDecimal hoursWorked,
                          int yearsOfService, boolean acceptsSavingsFund) {
        super(id, fullName);
        if (yearsOfService < 0) throw new IllegalArgumentException("La antiguedad no puede ser negativa");
        this.hourlyRate = Money.requireNonNegative("tarifa por hora", hourlyRate);
        this.hoursWorked = Money.requireNonNegative("horas trabajadas", hoursWorked);
        this.yearsOfService = yearsOfService;
        this.acceptsSavingsFund = acceptsSavingsFund;
    }

    @Override
    public BigDecimal grossPay() {
        BigDecimal regularHours = hoursWorked.min(STANDARD_HOURS);
        BigDecimal overtimeHours = hoursWorked.subtract(regularHours);
        BigDecimal regularPay = regularHours.multiply(hourlyRate);
        BigDecimal overtimePay = overtimeHours.multiply(hourlyRate).multiply(Money.OVERTIME_MULTIPLIER);
        return Money.round(regularPay.add(overtimePay));
    }

    @Override
    public BigDecimal employerPaidBenefits() {
        return Money.ZERO;
    }

    @Override
    public BigDecimal savingsContribution() {
        if (yearsOfService <= 1 || !acceptsSavingsFund) return Money.ZERO;
        return Money.percentOf(grossPay(), Money.HOURLY_SAVINGS_RATE);
    }
}
