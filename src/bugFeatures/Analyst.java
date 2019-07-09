package bugFeatures;

public class Analyst {
	
	public String  Country;
    public String  Last_name;
    public String  First_name;
    public String  Company;
    public String  Manager;
    public String  Job;
    public String  Team;
    public String  TeamStartDate;
    public String  TeamEndDate;
    public String  AXname;
    public String  RHStartDate;
    public String  RHEndDate;
    public String  LDAPName;
    public String  City;
    public String  Gender;
    public String  ClearQuestLogin;
    public String  Email;

    //Constructor
    public Analyst  (String theCountry, String theLast_name, String theFirst_name, String theCompany, String theManager, 
    		String theJob, String theTeam, String theTeamStartDate, String theTeamEndDate, String theAXname, String theRHStartDate, 
    		String theRHEndDate, String theLDAPName, String theCity, 
    		String theGender, String theClearQuestLogin, String theEmail) {
    	
    	Last_name = theLast_name;
    	
    	First_name = theFirst_name;
    	Company = theCompany;
    	Country = theCountry;
    	City = theCity;
    	Manager = theManager;
    	Job = theJob;
    	Team = theTeam;
    	TeamStartDate = theTeamStartDate;
    	TeamEndDate = theTeamEndDate;
    	AXname = theAXname;
    	RHStartDate = theRHStartDate;
    	RHEndDate = theRHEndDate;
    	LDAPName = theLDAPName;
    	Gender = theGender;
    	ClearQuestLogin = theClearQuestLogin;
    	Email = theEmail;

    }
}
