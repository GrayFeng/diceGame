package net.netne.mina.pojo.broadcast;

public class NewGamerJoinTO {
	
	private String newGamerName;
	
	private String sex;
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNewGamerName() {
		return newGamerName;
	}

	public void setNewGamerName(String newGamerName) {
		this.newGamerName = newGamerName;
	}

}
