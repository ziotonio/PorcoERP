package cn.itcast.invoice.emp.action;

public class UserModel {
	public UserModel(){
		System.out.println("object instance....");
	}
	private Long uuid;
	private String name;
	private Integer age;
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		System.out.println("uuid...");
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		System.out.println("name...");
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		System.out.println("age....");
		this.age = age;
	}
	
}
