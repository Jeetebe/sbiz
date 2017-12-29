package object;

public class MyApp {
	String appname;
	String apppk;
	String version;
	int imgint;
	String iconname;
	public MyApp(String appname,String apppk,String version,int imgint, String iconname){
		this.appname=appname;
		this.apppk=apppk;
		this.version=version;
		this.imgint=imgint;
		this.iconname=iconname;

	}
	public void set_appname(String str){
		appname=str;
	}
	public String get_appname(){
		return appname;
	}
	public void set_apppk(String str){
		apppk=str;
	}
	public String get_apppk(){
		return apppk;
	}

	public void set_version(String str){
		version=str;
	}
	public String get_version(){
		return version;
	}

	public void set_imgname(int i){
		imgint=i;
	}
	public int get_imgint(){
		return imgint;
	}
	public void set_iconname(String str){
		iconname=str;
	}
	public String get_iconname(){
		return iconname;
	}

}

