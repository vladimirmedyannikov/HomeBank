package ru.medyannikov.homebank.Model;

/**
 * Created by Vladimir on 13.10.2015.
 */
public class Operation {
    private int id;
    private int idServ;
    private int date;
    private int type;
    private Double value;
    private int sync;
    private int idBill;
    private String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Operation(int id, int idServ, int date, int type, double value, int sync, int idBill) {
        this.id = id;

        this.idServ = idServ;
        this.date = date;
        this.type = type;
        this.value = value;
        this.sync = sync;
        this.idBill = idBill;
    }

    public Operation(){
        this.id = 0;
        this.about = "about";
        this.idServ = 1;
        this.date = 121212412;
        this.type = 1;
        this.value = 2d;
        this.sync = 1;
        this.idBill = 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdServ() {
        return idServ;
    }

    public void setIdServ(int idServ) {
        this.idServ = idServ;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public Integer getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }
}
