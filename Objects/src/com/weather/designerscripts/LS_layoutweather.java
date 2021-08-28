package com.weather.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layoutweather{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setTop((int)((0d * scale)));
//BA.debugLineNum = 5;BA.debugLine="Panel1.Left=0dip"[layoutWeather/General script]
views.get("panel1").vw.setLeft((int)((0d * scale)));
//BA.debugLineNum = 7;BA.debugLine="Panel2.Top=10dip"[layoutWeather/General script]
views.get("panel2").vw.setTop((int)((10d * scale)));
//BA.debugLineNum = 9;BA.debugLine="Spinner1.Left=10dip"[layoutWeather/General script]
views.get("spinner1").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 11;BA.debugLine="Label1.Top=Panel2.Top+Panel2.Height+20dip"[layoutWeather/General script]
views.get("label1").vw.setTop((int)((views.get("panel2").vw.getTop())+(views.get("panel2").vw.getHeight())+(20d * scale)));
//BA.debugLineNum = 12;BA.debugLine="PanelLine1.Top=Panel1.Height-10dip"[layoutWeather/General script]
views.get("panelline1").vw.setTop((int)((views.get("panel1").vw.getHeight())-(10d * scale)));
//BA.debugLineNum = 13;BA.debugLine="Panel3.Top=Panel1.Top+Panel1.Height"[layoutWeather/General script]
views.get("panel3").vw.setTop((int)((views.get("panel1").vw.getTop())+(views.get("panel1").vw.getHeight())));
//BA.debugLineNum = 15;BA.debugLine="Label7.Left=ImageView1.Left"[layoutWeather/General script]
views.get("label7").vw.setLeft((int)((views.get("imageview1").vw.getLeft())));
//BA.debugLineNum = 16;BA.debugLine="Label7.Top=ImageView1.Top+ImageView1.Height-20dip"[layoutWeather/General script]
views.get("label7").vw.setTop((int)((views.get("imageview1").vw.getTop())+(views.get("imageview1").vw.getHeight())-(20d * scale)));
//BA.debugLineNum = 18;BA.debugLine="PanelLine2.Top= Panel3.Height-10dip"[layoutWeather/General script]
views.get("panelline2").vw.setTop((int)((views.get("panel3").vw.getHeight())-(10d * scale)));
//BA.debugLineNum = 20;BA.debugLine="Label8.Left=30dip"[layoutWeather/General script]
views.get("label8").vw.setLeft((int)((30d * scale)));
//BA.debugLineNum = 21;BA.debugLine="Label9.Left = Label8.Left+Label9.Width"[layoutWeather/General script]
views.get("label9").vw.setLeft((int)((views.get("label8").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 22;BA.debugLine="Label10.Left=Label9.Left+Label9.Width"[layoutWeather/General script]
views.get("label10").vw.setLeft((int)((views.get("label9").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 23;BA.debugLine="Label11.Left=Label10.Left+Label9.Width"[layoutWeather/General script]
views.get("label11").vw.setLeft((int)((views.get("label10").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 24;BA.debugLine="Label12.Left=Label11.Left+Label9.Width"[layoutWeather/General script]
views.get("label12").vw.setLeft((int)((views.get("label11").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 25;BA.debugLine="Label13.Left=Label12.Left+Label9.Width"[layoutWeather/General script]
views.get("label13").vw.setLeft((int)((views.get("label12").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 26;BA.debugLine="Label14.Left=Label13.Left+Label9.Width"[layoutWeather/General script]
views.get("label14").vw.setLeft((int)((views.get("label13").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 28;BA.debugLine="ImageView2.Left=Label8.Left+Label8.Width/2 -ImageView2.Width/2"[layoutWeather/General script]
views.get("imageview2").vw.setLeft((int)((views.get("label8").vw.getLeft())+(views.get("label8").vw.getWidth())/2d-(views.get("imageview2").vw.getWidth())/2d));
//BA.debugLineNum = 29;BA.debugLine="ImageView3.Left=Label9.Left+Label9.Width/2 -ImageView3.Width/2"[layoutWeather/General script]
views.get("imageview3").vw.setLeft((int)((views.get("label9").vw.getLeft())+(views.get("label9").vw.getWidth())/2d-(views.get("imageview3").vw.getWidth())/2d));
//BA.debugLineNum = 30;BA.debugLine="ImageView4.Left=Label10.Left+Label10.Width/2 -ImageView2.Width/2"[layoutWeather/General script]
views.get("imageview4").vw.setLeft((int)((views.get("label10").vw.getLeft())+(views.get("label10").vw.getWidth())/2d-(views.get("imageview2").vw.getWidth())/2d));
//BA.debugLineNum = 31;BA.debugLine="ImageView5.Left=Label11.Left+Label11.Width/2 -ImageView2.Width/2"[layoutWeather/General script]
views.get("imageview5").vw.setLeft((int)((views.get("label11").vw.getLeft())+(views.get("label11").vw.getWidth())/2d-(views.get("imageview2").vw.getWidth())/2d));
//BA.debugLineNum = 32;BA.debugLine="ImageView6.Left=Label12.Left+Label12.Width/2 -ImageView2.Width/2"[layoutWeather/General script]
views.get("imageview6").vw.setLeft((int)((views.get("label12").vw.getLeft())+(views.get("label12").vw.getWidth())/2d-(views.get("imageview2").vw.getWidth())/2d));
//BA.debugLineNum = 33;BA.debugLine="ImageView7.Left=Label13.Left+Label13.Width/2 -ImageView2.Width/2"[layoutWeather/General script]
views.get("imageview7").vw.setLeft((int)((views.get("label13").vw.getLeft())+(views.get("label13").vw.getWidth())/2d-(views.get("imageview2").vw.getWidth())/2d));
//BA.debugLineNum = 34;BA.debugLine="ImageView8.Left=Label14.Left+Label14.Width/2 -ImageView2.Width/2"[layoutWeather/General script]
views.get("imageview8").vw.setLeft((int)((views.get("label14").vw.getLeft())+(views.get("label14").vw.getWidth())/2d-(views.get("imageview2").vw.getWidth())/2d));
//BA.debugLineNum = 38;BA.debugLine="Label15.Left=30dip"[layoutWeather/General script]
views.get("label15").vw.setLeft((int)((30d * scale)));
//BA.debugLineNum = 39;BA.debugLine="Label16.Left = Label8.Left+Label9.Width"[layoutWeather/General script]
views.get("label16").vw.setLeft((int)((views.get("label8").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 40;BA.debugLine="Label17.Left=Label9.Left+Label9.Width"[layoutWeather/General script]
views.get("label17").vw.setLeft((int)((views.get("label9").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 41;BA.debugLine="Label18.Left=Label10.Left+Label9.Width"[layoutWeather/General script]
views.get("label18").vw.setLeft((int)((views.get("label10").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 42;BA.debugLine="Label19.Left=Label11.Left+Label9.Width"[layoutWeather/General script]
views.get("label19").vw.setLeft((int)((views.get("label11").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 43;BA.debugLine="Label20.Left=Label12.Left+Label9.Width"[layoutWeather/General script]
views.get("label20").vw.setLeft((int)((views.get("label12").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 44;BA.debugLine="Label21.Left=Label13.Left+Label9.Width"[layoutWeather/General script]
views.get("label21").vw.setLeft((int)((views.get("label13").vw.getLeft())+(views.get("label9").vw.getWidth())));
//BA.debugLineNum = 45;BA.debugLine="Panel5.Left=Label8.Left"[layoutWeather/General script]
views.get("panel5").vw.setLeft((int)((views.get("label8").vw.getLeft())));
//BA.debugLineNum = 46;BA.debugLine="Panel6.Left=100%x-Panel6.Width-20dip"[layoutWeather/General script]
views.get("panel6").vw.setLeft((int)((100d / 100 * width)-(views.get("panel6").vw.getWidth())-(20d * scale)));

}
}