package ru.medyannikov.homebank.Model;

/**
 * Created by Vladimir on 26.09.2015.
 */
public class Bill {
    private int _id;
    private String name;
    private String about;
    private Double value;
    private int dependence;
    private Long date;
    private Integer sync;
    private int idServ;

    public int getIdServ() {
        return idServ;
    }

    public void setIdServ(int idServ) {
        this.idServ = idServ;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }



    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        if(sync > 0){
            this.sync = 1;
        }
        else this.sync = 0;
    }

    public Bill(int _id, String name, Double value, int dependence, Long date, String about, Integer sync) {
        this._id = _id;
        this.name = name;
        this.value = value;
        this.dependence = dependence;
        this.date = date;
        this.about = about;
        this.setSync(sync);
    }
    public Bill(int _id, String name, Double value, int dependence, Long date, String about, Integer sync, int id_serv) {
        this._id = _id;
        this.name = name;
        this.value = value;
        this.dependence = dependence;
        this.date = date;
        this.about = about;
        this.setSync(sync);
        this.idServ = id_serv;
    }

    public Bill() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public int getDependence() {
        return dependence;
    }

    public void setDependence(int dependence) {
        this.dependence = dependence;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getName();
    }
}
