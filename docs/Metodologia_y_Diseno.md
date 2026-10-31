# Metodología y diseño

## Alcance

El proyecto implementa el cálculo mensual de nómina solicitado en la Actividad de la Unidad 3. Las reglas específicas de cada clase de empleado se mantienen en sus tipos de dominio; el servicio de aplicación calcula deducciones, aportes y salario neto.

## Método de trabajo

Se siguió una secuencia incremental y verificable:

1. Se extrajeron del enunciado los cuatro tipos de empleado, sus ingresos, beneficios, deducciones y validaciones.
2. Se definió un contrato común para el salario bruto y los beneficios pagados por la empresa.
3. Se implementó cada regla por separado en su clase de empleado.
4. Se aisló la política de deducciones detrás de una interfaz para mantener configurable la tasa ARL.
5. Se añadió el servicio de nómina para calcular y validar el resultado neto.
6. Se escribieron pruebas unitarias de límites, casos habituales y entradas inválidas.
7. Se ejecutaron las pruebas con Maven Surefire y se generó una salida demostrativa con tasas explícitas.

El estudiante informa que desarrolló el trabajo individualmente, sin aportes de compañeros. Codex asistió en la implementación y en la preparación documental. Esta metodología describe las actividades técnicas locales; no acredita una exposición ni resuelve si la modalidad individual cumple la consigna, que menciona una presentación por CIPA.

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

- **Responsabilidad única:** los empleados calculan sus ingresos y beneficios; la política calcula deducciones; `PayrollService` compone el resultado; `PayrollDemo` solo presenta ejemplos.
- **Abierto/cerrado:** puede añadirse un tipo de empleado implementando `Employee` sin reescribir las clases existentes.
- **Sustitución de Liskov:** cada tipo satisface el contrato común de salario bruto y beneficios.
- **Segregación de interfaces:** el fondo de ahorro se expresa mediante `SavingsFundParticipant`, no como obligación para todos los empleados.
- **Inversión de dependencias:** `PayrollService` recibe la abstracción `DeductionPolicy`.

## Reglas y decisiones de cálculo

| Regla | Implementación |
|---|---|
| Bono asalariado | 10% del salario fijo solo si los años de servicio son mayores que 5. |
| Horas extra | Horas después de 40 multiplicadas por la tarifa y por 1.5. |
| Comisión | Salario base más ventas por tasa configurable. |
| Bono de ventas | 3% de las ventas solo cuando superan COP 20.000.000; no aplica exactamente en el umbral. |
| Seguridad social y pensión | 4% del salario bruto. |
| ARL | Porcentaje configurable aplicado al salario bruto en este modelo; la tasa y la base no se especifican en la guía y deben confirmarse. |
| Alimentación | COP 1.000.000 separado del neto, porque la empresa cubre el beneficio. |
| Fondo de ahorro | 2% del bruto para empleado por horas con más de un año y consentimiento; se descuenta del neto como aporte reservado. Es una interpretación que debe confirmarse. |
| Salario neto negativo | Se rechaza el cálculo con excepción, en vez de presentar una nómina inválida. |

Las tasas de ARL y comisión son entradas configurables, no se incorporó una tasa no suministrada por la consigna. En la demostración automatizada se usaron 1.0% para ARL y 5.0% para comisión únicamente como parámetros ilustrativos. Los nombres y datos de los cuatro registros de demostración son ficticios.

## Refactorización y calidad

Se mantuvieron los cálculos específicos fuera de un bloque monolítico de condicionales: la lógica de salarios vive en tipos cohesivos, los porcentajes comunes se concentran en `Money` y la deducción se desacopla mediante `DeductionPolicy`. Esto permite probar reglas individuales, reemplazar tasas y extender el dominio. Los comentarios se limitan a decisiones no obvias, como la tasa ARL inyectada y la elegibilidad opcional del fondo.

## Pruebas unitarias

Las pruebas se ejecutan con JUnit Jupiter mediante Maven Surefire. La configuración sigue las guías oficiales de JUnit y Maven Surefire para pruebas sobre JUnit Platform. En la última ejecución se reportaron 10 pruebas, 0 fallas, 0 errores y 0 omitidas.

Casos cubiertos: umbral de antigüedad; cálculo de horas extra; elegibilidad y consentimiento del ahorro; umbral estricto del bono de comisión; alimentación pagada por la empresa; empleado temporal; horas negativas; ventas negativas; tasas fuera de rango; salario neto negativo; y redondeo monetario.

## Pendientes de revisión y entrega

- Confirmar con el docente o con las instrucciones del curso si se admite la modalidad individual, ya que la consigna menciona una presentación por CIPA.
- Confirmar con el docente/empresa las tasas ARL y comisión y el tratamiento del beneficio y ahorro.
- El repositorio publicado es una copia personal y no demuestra colaboración ni aportes de compañeros. Confirmar si satisface el requisito de GitHub indicado en la consigna.
- Revisar el comportamiento y las pruebas desde el entorno de trabajo del estudiante.
- Preparar y grabar la exposición solicitada y añadir el enlace real al documento final. No se ha grabado un video en este proyecto.
