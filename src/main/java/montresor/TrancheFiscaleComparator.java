package montresor;

import java.util.Comparator;


public class TrancheFiscaleComparator implements Comparator <TrancheFiscale>{

	public int compare(TrancheFiscale o1, TrancheFiscale o2) {
		// TODO Auto-generated method stub
		return (o1.getBorneInf() - o2.getBorneInf());
	}

}
