package object;

import java.io.Serializable;


public class Nghesi implements Serializable{	
	private String name;
	private String nameid;
	private String type1;	
	private String urlimg;
	private String mota;
	
	public Nghesi(String name, String nameid, String type1, String urlimg, String mo){
		super();
		this.name = name;
		this.nameid = nameid;
		this.type1 = type1;	
		this.urlimg=urlimg;
		this.mota=mo;
	}
	
	
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getnameid() {
		return nameid;
	}
	public void setnameid(String name) {
		this.nameid = name;
	}
	public void settype1(String name) {
		this.type1 = name;
	}
	public String gettype1() {
		return type1;
	}	
	
	
	public String geturlimg() {
		return urlimg;
	}
	public void seturlimg(String name) {
		this.urlimg = name;
	}
	
	public String getmota() {
		return mota;
	}
	public void setmota(String name) {
		this.mota = name;
	}
	
}
