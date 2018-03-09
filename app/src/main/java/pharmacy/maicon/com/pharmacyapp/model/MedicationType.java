package pharmacy.maicon.com.pharmacyapp.model;

/**
 * Created on 09/03/2018.
 */

public enum MedicationType {
    ANALGESIC("Analgesic"), ANALEPTIC("Analeptic"), ANESTHETIC("Anesthetic"),
    ANTACID("Antacid"), ANTIDEPRESSANT("Antidepressant"), ANTIBIOTICS("Antibiotics");

    private final String description;

    MedicationType(String description) {
        this.description = description;
    }
}
