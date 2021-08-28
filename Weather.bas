B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Private SQL1 As SQL
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private Spinner1 As Spinner
	Private Label6 As Label
	Private lblTime As String
	Private lblLat As Double
	Private lblLon As Double
	Private lblSpeed As String
	Private lblAccuracy As String
	Private Geocoder1 As Geocoder
	Private fm_date As String
	Private Label1 As Label
	Private Current_location As String
	Private Label4 As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("layoutWeather")
	Spinner1.Add("Choose your city")
	Spinner1.DropdownBackgroundColor = Colors.RGB(40,104,225)
	Spinner1.DropdownTextColor=Colors.White
	Spinner1.TextColor=Colors.White
	Label6.Text="28" & Chr(0x00B0)
	LoadData
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub LoadData
	If File.Exists(File.DirDefaultExternal, "weather.db") = False Then
		File.Copy(File.DirAssets, "weather.db", File.DirDefaultExternal,"weather.db")
		SQL1.Initialize(File.DirDefaultExternal, "weather.db", True)
	Else
		SQL1.Initialize(File.DirDefaultExternal, "weather.db", True)
	End If
	
	Dim Cursor1 As Cursor
	Dim i As Int
	
	Cursor1 = SQL1.ExecQuery("SELECT id,city FROM city ORDER by id ASC")
	If Cursor1.RowCount > 0 Then
	'Log (Cursor1.RowCount)
	For i = 0 To Cursor1.RowCount - 1
		Cursor1.Position = i
		'Log(Cursor1.Getint("id"))
		Spinner1.Add(Cursor1.GetString("city"))
	Next
	End If 
End Sub

Public Sub LocationChanged(Location1 As Location)
	lblTime = $"$Time{Location1.Time}"$
	lblLat =  Location1.Latitude
	lblLon =  Location1.Longitude
	lblSpeed = "$1.2{Location1.Speed} m/s "$
	lblAccuracy = $"$1.2{Location1.Accuracy} m"$
	Log(lblLat)
	Log(lblLon)
	Geocoder1.Initialize("GeoCoder1")
	Geocoder1.GetFromLocation(lblLat,lblLon,5,Null)
End Sub

Sub Geocoder1_GeocodeDone(Results() As Address, Tag As Object)
	If Results.Length>0 Then
		Dim Address1 As Address
		Dim i As Int
		For i=0 To Results.Length-1
			Address1=Results(i)
			Log("Address1.AddressLines.Size="&Address1.AdminArea.Length)
			If Address1.AdminArea.Length>0 Then
				'ResultsList.AddTwoLines(Address1.AddressLines.Get(0), Address1.AddressLines.Get(0))
				fm_date = general.DayName(DateTime.GetDayOfWeek(DateTime.Now)) & "," & general.MonthName(DateTime.GetMonth(DateTime.Now)) & " " & DateTime.GetDayOfMonth(DateTime.Now)
				Current_location =Address1.SubAdminArea & " " & Address1.CountryName
				Label1.Text = fm_date & CRLF & Current_location
				Log(fm_date)
				Log (Address1.SubAdminArea & " " & Address1.CountryName)
				GetWeather(Address1.SubAdminArea)
			End If
		Next
	Else
		'MsgboxAsync("GetFromLocation", "No Address matched the Latitude and Longitude")
		ToastMessageShow("No Address matched the Latitude and Longitude",False)
	End If
End Sub


Sub GetWeather(cityname As String)
	Dim API_KEY As String = "8f7c91d4cbd09df4129913dd6fb51b75"
	Dim url As String ="https://api.openweathermap.org/data/2.5/weather?q="& cityname & "&appid=" & API_KEY
	Dim j As HttpJob
	j.Initialize("", Me)
	j.Download(url)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		'Log(j.GetString)
		Dim response As String = j.GetString
		Dim parser As JSONParser
		parser.Initialize(response)
		Dim root As Map = parser.NextObject
		Dim visibility As Int = root.Get("visibility")
		Dim timezone As Int = root.Get("timezone")
		Dim Main_data As Map = root.Get("Main_data")
		Dim temp As Double = Main_data.Get("temp")
		Dim temp_min As Double = Main_data.Get("temp_min")
		Dim humidity As Int = Main_data.Get("humidity")
		Dim pressure As Int = Main_data.Get("pressure")
		Dim feels_like As Double = Main_data.Get("feels_like")
		Dim temp_max As Double = Main_data.Get("temp_max")
		Dim clouds As Map = root.Get("clouds")
		Dim all As Int = clouds.Get("all")
		Dim sys As Map = root.Get("sys")
		Dim country As String = sys.Get("country")
		Dim sunrise As Int = sys.Get("sunrise")
		Dim sunset As Int = sys.Get("sunset")
		Dim id As Int = sys.Get("id")
		Dim Type As Int = sys.Get("type")
		Dim dt As Int = root.Get("dt")
		Dim coord As Map = root.Get("coord")
		Dim lon As Double = coord.Get("lon")
		Dim lat As Double = coord.Get("lat")
		Dim weather As List = root.Get("weather")
		For Each colweather As Map In weather
			Dim icon As String = colweather.Get("icon")
			Dim description As String = colweather.Get("description")
			Dim Main_data1 As String = colweather.Get("Main_data")
			Dim id As Int = colweather.Get("id")
		Next
		Dim name As String = root.Get("name")
		Dim cod As Int = root.Get("cod")
		Dim id As Int = root.Get("id")
		Dim base As String = root.Get("base")
		Dim wind As Map = root.Get("wind")
		Dim deg As Int = wind.Get("deg")
		Dim speed As Double = wind.Get("speed")
		
		Label4.Text = humidity & " hPa"
		Label6.Text=deg & Chr(0x00B0)
	End If
	j.Release
End Sub
	


