package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClearQuest {
	
	
	
	private String requete;
	private Connection conn;
	private ResultSet rs;
	private String db_connect_string = "jdbc:oracle:thin:@CQSCM1_SEYCSMC1";
	private String db_connect_string2 = "jdbc:oracle:thin:@RDTOOLS";
	//OLEDB;Provider=OraOLEDB.Oracle.1;Password=READCQUEST;User ID=READCQUEST;Data Source=CQSCM1_SEYCSMC1
	
	public void dbConnect() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
			conn = null;
			conn = DriverManager.getConnection(db_connect_string,"readcquest","readcquest");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public void dbConnectRDTools() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
			conn = null;
			conn = DriverManager.getConnection(db_connect_string2,"read_only","read_only");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readCQ (int request, String gcent) {
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 

	        switch (request)
	        {
	          case 1: //requete GCENT Open : liste des fiches 'En_Attente','Assignée', 'Ouverte' UNION corrigée depuis 120 jours
	        	  requeteGCOpen();
	            break;
	          case 2 : //requete GCENT Closed
	        	  requeteGCClosed();
	            break;
	          case 3 : //requete GCENT Interne
	        	  requeteGCInternal();
	            break;
	          case 4 : //requete GCENT Interne
	        	  requeteGetInfo(gcent);
	            break;
	          case 5 : //requete GCENT Interne
	        	  requeteGCTeam();
	            break;
	          default:
	            System.out.println("");
	        }
	        
	        
	        rs = statement.executeQuery(requete);
	       
        } catch (Exception e) {
	         e.printStackTrace();
	    }
		
	}
		
	public ResultSet getRS() {
		return rs;
	}
	
	
	public void requeteGetInfo(String gcent) {
			
		if (gcent.substring(1,1) == "'") {
				gcent = "'" + gcent;
			}
		
	    if (gcent.substring(gcent.length() - 1) == "'") {
	    	gcent = gcent + "'";
	    }
	    
	    String CQbase;
	
		CQbase = "cqcentral";
		
		if (gcent.contains("GTOP"))
		    CQbase = "cqgtop";

		if (gcent.contains("GADMI"))
		    CQbase = "cqgadmin";
	
		
		if (gcent.contains("GSTOC"))
		    CQbase = "CQGOLDSTOCK";
	
		
		
//	    requete = "select  T1.dbid,T1.titre,T5.name,T1.numeroreference,T1.dateplanifauplustard_1 + 2/24,T1.dateplanifauplustot + 2/24,T1.numerofiche,T1.label,T1.opengisdate,T1.id,1,T1.rfeid,T1.typereference,T16.nom,T3.name,T1.numeroreference,T11.login_name,T12.NOM Produit, SP.name SUBPRODUCT, " +// 
//	    		" DBMS_LOB.substr(T1.sourcesmodifies," +// 
//	    		" DBMS_LOB.getlength(T1.sourcesmodifies)) SOURCE_OR, b.nom_branch   BRANCH, T1.RETROFITCARD, " +// 
//	    		" to_char(h.action_timestamp, 'DD/MM/YY') OPENING_DATE, T1.internalstate, T1.Regressionc, T1.REGRESSIONCAUSE  from " +// 
		requete = "select T1.numerofiche, "+ //
		"T1.dbid, "+ //
		"T1.titre, "+ //
		"T5.name, "+ //
		"T1.NUMEROREFERENCE case, "+ //
		"T1.dateplanifauplustard_1 + 2 / 24, "+ //
		"T1.dateplanifauplustot + 2 / 24, "+ //
		"T1.label, "+ //
		"T1.id, "+ //
		"T1.rfeid, "+ //
		"T1.typereference, "+ //
		"T16.nom, "+ //
		"T3.name, "+ //
		"T11.login_name, "+ //
		"T12.NOM Produit, "+ //
		"T1.RETROFITCARD, "+ //
		"h.action_timestamp Opening_Date, "+ //
		"T1.expectedcorrectiondate, "+ //
		"T1.SEVERITE, "+ //
		"round(sysdate - h.action_timestamp) ageold, "+ //
		"SP.name subproduct, "+ //
		"T1.internalstate, "+ //
		"T1.Regressionc, "+ //
		"T1.REGRESSIONCAUSE, "+ //
		"T1.Reportfiche, "+//
	    "T1.Rfecode, "+ //
	    "T1.Chefprojet, "+ //
	    "T1.Urgence, "+ //
	    "h.action_name from "+ //
		CQbase + ".anomalie T1," +// 
		  CQbase + ".statedef T5," +// 
		  CQbase + ".client T16," +// 
		  CQbase + ".DOMAINRECORD T3," +// 
		  CQbase + ".users T11," +// 
		  CQbase + ".produit T12," +// 
		  CQbase + ".History      h," +// 
		  CQbase + ".subproduct   SP " +// 
		" where T1.State = T5.ID " +//
		" and T1.assigne = T11.dbid " +//
		" and T1.client_champ = T16.dbid " +//
		" and T1.Domain_1 = T3.dbid " +//
		" and T1.PRODUIT_1 = T12.dbid "+//
		" and h.entity_dbid = T1.DBID "+//
		" and T1.Subproduct = SP.DBID "+//
		" and h.action_name = 'Ouvrir' " +// 
		" and T1.numerofiche = '" + gcent + "'";

	}
	
	
	public void requeteGCTeam() {
		requete = "select t.country, t.LASTNAME, t.FIRSTNAME, t.COMPANY, t.MANAGER, t.job, t.team, t.TEAM_START, t.TEAM_END, t.AX_NAME, t.RH_START, t.RH_END, t.LDAP_NAME, t.city, t.gender, t.CLEARQUESTLOGIN, t.EMAIL "+ //
		  "from V_GLOBAL_RDRESOURCES t "+ //
		  "where t.TEAM like 'Product Support - Retai%'  "+ //
		  "and Team_end is null ";
		
	}
	
	public void requeteGCClosed() {
		//Dans Dashboard maintenance : GCENT Closed
		
		requete = "select T1.numerofiche, "+ //
				"T1.dbid, "+ //
				"T1.titre, "+ //
				"T5.name, "+ //
				"T1.NUMEROREFERENCE case, "+ //
				"T1.dateplanifauplustard_1 + 2 / 24, "+ //
				"T1.dateplanifauplustot + 2 / 24, "+ //
				"T1.label, "+ //
				"T1.id, "+ //
				"T1.rfeid, "+ //
				"T1.typereference, "+ //
				"T16.nom, "+ //
				"T3.name, "+ //
				"T11.login_name, "+ //
				"T12.NOM Produit, "+ //
				"T1.RETROFITCARD, "+ //
				" (select h.action_timestamp "+ //
				"    from CQCENTRAL.History h"+ //
				"   where h.entity_dbid = T1.DBID"+ //
				"     and h.action_name = 'Ouvrir') OPENING_DATE, "+ //
				"T1.expectedcorrectiondate, "+ //
				"T1.SEVERITE, "+ //
				"round(sysdate - h.action_timestamp) ageold, "+ //
				"SP.name subproduct, "+ //
				"T1.internalstate, "+ //
				"T1.Regressionc, "+ //
				"T1.REGRESSIONCAUSE, "+ //
				"T1.Reportfiche, "+//
			    "T1.Rfecode, "+ //
			    "T1.Chefprojet, "+ //
			    "T1.Urgence, "+ //
			    "h.action_name "+ //
				" from cqcentral.anomalie     T1,"+ //
				" cqcentral.statedef     T5,"+ //
				" cqcentral.client T16,"+ //
				" cqcentral.DOMAINRECORD T3,"+ //
				" cqcentral.history      h,"+ //
				" cqcentral.users T11,"+ //
				" cqcentral.produit      T12,"+ //
				" cqcentral.subproduct   SP"+ //
				" where T1.state = T5.id"+ //
				" and T1.assigne = T11.dbid"+ //
				" and T1.client_champ = T16.dbid"+ //
				" and T1.domain_1 = T3.dbid"+ //
				" and T1.Subproduct = SP.DBID"+ //
				" and T1.dbid <> 0"+ //
				" and T1.PRODUIT_1 = T12.dbid"+ //
				" and T5.name = 'Fermée'"+ //
				" and T1.dbid = h.entity_dbid"+ //
				" and (h.action_name = 'Fermer' and h.ACTION_TIMESTAMP between sysdate - 120 AND sysdate)"+ //
				" and (T1.typereference = 'Interne' or T1.typereference = 'SalesForce' or T1.typereference = 'GIS' or T1.typereference = 'CRM')"; //
	}
	
	public void requeteGCOpen() {
		//Dans Dashboard maintenance : GCENT Open
		requete = "select T1.numerofiche, "+ //
				"T1.dbid, "+ //
				"T1.titre, "+ //
				"T5.name, "+ //
				"T1.NUMEROREFERENCE case, "+ //
				"T1.dateplanifauplustard_1 + 2 / 24, "+ //
				"T1.dateplanifauplustot + 2 / 24, "+ //
				"T1.label, "+ //
				"T1.id, "+ //
				"T1.rfeid, "+ //
				"T1.typereference, "+ //
				"T16.nom, "+ //
				"T3.name, "+ //
				"T11.login_name, "+ //
				"T12.NOM Produit, "+ //
				"T1.RETROFITCARD, "+ //
				"h.action_timestamp Opening_Date, "+ //
				"T1.expectedcorrectiondate, "+ //
				"T1.SEVERITE, "+ //
				"round(sysdate - h.action_timestamp) ageold, "+ //
				"SP.name subproduct, "+ //
				"T1.internalstate, "+ //
				"T1.Regressionc, "+ //
				"T1.REGRESSIONCAUSE, "+ //
				"T1.Reportfiche, "+//
			    "T1.Rfecode, "+ //
			    "T1.Chefprojet, "+ //
			    "T1.Urgence, "+ //
			    "h.action_name "+ //
				
				"from cqcentral.anomalie T1, "+ //
				"cqcentral.statedef T5, "+ //
				"cqcentral.client T16, "+ //
				"cqcentral.DOMAINRECORD T3, "+ //
				"cqcentral.users T11, "+ //
				"cqcentral.produit T12, "+ //
				"cqcentral.History h, "+ //
				"cqcentral.subproduct SP, "+ //
				"cqcentral.rfe rfe "+ //
				"where T1.state = T5.id "+ //
				"and T1.assigne = T11.dbid "+ //
				"and T1.client_champ = T16.dbid "+ //
				"and T1.subproduct = SP.DBID "+ //
				"and T1.domain_1 = T3.dbid "+ //
				"and T1.PRODUIT_1 = T12.dbid "+ //
				"and rfe.dbid = T1.RFECODE "+ //
				"and T1.dbid <> 0 "+ //
				"and T5.name in ('En_Attente','Assignée', 'Ouverte') "+ //
				"and h.entity_dbid = T1.DBID "+ //
				"and h.action_name = 'Ouvrir' "+ //
				"and (T1.typereference = 'Interne' or T1.typereference = 'SalesForce' or "+ //
				"T1.typereference = 'GIS' or T1.typereference = 'CRM') "+ //
				"and sp.name <> 'GCO V6.00' "+ //
				//"and T1.numeroreference = 'CAS-126456-Y8J4' "+
				"union "+ //
				"select T1.numerofiche, "+ //
				"T1.dbid, "+ //
				"T1.titre, "+ //
				"T5.name, "+ //
				"T1.NUMEROREFERENCE case, "+ //
				"T1.dateplanifauplustard_1 + 2 / 24, "+ //
				"T1.dateplanifauplustot + 2 / 24, "+ //
				"T1.label, "+ //
				"T1.id, "+ //
				"T1.rfeid, "+ //
				"T1.typereference, "+ //
				"T16.nom, "+ //
				"T3.name, "+ //
				"T11.login_name, "+ //
				"T12.NOM Produit, "+ //
				"T1.RETROFITCARD, "+ //
				"h.action_timestamp, "+ //
				"T1.expectedcorrectiondate, "+ //
				"T1.SEVERITE, "+ //
				"round(sysdate - h.action_timestamp) ageold, "+ //
				"SP.name subproduct, "+ //
				"T1.internalstate, "+ //
				"T1.Regressionc, "+ //
				"T1.REGRESSIONCAUSE, "+ //
				"T1.Reportfiche, "+//
			    "T1.Rfecode, "+ //
			    "T1.Chefprojet, "+ //
			    "T1.Urgence, "+ //
			    "h.action_name "+ //
				"from cqcentral.anomalie T1, "+ //
				"cqcentral.statedef T5, "+ //
				"cqcentral.client T16, "+ //
				"cqcentral.DOMAINRECORD T3, "+ //
				"cqcentral.history T7, "+ //
				"cqcentral.users T11, "+ //
				"cqcentral.produit T12, "+ //
				"CQCENTRAL.History h, "+ //
				"cqcentral.subproduct SP, "+ //
				"cqcentral.rfe rfe "+ //
				"where T1.state = T5.id "+ //
				"and T1.assigne = T11.dbid "+ //
				"and T1.client_champ = T16.dbid "+ //
				"and T1.subproduct = SP.DBID "+ //
				"and T1.Domain_1 = T3.dbid "+ //
				"and T1.PRODUIT_1 = T12.dbid "+ //
				"and rfe.dbid = T1.RFECODE "+ //
				"and T1.dbid <> 0   "+ //
				"and h.entity_dbid = T1.DBID "+ //
				"and h.action_name = 'Ouvrir' "+ //
				"and T5.name in ('A_installer', "+ //
				"'Corrigée', "+ //
				"'En_Integration', "+ //
				"'En_qualification', "+ //
				"'Sans_suite') "+ //
				"and T1.dbid = T7.entity_dbid "+ //
				"and (T7.ACTION_TIMESTAMP between sysdate - 120 AND sysdate) "+ //
				"and (T1.typereference = 'Interne' or T1.typereference = 'SalesForce' or "+ //
				"T1.typereference = 'GIS' or T1.typereference = 'CRM') "+ //
				//"and T1.numeroreference = 'CAS-126456-Y8J4' "+
				"and sp.name <> 'GCO V6.00'    order by 5";// 

	}
	
		
	public void requeteGCInternal() {
		//Dans Dashboard maintenance : GCENT Interne
		requete =  " select T1.numerofiche, " + //
				 " T1.dbid, " + //
				 " T1.titre, " + //
				 " T5.name, " + //
				 " T1.NUMEROREFERENCE case, " + //
				 " T1.dateplanifauplustard_1 + 2 / 24, " + //
				 " T1.dateplanifauplustot + 2 / 24, " + //
				 " T1.label, " + //
				 " T1.id, " + //
				 " T1.rfeid, " + //
				 " T1.typereference, " + //
				 " T16.nom, " + //
				 " T3.name, " + //
				 " T11.login_name, " + //
				 " T12.NOM Produit, " + //
				 " T1.RETROFITCARD, " + //
				 " h.action_timestamp OPENING_DATE, "+ // 
				 " T1.expectedcorrectiondate, " + //
				 " T1.SEVERITE, " + //
				 " round(sysdate - h.action_timestamp) ageold, " + //
				 " SP.name subproduct, " + //
				 " T1.internalstate, " + //
				 " T1.Regressionc, " + //
				 " T1.REGRESSIONCAUSE, " + //
				 " T1.Reportfiche," + //
				 " T1.Rfecode, " + //
				 " T1.Chefprojet, " + //
				 " T1.Urgence, " + //
				 " h.action_name " + //
				 " from cqcentral.anomalie     T1,  " + //
				 "      cqcentral.statedef     T5,  " + //
				 "      cqcentral.domainrecord T3,  " + //
				 "      cqcentral.users        T9,  " + //
				 "      cqcentral.users        T11,  " + //
				 "      cqcentral.produit      T12,  " + //
				 "      cqcentral.history      h,  " + //
				 "      CQCENTRAL.branch       b,  " + //
				 "      cqcentral.client       T16,  " + //
				 "      cqcentral.rfe          rfe,  " + //
				 "      cqcentral.subproduct   SP  " + //
				 " where T1.state = T5.id  " + //
				 "  and T1.client_champ = T16.dbid  " + //
				 "  and T1.Domain_1 = T3.dbid  " + //
				 "  and T1.assigne = T9.dbid  " + //
				 "  and T1.Subproduct = SP.DBID  " + //
				 "  and T1.produit_1 = T12.dbid  " + //
				 "  and T1.dbid <> 0  " + //
				 "  and T1.typereference in ('Interne')  " + //
				 "  and T1.chefprojet = T11.dbid  " + //
				 "  and T5.name in ('Assignée', 'Ouverte', 'En_Attente')  " + //
				 "  and T12.nom in ('Central V5.10 Evo', 'Central V5.10 Maint')  " + //
				 "  and h.entity_dbid = T1.dbid  " + //
				 "  and h.action_name = 'Ouvrir'  " + //
				 "  and T1.branchname = b.dbid  " + //
				 "  and rfe.dbid = T1.RFECODE  " + //
				 "  and sp.name <> 'GCO V6.00'" + //
		//" --and t1.numerofiche = 'GCENT16060191' "+ // 
		" union "+ // 
		" select T1.numerofiche, " + //
			 " T1.dbid, " + //
			 " T1.titre, " + //
			 " T5.name, " + //
			 " T1.NUMEROREFERENCE case, " + //
			 " T1.dateplanifauplustard_1 + 2 / 24, " + //
			 " T1.dateplanifauplustot + 2 / 24, " + //
			 " T1.label, " + //
			 " T1.id, " + //
			 " T1.rfeid, " + //
			 " T1.typereference, " + //
			 " T16.nom, " + //
			 " T5.name, " + //
			 " T11.login_name, " + //
			 " T12.NOM Produit, " + //
			 " T1.RETROFITCARD, " + //
			 " h.action_timestamp OPENING_DATE, "+ // 
			 " T1.expectedcorrectiondate, " + //
			 " T1.SEVERITE, " + //
			 " round(sysdate - h.action_timestamp) ageold, " + //
			 " SP.name subproduct, " + //
			 " T1.internalstate, " + //
			 " T1.Regressionc, " + //
			 " T1.REGRESSIONCAUSE, " + //
			 " T1.Reportfiche," + //
			 " T1.Rfecode, " + //
			 " T1.Chefprojet, " + //
			 " T1.Urgence, " + //
			 " h.action_name " + //
		"   from cqcentral.anomalie     T1, "+ // 
		"        cqcentral.statedef     T5, "+ // 
		"        cqcentral.domainrecord T3, "+ // 
		"        cqcentral.users        T9, "+ // 
		"        cqcentral.users        T11, "+ // 
		"        cqcentral.produit      T12, "+ // 
		"        cqcentral.history      h, "+ // 
		"        CQCENTRAL.branch       b, "+ // 
		"        cqcentral.client       T16, "+ // 
		"        cqcentral.rfe          rfe, "+ // 
		"        cqcentral.subproduct   SP "+ // 
		"  where T1.state = T5.id "+ // 
		"    and T1.client_champ = T16.dbid "+ // 
		"    and T1.Domain_1 = T3.dbid "+ // 
		"    and T1.assigne = T9.dbid "+ // 
		"    and T1.Subproduct = SP.DBID "+ // 
		"    and T1.produit_1 = T12.dbid "+ // 
		"    and T1.dbid <> 0 "+ // 
		"    and T1.chefprojet = T11.dbid "+ // 
		"    and T1.typereference = 'Interne' "+ // 
		"    and T5.name in ('Assignée', 'Ouverte', 'En_Attente') "+ // 
		////"       --and b.nom_branch = 'DLG04' "+ // 
		"    and (b.nom_branch = 'DLG04' or b.nom_branch = 'DLG02') "+ // 
		"    and h.entity_dbid = T1.dbid "+ // 
		"    and h.action_name = 'Ouvrir' "+ // 
		"    and T1.branchname = b.dbid "+ // 
		////"    --and T1.REPORTFICHE is null "+ // 
		"    and rfe.dbid = T1.RFECODE "+ // 
		"    and sp.name <> 'GCO V6.00' "+ // 
		" union "+ // 
		" select T1.numerofiche, " + //
			 " T1.dbid, " + //
			 " T1.titre, " + //
			 " T5.name, " + //
			 " T1.NUMEROREFERENCE case, " + //
			 " T1.dateplanifauplustard_1 + 2 / 24, " + //
			 " T1.dateplanifauplustot + 2 / 24, " + //
			 " T1.label, " + //
			 " T1.id, " + //
			 " T1.rfeid, " + //
			 " T1.typereference, " + //
			 " T16.nom, " + //
			 " T5.name, " + //
			 " T11.login_name, " + //
			 " T12.NOM Produit, " + //
			 " T1.RETROFITCARD, " + //
			 " h.action_timestamp OPENING_DATE, "+ //
			 " T1.expectedcorrectiondate, " + //
			 " T1.SEVERITE, " + //
			 " round(sysdate - h.action_timestamp) ageold, " + //
			 " SP.name subproduct, " + //
			 " T1.internalstate, " + //
			 " T1.Regressionc, " + //
			 " T1.REGRESSIONCAUSE, " + //
			 " T1.Reportfiche," + //
			 " T1.Rfecode, " + //
			 " T1.Chefprojet, " + //
			 " T1.Urgence, " + //
			 " h.action_name " + //
		"   from cqcentral.anomalie     T1, "+ // 
		"        cqcentral.statedef     T5, "+ // 
		"        cqcentral.domainrecord T3, "+ // 
		"        cqcentral.users        T9, "+ // 
		"        cqcentral.users        T11, "+ // 
		"        cqcentral.produit      T12, "+ // 
		"        cqcentral.history      h, "+ // 
		"        CQCENTRAL.branch       b, "+ // 
		"        cqcentral.client       T16, "+ // 
		"        cqcentral.rfe          rfe, "+ // 
		"        cqcentral.subproduct   SP "+ // 
		"  where T1.state = T5.id "+ // 
		"    and T1.client_champ = T16.dbid "+ // 
		"    and T1.Domain_1 = T3.dbid     "+ // 
		"    and T1.assigne = T9.dbid       "+ // 
		"    and T1.produit_1 = T12.dbid "+ // 
		"    and T1.Subproduct = SP.DBID "+ // 
		"    and T1.dbid <> 0 "+ // 
		"    and T1.chefprojet = T11.dbid       "+ // 
		"    and t11.login_name = 'guerrier'    "+ // 
		"    and T1.typereference = 'Interne'   "+ // 
		"    and T5.name in ('Assignée', 'Ouverte', 'En_Attente')       "+ // 
		"    and h.entity_dbid = T1.dbid        "+ // 
		"    and h.action_name = 'Ouvrir'       "+ // 
		"    and T1.branchname = b.dbid              "+ // 
		"    and T1.REPORTFICHE is null              "+ // 
		"    and rfe.dbid = T1.RFECODE    "+ // 
		"    and sp.name <> 'GCO V6.00'   "+ // 
		" union "+ // 
		" select T1.numerofiche, " + //
			 " T1.dbid, " + //
			 " T1.titre, " + //
			 " T5.name, " + //
			 " T1.NUMEROREFERENCE case, " + //
			 " T1.dateplanifauplustard_1 + 2 / 24, " + //
			 " T1.dateplanifauplustot + 2 / 24, " + //
			 " T1.label, " + //
			 " T1.id, " + //
			 " T1.rfeid, " + //
			 " T1.typereference, " + //
			 " T16.nom, " + //
			 " T5.name, " + //
			 " T11.login_name, " + //
			 " T12.NOM Produit, " + //
			 " T1.RETROFITCARD, " + //
			 " h.action_timestamp OPENING_DATE, "+ // 
			 " T1.expectedcorrectiondate, " + //
			 " T1.SEVERITE, " + //
			 " round(sysdate - h.action_timestamp) ageold, " + //
			 " SP.name subproduct, " + //
			 " T1.internalstate, " + //
			 " T1.Regressionc, " + //
			 " T1.REGRESSIONCAUSE, " + //
			 " T1.Reportfiche," + //
			 " T1.Rfecode, " + //
			 " T1.Chefprojet, " + //
			 " T1.Urgence, " + //
			 " h.action_name " + //
		"   from cqcentral.anomalie     T1, "+ // 
		"        cqcentral.statedef     T5, "+ // 
		"        cqcentral.domainrecord T3, "+ // 
		"        cqcentral.users        T9, "+ // 
		"        cqcentral.users        T11, "+ // 
		"        cqcentral.produit      T12, "+ // 
		"        cqcentral.history      h, "+ // 
		"        CQCENTRAL.branch       b, "+ // 
		"        cqcentral.client       T16, "+ // 
		"        cqcentral.rfe          rfe, "+ // 
		"        cqcentral.subproduct   SP "+ // 
		"  where T1.state = T5.id "+ // 
		"    and T1.client_champ = T16.dbid "+ // 
		"    and T1.Domain_1 = T3.dbid "+ // 
		"    and T1.assigne = T9.dbid "+ // 
		"    and T1.Subproduct = SP.DBID "+ // 
		"    and T1.produit_1 = T12.dbid "+ // 
		"    and T1.dbid <> 0 "+ // 
		"    and T1.chefprojet = T11.dbid "+ // 
		////"       --and t11.login_name = 'guerrier' "+ // 
		"    and T1.Numeroreference = 'GSM-R2' "+ // 
		"    and T5.name in ('Assignée', 'Ouverte', 'En_Attente') "+ // 
		"    and h.entity_dbid = T1.dbid "+ // 
		"    and h.action_name = 'Ouvrir' "+ // 
		"    and T1.branchname = b.dbid "+ // 
		"    and T1.REPORTFICHE is null "+ // 
		"    and rfe.dbid = T1.RFECODE "+ // 
		"    and sp.name <> 'GCO V6.00' "+ // 
		" Union "+ // 
		" select T1.numerofiche, " + //
			 " T1.dbid, " + //
			 " T1.titre, " + //
			 " T5.name, " + //
			 " T1.NUMEROREFERENCE case, " + //
			 " T1.dateplanifauplustard_1 + 2 / 24, " + //
			 " T1.dateplanifauplustot + 2 / 24, " + //
			 " T1.label, " + //
			 " T1.id, " + //
			 " T1.rfeid, " + //
			 " T1.typereference, " + //
			 " T16.nom, " + //
			 " T5.name, " + //
			 " T11.login_name, " + //
			 " T12.NOM Produit, " + //
			 " T1.RETROFITCARD, " + //
			 " h.action_timestamp OPENING_DATE, "+ // 
			 " T1.expectedcorrectiondate, " + //
			 " T1.SEVERITE, " + //
			 " round(sysdate - h.action_timestamp) ageold, " + //
			 " SP.name subproduct, " + //
			 " T1.internalstate, " + //
			 " T1.Regressionc, " + //
			 " T1.REGRESSIONCAUSE, " + //
			 " T1.Reportfiche," + //
			 " T1.Rfecode, " + //
			 " T1.Chefprojet, " + //
			 " T1.Urgence, " + //
			 " h.action_name " + //
		"   from cqgadmin.anomalie     T1, "+ // 
		"        cqgadmin.statedef     T5, "+ // 
		"        cqgadmin.domainrecord T3, "+ // 
		"        cqgadmin.users        T9, "+ // 
		"        cqgadmin.users        T11, "+ // 
		"        cqgadmin.produit      T12, "+ // 
		"        cqgadmin.history      h, "+ // 
		"        cqgadmin.branch       b, "+ // 
		"        cqgadmin.client       T16, "+ // 
		"        cqgadmin.rfe          rfe, "+ // 
		"        cqgadmin.subproduct   SP "+ // 
		"  where T1.state = T5.id "+ // 
		"    and T1.client_champ = T16.dbid "+ // 
		"    and T1.Domain_1 = T3.dbid "+ // 
		"    and T1.assigne = T9.dbid "+ // 
		"    and T1.Subproduct = SP.DBID "+ // 
		"    and T1.produit_1 = T12.dbid "+ // 
		"    and T1.dbid <> 0 "+ // 
		"    and T1.typereference in ('Interne') "+ // 
		"    and T1.chefprojet = T11.dbid "+ // 
		"    and T5.name in ('Assignée', 'Ouverte', 'En_Attente') "+ // 
		"    and T12.nom in ('Adm. V5.10 Maint', 'Adm. V5.10 Client', 'Adm. V5.10 Evo') "+ // 
		"    and T1.REPORTFICHE is null "+ // 
		"    and h.entity_dbid = T1.dbid "+ // 
		"    and h.action_name = 'Ouvrir' "+ // 
		"    and T1.branchname = b.dbid "+ // 
		"    and rfe.dbid = T1.RFECODE "+ // 
		"    and sp.name <> 'GCO V6.00' "+ // 
		" Union "+ // 
		" select T1.numerofiche, " + //
			 " T1.dbid, " + //
			 " T1.titre, " + //
			 " T5.name, " + //
			 " T1.NUMEROREFERENCE case, " + //
			 " T1.dateplanifauplustard_1 + 2 / 24, " + //
			 " T1.dateplanifauplustot + 2 / 24, " + //
			 " T1.label, " + //
			 " T1.id, " + //
			 " T1.rfeid, " + //
			 " T1.typereference, " + //
			 " T16.nom, " + //
			 " T5.name, " + //
			 " T11.login_name, " + //
			 " T12.NOM Produit, " + //
			 " T1.RETROFITCARD, " + //
			 " h.action_timestamp OPENING_DATE, "+ // 
			 " T1.expectedcorrectiondate, " + //
			 " T1.SEVERITE, " + //
			 " round(sysdate - h.action_timestamp) ageold, " + //
			 " SP.name subproduct, " + //
			 " T1.internalstate, " + //
			 " T1.Regressionc, " + //
			 " T1.REGRESSIONCAUSE, " + //
			 " T1.Reportfiche," + //
			 " T1.Rfecode, " + //
			 " T1.Chefprojet, " + //
			 " T1.Urgence, " + //
			 " h.action_name " + //
		"   from cqcentral.anomalie     T1, "+ // 
		"        cqcentral.statedef     T5, "+ // 
		"        cqcentral.domainrecord T3, "+ // 
		"        cqcentral.users        T9, "+ // 
		"        cqcentral.users        T11, "+ // 
		"        cqcentral.produit      T12, "+ // 
		"        cqcentral.history      h, "+ // 
		"        CQCENTRAL.branch       b, "+ // 
		"        cqcentral.client       T16, "+ // 
		"        cqcentral.rfe          rfe, "+ // 
		"        cqcentral.subproduct   SP "+ // 
		"  where T1.state = T5.id "+ // 
		"    and T1.client_champ = T16.dbid "+ // 
		"    and T1.Domain_1 = T3.dbid "+ // 
		"    and T1.assigne = T9.dbid "+ // 
		"    and T1.Subproduct = SP.DBID "+ // 
		"    and T1.produit_1 = T12.dbid "+ // 
		"    and T1.dbid <> 0 "+ // 
		"    and T1.chefprojet = T11.dbid "+ // 
		////"       --and t11.login_name = 'guerrier' "+ // 
		"    and T5.name in ('Assignée', 'Ouverte', 'En_Attente')      "+ // 
		"    and T1.Numeroreference like 'GSM2-SP2%' "+ // 
		"    and h.entity_dbid = T1.dbid "+ // 
		"    and h.action_name = 'Ouvrir' "+ // 
		"    and T1.branchname = b.dbid "+ // 
		//"    --and b.nom_branch = 'MUS05' "+ // 
		"    and T1.REPORTFICHE is null "+ // 
		"    and rfe.dbid = T1.RFECODE "+ // 
		"    and sp.name <> 'GCO V6.00' "+ // 
		" Union "+ // 
		" select T1.numerofiche, " + //
			 " T1.dbid, " + //
			 " T1.titre, " + //
			 " T5.name, " + //
			 " T1.NUMEROREFERENCE case, " + //
			 " T1.dateplanifauplustard_1 + 2 / 24, " + //
			 " T1.dateplanifauplustot + 2 / 24, " + //
			 " T1.label, " + //
			 " T1.id, " + //
			 " T1.rfeid, " + //
			 " T1.typereference, " + //
			 " T16.nom, " + //
			 " T5.name, " + //
			 " T11.login_name, " + //
			 " T12.NOM Produit, " + //
			 " T1.RETROFITCARD, " + //
			 " h.action_timestamp OPENING_DATE, "+ // 
			 " T1.expectedcorrectiondate, " + //
			 " T1.SEVERITE, " + //
			 " round(sysdate - h.action_timestamp) ageold, " + //
			 " SP.name subproduct, " + //
			 " T1.internalstate, " + //
			 " T1.Regressionc, " + //
			 " T1.REGRESSIONCAUSE, " + //
			 " T1.Reportfiche," + //
			 " T1.Rfecode, " + //
			 " T1.Chefprojet, " + //
			 " T1.Urgence, " + //
			 " h.action_name " + //
		"   from cqcentral.anomalie     T1, "+ // 
		"        cqcentral.statedef     T5, "+ // 
		"        cqcentral.domainrecord T3, "+ // 
		"        cqcentral.users        T9, "+ // 
		"        cqcentral.users        T11, "+ // 
		"        cqcentral.produit      T12, "+ // 
		"        cqcentral.history      h, "+ // 
		"        CQCENTRAL.branch       b, "+ // 
		"        cqcentral.client       T16, "+ // 
		"        cqcentral.rfe          rfe, "+ // 
		"        cqcentral.subproduct   SP "+ // 
		"  where T1.state = T5.id "+ // 
		"    and T1.client_champ = T16.dbid "+ // 
		"    and T1.Domain_1 = T3.dbid "+ // 
		"    and T1.assigne = T9.dbid "+ // 
		"    and T1.Subproduct = SP.DBID "+ // 
		"    and T1.produit_1 = T12.dbid "+ // 
		"    and T1.dbid <> 0 "+ // 
		"    and T1.chefprojet = T11.dbid "+ // 
		//"       --and t11.login_name = 'guerrier' "+ // 
		"    and T5.name in ('Assignée', 'Ouverte', 'En_Attente') "+ // 
		"    and T1.Numeroreference = 'HN_REG_QANR' "+ // 
		"    and h.entity_dbid = T1.dbid "+ // 
		"    and h.action_name = 'Ouvrir' "+ // 
		"    and T1.branchname = b.dbid "+ // 
		//"    --and b.nom_branch = 'MUS05' "+ // 
		"    and T1.REPORTFICHE is null "+ // 
		"    and rfe.dbid = T1.RFECODE "+ // 
		"    and sp.name <> 'GCO V6.00' "+ // 
		"  order by 1, 2";
	}
	
	
	public void connectionClose() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
