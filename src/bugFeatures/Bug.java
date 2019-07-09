package bugFeatures;

import java.sql.ResultSet;

public class Bug {
	
	public Case theCase;

	//nouvelles collection de cases
	
	//private ArrayList<?> caseslist = new ArrayList<?>();
	
	public Bug(ResultSet rs) {
		//Constructeur des bugs, construit le case et la/les/0 Card(s) correspondant
		try {
			if (rs.getString(1).contentEquals("CAS-142213-Q8N5")) {
				System.out.println ("Found !");
			}
			theCase = new Case(rs.getString(1), rs.getString(2), rs.getShort(3), rs.getString(4), rs.getString(5), rs.getString(6),
					rs.getString(7), rs.getString(8), rs.getDate(9));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			}
	}
	
	/*public boolean Contains (Bug Bug2) {
		
	}*/
	
	public void  addCard(Card cardNumber) {
		theCase.addCard(cardNumber);
	}
	
	public boolean equals(Object o) {
		
	    if(!(o instanceof Bug)) return false;
	    Bug Bug2 = (Bug) o;
        boolean sameSame = false;

        if ((Bug2 != null && Bug2 instanceof Bug) && (this.theCase.caseNumber.contentEquals(Bug2.theCase.caseNumber)))
        {
            sameSame = true;
        }

        return sameSame;
	}
	

	
//	public boolean Equals(Bug Bug2)
//    {
//        boolean sameSame = false;
//
//        if ((Bug2 != null && Bug2 instanceof Bug) && (this.theCase.caseNumber.contentEquals(Bug2.theCase.caseNumber)))
//        {
//            sameSame = true;
//        }

//        return sameSame;
//    }
}
