package net.netne.common.pojo;

import java.util.Date;

/**
 * diceGame
 * @date 2014-8-17 下午11:04:16
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class Prize {
	
	private Integer id;
	
	private String name;
	
	private Integer stock;
	
	private Double probability;
	
	private Date createTime;
	
	private String photoUrl;
	
	private byte[] photoData;
	
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public byte[] getPhotoData() {
		return photoData;
	}

	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
