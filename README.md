# Sistema de nómina — Actividad de la Unidad 3

Implementación académica en Java 17 de reglas de nómina para cuatro modalidades de empleo. El trabajo fue realizado individualmente por **Nicolás Andrés Arnedo Puello**, sin aportes de compañeros, según lo indicado por el estudiante.

> **Herramientas de apoyo:** Codex asistió en la implementación del código y la preparación de documentos y evidencias. El estudiante debe revisar y comprender los materiales, y seguir las reglas de declaración de asistencia de su curso.

## Contenido

- Código Java y pruebas unitarias JUnit 5.
- Informe técnico en Word.
- Consigna original en PDF y documentación de diseño.
- Capturas locales de pruebas y demostración, con sus metadatos.
- Archivo ZIP con los materiales del proyecto.

## Ejecución

Requiere Java 17 o posterior y Maven 3.9 o compatible.

```powershell
mvn clean test
java -cp target/classes co.edu.unicartagena.nomina.cli.PayrollDemo 1.0 5.0
```

El primer argumento configura la tasa ARL porcentual y el segundo la tasa de comisión porcentual. La consigna no proporciona esas tasas: `1.0` y `5.0` son parámetros ilustrativos. Los registros de la demostración son ficticios. No se deben usar estos valores como tasas reales.

## Reglas implementadas

- Asalariado: salario mensual fijo y bono del 10% cuando la antigüedad supera cinco años.
- Por horas: pago regular hasta 40 horas y pago de 1,5 veces la tarifa por horas adicionales.
- Por comisión: salario base más comisión configurable; bono del 3% sobre ventas si superan COP 20.000.000.
- Temporal: salario fijo por término contractual, sin beneficios adicionales.
- Seguridad social y pensión: descuento combinado del 4% del salario bruto.
- ARL: tasa configurable, pendiente de confirmación porque no está indicada en la consigna.
- Alimentación: COP 1.000.000 para asalariados y comisionistas, separado del salario neto.
- Ahorro: aporte opcional del 2% para empleados por horas con más de un año; su tratamiento debe confirmarse.
- Validaciones de horas, ventas, tasas y salario neto.

## Evidencia y alcance

La última ejecución local registrada reporta 10 pruebas unitarias aprobadas, 0 fallas, 0 errores y 0 omitidas. Las capturas muestran páginas locales de evidencia basadas en el resultado de Maven y la salida de demostración. El registro Maven publicado omite las rutas absolutas de esta computadora. Estas evidencias no acreditan una exposición grabada ni la participación de compañeros.

