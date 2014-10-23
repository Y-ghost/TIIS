package com.rest.tiis.beans;

import java.util.Date;

public class Equipment {
    private Integer id;

    private String name;

    private Integer controlhostid;

    private String code;

    private Integer createuser;

    private Date createtime;

    private Integer modifyuser;

    private Date modifytime;
    
    private Float airtemperatrue;

    private Integer airhumidity;

    private Float soiltemperatrue;

    private Integer soilhumidity;

    private String status;
    
    private Integer flowparameter;
    
    private Long times;
    
    private Long eData;
    
    private Project project;
    
    
	public Long geteData() {
		return eData;
	}

	public void seteData(Long eData) {
		this.eData = eData;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public Integer getFlowparameter() {
		return flowparameter;
	}

	public void setFlowparameter(Integer flowparameter) {
		this.flowparameter = flowparameter;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getControlhostid() {
		return controlhostid;
	}

	public void setControlhostid(Integer controlhostid) {
		this.controlhostid = controlhostid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreateuser() {
		return createuser;
	}

	public void setCreateuser(Integer createuser) {
		this.createuser = createuser;
	}

	public Integer getModifyuser() {
		return modifyuser;
	}

	public void setModifyuser(Integer modifyuser) {
		this.modifyuser = modifyuser;
	}
    
    
}