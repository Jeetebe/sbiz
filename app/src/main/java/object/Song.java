package object;

import java.io.Serializable;

public class Song implements Serializable{
	private String tonecode;
	private String tonename;
	private String singer;
	private String price;
	private String downtimes;
	private String toneid;
	
	public Song(String tonecode, String tonename, String singer, String price, String downtimes, String toneid) {
		super();
		this.tonecode = tonecode;
		this.tonename = tonename;
		this.singer = singer;
		this.price=price;
		this.downtimes=downtimes;
		this.toneid = toneid;
	}

	public Song() {
		// TODO Auto-generated constructor stub
		this.tonecode = "123456";
		this.tonename = "test";
		this.singer ="singer";
		this.price="5000";
		this.downtimes="1";
		this.toneid = "123456";
	}

	public String getTonecode() {
		return tonecode;
	}

	public void setTonecode(String name) {
		this.tonecode = name;
	}
	public String getToneid() {
		return toneid;
	}

	public void setToneid(String name) {
		this.toneid = name;
	}
	public String getTonename() {
		return tonename;
	}

	public void setTonename(String name) {
		this.tonename = name;
	}
	public String getSinger() {
		return singer;
	}

	public void setSinger(String name) {
		this.singer = name;
	}
	public String getPrice() {
		return price;
	}

	public void setPrice(String name) {
		this.price = name;
	}
	public String getDowntimes() {
		return downtimes;
	}

	public void setDowntimes(String name) {
		this.downtimes = name;
	}

	

}