package utils;

public class Const {
	//Basic
	public static final String loginstr= "login_Showbiz_v2.2fix";
	public static final int currversion=4;
	public static final String URLIP="http://123.30.100.126:8081";
	public static final String share_app_fb= "share_fb_Showbiz_v2.2";
	public static final String PREFS_NAME = "Showbiz";

	//General
	public static final String url_apppara=URLIP+ "/Restapi/rest/GeneralService/getparaapp/Musicquiz?device=";
	public static final String urlmyapp=URLIP+"/Restapi/rest/GeneralService/getmyapp?apppk=";
	public static final String urlTopplayer=URLIP+"/Restapi/rest/GeneralService/gettopplayer?gamename=Musicquiz&device=";
	public static final String urlpostlog2server=URLIP+ "/Restapi/rest/GeneralService/postlog2server?logstr=" ;
	public static final String urlupdatepoint=URLIP+ "/Restapi/rest/GeneralService/updatepoint?device=" ;
	public static final String urlinsertdevice=URLIP+ "/Restapi/rest/Musicquiz/insertdevice?device=" ;
	public static final String urlpostketqua = URLIP+ "/Restapi/rest/GeneralService/postketqua?ketqua=";
	public static final String urlgetxephang=URLIP+"/Restapi/rest/GeneralService/gethang?gamename=Musicquiz&device=";
	public static final String url_updateprofile =  URLIP+ "/Restapi/rest/GeneralService/updateprofile?device=";
	public static final String urlalbum_pic =  URLIP+ "/Restapi/rest/GeneralService/getalbumpic/";
	//Json api
	public static final String urlgetsuggestion =  URLIP+ "/Restapi/rest/showbiz/getsuggestion?diemso=";
	public static final String urlgetnghesiv2 =  URLIP+ "/Restapi/rest/showbiz/getnghesiv2?diemso=";
	public static final String urlgetnghesi =  URLIP+ "/Restapi/rest/showbiz/getnghesi?diemso=";
	public static final String urlgetsong =  URLIP+ "/Restapi/rest/showbiz/getsong4showbiz?level=";


	public static final String urlsuggestion_v5 =  URLIP+ "/Restapi/rest/Appservice/getsugesstion?id=";

	//update
	//public static final String urlgetnghesiv2 =  URLIP+ "/Restapi/rest/newshowbiz/getnghesiv2?diemso=";


}
