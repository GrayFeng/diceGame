package net.netne.mina.pojo.broadcast;

public class NewGamerJoinTO {
	
	private String newGamerName;
	
	private Integer sex;
	
	private Integer id;
	
	private String photoUrl;
	
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNewGamerName() {
		return newGamerName;
	}

	public void setNewGamerName(String newGamerName) {
		this.newGamerName = newGamerName;
	}

}
