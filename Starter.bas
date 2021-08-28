B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=9.9
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Public FLP As FusedLocationProvider
	Private flpStarted As Boolean
End Sub

Sub Service_Create
	'This is the program entry point.
	'This is a good place to load resources that are not specific to a single activity.
	FLP.Initialize("flp")
	FLP.Connect
End Sub

Sub Service_Start (StartingIntent As Intent)
	Service.StopAutomaticForeground 'Starter service can start in the foreground state in some edge cases.
End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy
	StopFLP
End Sub


Sub flp_ConnectionSuccess
	Log("Connected to location provider")
End Sub

Sub flp_ConnectionFailed(ConnectionResult1 As Int)
	Log("Failed to connect to location provider")
End Sub


Public Sub StartFLP
	Do While FLP.IsConnected = False
		Sleep(1000)
	Loop
	If flpStarted = False Then
		FLP.RequestLocationUpdates(CreateLocationRequest)
		flpStarted = True
	End If
End Sub


Private Sub flp_LocationChanged (Location1 As Location)
	CallSub2(Weather, "LocationChanged", Location1)
End Sub

Private Sub CreateLocationRequest As LocationRequest
	Dim lr As LocationRequest
	lr.Initialize
	lr.SetInterval(10000)
	lr.SetFastestInterval(lr.GetInterval / 2)
	lr.SetPriority(lr.Priority.PRIORITY_HIGH_ACCURACY)
	Return lr
End Sub

Public Sub StopFLP
	If flpStarted Then
		FLP.RemoveLocationUpdates
		flpStarted = False
	End If
End Sub

