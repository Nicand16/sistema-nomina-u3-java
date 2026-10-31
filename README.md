# Sistema de nómina — Actividad de la Unidad 3

Realicé individualmente el análisis, diseño, implementación, pruebas y documentación de un sistema de nómina en Java 17 para cuatro modalidades de empleo.

## Archivos

- `src/`: código fuente y pruebas unitarias.
- `docs/Consigna_Actividad_Unidad_3.pdf`: consigna original.
- `docs/Informe_Tecnico_Actividad_U3_Nomina.docx` y `.pdf`: informe técnico.
- `docs/Guion_y_Estructura_Exposicion_U3.docx`: estructura y guion oral.
- `docs/evidence/`: registros originales de ejecución.
- `screenshots/`: capturas de las salidas de ejecución, con metadatos.

## Ejecución

Requiere Java 17 o posterior y Maven 3.9 o compatible.

```powershell
mvn clean test
java -cp target/classes co.edu.unicartagena.nomina.cli.PayrollDemo 1.0 5.0
```

Para los parámetros no definidos en el enunciado adopté supuestos de demostración. Los datos de salida son ficticios y tienen fines académicos.

## Reglas implementadas

- Asalariado: salario mensual y bono de 10% cuando la antigüedad supera cinco años.
- Por horas: tarifa base hasta 40 horas y tarifa de 1,5 veces para las horas adicionales.
- Por comisión: salario base, comisión configurable y bono de 3% si las ventas superan COP 20.000.000.
- Temporal: salario fijo durante el término contractual.
- Seguridad social y pensión: descuento combinado del 4% del salario bruto.
- Alimentación: beneficio cubierto por la empresa para las modalidades indicadas en la consigna.
- Ahorro: aporte opcional del 2% para empleados por horas que cumplen las condiciones del enunciado.

## Resultados

La ejecución local del 31 de octubre de 2026 terminó con 10 pruebas aprobadas, sin fallas ni errores. Las capturas corresponden a los registros de salida de las ejecuciones reales.

## Enlaces

- Video de exposición: https://youtu.be/EykNXmOPGHQ
- Repositorio público del proyecto: https://github.com/Nicand16/sistema-nomina-u3-java
