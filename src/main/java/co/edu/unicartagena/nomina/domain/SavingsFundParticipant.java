package co.edu.unicartagena.nomina.domain;

import java.math.BigDecimal;

/** Optional employee contribution to the savings fund; not every employee is eligible. */
public interface SavingsFundParticipant {
    BigDecimal savingsContribution();
}
