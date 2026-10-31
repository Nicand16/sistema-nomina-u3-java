package co.edu.unicartagena.nomina;

import co.edu.unicartagena.nomina.application.PayrollResult;
import co.edu.unicartagena.nomina.application.PayrollService;
import co.edu.unicartagena.nomina.application.PercentageDeductionPolicy;
import co.edu.unicartagena.nomina.domain.CommissionEmployee;
import co.edu.unicartagena.nomina.domain.HourlyEmployee;
import co.edu.unicartagena.nomina.domain.Money;
import co.edu.unicartagena.nomina.domain.SalariedEmployee;
import co.edu.unicartagena.nomina.domain.TemporaryEmployee;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PayrollServiceTest {
    private static BigDecimal bd(String value) { return new BigDecimal(value); }
    private static PayrollService service(String arlRate) {
        return new PayrollService(new PercentageDeductionPolicy(bd(arlRate)));
    }

    @Test
    void salariedEmployeeGetsTenPercentBonusOnlyAfterFiveYears() {
        PayrollResult exactlyFive = service("0.01").calculate(
                new SalariedEmployee("S1", "Ana", bd("1000000"), 5));
        PayrollResult sixYears = service("0.01").calculate(
                new SalariedEmployee("S2", "Bea", bd("1000000"), 6));
        assertEquals(bd("1000000.00"), exactlyFive.grossPay());
        assertEquals(bd("1100000.00"), sixYears.grossPay());
    }

    @Test
    void hourlyEmployeeReceivesOnePointFiveOvertimeRateAndOptionalSavings() {
        PayrollResult result = service("0.01").calculate(
                new HourlyEmployee("H1", "Luis", bd("20000"), bd("45"), 2, true));
        assertEquals(bd("950000.00"), result.grossPay());
        assertEquals(bd("38000.00"), result.socialSecurityAndPension());
        assertEquals(bd("9500.00"), result.arl());
        assertEquals(bd("19000.00"), result.savingsContribution());
        assertEquals(bd("883500.00"), result.netPay());
    }

    @Test
    void hourlySavingsRequiresMoreThanOneYearAndConsent() {
        PayrollResult oneYear = service("0.00").calculate(
                new HourlyEmployee("H2", "Mia", bd("10000"), bd("40"), 1, true));
        PayrollResult declined = service("0.00").calculate(
                new HourlyEmployee("H3", "Noe", bd("10000"), bd("40"), 3, false));
        assertEquals(Money.ZERO, oneYear.savingsContribution());
        assertEquals(Money.ZERO, declined.savingsContribution());
    }

    @Test
    void commissionBonusAppliesOnlyWhenSalesExceedTwentyMillion() {
        PayrollService payroll = service("0.00");
        PayrollResult atThreshold = payroll.calculate(
                new CommissionEmployee("C1", "Sara", bd("1000000"), bd("20000000"), bd("0.05")));
        PayrollResult aboveThreshold = payroll.calculate(
                new CommissionEmployee("C2", "Sol", bd("1000000"), bd("20000001"), bd("0.05")));
        assertEquals(bd("2000000.00"), atThreshold.grossPay());
        assertEquals(bd("2600000.08"), aboveThreshold.grossPay());
    }

    @Test
    void foodBenefitIsEmployerPaidAndNotAddedToCashNet() {
        PayrollResult result = service("0.00").calculate(
                new SalariedEmployee("S3", "Luz", bd("1000000"), 0));
        assertEquals(bd("1000000.00"), result.employerPaidBenefits());
        assertEquals(bd("960000.00"), result.netPay());
    }

    @Test
    void temporaryEmployeeReceivesNoAdditionalBenefits() {
        PayrollResult result = service("0.00").calculate(
                new TemporaryEmployee("T1", "Eva", bd("1800000"), 6));
        assertEquals(bd("1800000.00"), result.grossPay());
        assertEquals(Money.ZERO, result.employerPaidBenefits());
        assertEquals(bd("1728000.00"), result.netPay());
    }

    @Test
    void rejectsNegativeHoursAndSales() {
        assertThrows(IllegalArgumentException.class, () ->
                new HourlyEmployee("H4", "A", bd("10000"), bd("-1"), 1, false));
        assertThrows(IllegalArgumentException.class, () ->
                new CommissionEmployee("C3", "B", bd("1000000"), bd("-1"), bd("0.05")));
    }

    @Test
    void rejectsAConfigurationThatWouldMakeNetPayNegative() {
        PayrollService payroll = service("1.00");
        SalariedEmployee lowPaid = new SalariedEmployee("S4", "Cami", bd("100000"), 0);
        assertThrows(IllegalStateException.class, () -> payroll.calculate(lowPaid));
    }

    @Test
    void rejectsRatesOutsideZeroToOne() {
        assertThrows(IllegalArgumentException.class, () -> service("1.01"));
        assertThrows(IllegalArgumentException.class, () ->
                new CommissionEmployee("C4", "D", bd("1000000"), bd("1000"), bd("-0.01")));
    }

    @Test
    void roundsPercentagesToTwoDecimalsUsingHalfUp() {
        assertTrue(Money.percentOf(bd("10.05"), bd("0.10")).compareTo(bd("1.01")) == 0);
    }
}
