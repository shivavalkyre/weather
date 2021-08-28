package com.weather;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, BA.class);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "com.weather", "com.weather.starter");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "com.weather.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (starter) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (starter) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (true) {
            BA.LogInfo("** Service (starter) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (starter) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper _flp = null;
public static boolean _flpstarted = false;
public b4a.example.dateutils _dateutils = null;
public com.weather.main _main = null;
public com.weather.general _general = null;
public com.weather.weather _weather = null;
public com.weather.httputils2service _httputils2service = null;
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 30;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return false;
}
public static uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest  _createlocationrequest() throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _lr = null;
 //BA.debugLineNum = 62;BA.debugLine="Private Sub CreateLocationRequest As LocationReque";
 //BA.debugLineNum = 63;BA.debugLine="Dim lr As LocationRequest";
_lr = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
 //BA.debugLineNum = 64;BA.debugLine="lr.Initialize";
_lr.Initialize();
 //BA.debugLineNum = 65;BA.debugLine="lr.SetInterval(10000)";
_lr.SetInterval((long) (10000));
 //BA.debugLineNum = 66;BA.debugLine="lr.SetFastestInterval(lr.GetInterval / 2)";
_lr.SetFastestInterval((long) (_lr.GetInterval()/(double)2));
 //BA.debugLineNum = 67;BA.debugLine="lr.SetPriority(lr.Priority.PRIORITY_HIGH_ACCURACY";
_lr.SetPriority(_lr.Priority.PRIORITY_HIGH_ACCURACY);
 //BA.debugLineNum = 68;BA.debugLine="Return lr";
if (true) return _lr;
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return null;
}
public static String  _flp_connectionfailed(int _connectionresult1) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub flp_ConnectionFailed(ConnectionResult1 As Int)";
 //BA.debugLineNum = 43;BA.debugLine="Log(\"Failed to connect to location provider\")";
anywheresoftware.b4a.keywords.Common.LogImpl("62359297","Failed to connect to location provider",0);
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _flp_connectionsuccess() throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub flp_ConnectionSuccess";
 //BA.debugLineNum = 39;BA.debugLine="Log(\"Connected to location provider\")";
anywheresoftware.b4a.keywords.Common.LogImpl("62293761","Connected to location provider",0);
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _flp_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Private Sub flp_LocationChanged (Location1 As Loca";
 //BA.debugLineNum = 59;BA.debugLine="CallSub2(Weather, \"LocationChanged\", Location1)";
anywheresoftware.b4a.keywords.Common.CallSubNew2(processBA,(Object)(mostCurrent._weather.getObject()),"LocationChanged",(Object)(_location1));
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Public FLP As FusedLocationProvider";
_flp = new uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Private flpStarted As Boolean";
_flpstarted = false;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 16;BA.debugLine="FLP.Initialize(\"flp\")";
_flp.Initialize(processBA,"flp");
 //BA.debugLineNum = 17;BA.debugLine="FLP.Connect";
_flp.Connect();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 34;BA.debugLine="StopFLP";
_stopflp();
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 21;BA.debugLine="Service.StopAutomaticForeground 'Starter service";
mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static void  _startflp() throws Exception{
ResumableSub_StartFLP rsub = new ResumableSub_StartFLP(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_StartFLP extends BA.ResumableSub {
public ResumableSub_StartFLP(com.weather.starter parent) {
this.parent = parent;
}
com.weather.starter parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 48;BA.debugLine="Do While FLP.IsConnected = False";
if (true) break;

case 1:
//do while
this.state = 4;
while (parent._flp.IsConnected()==anywheresoftware.b4a.keywords.Common.False) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 1;
 //BA.debugLineNum = 49;BA.debugLine="Sleep(1000)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (1000));
this.state = 8;
return;
case 8:
//C
this.state = 1;
;
 if (true) break;
;
 //BA.debugLineNum = 51;BA.debugLine="If flpStarted = False Then";

case 4:
//if
this.state = 7;
if (parent._flpstarted==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 52;BA.debugLine="FLP.RequestLocationUpdates(CreateLocationRequest";
parent._flp.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_createlocationrequest().getObject()));
 //BA.debugLineNum = 53;BA.debugLine="flpStarted = True";
parent._flpstarted = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 7:
//C
this.state = -1;
;
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _stopflp() throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Public Sub StopFLP";
 //BA.debugLineNum = 72;BA.debugLine="If flpStarted Then";
if (_flpstarted) { 
 //BA.debugLineNum = 73;BA.debugLine="FLP.RemoveLocationUpdates";
_flp.RemoveLocationUpdates();
 //BA.debugLineNum = 74;BA.debugLine="flpStarted = False";
_flpstarted = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
}
