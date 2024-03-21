package com.ph30891.asm_ph30891_qlsv.model;

import java.io.Serializable;

public class Students implements Serializable {
    private String _id;
    private String name;
    private String msv;
    private double diemTb;
    private String avatar;

    public Students(String name, String msv, double diemTb, String avatar) {
        this.name = name;
        this.msv = msv;
        this.diemTb = diemTb;
        this.avatar = avatar;
    }

    public Students(String _id, String name, String msv, double diemTb, String avatar) {
        this._id = _id;
        this.name = name;
        this.msv = msv;
        this.diemTb = diemTb;
        this.avatar = avatar;
    }

    public Students() {
    }


    public String get_id() {
        return _id;
    }

    public Students set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Students setName(String name) {
        this.name = name;
        return this;
    }

    public String getMsv() {
        return msv;
    }

    public Students setMsv(String msv) {
        this.msv = msv;
        return this;
    }

    public double getDiemTb() {
        return diemTb;
    }

    public Students setDiemTb(double diemTb) {
        this.diemTb = diemTb;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Students setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
