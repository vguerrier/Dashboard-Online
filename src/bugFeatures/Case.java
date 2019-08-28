package bugFeatures;
import java.util.ArrayList;
import java.util.Date;

//classe Case, objets des cases de CRM
public class Case {
	
	public String caseNumber;               		
	public String customerName;
	public Short scorePriority;
	public String functionalDomain;      
	public String status;
	//public String CQcard;
	public String module;
	public String priority;
	public Date EDD ;
	public String Owner;
	public String assignedGroup;
	public String VTP;
	public String title;
	public String workstream;
	public Date creationDate;
	public Date lastModificationDate;
	public String customerCaseNumber;
	public String production;
	public String CQassignedTo;
	public String CQlabel;
	public String CQstate;
	public String assignedPerson;
	public Short rejectedCount;
	public Date ECD;
	public String targetPatch;
	public String IncID;
	public String version;
	public String clientnumber;
	
	public ArrayList<Card> cardList = new ArrayList<Card>();
       	  
	//Constructeur
	public Case (String theCaseNumber, String theCustomerName, Short theScorePriority, String theFunctionalDomain, String theStatus,
			String theCQcard, String theModule, String thePriority, Date TheEDD, String theOwner, String theassignedGroup, String theVTP, 
			String thetitle, String theworkstream, Date thecreationDate, Date thelastModificationDate, String thecustomerCaseNumber, 
			String theproduction, String theCQassignedTo, String theCQlabel, String theCQstate, String theassignedPerson, 
			Short therejectedCount, Date theECD, String thetargetPatch, String theIncID, String theversion, String theclientnumber ) {
		caseNumber = theCaseNumber;
		customerName = theCustomerName;
		scorePriority= theScorePriority;
		functionalDomain = theFunctionalDomain;
		status = theStatus;
		Card theCard = new Card(theCQcard);
		cardList.add(theCard);
		module = theModule;
		priority = thePriority;
		EDD = TheEDD;
		Owner=theOwner;
		assignedGroup=theassignedGroup;
		VTP=theVTP;
		title=thetitle;
		workstream=theworkstream;
		creationDate=thecreationDate;
		lastModificationDate=thelastModificationDate;
		customerCaseNumber=thecustomerCaseNumber;
		production=theproduction;
		CQassignedTo=theCQassignedTo;
		CQlabel=theCQlabel;
		CQstate=theCQstate;
		assignedPerson=theassignedPerson;
		rejectedCount=therejectedCount;
		ECD=theECD;
		targetPatch=thetargetPatch;
		IncID=theIncID;
		version=theversion;
		clientnumber=theclientnumber;
		
	}
	
	public boolean constains(Card theCard) {
		return this.cardList.contains(theCard);
	}
	
	public void addCard(Card theCard) {
		cardList.add(theCard);
	}
	
			
	//Affiche les données de l'objets
	public void show() {
		if (caseNumber == "CAS-150708-Z6Z0") {
				int toto = 0;
				int titi =0;
				toto = titi;
				titi = toto;
		}
		//System.out.println("\n");
		System.out.println("caseNumber : "+caseNumber);
		//System.out.println(customerName);
		//System.out.println(scorePriority);
		//System.out.println(functionalDomain);
		//System.out.println(status);
		for (Card theCard : cardList) {
			theCard.show();
			//System.out.println("CQCard : "+ theCard.numerofiche);
		}
		//System.out.println(module);
		//System.out.println(priority);
		//System.out.println(EDD);
	}
}
