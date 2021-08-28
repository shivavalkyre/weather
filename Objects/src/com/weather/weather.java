package com.weather;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class weather extends Activity implements B4AActivity{
	public static weather mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.weather", "com.weather.weather");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (weather).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.weather", "com.weather.weather");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.weather.weather", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (weather) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (weather) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return weather.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (weather) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (weather) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            weather mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (weather) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL _sql1 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public static String _lbltime = "";
public static double _lbllat = 0;
public static double _lbllon = 0;
public static String _lblspeed = "";
public static String _lblaccuracy = "";
public uk.co.martinpearman.b4a.location.GeocoderWrapper _geocoder1 = null;
public static String _fm_date = "";
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public static String _current_location = "";
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public b4a.example.dateutils _dateutils = null;
public com.weather.main _main = null;
public com.weather.starter _starter = null;
public com.weather.general _general = null;
public com.weather.httputils2service _httputils2service = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 32;BA.debugLine="Activity.LoadLayout(\"layoutWeather\")";
mostCurrent._activity.LoadLayout("layoutWeather",mostCurrent.activityBA);
 //BA.debugLineNum = 33;BA.debugLine="Spinner1.Add(\"Choose your city\")";
mostCurrent._spinner1.Add("Choose your city");
 //BA.debugLineNum = 34;BA.debugLine="Spinner1.DropdownBackgroundColor = Colors.RGB(40,";
mostCurrent._spinner1.setDropdownBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (40),(int) (104),(int) (225)));
 //BA.debugLineNum = 35;BA.debugLine="Spinner1.DropdownTextColor=Colors.White";
mostCurrent._spinner1.setDropdownTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 36;BA.debugLine="Spinner1.TextColor=Colors.White";
mostCurrent._spinner1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 37;BA.debugLine="Label6.Text=\"28\" & Chr(0x00B0)";
mostCurrent._label6.setText(BA.ObjectToCharSequence("28"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x00b0)))));
 //BA.debugLineNum = 38;BA.debugLine="LoadData";
