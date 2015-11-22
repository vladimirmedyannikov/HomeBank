package ru.medyannikov.homebank.Model;

import java.util.List;

/**
 * Created by Vladimir on 22.11.2015.
 */
public class ApartamentBill {
    private int _id;
    private int idApartament;
    private int month;
    private int year;
    private List<ApartamentOperation> listOperation;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getIdApartament() {
        return idApartament;
    }

    public void setIdApartament(int idApartament) {
        this.idApartament = idApartament;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<ApartamentOperation> getListOperation() {
        return listOperation;
    }

    public void setListOperation(List<ApartamentOperation> listOperation) {
        this.listOperation = listOperation;
    }
}
