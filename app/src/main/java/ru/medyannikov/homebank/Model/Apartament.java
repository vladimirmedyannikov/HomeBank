package ru.medyannikov.homebank.Model;

/**
 * Created by Vladimir on 15.11.2015.
 */
public class Apartament {
    private int _id;
    private String name;
    private Double value;

    public Apartament() {
    }

    public Apartament(int _id, String name, Double value) {
        this._id = _id;
        this.name = name;
        this.value = value;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