_loaddata();
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _geocoder1_geocodedone(uk.co.martinpearman.b4a.location.AddressWrapper[] _results,Object _tag) throws Exception{
uk.co.martinpearman.b4a.location.AddressWrapper _address1 = null;
int _i = 0;
 //BA.debugLineNum = 84;BA.debugLine="Sub Geocoder1_GeocodeDone(Results() As Address, Ta";
 //BA.debugLineNum = 85;BA.debugLine="If Results.Length>0 Then";
if (_results.length>0) { 
 //BA.debugLineNum = 86;BA.debugLine="Dim Address1 As Address";
_address1 = new uk.co.martinpearman.b4a.location.AddressWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 88;BA.debugLine="For i=0 To Results.Length-1";
{
final int step4 = 1;
final int limit4 = (int) (_results.length-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 89;BA.debugLine="Address1=Results(i)";
_address1 = _results[_i];
 //BA.debugLineNum = 90;BA.debugLine="Log(\"Address1.AddressLines.Size=\"&Address1.Admi";
anywheresoftware.b4a.keywords.Common.LogImpl("62752518","Address1.AddressLines.Size="+BA.NumberToString(_address1.getAdminArea().length()),0);
 //BA.debugLineNum = 91;BA.debugLine="If Address1.AdminArea.Length>0 Then";
if (_address1.getAdminArea().length()>0) { 
 //BA.debugLineNum = 93;BA.debugLine="fm_date = general.DayName(DateTime.GetDayOfWee";
mostCurrent._fm_date = mostCurrent._general._dayname /*String*/ (mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfWeek(anywheresoftware.b4a.keywords.Common.DateTime.getNow()))+","+mostCurrent._general._monthname /*String*/ (mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()))+" "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 94;BA.debugLine="Current_location =Address1.SubAdminArea & \" \"";
mostCurrent._current_location = _address1.getSubAdminArea()+" "+_address1.getCountryName();
 //BA.debugLineNum = 95;BA.debugLine="Label1.Text = fm_date & CRLF & Current_locatio";
mostCurrent._label1.setText(BA.ObjectToCharSequence(mostCurrent._fm_date+anywheresoftware.b4a.keywords.Common.CRLF+mostCurrent._current_location));
 //BA.debugLineNum = 96;BA.debugLine="Log(fm_date)";
anywheresoftware.b4a.keywords.Common.LogImpl("62752524",mostCurrent._fm_date,0);
 //BA.debugLineNum = 97;BA.debugLine="Log (Address1.SubAdminArea & \" \" & Address1.Co";
anywheresoftware.b4a.keywords.Common.LogImpl("62752525",_address1.getSubAdminArea()+" "+_address1.getCountryName(),0);
 //BA.debugLineNum = 98;BA.debugLine="GetWeather(Address1.SubAdminArea)";
_getweather(_address1.getSubAdminArea());
 };
 }
};
 }else {
 //BA.debugLineNum = 103;BA.debugLine="ToastMessageShow(\"No Address matched the Latitud";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No Address matched the Latitude and Longitude"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static void  _getweather(String _cityname) throws Exception{
ResumableSub_GetWeather rsub = new ResumableSub_GetWeather(null,_cityname);
rsub.resume(processBA, null);
}
public static class ResumableSub_GetWeather extends BA.ResumableSub {
public ResumableSub_GetWeather(com.weather.weather parent,String _cityname) {
this.parent = parent;
this._cityname = _cityname;
}
com.weather.weather parent;
String _cityname;
String _api_key = "";
String _url = "";
com.weather.httpjob _j = null;
String _response = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
int _visibility = 0;
int _timezone = 0;
anywheresoftware.b4a.objects.collections.Map _main_data = null;
double _temp = 0;
double _temp_min = 0;
int _humidity = 0;
int _pressure = 0;
double _feels_like = 0;
double _temp_max = 0;
anywheresoftware.b4a.objects.collections.Map _clouds = null;
int _all = 0;
anywheresoftware.b4a.objects.collections.Map _sys = null;
String _country = "";
int _sunrise = 0;
int _sunset = 0;
int _id = 0;
int _type = 0;
int _dt = 0;
anywheresoftware.b4a.objects.collections.Map _coord = null;
double _lon = 0;
double _lat = 0;
anywheresoftware.b4a.objects.collections.List _weather = null;
anywheresoftware.b4a.objects.collections.Map _colweather = null;
String _icon = "";
String _description = "";
String _main_data1 = "";
String _name = "";
int _cod = 0;
String _base = "";
anywheresoftware.b4a.objects.collections.Map _wind = null;
int _deg = 0;
double _speed = 0;
anywheresoftware.b4a.BA.IterableList group34;
int index34;
int groupLen34;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 109;BA.debugLine="Dim API_KEY As String = \"8f7c91d4cbd09df4129913dd";
_api_key = "8f7c91d4cbd09df4129913dd6fb51b75";
 //BA.debugLineNum = 110;BA.debugLine="Dim url As String =\"https://api.openweathermap.or";
_url = "https://api.openweathermap.org/data/2.5/weather?q="+_cityname+"&appid="+_api_key;
 //BA.debugLineNum = 111;BA.debugLine="Dim j As HttpJob";
_j = new com.weather.httpjob();
 //BA.debugLineNum = 112;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",weather.getObject());
 //BA.debugLineNum = 113;BA.debugLine="j.Download(url)";
_j._download /*String*/ (_url);
 //BA.debugLineNum = 114;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 9;
return;
case 9:
//C
this.state = 1;
_j = (com.weather.httpjob) result[0];
;
 //BA.debugLineNum = 115;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 8;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 117;BA.debugLine="Dim response As String = j.GetString";
_response = _j._getstring /*String*/ ();
 //BA.debugLineNum = 118;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 119;BA.debugLine="parser.Initialize(response)";
_parser.Initialize(_response);
 //BA.debugLineNum = 120;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 121;BA.debugLine="Dim visibility As Int = root.Get(\"visibility\")";
_visibility = (int)(BA.ObjectToNumber(_root.Get((Object)("visibility"))));
 //BA.debugLineNum = 122;BA.debugLine="Dim timezone As Int = root.Get(\"timezone\")";
_timezone = (int)(BA.ObjectToNumber(_root.Get((Object)("timezone"))));
 //BA.debugLineNum = 123;BA.debugLine="Dim Main_data As Map = root.Get(\"Main_data\")";
_main_data = new anywheresoftware.b4a.objects.collections.Map();
_main_data = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_root.Get((Object)("Main_data"))));
 //BA.debugLineNum = 124;BA.debugLine="Dim temp As Double = Main_data.Get(\"temp\")";
