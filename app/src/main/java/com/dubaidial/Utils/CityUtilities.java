package com.dubaidial.Utils;

import com.dubaidial.activities.MainActivity;


public class CityUtilities 
{
//	HashMap<String, String> listDataChildwithID;
//	public static String CityJsonData = "";
	public static String AbuDhabi = "1";
	public static String Ajman = "2";
	public static String AlAin = "3";
	public static String Dubai = "42";
	public static String Fujairah = "43";
	public static String Sharjah = "53";
	public static String RasAlKhaimah = "57";
	public static String UmmAlQuwain = "58";
	public static String SelectedCity = "My Location";
	
	public static String getCityID(String cityName)
	{
		if(cityName.equals("Abu Dhabi"))
		{
			return AbuDhabi;
		}
		else if(cityName.equals("Ajman"))
		{
			return Ajman;
		}
		else if(cityName.equals("Al Ain"))
		{
			return AlAin;
		}
		else if(cityName.equals("Dubai"))
		{
			return Dubai;
		}
		else if(cityName.equals("Fujairah"))
		{
			return Fujairah;
		}
		else if(cityName.equals("Sharjah"))
		{
			return Sharjah;
		}
		else if(cityName.equals("Ras Al Khaimah"))
		{
			return RasAlKhaimah;
		}
		else if(cityName.equals("Umm Al Quwain"))
		{
			return UmmAlQuwain;
		}
		else if(!(MainActivity.latitude.equals("0.00") ||MainActivity.longitude.equals("0.00")))
		{
			return MainActivity.latitude+","+MainActivity.longitude;
		}
		else
		{
			return Dubai;
		}
	}
	
	public static String getCityName(String id)
	{
		if(id.equals("1"))
		{
			return "Abu Dhabi";
		}
		else if(id.equals("2"))
		{
			return "Ajman";
		}
		else if(id.equals("3"))
		{
			return "Al Ain";
		}
		else if(id.equals("42"))
		{
			return "Dubai";
		}
		else if(id.equals("43"))
		{
			return "Fujairah";
		}
		else if(id.equals("53"))
		{
			return "Sharjah";
		}
		else if(id.equals("57"))
		{
			return "Ras Al Khaimah";
		}
		else if(id.equals("58"))
		{
			return "Umm Al Quwain";
		}
		else
		{
			return MainActivity.areaName;
		}
	}
	
	public static String getCityLocation(String cityName)
	{
		if(cityName.contains("Abu Dhabi"))
		{
			return "24.4667,54.3667";
		}
		else if(cityName.contains("Ajman"))
		{
			return "25.4167,55.5000";
		}
		else if(cityName.contains("Al Ain"))
		{
			return "24.2075,55.7447";
		}
		else if(cityName.contains("Dubai"))
		{
			return "24.9500,55.3333";
		}
		else if(cityName.contains("Fujairah"))
		{
			return "25.2667,56.3333";
		}
		else if(cityName.contains("Sharjah"))
		{
			return "25.4333,55.3833";
		}
		else if(cityName.contains("Ras Al Khaimah"))
		{
			return "25.7833,55.9500";
		}
		else if(cityName.contains("Umm Al Quwain"))
		{
			return "25.9864,55.9400";
		}
//		else if(!(MainActivity.latitude.equals("0.00") ||MainActivity.longitude.equals("0.00")))
//		{
//			return MainActivity.latitude+","+MainActivity.longitude;
//		}
		else
		{
			return "24.9500,55.3333";
		}
	}
}
