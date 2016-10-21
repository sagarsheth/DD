package com.dubaidial.activities.controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dubaidial.Utils.CityUtilities;
import com.dubaidial.Utils.Constants;
import com.dubaidial.activities.SearchResultActivity;
import com.techpro.dubaidial.R;

public class MenuController 
{
	TextView repairservices,maidservice,entertainmetn,cosmatic,otherservices,AdvertisingnMarketing,AirConditioning,Astrology,AutomobilenCarAccessories,Bank,Boutiques,SpanSaloon,BuildingMaterialnConstructionContractors,
	CarHirenLeasing,CargoServices,Catering,Cinema,CleaningnJanitorialServices,Clinics,Computer,CourierServices,Dentist,DigitalPrinting,
	ElectriciansnElectromechanicalContractors,Electronics,EngineersConsulting,EventManagement,FoodnFruitDealers,Furniture ,Gym ,Hospital ,Hotels ,Hardware ,
	InteriorDecoratorsnDesigners ,LabourSupply ,Lawyers ,MarblenGraniteMfrsnSuppliers ,MedicalEquipmentSuppliers ,MineralWater ,OilCompanies ,PestControl ,
	PetsCare ,Pharmacy ,PipenPipeFittingSuppliers ,PlasticsnPlasticProductsMfrsnSuppliers ,	RealEstate ,RecruitmentConsultants ,Restaurant ,School ,
	SafetynSecurity ,ShippingCompaniesnAgents ,SteelFabricatorsnEngineers ,Supermarket ,Textiles ,TransportCompanies ,TravelAgents ,TyreDealers ,TypingCenters ,
	Jewellers ,Florists ,CharityOrganisations ,MinistriesnGovtDepartment ,EmbassiesnConsulates ,CollegesnUniversities ,DrivingInstitutions ,
	EducationalInstitutes ,RadioStations ,TVStations ,InsuranceCompanies ,PhotoStudios ,Accountants ,HousewareDealers ,Laundries ,Bookshop,FootWear,Opticals;
	Activity activity;
	
	public MenuController(Activity activity)
	{
		this.activity = activity;
		setupMenu();
	}
	
	private void setupMenu() 
	{
		repairservices = (TextView)activity.findViewById(R.id.repairservices);
		repairservices.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "repair services");
//				intent.putExtra("category", "370");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		maidservice = (TextView)activity.findViewById(R.id.maidservice);
		maidservice.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "maid service");
//				intent.putExtra("category", "370");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		entertainmetn = (TextView)activity.findViewById(R.id.entertainmetn);
		entertainmetn.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "entertainment");
//				intent.putExtra("category", "370");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		cosmatic = (TextView)activity.findViewById(R.id.cosmatic);
		cosmatic.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "cosmatic");
//				intent.putExtra("category", "370");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		otherservices = (TextView)activity.findViewById(R.id.otherservices);
		otherservices.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "other services");
//				intent.putExtra("category", "370");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		AdvertisingnMarketing = (TextView)activity.findViewById(R.id.AdvertisingMarketing);
		AdvertisingnMarketing.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Advertising & Marketing");
//				intent.putExtra("category", "370");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		AirConditioning = (TextView)activity.findViewById(R.id.AirConditioning);
		AirConditioning.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Air Conditioning");
//				intent.putExtra("category", "47");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Astrology = (TextView)activity.findViewById(R.id.Astrology);
		Astrology.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Astrology");
//				intent.putExtra("category", "47");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		AutomobilenCarAccessories = (TextView)activity.findViewById(R.id.AutomobileCarAccessories);
		AutomobilenCarAccessories.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Automobile & Car Accessories");
//				intent.putExtra("category", "310");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Bank = (TextView)activity.findViewById(R.id.Bank);
		Bank.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Bank");
