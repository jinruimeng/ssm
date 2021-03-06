package com.atguigu.mybatis.bean;

import java.util.List;

public class Department {

	private Integer id;
	private String departmentName;
	private List<Employee> emps;

	public List<Employee> getEmps() {
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}

	public Department(Integer id) {
		super();
		this.id = id;
	}
	
	public Department() {
		// TODO 自动生成的构造函数存根
	}
}
