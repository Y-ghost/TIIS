package com.rest.tiis.beans;

import java.util.Date;

public class EquipmentStatus {
    private Integer id;

    private Integer equipmentid;

    private Float airtemperatrue;

    private Integer airhumidity;

    private Float soiltemperatrue;

    private Integer soilhumidity;

    private Date createtime;

    private String status;

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

	public Float getAirtemperatrue() {
		return airtemperatrue;
	}

	public void setAirtemperatrue(Float airtemperatrue) {
		this.airtemperatrue = airtemperatrue;
	}

	public Integer getAirhumidity() {
		return airhumidity;
	}

	public void setAirhumidity(Integer airhumidity) {
		this.airhumidity = airhumidity;
	}
	
	public Float getSoiltemperatrue() {
		return soiltemperatrue;
	}

	public void setSoiltemperatrue(Float soiltemperatrue) {
		this.soiltemperatrue = soiltemperatrue;
	}

	public Integer getSoilhumidity() {
		return soilhumidity;
	}

	public void setSoilhumidity(Integer soilhumidity) {
		this.soilhumidity = soilhumidity;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}