package co.edu.unicartagena.nomina.application;

import co.edu.unicartagena.nomina.domain.Employee;
import co.edu.unicartagena.nomina.domain.Money;
import co.edu.unicartagena.nomina.domain.SavingsFundParticipant;
import java.math.BigDecimal;
import java.util.Objects;

public final class PayrollService {
    private final DeductionPolicy deductionPolicy;

    public PayrollService(DeductionPolicy deductionPolicy) {
        this.deductionPolicy = Objects.requireNonNull(deductionPolicy, "deductionPolicy");
    }

    public PayrollResult calculate(Employee employee) {
        Objects.requireNonNull(employee, "employee");
        BigDecimal gross = Money.requireNonNegative("salario bruto", employee.grossPay());
        DeductionBreakdown deductions = deductionPolicy.calculate(gross);
        BigDecimal savings = employee instanceof SavingsFundParticipant participant
                ? Money.requireNonNegative("aporte al fondo de ahorro", participant.savingsContribution())
                : Money.ZERO;
        BigDecimal net = Money.round(gross.subtract(deductions.total()).subtract(savings));
        if (net.signum() < 0) {
            throw new IllegalStateException("El salario neto no puede ser negativo");
        }
        return new PayrollResult(employee.id(), employee.fullName(), gross, employee.employerPaidBenefits(),
                deductions.socialSecurityAndPension(), deductions.arl(), savings, net);
    }
}
