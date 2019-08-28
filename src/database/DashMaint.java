package database;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import bugFeatures.Bug;
import bugFeatures.Card;

public class DashMaint {

	private String requete;
	private Connection conn;
	private ResultSet rs;
	private String db_connect_string2 = "jdbc:oracle:thin:@RDTOOLS";
	//OLEDB;Provider=OraOLEDB.Oracle.1;Password=READCQUEST;User ID=READCQUEST;Data Source=CQSCM1_SEYCSMC1
	private static final String SQL_INSERT_CARDS ="insert into CARDS " +
			"(Numerofiche,			\r\n" + 
			"Dbid,					\r\n" + 
			"Titre,					\r\n" + 
			"Status,				\r\n" + 
			"Casenumber,			\r\n" + 
			"DatePlanningLatest,	\r\n" + 
			"DatePlanningEarliest,	\r\n" + 
			"Label,					\r\n" + 
			"Id,					\r\n" + 
			"RfeId,					\r\n" + 
			"TypeReference,			\r\n" + 
			"Client,				\r\n" + 
			"DomainFunctional,		\r\n" + 
			"LoginName,				\r\n" + 
			"ProductName,			\r\n" + 
			"RetrofitCard,			\r\n" + 
			"OpeningDate,			\r\n" + 
			"ECD,					\r\n" + 
			"Severity,				\r\n" + 
			"Age,					\r\n" + 
			"SubProduct,			\r\n" + 
			"InternalState,			\r\n" + 
			"Regression,			\r\n" + 
			"RegressionCause)		\r\n" +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_INSERT_CASETOCARD ="insert into CASETOCARD (CASEID, CARDID) values (?,?)";
	private static final String SQL_INSERT_CASES = "insert into Cases \r\n" + 
			"(caseNumber,           \r\n" + 
			"customerName,         \r\n" + 
			"scorePriority,        \r\n" + 
			"functionalDomain,     \r\n" + 
			"status,               \r\n" + 
			//"CQcard,             \r\n" + 
			"module,               \r\n" + 
			"priority,             \r\n" + 
			"EDD ,                 \r\n" + 
			"Owner,                \r\n" + 
			"assignedGroup,        \r\n" + 
			"VTP,                  \r\n" + 
			"title,                \r\n" + 
			"workstream,           \r\n" + 
			"creationDate,         \r\n" + 
			"lastModificationDate, \r\n" + 
			"customerCaseNumber,   \r\n" + 
			"production,           \r\n" + 
			"CQassignedTo,         \r\n" + 
			"CQlabel,              \r\n" + 
			"CQstate,              \r\n" + 
			"assignedPerson,       \r\n" + 
			"rejectedCount,        \r\n" + 
			"ECD,                  \r\n" + 
			"targetPatch,          \r\n" + 
			"IncID,                \r\n" + 
			"VERSION,              \r\n" + 
			"clientnumber)			\r\n" + 
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_TRUNC_CASES = "delete from CASES \r\n";
	private static final String SQL_TRUNC_CARDS = "delete from CARDS \r\n";
	private static final String SQL_TRUNC_CASETOCARD = "delete from CASETOCARD CASCADE \r\n";

	
	public void dbConnectRDTools() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
			conn = null;
			conn = DriverManager.getConnection(db_connect_string2,"DASHMAINT","DASHMAINT");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error DashMaint.dbConnectRDTools : "+e.toString());
			e.printStackTrace();
		}
	}

	public void truncateTables() {
		try {
			Statement stTruncate = conn.createStatement();
			stTruncate.executeUpdate(SQL_TRUNC_CASETOCARD);
			stTruncate.executeUpdate(SQL_TRUNC_CASES);
			stTruncate.executeUpdate(SQL_TRUNC_CARDS);
			
		} catch (SQLException e1) {
			System.out.println("Error DashMaint.truncateTables : "+e1.toString());
			e1.printStackTrace();
		}
	}
	
	public boolean InsertCard (Card theCard, PreparedStatement PSCards, ArrayList<String> cardList) {
		
		boolean found = false;
		try {
			if (theCard.numerofiche != null) {
				if (theCard.numerofiche != null && theCard.numerofiche.contentEquals("GCENT19040364")) {
					//System.out.println("toto");
				}
				//Insertion dans CARDS
				//insertion dasn le tableau pour checker les doublons
				String value = null;
				//on check si doublon uniquement dans le cas d'insertion de fiches de cases, si interne, pas besoin, il ne peut pas y avoir de doublons.
				if (cardList != null) {
					Iterator<String> iter = cardList.iterator();
					
					found = false;
					
					while (iter.hasNext() && !found) {
					     
					     if (iter.next() == theCard.numerofiche) {
					    	 value = iter.next();
					    	 found = true;
					     }
					     
					}
				}
				
				
				if (value == null) {
					if (cardList == null) {
						// cas des fiches internes
						//Pas de numéro de case, donc : Intern
						PSCards.setString(5,"Intern");
					}
					else {
						//cas des fiches liées à un case
						cardList.add(theCard.numerofiche);
						PSCards.setString(5,theCard.casenumber);
					}
					PSCards.setString(1,theCard.numerofiche);
					if (theCard.dbid !=null) {
						PSCards.setInt(2,theCard.dbid);
					}
					else
						PSCards.setInt(2,0);
					PSCards.setString(3,theCard.titre);
					PSCards.setString(4,theCard.status);
					
					if (theCard.datePlanningLatest != null) {
						PSCards.setTimestamp(6,new java.sql.Timestamp(theCard.datePlanningLatest.getTime()));
					}
					else
						PSCards.setTimestamp(6,null);
					if (theCard.datePlanningEarliest != null) {
						PSCards.setTimestamp(7,new java.sql.Timestamp(theCard.datePlanningEarliest.getTime()));
					}
					else
						PSCards.setTimestamp(7,null);
					PSCards.setString(8,theCard.label);
					PSCards.setString(9,theCard.id);
					PSCards.setString(10,theCard.rfeId);
					PSCards.setString(11,theCard.typeReference);
					PSCards.setString(12,theCard.client);
					PSCards.setString(13,theCard.domainFunctional);
					PSCards.setString(14,theCard.loginName);
					PSCards.setString(15,theCard.productName);
					if (theCard.retrofitCard !=null) {
						PSCards.setShort(16,theCard.retrofitCard);
					}
					else
						PSCards.setShort(16,(short) 0);
					
					if (theCard.openingDate != null) {
						PSCards.setTimestamp(17,new java.sql.Timestamp(theCard.openingDate.getTime()));
					}
					else
						PSCards.setTimestamp(17,null);
					if (theCard.ECD != null) {
						PSCards.setTimestamp(18,new java.sql.Timestamp(theCard.ECD.getTime()));
					}
					else
						PSCards.setTimestamp(18,null);
					PSCards.setString(19,theCard.severity);
					PSCards.setShort(20,(short) theCard.age);
					PSCards.setString(21,theCard.subProduct);
					PSCards.setString(22,theCard.internalState);
					PSCards.setShort(23,theCard.regression);
					PSCards.setString(24,theCard.regressionCause);
					PSCards.addBatch();
				}
				else
					return false;
			}
			else
				return false;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return false;
	    } 
		return true;
	}
	
	//insertion des fiches internes
	public int insertDMIntern(HashMap<String, Card> cardInternListHM ) {
		PreparedStatement PSInsertCards = null;
		Set<Entry<String, Card>> setHmIntern = cardInternListHM.entrySet();
	    Iterator<Entry<String, Card>> theCardIt = setHmIntern.iterator();
		int nbInsertedCards = 0;
		
		try {
			PSInsertCards = conn.prepareStatement(SQL_INSERT_CARDS);
			while (theCardIt.hasNext()) {
				Entry<String, Card> theCardEntry = theCardIt.next();
				//System.out.println(e.getKey() + " : \n");
				Card theCard = (Card) theCardEntry.getValue();
				if (theCard.numerofiche.contentEquals("GCENT11110187")) {
					//System.out.println("GCENT11110187");
				}
				InsertCard (theCard, PSInsertCards, null);
				nbInsertedCards ++;
			}
			
			PSInsertCards.executeBatch();
			PSInsertCards.close();

		} catch (SQLException e1) {
			System.out.println("Error DashMaint.insertDMIntern : "+e1.toString());
			e1.printStackTrace();
		}
		System.out.println("DashMaint Insert Internal cards in DashMaint database CASES:"+  nbInsertedCards + " rows inserted");
		
		 return nbInsertedCards;
	}
	
	//insertion des cases et des fiches
	public int insertDM(HashMap<String, Bug> bugList ) {
		
		PreparedStatement PSInsertCases = null;
		PreparedStatement PSInsertCaseToCard = null;
		PreparedStatement PSInsertCards = null;

			try {
				PSInsertCases = conn.prepareStatement(SQL_INSERT_CASES);
				PSInsertCaseToCard = conn.prepareStatement(SQL_INSERT_CASETOCARD);
				PSInsertCards = conn.prepareStatement(SQL_INSERT_CARDS);
			} catch (SQLException e1) {
				System.out.println("Error DashMaint.insertDM : "+e1.toString());
				e1.printStackTrace();
			}
			
			
			Set<Entry<String, Bug>> setHm = bugList.entrySet();
		    Iterator<Entry<String, Bug>> theBugIt = setHm.iterator();
			//tabeau des fiches pour ne pas en insérer 2
			ArrayList<String> cardList = new ArrayList<String>();
			
		    int nbInsertedCases = 0;
		    int nbInsertedCaseToCard = 0;
		    int nbInsertedCards =0;
		    try {
				while (theBugIt.hasNext()) {
					Entry<String, Bug> theBugEntry = theBugIt.next();
					//System.out.println(e.getKey() + " : \n");
					Bug theBug = (Bug) theBugEntry.getValue();
					
					//construire requete
					PSInsertCases.setString(1 ,theBug.theCase.caseNumber);
					PSInsertCases.setString(2 ,theBug.theCase.customerName);
					PSInsertCases.setShort(3 ,theBug.theCase.scorePriority);
					PSInsertCases.setString(4 ,theBug.theCase.functionalDomain);
					PSInsertCases.setString(5 ,theBug.theCase.status);
					//PSInsertCases.setString(6 ,theBug.theCase.CQcard);
					PSInsertCases.setString(6 ,theBug.theCase.module);
					PSInsertCases.setString(7 ,theBug.theCase.priority);
					if (theBug.theCase.EDD != null) {
						PSInsertCases.setTimestamp(8 ,new java.sql.Timestamp(theBug.theCase.EDD.getTime()));
					}
					else
						PSInsertCases.setTimestamp(8 ,null);
					PSInsertCases.setString(9 ,theBug.theCase.Owner);
					PSInsertCases.setString(10 ,theBug.theCase.assignedGroup);
					PSInsertCases.setString(11 ,theBug.theCase.VTP);
					PSInsertCases.setString(12 ,theBug.theCase.title);
					PSInsertCases.setString(13 ,theBug.theCase.workstream);
					if (theBug.theCase.creationDate != null) {
						PSInsertCases.setTimestamp(14 ,new java.sql.Timestamp(theBug.theCase.creationDate.getTime()));
					}
					else
						PSInsertCases.setTimestamp(14 ,null);
					if (theBug.theCase.lastModificationDate != null) {
						PSInsertCases.setTimestamp(15 ,new java.sql.Timestamp(theBug.theCase.lastModificationDate.getTime()));
					}
					else
						PSInsertCases.setTimestamp(15 ,null);
					PSInsertCases.setString(16 ,theBug.theCase.customerCaseNumber);
					PSInsertCases.setString(17 ,theBug.theCase.production);
					PSInsertCases.setString(18 ,theBug.theCase.CQassignedTo);
					PSInsertCases.setString(19 ,theBug.theCase.CQlabel);
					PSInsertCases.setString(20 ,theBug.theCase.CQstate);
					PSInsertCases.setString(21 ,theBug.theCase.assignedPerson);

					PSInsertCases.setShort(22 ,theBug.theCase.rejectedCount);
					if (theBug.theCase.ECD != null) {
						PSInsertCases.setTimestamp(23 ,new java.sql.Timestamp(theBug.theCase.ECD.getTime()));
					}
					else
						PSInsertCases.setTimestamp(23 ,null);
					PSInsertCases.setString(24 ,theBug.theCase.targetPatch);
					PSInsertCases.setString(25 ,theBug.theCase.IncID);
					PSInsertCases.setString(26 ,theBug.theCase.version);
					PSInsertCases.setString(27 ,theBug.theCase.clientnumber);
					PSInsertCases.addBatch();
					nbInsertedCases ++;
					//insertion des fiches CQ
					//On insert dans CARDS et CASETOCARD
					//parcourt des fiche du case
					
					if (theBug.theCase.cardList.size() >0) {

						for (Card theCard : theBug.theCase.cardList) {
							//Insertion dans Cards
							if (theCard.numerofiche != null) {
								if (theCard.numerofiche.contentEquals("GCENT11110187")) {
									//System.out.println("GCENT11110187");
								}
								InsertCard (theCard, PSInsertCards, cardList);
								nbInsertedCards ++;
								//Insertion dans CASETOCARD
								PSInsertCaseToCard.setString(1, theBug.theCase.caseNumber);
								PSInsertCaseToCard.setString(2, theCard.numerofiche);
								PSInsertCaseToCard.addBatch();
								nbInsertedCaseToCard ++;
							}
								
							
						}
					}
		
				}
				//System.out.println("SQL :" + PSInsertCases);
				PSInsertCases.executeBatch();
				PSInsertCases.close();
				PSInsertCards.executeBatch();
				PSInsertCards.close();
				PSInsertCaseToCard.executeBatch();
				PSInsertCaseToCard.close();
			

		    } catch (SQLException e) {
		    	e.printStackTrace();
		    } 
		    
		    System.out.println("DashMaint Insert Bugs in DashMaint database CASES:"+  nbInsertedCases + " rows inserted");
			System.out.println("DashMaint Insert Bugs in DashMaint database CARDS:"+  nbInsertedCards + " rows inserted");
			System.out.println("DashMaint Insert Bugs in DashMaint database CASESTOCARD:"+  nbInsertedCaseToCard + " rows inserted");
			
	        return nbInsertedCases;

	}


	public void readDashMaint (int request, String gcent) {
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
			
	        
	        
	        rs = statement.executeQuery(requete);
	       
        } catch (Exception e) {
	         e.printStackTrace();
	    }
		
	}
		
	public ResultSet getRS() {
		return rs;
	}
	
	


	

	

	
	
	public void connectionClose() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error DashMaint.connectionClose : "+e.toString());
			e.printStackTrace();
		}
	}


}
