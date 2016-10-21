package com.dubaidial.Utils;

public class Config 
{
	public static String BASE_URL = "http://www.dubaidial.com/ddapi_live/";
//	public static String BASE_URL = "http://www.twoxt.com/test/dd/ddapi/";

	public static String SIGNIN_URL = "login/";
	public static String REGISTRATION_URL = "register/";
	public static String FORGOTPASS_URL = "forgotpwd/";
	public static String ADDLISTING_URL = "addlisting/";
	public static String LOCATION_URL = "getlocations/"; 
	public static String SEARCHRESULT_URL = "searchlistings/";
	public static String SEARCHRESULTBYCATEGORY_URL = "searchlistings_bycategory/";
	public static String LOGINGUEST_URL = "login_as_guest/"; 
	public static String FEEDBACK_URL = "add_feedback/";
	public static String ONLINEUSER_URL = "getonlineusers/";
	public static String LISTREVIEW_URL = "list_reviews/";
	public static String ADDREVIEW_URL = "add_review/";
	public static String LISTFAVOURITE_URL = "list_favourites/";
	public static String DELETEFAVOURITE_URL = "delete_favourite/";
	public static String ADDFAVOURITE_URL = "add_favourite/";
	public static String AUTOSEARCH = "auto_complete/";
	public static String COMPONYDATA = "get_companydata/";
	public static String SOCIALMEDIA = "login_via_social/";
	public static String SENDENQUIRY = "send_enquiry/";
	public static String ADDMOBILE = "update_mobile4social/";
	
	public static String LINKEDIN_CONSUMER_KEY = "75ya23733pps68";
	public static String LINKEDIN_CONSUMER_SECRET = "LdLO0yDW1GitblSz";
	public static String scopeParams = "rw_nus+r_basicprofile";
	
	public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static String OAUTH_CALLBACK_HOST = "callback";
	public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
	

	public static String RELEASE = android.os.Build.VERSION.RELEASE;
	public static String PLATFORM = "Android";
}
