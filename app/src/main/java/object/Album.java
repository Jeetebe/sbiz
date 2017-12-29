package object;

import java.io.Serializable;


public class Album implements Serializable{	
	private String name;
	private String nameid;
	private String type1;	
	private String urlimg;
	
	public Album(String name, String nameid, String type1, String urlimg){
		super();
		this.name = name;
		this.nameid = nameid;
		this.type1 = type1;	
		this.urlimg=urlimg;
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
	
}
