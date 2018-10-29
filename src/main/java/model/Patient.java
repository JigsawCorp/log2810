package main.java.model;

/**
 * Holds relevant information concerning the type of patient we transport in vehicles
 */
public class Patient {

    /**
     * Represents the danger risk of a particular patient.
     */
    public enum Type {
        LOW_RISK, MEDIUM_RISK, HIGH_RISK
    }
}
