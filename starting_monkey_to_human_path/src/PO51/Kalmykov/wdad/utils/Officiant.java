package PO51.Kalmykov.wdad.utils;

import java.io.Serializable;

public class Officiant implements Serializable{
    private String firstName;
    private String secondName;

    public Officiant(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Officiant() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Officiant other = (Officiant) obj;
        if (!secondName.equals(other.getSecondName())) return false;
        if (!firstName.equals(other.getFirstName())) return false;
        return true;
    }
}
