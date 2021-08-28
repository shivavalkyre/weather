package com.weather;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class general {
private static general mostCurrent = new general();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example.dateutils _dateutils = null;
public com.weather.main _main = null;
public com.weather.starter _starter = null;
public com.weather.weather _weather = null;
public com.weather.httputils2service _httputils2service = null;
public static String  _dayname(anywheresoftware.b4a.BA _ba,int _weekday) throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub DayName(weekday As Int) As String";
 //BA.debugLineNum = 10;BA.debugLine="Select weekday";
switch (_weekday) {
case 1: {
 //BA.debugLineNum = 12;BA.debugLine="Return \"Sunday\"";
if (true) return "Sunday";
 break; }
case 2: {
 //BA.debugLineNum = 14;BA.debugLine="Return \"Monday\"";
if (true) return "Monday";
 break; }
case 3: {
 //BA.debugLineNum = 16;BA.debugLine="Return \"Tuesday\"";
if (true) return "Tuesday";
 break; }
case 4: {
 //BA.debugLineNum = 18;BA.debugLine="Return \"Wednesday\"";
if (true) return "Wednesday";
 break; }
case 5: {
 //BA.debugLineNum = 20;BA.debugLine="Return \"Thursday\"";
if (true) return "Thursday";
 break; }
case 6: {
 //BA.debugLineNum = 22;BA.debugLine="Return \"Friday\"";
if (true) return "Friday";
 break; }
case 7: {
 //BA.debugLineNum = 24;BA.debugLine="Return \"Saturday\"";
if (true) return "Saturday";
 break; }
}
;
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _monthname(anywheresoftware.b4a.BA _ba,int _month) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub MonthName(month As Int) As String";
 //BA.debugLineNum = 30;BA.debugLine="Select month";
switch (_month) {
case 1: {
 //BA.debugLineNum = 32;BA.debugLine="Return \"January\"";
if (true) return "January";
 break; }
case 2: {
 //BA.debugLineNum = 34;BA.debugLine="Return \"February\"";
if (true) return "February";
 break; }
case 3: {
 //BA.debugLineNum = 36;BA.debugLine="Return \"March\"";
if (true) return "March";
 break; }
case 4: {
 //BA.debugLineNum = 38;BA.debugLine="Return \"April\"";
if (true) return "April";
 break; }
case 5: {
 //BA.debugLineNum = 40;BA.debugLine="Return \"May\"";
if (true) return "May";
 break; }
case 6: {
 //BA.debugLineNum = 42;BA.debugLine="Return \"June\"";
if (true) return "June";
 break; }
case 7: {
 //BA.debugLineNum = 44;BA.debugLine="Return \"July\"";
if (true) return "July";
 break; }
case 8: {
 //BA.debugLineNum = 46;BA.debugLine="Return \"August\"";
if (true) return "August";
 break; }
case 9: {
 //BA.debugLineNum = 48;BA.debugLine="Return \"September\"";
if (true) return "September";
 break; }
case 10: {
 //BA.debugLineNum = 50;BA.debugLine="Return \"October\"";
if (true) return "October";
 break; }
case 11: {
 //BA.debugLineNum = 52;BA.debugLine="Return \"November\"";
if (true) return "November";
 break; }
case 12: {
 //BA.debugLineNum = 54;BA.debugLine="Return \"December\"";
if (true) return "December";
 break; }
}
;
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
}
