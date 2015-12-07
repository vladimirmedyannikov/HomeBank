package ru.medyannikov.homebank.Model;

/**
 * Created by Vladimir on 22.11.2015.
 */
public class ApartamentOperation {
    private int _id;
    private int typeOperation;
    private int idApartamentBill;
    private Double summa;
    private Double value;
    private Double tariff;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(int typeOperation) {
        this.typeOperation = typeOperation;
    }

    public int getIdApartamentBill() {
        return idApartamentBill;
    }

    public void setIdApartamentBill(int idApartamentBill) {
        this.idApartamentBill = idApartamentBill;
    }

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getTariff() {
        return tariff;
    }

    public void setTariff(Double tariff) {
        this.tariff = tariff;
    }
}
