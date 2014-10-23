package com.rest.tiis.beans;

import java.util.Date;

public class EquipmentData {
    private Integer id;

    private Integer equipmentid;

    private Long times;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Integer equipmentid) {
        this.equipmentid = equipmentid;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}