_temp = (double)(BA.ObjectToNumber(_main_data.Get((Object)("temp"))));
 //BA.debugLineNum = 125;BA.debugLine="Dim temp_min As Double = Main_data.Get(\"temp_min";
_temp_min = (double)(BA.ObjectToNumber(_main_data.Get((Object)("temp_min"))));
 //BA.debugLineNum = 126;BA.debugLine="Dim humidity As Int = Main_data.Get(\"humidity\")";
_humidity = (int)(BA.ObjectToNumber(_main_data.Get((Object)("humidity"))));
 //BA.debugLineNum = 127;BA.debugLine="Dim pressure As Int = Main_data.Get(\"pressure\")";
_pressure = (int)(BA.ObjectToNumber(_main_data.Get((Object)("pressure"))));
 //BA.debugLineNum = 128;BA.debugLine="Dim feels_like As Double = Main_data.Get(\"feels_";
_feels_like = (double)(BA.ObjectToNumber(_main_data.Get((Object)("feels_like"))));
 //BA.debugLineNum = 129;BA.debugLine="Dim temp_max As Double = Main_data.Get(\"temp_max";
_temp_max = (double)(BA.ObjectToNumber(_main_data.Get((Object)("temp_max"))));
 //BA.debugLineNum = 130;BA.debugLine="Dim clouds As Map = root.Get(\"clouds\")";
_clouds = new anywheresoftware.b4a.objects.collections.Map();
_clouds = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_root.Get((Object)("clouds"))));
 //BA.debugLineNum = 131;BA.debugLine="Dim all As Int = clouds.Get(\"all\")";
_all = (int)(BA.ObjectToNumber(_clouds.Get((Object)("all"))));
 //BA.debugLineNum = 132;BA.debugLine="Dim sys As Map = root.Get(\"sys\")";
_sys = new anywheresoftware.b4a.objects.collections.Map();
_sys = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_root.Get((Object)("sys"))));
 //BA.debugLineNum = 133;BA.debugLine="Dim country As String = sys.Get(\"country\")";
_country = BA.ObjectToString(_sys.Get((Object)("country")));
 //BA.debugLineNum = 134;BA.debugLine="Dim sunrise As Int = sys.Get(\"sunrise\")";
_sunrise = (int)(BA.ObjectToNumber(_sys.Get((Object)("sunrise"))));
 //BA.debugLineNum = 135;BA.debugLine="Dim sunset As Int = sys.Get(\"sunset\")";
_sunset = (int)(BA.ObjectToNumber(_sys.Get((Object)("sunset"))));
 //BA.debugLineNum = 136;BA.debugLine="Dim id As Int = sys.Get(\"id\")";
_id = (int)(BA.ObjectToNumber(_sys.Get((Object)("id"))));
 //BA.debugLineNum = 137;BA.debugLine="Dim Type As Int = sys.Get(\"type\")";
_type = (int)(BA.ObjectToNumber(_sys.Get((Object)("type"))));
 //BA.debugLineNum = 138;BA.debugLine="Dim dt As Int = root.Get(\"dt\")";
_dt = (int)(BA.ObjectToNumber(_root.Get((Object)("dt"))));
 //BA.debugLineNum = 139;BA.debugLine="Dim coord As Map = root.Get(\"coord\")";
_coord = new anywheresoftware.b4a.objects.collections.Map();
_coord = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_root.Get((Object)("coord"))));
 //BA.debugLineNum = 140;BA.debugLine="Dim lon As Double = coord.Get(\"lon\")";
_lon = (double)(BA.ObjectToNumber(_coord.Get((Object)("lon"))));
 //BA.debugLineNum = 141;BA.debugLine="Dim lat As Double = coord.Get(\"lat\")";
