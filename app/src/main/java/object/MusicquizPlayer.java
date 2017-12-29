package object;

public class MusicquizPlayer{
String device;
int point;

public MusicquizPlayer(String d,int p){
	this.device=d;
	this.point=p;
}
public void set_device(String str){
	device=str;
}
public String get_device(){
	return device;
}

public void set_point(int str){
	point=str;
}
public int get_point(){
	return point;
}
}
