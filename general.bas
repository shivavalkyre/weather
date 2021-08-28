B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=11
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub DayName(weekday As Int) As String
	Select weekday
		Case 1
			Return "Sunday"
		Case 2
			Return "Monday"
		Case 3
			Return "Tuesday"
		Case 4
			Return "Wednesday"
		Case 5
			Return "Thursday"
		Case 6
			Return "Friday"
		Case 7
			Return "Saturday"
	End Select
End Sub


Sub MonthName(month As Int) As String
	Select month
		Case 1
			Return "January"
		Case 2
			Return "February"
		Case 3
			Return "March"
		Case 4
			Return "April"
		Case 5
			Return "May"
		Case 6
			Return "June"
		Case 7
			Return "July"
		Case 8
			Return "August"
		Case 9
			Return "September"
		Case 10
			Return "October"
		Case 11
			Return "November"
		Case 12
			Return "December"
	End Select
End Sub