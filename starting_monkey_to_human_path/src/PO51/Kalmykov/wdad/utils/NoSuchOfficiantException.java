package PO51.Kalmykov.wdad.utils;

public class NoSuchOfficiantException extends Exception {
    public NoSuchOfficiantException() {
        System.err.println("Officiant not finded");
    }
}
