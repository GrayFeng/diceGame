package net.netne.common.pojo;

public class VersionInfo {
	
	private boolean upgrade;
	
    private int newver;
    
    private String msg;
    
    private String address;
    
    private String channel;
    
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public boolean isUpgrade() {
		return upgrade;
	}
	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}
	public int getNewver() {
		return newver;
	}
	public void setNewver(int newver) {
		this.newver = newver;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
