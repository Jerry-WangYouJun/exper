package com.pojo;

public class ClassInfo {
	  private Integer id ;
	  private String className;
	  private String classDate;
	  private Integer teacherId;
	  private Integer allowed ;
	  private Integer rest ;
	  private String duration;
	  private String selectDate;
	  private String experName;
	  private String experInfo;
	  private String experData;
	  private String remark;
	  
	  private User  user;
	  private ParentClassInfo  pclassInfo;
	  private OptionInfo option ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassDate() {
		return classDate;
	}
	public void setClassDate(String classDate) {
		this.classDate = classDate;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public Integer getAllowed() {
		return allowed;
	}
	public void setAllowed(Integer allowed) {
		this.allowed = allowed;
	}
	public Integer getRest() {
		return rest;
	}
	public void setRest(Integer rest) {
		this.rest = rest;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getExperName() {
		return experName;
	}
	public void setExperName(String experName) {
		this.experName = experName;
	}
	public String getExperInfo() {
		return experInfo;
	}
	public void setExperInfo(String experInfo) {
		this.experInfo = experInfo;
	}
	public String getExperData() {
		return experData;
	}
	public void setExperData(String experData) {
		this.experData = experData;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ParentClassInfo getPclassInfo() {
		return pclassInfo;
	}
	public void setPclassInfo(ParentClassInfo pclassInfo) {
		this.pclassInfo = pclassInfo;
	}
	public OptionInfo getOption() {
		return option;
	}
	public void setOption(OptionInfo option) {
		this.option = option;
	}
}
