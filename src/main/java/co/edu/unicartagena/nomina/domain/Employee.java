package co.edu.unicartagena.nomina.domain;

import java.math.BigDecimal;

/** Common payroll contract. Employee-specific calculations remain in each subtype. */
public interface Employee {
    String id();
    String fullName();
    BigDecimal grossPay();
    BigDecimal employerPaidBenefits();
}
