package main;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import writer.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import bugFeatures.Analyst;
import bugFeatures.Bug;
import bugFeatures.Card;
import database.CRM;
import database.ClearQuest;

import java.util.Set;

import jxl.write.WriteException;




public class Main {
	
	//list des bugs
	public static HashMap<String, Bug> bugList = new HashMap<>();
	public static ArrayList<Card> cardList = new ArrayList<Card>();
	public static HashMap<String, Card> cardListHM = new HashMap<>();
	public static HashMap<String, Analyst> AnalystList = new HashMap<>();
	public static Date dateDebutCRM;
	public static int nb1 =0;
	public static int nb2 =0;

	
	
	public static int Analysts() {
		int rowCount = 0;
		
		//connection et exécution de la requête pour récupérer la liste des analystes
		System.out.println("RDTools Analyst Request starts");
		ClearQuest RDTRequestA = new ClearQuest();
		RDTRequestA.dbConnectRDTools();
		RDTRequestA.readCQ(5,"");
		ResultSet rsRDTA = RDTRequestA.getRS();
		rowCount = 0;
		try {
			if(rsRDTA.last()){
			    rowCount = rsRDTA.getRow(); 
			    rsRDTA.beforeFirst();
			}
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		//parcours et Construction des GCENTs
		//parcours des fiches Opens
		try {
			System.out.println("RDTool Analyst Data read starts");
			while (rsRDTA.next()) {

				Analyst oneAnalyst = new Analyst( rsRDTA.getString(1), rsRDTA.getString(2), rsRDTA.getString(3), rsRDTA.getString(4), rsRDTA.getString(5),
						rsRDTA.getString(6), rsRDTA.getString(7), rsRDTA.getString(8), rsRDTA.getString(9), rsRDTA.getString(10), rsRDTA.getString(11),
						rsRDTA.getString(12), rsRDTA.getString(13), rsRDTA.getString(14), rsRDTA.getString(15), rsRDTA.getString(16), rsRDTA.getString(17));

				if (AnalystList.get(oneAnalyst.ClearQuestLogin) == null) {
					AnalystList.put(oneAnalyst.ClearQuestLogin, oneAnalyst);

				}
				
			}
			System.out.println("CQ Open Data read finihed, cards created");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RDTRequestA.connectionClose();
		
		//Constitution de la liste HashMap des analystes
		System.out.println("RDTools Request finished : " + rowCount);

		return rowCount;
	}

	//parcours des cases
	//on recherche la liste des cases en intérrogant CRM
	static int CRMCases() {

			ResultSet rsCRM;
			CRM CRMRequest;
			int rowCount = 0;
			
			//connection et exécution de la requête CRM
			System.out.println("CRM Request starts");
			dateDebutCRM = new Date (System.currentTimeMillis ()); //Relever l'heure avant le debut du progamme (en milliseconde)
			CRMRequest = new CRM();
			CRMRequest.dbConnect();
			CRMRequest.readCRM();
			rsCRM = CRMRequest.getRS();
			rowCount = 0;

			try {
				if(rsCRM.last()){
				    rowCount = rsCRM.getRow(); 
				    rsCRM.beforeFirst();
				}
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			System.out.println("CRM Request finished : " + rowCount);
			
			
			nb1=0;
			//parcours et Construction des bugs/Case
			Bug oneBug = null;
			//variable pour déterminer si le case a déjà été ajouté, si oui on y ajoute la Gcent.
			Bug previousBug = null;
			String cardNumber = "";
			String caseNumber;
			
			try {
				System.out.println("CRM Cases Data read starts");
				Date dateDebutCQInfo = null;
				
				while (rsCRM.next()) {
					//On crée le bug (et donc case)
					oneBug = new Bug(rsCRM);
					cardNumber = null;
					if (oneBug.theCase.caseNumber.contentEquals("CAS-142213-Q8N5")) {
						//System.out.println ("Found !");
					}
					//on récupère l'objet Card créée uniquement avec la variable cardNumber (les autres champs sont null)
					Card oneCard = oneBug.theCase.cardList.get(0);
					//on récupère le numéro de la fiche
					if (oneCard.numerofiche != "" && oneCard.numerofiche != null) 
						cardNumber = oneCard.numerofiche;
					//on le recherche dans la liste des GCENT cardListHM
					//on récupère la fiche dans cardListHM
					Card oneCard2 = cardListHM.get(cardNumber);
					//Si on l'a trouvé, on enrichit la fiche du case.
					nb1++;
					if (nb1==210) {
						nb1 = nb1;
					}
					if (oneCard2 != null) {
						oneBug.theCase.cardList.remove(0);
						oneBug.addCard(oneCard2);
						oneCard = oneCard2;
						//oneBug.theCase.cardList.get(0) = oneCard;
					}
					else {
						//Si on a toujours pas trouvé on fait une requete dans CQ pour avoir les infos
						//connection et exécution de la requête ClearQuest
						
						if (cardNumber != "" && cardNumber != null) {
							nb2++;
							dateDebutCQInfo = new Date (System.currentTimeMillis ()); //Relever l'heure avant le debut du progamme (en milliseconde)
							
							ClearQuest CQRequestGetInfo = new ClearQuest();
							CQRequestGetInfo.dbConnect();
							CQRequestGetInfo.readCQ(4,cardNumber);
							ResultSet rsCQGetInfo = CQRequestGetInfo.getRS();
							rsCQGetInfo.next();
							cardNumber = rsCQGetInfo.getString(1);
							caseNumber = rsCQGetInfo.getString(5);
							oneCard = new Card(cardNumber, rsCQGetInfo.getInt(2), rsCQGetInfo.getString(3), rsCQGetInfo.getString(4), caseNumber,
									rsCQGetInfo.getDate(6), rsCQGetInfo.getDate(7), rsCQGetInfo.getString(8), rsCQGetInfo.getString(9), rsCQGetInfo.getString(10),
									rsCQGetInfo.getString(11), rsCQGetInfo.getString(12), rsCQGetInfo.getString(13), rsCQGetInfo.getString(14),
									rsCQGetInfo.getString(15), rsCQGetInfo.getShort(16), rsCQGetInfo.getDate(17), rsCQGetInfo.getDate(18), rsCQGetInfo.getString(19),
									rsCQGetInfo.getShort(20), rsCQGetInfo.getString(21), rsCQGetInfo.getString(22), rsCQGetInfo.getShort(23),
									rsCQGetInfo.getString(24));
							CQRequestGetInfo.connectionClose();
							
						} 
					}
					previousBug = bugList.get(oneBug.theCase.caseNumber);
					
					if (previousBug == null) {
						bugList.put(oneBug.theCase.caseNumber, oneBug);
					}
					else
						previousBug.addCard(oneCard);
					//System.out.println(" contain : " + bugList.contains(previousBug));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("CRM Cases Data read finished, Bug and cases created");
			
			
			CRMRequest.connectionClose();
			return rowCount;
}
	
	//connection et exécution de la requête ClearQuest
	//on recherche la liste des fiches en intérrogant CQ GCENT Open
	static int GCOpens() {

		int rowCount = 0;
		ResultSet rsCQGCO;
		
		System.out.println("CQ Open Request starts");
		ClearQuest CQRequestGCO = new ClearQuest();
		CQRequestGCO.dbConnect();
		CQRequestGCO.readCQ(1,"");
		rsCQGCO = CQRequestGCO.getRS();
		rowCount = 0;
		try {
			if(rsCQGCO.last()){
			    rowCount = rsCQGCO.getRow(); 
			    rsCQGCO.beforeFirst();
			}
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		System.out.println("CQ Open Request finished : " + rowCount);
		
		//parcours et Construction des GCENTs
		//parcours des fiches Opens
		try {
			System.out.println("CQ Open Data read starts");
			while (rsCQGCO.next()) {
				String cardNumber = rsCQGCO.getString(1);
				String caseNumber = rsCQGCO.getString(5);
				Card oneCard = new Card(cardNumber, rsCQGCO.getInt(2), rsCQGCO.getString(3), rsCQGCO.getString(4), caseNumber,
						rsCQGCO.getDate(6), rsCQGCO.getDate(7), rsCQGCO.getString(8), rsCQGCO.getString(9), rsCQGCO.getString(10),
						rsCQGCO.getString(11), rsCQGCO.getString(12), rsCQGCO.getString(13), rsCQGCO.getString(14),
						rsCQGCO.getString(15), rsCQGCO.getShort(16), rsCQGCO.getDate(17), rsCQGCO.getDate(18), rsCQGCO.getString(19),
						rsCQGCO.getShort(20), rsCQGCO.getString(21), rsCQGCO.getString(22), rsCQGCO.getShort(23),
						rsCQGCO.getString(24));

				if (cardListHM.get(cardNumber) == null) {
					cardListHM.put(cardNumber, oneCard);

				}
				
			}
			System.out.println("CQ Open Data read finihed, cards created");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		CQRequestGCO.connectionClose();
		return rowCount;
	}
	
	//connection et exécution de la requête ClearQuest
	//on recherche la liste des fiches en intérrogant CQ GCENT Closed
	static int GCClosed() {
		int rowCount = 0;
		
		System.out.println("CQ Closed Request starts");
		ClearQuest CQRequestGCC = new ClearQuest();
		CQRequestGCC.dbConnect();
		CQRequestGCC.readCQ(2,"");
		ResultSet rsCQGCC = CQRequestGCC.getRS();
		rowCount = 0;
		try {
			if(rsCQGCC.last()){
			    rowCount = rsCQGCC.getRow(); 
			    rsCQGCC.beforeFirst();
			}
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		//parcours des fiches Closed
		try {
			
			System.out.println("CQ Closed Data read starts");
			while (rsCQGCC.next()) {
				String cardNumber = rsCQGCC.getString(1);
				String caseNumber = rsCQGCC.getString(5);
				Card oneCard = new Card(cardNumber, rsCQGCC.getInt(2), rsCQGCC.getString(3), rsCQGCC.getString(4), caseNumber,
						rsCQGCC.getDate(6), rsCQGCC.getDate(7), rsCQGCC.getString(8), rsCQGCC.getString(9), rsCQGCC.getString(10),
						rsCQGCC.getString(11), rsCQGCC.getString(12), rsCQGCC.getString(13), rsCQGCC.getString(14),
						rsCQGCC.getString(15), rsCQGCC.getShort(16), rsCQGCC.getDate(17), rsCQGCC.getDate(18), rsCQGCC.getString(19),
						rsCQGCC.getShort(20), rsCQGCC.getString(21), rsCQGCC.getString(22), rsCQGCC.getShort(23),
						rsCQGCC.getString(24));

				if (cardListHM.get(cardNumber) == null) {
					cardListHM.put(cardNumber, oneCard);

				}
			}
			System.out.println("CQ Closed Data read finihed, cards created");
				// System.out.println(rs.getString(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("CQ Closed Request finished : " + rowCount);
		CQRequestGCC.connectionClose();
		return rowCount;
	}
	

	//connection et exécution de la requête ClearQuest
	//on recherche la liste des fiches en intérrogant CQ GCENT Interne
	static int GCIntern() {

		int rowCount = 0;
		
		System.out.println("CQ Internal Request starts");
		ClearQuest CQRequestGCI = new ClearQuest();
		CQRequestGCI.dbConnect();
		CQRequestGCI.readCQ(3,"");
		ResultSet rsCQGCI = CQRequestGCI.getRS();
		rowCount = 0;
		try {
			if(rsCQGCI.last()){
			    rowCount = rsCQGCI.getRow(); 
			    rsCQGCI.beforeFirst();
			}
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		//parcours des fiches Internes
		try {
			System.out.println("CQ Internal Data read starts");
			while (rsCQGCI.next()) {
				String cardNumber = rsCQGCI.getString(1);
				String caseNumber = rsCQGCI.getString(5);
				Card oneCard = new Card(cardNumber, rsCQGCI.getInt(2), rsCQGCI.getString(3), rsCQGCI.getString(4), caseNumber,
						rsCQGCI.getDate(6), rsCQGCI.getDate(7), rsCQGCI.getString(8), rsCQGCI.getString(9), rsCQGCI.getString(10),
						rsCQGCI.getString(11), rsCQGCI.getString(12), rsCQGCI.getString(13), rsCQGCI.getString(14),
						rsCQGCI.getString(15), rsCQGCI.getShort(16), rsCQGCI.getDate(17), rsCQGCI.getDate(18), rsCQGCI.getString(19),
						rsCQGCI.getShort(20), rsCQGCI.getString(21), rsCQGCI.getString(22), rsCQGCI.getShort(23),
						rsCQGCI.getString(24));

				if (cardListHM.get(cardNumber) == null) {
					cardListHM.put(cardNumber, oneCard);

				}
			}
			System.out.println("CQ Internal Data read finihed, cards created");
				// System.out.println(rs.getString(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("CQ Internal Request finished : " + rowCount);
		CQRequestGCI.connectionClose();
		return rowCount;
	}
	
	static int GCTeam() {
		//connection et exécution de la requête ClearQuest
		//on recherche la liste des fiches en intérrogant CQ GCENT Team
		int rowCount = 0;
		
		System.out.println("CQ Team Request starts");
		ClearQuest CQRequestGCT = new ClearQuest();
		CQRequestGCT.dbConnect();
		CQRequestGCT.readCQ(5,"");
		ResultSet rsCQGCT = CQRequestGCT.getRS();
		rowCount = 0;
		try {
			if(rsCQGCT.last()){
			    rowCount = rsCQGCT.getRow(); 
			    rsCQGCT.beforeFirst();
			}
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		CQRequestGCT.connectionClose();
		System.out.println("CQ Team Request finished : " + rowCount);
		return rowCount;
	}
	
	public static void main(String[] args) {

		//WriteExcel xls = new WriteExcel();
		
		System.out.println("Batch started");
		Date dateDebut = new Date (System.currentTimeMillis ()); //Relever l'heure avant le debut du progamme (en milliseconde) 
		
		
		//constitutions de la liste des analystes maintenance
		Analysts();
		//connection et exécution de la requête ClearQuest
		//on recherche la liste des fiches en intérrogant CQ GCENT Open
		GCOpens();
		//connection et exécution de la requête ClearQuest
		//on recherche la liste des fiches en intérrogant CQ GCENT Closed
		GCClosed();
		//connection et exécution de la requête ClearQuest
		//on recherche la liste des fiches en intérrogant CQ GCENT Interne
		GCIntern();
		//parcours des cases
		//on recherche la liste des cases en intérrogant CRM
		//parcours et Construction des bugs/Case
		CRMCases();		
		

		//afficahge de la liste des bugs
//		Set<Entry<String, Bug>> setHm = bugList.entrySet();
//	    Iterator<Entry<String, Bug>> it = setHm.iterator();
//		while (it.hasNext()) {
//			Entry<String, Bug> e = it.next();
//			System.out.println(e.getKey() + " : \n");
//			Bug c = (Bug) e.getValue();
//			// bugList.indexOf();
//			c.theCase.show();
//		}
//		for (Bug theBug : bugList) {
//			 theBug.theCase.show();
//		}
		
//		try {
//			rsCRM.next();
//			oneBug = new Bug(rsCRM);
//			bugList.put(oneBug.theCase.caseNumber, oneBug);
//			
//			while (rsCRM.next()) {
//				previousBug = oneBug;
//				oneBug = new Bug(rsCRM);
//				bugList.put(oneBug.theCase.caseNumber, oneBug);
//				//System.out.println(" contain : " + bugList.contains(previousBug));
//				
//				if (bugList.contains(previousBug)) {
//					//le bug précédent est le même (même case number)
//					//on ajoute la CQCard au Bug
//					previousBug.addCard(oneBug.theCase.cardList.get(0));
//					oneBug = previousBug;
//				}
//				else bugList.put(oneBug);
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		//afficahge de la liste des bugs
//		for (Bug theBug : bugList) {
//			theBug.theCase.show();
//		}
		
		// calcul du temps d'exécution de la requete
		Date dateFinCRM = new Date (System.currentTimeMillis()); //Relever l'heure a la fin du progamme (en milliseconde) 
		Date dureeCRM = new Date (System.currentTimeMillis()); //Pour calculer la différence
		dureeCRM.setTime (dateFinCRM.getTime () - dateDebutCRM.getTime ());  //Calcul de la différence
		long secondesCRM = dureeCRM.getTime () / 1000;
		long minCRM = secondesCRM / 60;
		long heuresCRM = minCRM / 60;
		long miliCRM = dureeCRM.getTime () % 1000;
		secondesCRM %= 60;

		//export Excel
		WriteExcel dashBoardXls = null;
		try {
			dashBoardXls = new WriteExcel("C:\\Users\\guerrier\\Desktop\\Dashboard Java\\Workspace\\DashboardBatch\\dashboard.xls");
		} catch (WriteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	        
		
		//afficahge de la liste des bugs
		Set<Entry<String, Bug>> setHm = bugList.entrySet();
	    Iterator<Entry<String, Bug>> it = setHm.iterator();
	    int line = 1;
	    try {
			while (it.hasNext()) {
				Entry<String, Bug> e = it.next();
				//System.out.println(e.getKey() + " : \n");
				Bug c = (Bug) e.getValue();
				
				line = dashBoardXls.write(c, line);
				line ++;
				
				// bugList.indexOf();
//				if (c.theCase.caseNumber.contentEquals("CAS-142213-Q8N5")) {
//					System.out.println (" ");
//					c.theCase.show();
//				}
	
			}
			dashBoardXls.closeExcel();
	    } catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//afficahge de la liste des bugs
		 //Parcours de l'objet HashMap
//	      Set<Entry<String, Card>> setHmCQ = cardListHM.entrySet();
//	      Iterator<Entry<String, Card>> itCQ = setHmCQ.iterator();
//	      while(itCQ.hasNext()){
//	         Entry<String, Card> e = itCQ.next();
//	         //System.out.println(e.getKey() + " : \n");
//	         Card c = (Card) e.getValue();
//	         //bugList.indexOf();
//	         c.show();
//	      }
		
		/*for (Card theCard : cardList) {
			theCard.show();
		}*/
		
		Date dateFin = new Date (System.currentTimeMillis()); //Relever l'heure a la fin du progamme (en milliseconde) 
		Date duree = new Date (System.currentTimeMillis()); //Pour calculer la différence
		duree.setTime (dateFin.getTime () - dateDebut.getTime ());  //Calcul de la différence
		long secondes = duree.getTime () / 1000;
		long min = secondes / 60;
		long heures = min / 60;
		long mili = duree.getTime () % 1000;
		secondes %= 60;
		int bugListNB = bugList.size();
		System.out.println ("durée CRM : \n Nb : " + bugListNB+ " \n" + heuresCRM + "h" + minCRM + "mn" + secondesCRM + "s" + miliCRM + "ms\n");
		System.out.println ("durée Cards : \n Nb : " + cardListHM.size()+ "\\" + nb1 + "\\" + nb2 +" \n" + heures + "h" + min + "mn" + secondes + "s" + mili + "ms\n");
		
		//association de la GCENT au Case correspondant.
		//parcours des case
//		for (Bug theBug : bugList) {
//			
//		}
		//update Cat case à traiter et construction des objets cases
		//UpdateCAT


		//"sqlsrv:server=seyccrmsqlsip1 ; Database=crm_MSCRM", "", ""
		//jdbc:sqlserver://seyccrmsqlsip1;databaseName=crm_MSCRM;integratedSecurity=true;
		
	    //WsExe.Cells(9, 2) = "16%"
		//UpdateGREP update des Gcent de reports
		//WsExe.Cells(9, 2) = "32%"
			    //Update
				//'UpdateGOUT
			    //WsExe.Cells(9, 2) = "48%"
			    //UpdateGOPEN
			    //WsExe.Cells(9, 2) = "55%"
			    //UpdateGCLOSED
			    //WsExe.Cells(9, 2) = "66%"
			    //TeamRequest
			    //WsExe.Cells(9, 2) = "78%"
			    //WsExe.Activate
			    //UpdateGINT
			    //WsExe.Cells(9, 2) = "88%"
			    //UpdateTTC
			    //WsExe.Activate
			    //WsExe.Cells(9, 2) = "90%"
			    //UpdateCUST
			    //WsExe.Cells(9, 2) = "93%"
			    //UpdateTeamSP
			    //WsExe.Cells(9, 2) = "97%"
			    //UpdateDomains
			    //WsExe.Cells(9, 2) = "100%"
	}
	

}
