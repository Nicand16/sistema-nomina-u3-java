# Metodología y diseño

## Alcance

Desarrollé un sistema de nómina para la Actividad de la Unidad 3. Las reglas de cada modalidad de empleo se ubican en tipos de dominio; el servicio de aplicación calcula deducciones, aportes y salario neto.

## Método de trabajo

Realicé individualmente esta secuencia:

1. Extraje del enunciado los cuatro tipos de empleado, ingresos, beneficios, deducciones y validaciones.
2. Definí un contrato común para el salario bruto y los beneficios pagados por la empresa.
3. Implementé cada regla en su clase de empleado.
4. Aislé la política de deducciones detrás de una interfaz y configuré la tasa ARL como parámetro.
5. Añadí el servicio de nómina para calcular y validar el resultado neto.
6. Escribí pruebas unitarias para límites, casos habituales y entradas inválidas.
7. Ejecuté las pruebas y preparé escenarios demostrativos para los cuatro tipos de empleado.

## Diseño orientado a objetos

```text
Employee
  ├── SalariedEmployee
  ├── HourlyEmployee ── SavingsFundParticipant
  ├── CommissionEmployee
  └── TemporaryEmployee

PayrollService ── DeductionPolicy
                       └── PercentageDeductionPolicy

PayrollService.calculate(Employee) → PayrollResult
```

`Employee` expone identificador, nombre, salario bruto y beneficios cubiertos por la empresa. Cada subtipo calcula su salario bruto. `HourlyEmployee` implementa además `SavingsFundParticipant`, pues solo esa clase puede generar el aporte descrito para el fondo. `PayrollService` depende de `DeductionPolicy`, no de una implementación concreta. Los valores monetarios utilizan `BigDecimal` y redondeo a dos decimales.

## Aplicación de SOLID

- **Responsabilidad única:** los empleados calculan sus ingresos y beneficios; la política calcula deducciones; `PayrollService` compone el resultado; `PayrollDemo` presenta escenarios.
- **Abierto/cerrado:** puedo añadir un tipo de empleado implementando `Employee` sin reescribir las clases existentes.
- **Sustitución de Liskov:** cada tipo satisface el contrato común de salario bruto y beneficios.
- **Segregación de interfaces:** el fondo de ahorro se expresa mediante `SavingsFundParticipant`, no como obligación para todos los empleados.
- **Inversión de dependencias:** `PayrollService` recibe la abstracción `DeductionPolicy`.

## Reglas y decisiones de cálculo

| Regla | Implementación |
|---|---|
| Bono asalariado | 10% del salario fijo solo si los años de servicio superan 5. |
| Horas extra | Horas posteriores a 40 multiplicadas por tarifa y por 1,5. |
| Comisión | Salario base más ventas por tasa configurable. |
| Bono de ventas | 3% de ventas cuando superan COP 20.000.000. |
| Seguridad social y pensión | 4% del salario bruto. |
| ARL | Porcentaje configurable aplicado al salario bruto en este modelo. |
| Alimentación | COP 1.000.000 separado del neto para las modalidades indicadas. |
| Fondo de ahorro | 2% del bruto para empleado por horas elegible y con consentimiento; se registra como aporte reservado. |
| Salario neto negativo | El cálculo se rechaza con una excepción. |

Para los valores no especificados en el enunciado adopté supuestos de demostración. Los nombres y datos de los registros son ficticios.

## Refactorización y pruebas

Separé los cálculos específicos en tipos cohesivos; `Money` concentra validaciones y redondeo; `DeductionPolicy` desacopla la configuración de tasas. Las pruebas cubren umbrales, entradas inválidas, redondeo y resultados de las modalidades de empleo.

En la ejecución del 31 de octubre de 2026 se reportaron 10 pruebas, 0 fallas, 0 errores y 0 omitidas. Los registros están en `docs/evidence/pruebas_maven_real.txt` y `docs/evidence/demo_nomina_real.txt`; las capturas correspondientes están en `screenshots/`.

## Enlaces

- Video de exposición: https://youtu.be/EykNXmOPGHQ
- Repositorio público: https://github.com/Nicand16/sistema-nomina-u3-java