_lat = (double)(BA.ObjectToNumber(_coord.Get((Object)("lat"))));
 //BA.debugLineNum = 142;BA.debugLine="Dim weather As List = root.Get(\"weather\")";
_weather = new anywheresoftware.b4a.objects.collections.List();
_weather = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_root.Get((Object)("weather"))));
 //BA.debugLineNum = 143;BA.debugLine="For Each colweather As Map In weather";
if (true) break;

case 4:
//for
this.state = 7;
_colweather = new anywheresoftware.b4a.objects.collections.Map();
group34 = _weather;
index34 = 0;
groupLen34 = group34.getSize();
this.state = 10;
if (true) break;

case 10:
//C
this.state = 7;
if (index34 < groupLen34) {
this.state = 6;
_colweather = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group34.Get(index34)));}
if (true) break;

case 11:
//C
this.state = 10;
index34++;
if (true) break;

case 6:
//C
this.state = 11;
 //BA.debugLineNum = 144;BA.debugLine="Dim icon As String = colweather.Get(\"icon\")";
_icon = BA.ObjectToString(_colweather.Get((Object)("icon")));
 //BA.debugLineNum = 145;BA.debugLine="Dim description As String = colweather.Get(\"des";
_description = BA.ObjectToString(_colweather.Get((Object)("description")));
 //BA.debugLineNum = 146;BA.debugLine="Dim Main_data1 As String = colweather.Get(\"Main";
_main_data1 = BA.ObjectToString(_colweather.Get((Object)("Main_data")));
 //BA.debugLineNum = 147;BA.debugLine="Dim id As Int = colweather.Get(\"id\")";
_id = (int)(BA.ObjectToNumber(_colweather.Get((Object)("id"))));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 149;BA.debugLine="Dim name As String = root.Get(\"name\")";
_name = BA.ObjectToString(_root.Get((Object)("name")));
 //BA.debugLineNum = 150;BA.debugLine="Dim cod As Int = root.Get(\"cod\")";
_cod = (int)(BA.ObjectToNumber(_root.Get((Object)("cod"))));
 //BA.debugLineNum = 151;BA.debugLine="Dim id As Int = root.Get(\"id\")";
_id = (int)(BA.ObjectToNumber(_root.Get((Object)("id"))));
 //BA.debugLineNum = 152;BA.debugLine="Dim base As String = root.Get(\"base\")";
_base = BA.ObjectToString(_root.Get((Object)("base")));
 //BA.debugLineNum = 153;BA.debugLine="Dim wind As Map = root.Get(\"wind\")";
_wind = new anywheresoftware.b4a.objects.collections.Map();
_wind = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_root.Get((Object)("wind"))));
 //BA.debugLineNum = 154;BA.debugLine="Dim deg As Int = wind.Get(\"deg\")";
_deg = (int)(BA.ObjectToNumber(_wind.Get((Object)("deg"))));
 //BA.debugLineNum = 155;BA.debugLine="Dim speed As Double = wind.Get(\"speed\")";
_speed = (double)(BA.ObjectToNumber(_wind.Get((Object)("speed"))));
 //BA.debugLineNum = 157;BA.debugLine="Label4.Text = humidity & \" hPa\"";
parent.mostCurrent._label4.setText(BA.ObjectToCharSequence(BA.NumberToString(_humidity)+" hPa"));
 //BA.debugLineNum = 158;BA.debugLine="Label6.Text=deg & Chr(0x00B0)";
parent.mostCurrent._label6.setText(BA.ObjectToCharSequence(BA.NumberToString(_deg)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x00b0)))));
 if (true) break;

case 8:
//C
this.state = -1;
;
 //BA.debugLineNum = 160;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(com.weather.httpjob _j) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblTime As String";
mostCurrent._lbltime = "";
 //BA.debugLineNum = 19;BA.debugLine="Private lblLat As Double";
_lbllat = 0;
 //BA.debugLineNum = 20;BA.debugLine="Private lblLon As Double";
_lbllon = 0;
 //BA.debugLineNum = 21;BA.debugLine="Private lblSpeed As String";