//				intent.putExtra("category", "2");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Boutiques = (TextView)activity.findViewById(R.id.Boutiques);
		Boutiques.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				SearchResultActivity.displayAdd = true;
				SearchResultActivity.img = Constants.backIMG_Boutiques;
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Boutiques");
//				intent.putExtra("category", "1501");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		SpanSaloon = (TextView)activity.findViewById(R.id.SpaSaloon);
		SpanSaloon.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Spa & Saloon");
//				intent.putExtra("category", "297");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		BuildingMaterialnConstructionContractors = (TextView)activity.findViewById(R.id.BuildingMaterialConstructionContractors);
		BuildingMaterialnConstructionContractors.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Building Material & Construction Contractors");
//				intent.putExtra("category", "3");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		

		CarHirenLeasing = (TextView)activity.findViewById(R.id.CarHireLeasing);
		CarHirenLeasing.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Car Hire & Leasing");
//				intent.putExtra("category", "47");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		CargoServices = (TextView)activity.findViewById(R.id.CargoServices);
		CargoServices.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Cargo Services");
//				intent.putExtra("category", "72");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Catering = (TextView)activity.findViewById(R.id.Catering);
		Catering.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Catering");
//				intent.putExtra("category", "84");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Cinema = (TextView)activity.findViewById(R.id.Cinema);
		Cinema.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Cinema");
//				intent.putExtra("category", "1574");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});

		CleaningnJanitorialServices = (TextView)activity.findViewById(R.id.CleaningJanitorialServices);
		CleaningnJanitorialServices.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Cleaning & Janitorial Services");
//				intent.putExtra("category", "85");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Clinics = (TextView)activity.findViewById(R.id.Clinics);
		Clinics.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Clinics");
//				intent.putExtra("category", "99");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Computer = (TextView)activity.findViewById(R.id.Computer);
		Computer.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Computer");
//				intent.putExtra("category", "301");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		CourierServices = (TextView)activity.findViewById(R.id.CourierServices);
		CourierServices.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Courier Services");
//				intent.putExtra("category", "83");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Dentist = (TextView)activity.findViewById(R.id.Dentist);
		Dentist.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Dentist");
//				intent.putExtra("category", "627");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		DigitalPrinting = (TextView)activity.findViewById(R.id.DigitalPrinting);
		DigitalPrinting.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Digital Printing");
//				intent.putExtra("category", "124");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});

		ElectriciansnElectromechanicalContractors = (TextView)activity.findViewById(R.id.ElectriciansElectromechanicalContractors);
		ElectriciansnElectromechanicalContractors.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Electricians & Electromechanical Contractors");
//				intent.putExtra("category", "108");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Electronics = (TextView)activity.findViewById(R.id.Electronics);
		Electronics.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Electronics");
//				intent.putExtra("category", "311");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		EngineersConsulting = (TextView)activity.findViewById(R.id.EngineersConsulting);
		EngineersConsulting.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Engineers Consulting");
//				intent.putExtra("category", "129");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		EventManagement = (TextView)activity.findViewById(R.id.EventManagement);
		EventManagement.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Event Management");
//				intent.putExtra("category", "133");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		FoodnFruitDealers = (TextView)activity.findViewById(R.id.FoodFruitDealers);
		FoodnFruitDealers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Food & Fruit Dealers");
//				intent.putExtra("category", "280");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Furniture = (TextView)activity.findViewById(R.id.Furniture);
		Furniture.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Furniture");
//				intent.putExtra("category", "1601");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Gym = (TextView)activity.findViewById(R.id.Gym);
		Gym.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Gym");
//				intent.putExtra("category", "147");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Hospital = (TextView)activity.findViewById(R.id.Hospital);
		Hospital.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Hospital");
//				intent.putExtra("category", "148");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Hotels = (TextView)activity.findViewById(R.id.Hotels);
		Hotels.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Hotels");
//				intent.putExtra("category", "1236");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Hardware = (TextView)activity.findViewById(R.id.Hardware);
		Hardware.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Hardware");
//				intent.putExtra("category", "47");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Lawyers = (TextView)activity.findViewById(R.id.Lawyers);
		Lawyers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Lawyers");
