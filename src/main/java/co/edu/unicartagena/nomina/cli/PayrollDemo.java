package co.edu.unicartagena.nomina.cli;

import co.edu.unicartagena.nomina.application.PayrollResult;
import co.edu.unicartagena.nomina.application.PayrollService;
import co.edu.unicartagena.nomina.application.PercentageDeductionPolicy;
import co.edu.unicartagena.nomina.domain.CommissionEmployee;
import co.edu.unicartagena.nomina.domain.Employee;
import co.edu.unicartagena.nomina.domain.HourlyEmployee;
import co.edu.unicartagena.nomina.domain.SalariedEmployee;
import co.edu.unicartagena.nomina.domain.TemporaryEmployee;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public final class PayrollDemo {
    private PayrollDemo() { }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java -cp target/classes co.edu.unicartagena.nomina.cli.PayrollDemo <ARL%> <comision%>");
            System.out.println("La consigna no define estas tasas. Proporcione valores confirmados o use valores solo demostrativos.");
            System.exit(2);
        }

        BigDecimal arlRate = percentArgument(args[0]);
        BigDecimal commissionRate = percentArgument(args[1]);
        PayrollService payroll = new PayrollService(new PercentageDeductionPolicy(arlRate));
        List<Employee> employees = List.of(
                new SalariedEmployee("E-001", "Caso asalariado", new BigDecimal("4000000"), 6),
                new HourlyEmployee("E-002", "Caso por horas", new BigDecimal("20000"),
                        new BigDecimal("45"), 2, true),
                new CommissionEmployee("E-003", "Caso por comision", new BigDecimal("3000000"),
                        new BigDecimal("25000000"), commissionRate),
                new TemporaryEmployee("E-004", "Caso temporal", new BigDecimal("1800000"), 6));

        System.out.println("DEMO ACADEMICA - tasas no suministradas por la consigna pueden ser ilustrativas");
        System.out.println("Registros y nombres ficticios; no representan empleados reales.");
        System.out.printf("ARL configurada: %s | Comision configurada: %s%n", args[0] + "%", args[1] + "%");
        for (Employee employee : employees) print(payroll.calculate(employee));
    }

    private static BigDecimal percentArgument(String raw) {
        BigDecimal percentage = new BigDecimal(raw);
        if (percentage.signum() < 0 || percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Las tasas porcentuales deben estar entre 0 y 100");
        }
        return percentage.movePointLeft(2);
    }

    private static void print(PayrollResult result) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("es-CO"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat cop = new DecimalFormat("#,##0.00", symbols);
        System.out.printf("%s | bruto COP $ %s | beneficio empresa COP $ %s | SS+P COP $ %s | ARL COP $ %s | ahorro COP $ %s | neto COP $ %s%n",
                result.employeeName(), cop.format(result.grossPay()), cop.format(result.employerPaidBenefits()),
                cop.format(result.socialSecurityAndPension()), cop.format(result.arl()),
                cop.format(result.savingsContribution()), cop.format(result.netPay()));
    }
}
