package com.rest.tiis.beans;

import java.util.Date;

public class DataTemp {
    private Integer id;

    private String address;

    private String controlid;

    private String datacontext;

    private Date receivetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getControlid() {
        return controlid;
    }

    public void setControlid(String controlid) {
        this.controlid = controlid == null ? null : controlid.trim();
    }

    public String getDatacontext() {
        return datacontext;
    }

    public void setDatacontext(String datacontext) {
        this.datacontext = datacontext == null ? null : datacontext.trim();
    }

    public Date getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }
}