//				intent.putExtra("category", "177");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		

		InteriorDecoratorsnDesigners = (TextView)activity.findViewById(R.id.InteriorDecoratorsDesigners);
		InteriorDecoratorsnDesigners.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Interior Decorators & Designers");
//				intent.putExtra("category", "38");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		LabourSupply = (TextView)activity.findViewById(R.id.LabourSupply);
		LabourSupply.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Labour Supply");
//				intent.putExtra("category", "176");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		MarblenGraniteMfrsnSuppliers = (TextView)activity.findViewById(R.id.MarbleGraniteMfrsSuppliers);
		MarblenGraniteMfrsnSuppliers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Marble & Granite Mfrs & Suppliers");
//				intent.putExtra("category", "24");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		MedicalEquipmentSuppliers = (TextView)activity.findViewById(R.id.MedicalEquipmentSuppliers);
		MedicalEquipmentSuppliers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Medical Equipment Suppliers");
//				intent.putExtra("category", "180");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		MineralWater = (TextView)activity.findViewById(R.id.MineralWater);
		MineralWater.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Mineral Water");
//				intent.putExtra("category", "181");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		OilCompanies = (TextView)activity.findViewById(R.id.OilCompanies);
		OilCompanies.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Oil Companies");
//				intent.putExtra("category", "180");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		PestControl = (TextView)activity.findViewById(R.id.PestControl);
		PestControl.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Pest Control");
//				intent.putExtra("category", "183");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		PetsCare = (TextView)activity.findViewById(R.id.PetsCare);
		PetsCare.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Pets Care");
//				intent.putExtra("category", "1603");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Pharmacy = (TextView)activity.findViewById(R.id.Pharmacy);
		Pharmacy.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Pharmacy");
//				intent.putExtra("category", "194");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		PipenPipeFittingSuppliers = (TextView)activity.findViewById(R.id.PipePipeFittingSuppliers);
		PipenPipeFittingSuppliers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Pipe & pipe fitting suppliers");
//				intent.putExtra("category", "195");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});

		PlasticsnPlasticProductsMfrsnSuppliers = (TextView)activity.findViewById(R.id.PlasticsPlasticProductsMfrsSuppliers);
		PlasticsnPlasticProductsMfrsnSuppliers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "plastics & plastic products mfrs & suppliers");
//				intent.putExtra("category", "295");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		RealEstate = (TextView)activity.findViewById(R.id.RealEstate);
		RealEstate.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				SearchResultActivity.displayAdd = true;
				SearchResultActivity.img = Constants.backIMG_realestate;
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Real Estate");
//				intent.putExtra("category", "73");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		RecruitmentConsultants = (TextView)activity.findViewById(R.id.RecruitmentConsultants);
		RecruitmentConsultants.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Recruitment Consultants");
//				intent.putExtra("category", "201");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Restaurant = (TextView)activity.findViewById(R.id.Restaurant);
		Restaurant.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Restaurant");
//				intent.putExtra("category", "197");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		School = (TextView)activity.findViewById(R.id.School);
		School.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "School");
//				intent.putExtra("category", "1555");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		SafetynSecurity = (TextView)activity.findViewById(R.id.SafetySecurity);
		SafetynSecurity.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Safety & Security");
//				intent.putExtra("category", "47");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		ShippingCompaniesnAgents = (TextView)activity.findViewById(R.id.ShippingCompaniesAgents);
		ShippingCompaniesnAgents.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Shipping Companies & Agents");
//				intent.putExtra("category", "76");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		SteelFabricatorsnEngineers = (TextView)activity.findViewById(R.id.SteelFabricatorsEngineers);
		SteelFabricatorsnEngineers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Steel Fabricators & Engineers");
//				intent.putExtra("category", "8");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Supermarket = (TextView)activity.findViewById(R.id.Supermarket);
		Supermarket.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Supermarket");
//				intent.putExtra("category", "228");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Textiles = (TextView)activity.findViewById(R.id.Textiles);
		Textiles.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Textiles");