mostCurrent._lblspeed = "";
 //BA.debugLineNum = 22;BA.debugLine="Private lblAccuracy As String";
mostCurrent._lblaccuracy = "";
 //BA.debugLineNum = 23;BA.debugLine="Private Geocoder1 As Geocoder";
mostCurrent._geocoder1 = new uk.co.martinpearman.b4a.location.GeocoderWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private fm_date As String";
mostCurrent._fm_date = "";
 //BA.debugLineNum = 25;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Current_location As String";
mostCurrent._current_location = "";
 //BA.debugLineNum = 27;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _loaddata() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
int _i = 0;
 //BA.debugLineNum = 50;BA.debugLine="Sub LoadData";
 //BA.debugLineNum = 51;BA.debugLine="If File.Exists(File.DirDefaultExternal, \"weather.";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"weather.db")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 52;BA.debugLine="File.Copy(File.DirAssets, \"weather.db\", File.Dir";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"weather.db",anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"weather.db");
 //BA.debugLineNum = 53;BA.debugLine="SQL1.Initialize(File.DirDefaultExternal, \"weathe";
_sql1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"weather.db",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 55;BA.debugLine="SQL1.Initialize(File.DirDefaultExternal, \"weathe";
_sql1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"weather.db",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 58;BA.debugLine="Dim Cursor1 As Cursor";
_cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 61;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT id,city FROM cit";
_cursor1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql1.ExecQuery("SELECT id,city FROM city ORDER by id ASC")));
 //BA.debugLineNum = 62;BA.debugLine="If Cursor1.RowCount > 0 Then";
if (_cursor1.getRowCount()>0) { 
 //BA.debugLineNum = 64;BA.debugLine="For i = 0 To Cursor1.RowCount - 1";
{
final int step11 = 1;
final int limit11 = (int) (_cursor1.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 65;BA.debugLine="Cursor1.Position = i";
_cursor1.setPosition(_i);
 //BA.debugLineNum = 67;BA.debugLine="Spinner1.Add(Cursor1.GetString(\"city\"))";
mostCurrent._spinner1.Add(_cursor1.GetString("city"));
 }
};
 };
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Public Sub LocationChanged(Location1 As Location)";
 //BA.debugLineNum = 73;BA.debugLine="lblTime = $\"$Time{Location1.Time}\"$";
mostCurrent._lbltime = (""+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("time",(Object)(_location1.getTime()))+"");
 //BA.debugLineNum = 74;BA.debugLine="lblLat =  Location1.Latitude";
_lbllat = _location1.getLatitude();
 //BA.debugLineNum = 75;BA.debugLine="lblLon =  Location1.Longitude";
_lbllon = _location1.getLongitude();
 //BA.debugLineNum = 76;BA.debugLine="lblSpeed = \"$1.2{Location1.Speed} m/s \"$";
mostCurrent._lblspeed = "$1.2{Location1.Speed} m/s ";
 //BA.debugLineNum = 77;BA.debugLine="lblAccuracy = $\"$1.2{Location1.Accuracy} m\"$";
mostCurrent._lblaccuracy = (""+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("1.2",(Object)(_location1.getAccuracy()))+" m");
 //BA.debugLineNum = 78;BA.debugLine="Log(lblLat)";
anywheresoftware.b4a.keywords.Common.LogImpl("62686982",BA.NumberToString(_lbllat),0);
 //BA.debugLineNum = 79;BA.debugLine="Log(lblLon)";
anywheresoftware.b4a.keywords.Common.LogImpl("62686983",BA.NumberToString(_lbllon),0);
 //BA.debugLineNum = 80;BA.debugLine="Geocoder1.Initialize(\"GeoCoder1\")";
mostCurrent._geocoder1.Initialize(processBA,"GeoCoder1");
 //BA.debugLineNum = 81;BA.debugLine="Geocoder1.GetFromLocation(lblLat,lblLon,5,Null)";
mostCurrent._geocoder1.GetFromLocation(processBA,_lbllat,_lbllon,(int) (5),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private SQL1 As SQL";
_sql1 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
