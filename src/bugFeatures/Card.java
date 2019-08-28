package bugFeatures;
import java.util.Date;

public class Card {

	public String 	numerofiche;
	public Integer 	dbid;																								
	public String 	titre;                                                
	public String 	status;                                                 
	public String 	casenumber;                                         
	public Date 	datePlanningLatest;                      
	public Date 	datePlanningEarliest;                         
	public String 	label;                                                
	public String 	id;                                                   
	public String 	rfeId;                                                
	public String 	typeReference;                                        
	public String 	client;                                                 
	public String 	domainFunctional;                                                 
	public String 	loginName;                                          
	public String 	productName;                                         
	public Short 	retrofitCard;                                    
	public Date 	openingDate;
	public Date 	ECD;                               
	public String 	severity;                  
	public Short 	age;       
	public String 	subProduct;																			
	public String 	internalState;                                        
	public Short 	regression;                                          
	public String 	regressionCause;                        
	
	public Card(String 	theNumerofiche, 
			Integer theDbid, 																								
			String 	theTitre,                                                 
			String 	theStatus,                                                  
			String 	theCasenumber,                                          
			Date 	theDatePlanningLatest,                       
			Date 	theDatePlanningEarliest,                          
			String 	theLabel,                                                 
			String 	theId,                                                    
			String 	theRfeId,                                                 
			String 	theTypeReference,                                         
			String 	theClient,                                                  
			String 	theDomainFunctional,                                                  
			String 	theLoginName,                                           
			String 	theProductName,                                          
			Short 	theRetrofitCard,                                     
			Date 	theOpeningDate, 
			Date 	theECD,                                
			String 	theSeverity,                   
			Short 	theAge,        
			String 	theSubProduct, 																			
			String 	theInternalState,                                         
			Short 	theRegression,                                           
			String 	theRegressionCause) {
		
		
		numerofiche = theNumerofiche;
		dbid = theDbid;
		titre = theTitre;
		status = theStatus;
		casenumber = theCasenumber;
		datePlanningLatest = theDatePlanningLatest;
		datePlanningEarliest = theDatePlanningEarliest;
		label = theLabel;
		id = theId;
		rfeId = theRfeId;
		typeReference = theTypeReference;
		client = theClient;
		domainFunctional = theDomainFunctional;
		loginName = theLoginName;
		productName = theProductName;
		retrofitCard = theRetrofitCard;
		openingDate = theOpeningDate;
		ECD = theECD;
		severity = theSeverity;
		age = theAge;
		subProduct = theSubProduct;
		internalState = theInternalState;
		regression = theRegression;
		regressionCause = theRegressionCause;
		
	}
	
	public Card(String 	theNumerofiche) {
		numerofiche = theNumerofiche; 
		dbid = null;
		titre = null;
		status = null;
		casenumber = null;
		datePlanningLatest = null;
		datePlanningEarliest = null;
		label = null;
		id = null;
		rfeId = null;
		typeReference = null;
		client = null;
		domainFunctional = null;
		loginName = null;
		productName = null;
		retrofitCard = 0;
		openingDate = null;
		ECD = null;
		severity = null;
		age = 0;
		subProduct = null;
		internalState = null;
		regression = 0;
		regressionCause = null;

	}
		
	
	public void show() {
		
//		System.out.println("\n"); 
		System.out.println("numerofiche : "+ numerofiche);                 
//		System.out.println(dbid);														
//		System.out.println(titre);                          
		System.out.println("status :" + status);                         
		System.out.println("casenumber : " + casenumber);                     
//		System.out.println(datePlanningLatest);             
//		System.out.println(datePlanningEarliest);           
//		System.out.println(label);                          
//		System.out.println(id);                             
//		System.out.println(rfeId);                          
//		System.out.println(typeReference);                  
//		System.out.println(client);                         
//		System.out.println(domainFunctional);               
//		System.out.println(loginName);                      
//		System.out.println(productName);                    
//		System.out.println(retrofitCard);                   
//		System.out.println(openingDate);                    
//		System.out.println(ECD);                            
//		System.out.println(severity);                       
//		System.out.println(age);                            
//		System.out.println(subProduct);											
//		System.out.println(internalState);                  
//		System.out.println(regression);                     
//		System.out.println(regressionCause); 
	}
}