//				intent.putExtra("category", "313");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		TransportCompanies = (TextView)activity.findViewById(R.id.TransportCompanies);
		TransportCompanies.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Transport Companies");
//				intent.putExtra("category", "25");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		TravelAgents = (TextView)activity.findViewById(R.id.TravelAgents);
		TravelAgents.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Travel Agents");
//				intent.putExtra("category", "262");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		TyreDealers = (TextView)activity.findViewById(R.id.TyreDealers);
		TyreDealers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Tyre Dealers");
//				intent.putExtra("category", "263");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		TypingCenters = (TextView)activity.findViewById(R.id.TypingCenters);
		TypingCenters.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Typing Centers");
//				intent.putExtra("category", "263");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Jewellers = (TextView)activity.findViewById(R.id.Jewellers);
		Jewellers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Jewellers");
//				intent.putExtra("category", "281");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Florists = (TextView)activity.findViewById(R.id.Florists);
		Florists.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Florists");
//				intent.putExtra("category", "1515");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		CharityOrganisations = (TextView)activity.findViewById(R.id.CharityOrganisations);
		CharityOrganisations.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Charity Organisations");
//				intent.putExtra("category", "1506");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		MinistriesnGovtDepartment = (TextView)activity.findViewById(R.id.MinistriesGDepartment);
		MinistriesnGovtDepartment.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "ministries and govt department");
//				intent.putExtra("category", "1536");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		EmbassiesnConsulates = (TextView)activity.findViewById(R.id.EmbassiesConsulates);
		EmbassiesnConsulates.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Embassies & Consulates");
//				intent.putExtra("category", "1512");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		CollegesnUniversities = (TextView)activity.findViewById(R.id.CollegesUniversities);
		CollegesnUniversities.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "colleges and universities");
//				intent.putExtra("category", "1507");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		DrivingInstitutions = (TextView)activity.findViewById(R.id.DrivingInstitutions);
		DrivingInstitutions.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Driving Institutions");
//				intent.putExtra("category", "1508");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		EducationalInstitutes = (TextView)activity.findViewById(R.id.EducationalInstitutes);
		EducationalInstitutes.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Educational Institutes");
//				intent.putExtra("category", "47");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		RadioStations = (TextView)activity.findViewById(R.id.RadioStations);
		RadioStations.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Radio Stations");
//				intent.putExtra("category", "1553");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		TVStations = (TextView)activity.findViewById(R.id.TVStations);
		TVStations.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "TV Stations");
//				intent.putExtra("category", "47");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		InsuranceCompanies = (TextView)activity.findViewById(R.id.InsuranceCompanies);
		InsuranceCompanies.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Insurance Companies");
//				intent.putExtra("category", "1529");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		PhotoStudios = (TextView)activity.findViewById(R.id.PhotoStudios);
		PhotoStudios.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Photo Studios");
//				intent.putExtra("category", "1552");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Accountants = (TextView)activity.findViewById(R.id.Accountants);
		Accountants.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Accountants");
//				intent.putExtra("category", "1489");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		HousewareDealers = (TextView)activity.findViewById(R.id.HousewareDealers);
		HousewareDealers.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Houseware Dealers");
//				intent.putExtra("category", "1527");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Laundries = (TextView)activity.findViewById(R.id.Laundries);
		Laundries.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Laundries");
//				intent.putExtra("category", "1534");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Bookshop = (TextView)activity.findViewById(R.id.Bookshop);
		Bookshop.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Bookshop");
//				intent.putExtra("category", "1587");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		FootWear = (TextView)activity.findViewById(R.id.footwear);
		FootWear.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Foot Wear");
//				intent.putExtra("category", "1589");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
		
		Opticals = (TextView)activity.findViewById(R.id.Opticals);
		Opticals.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(activity,SearchResultActivity.class);
				intent.putExtra("category", "Opticals");
//				intent.putExtra("category", "1590");
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				activity.startActivity(intent);
			}
		});
	}
}
