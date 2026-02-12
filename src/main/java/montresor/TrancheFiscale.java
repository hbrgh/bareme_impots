package montresor;

public class TrancheFiscale {
	
	private String nom;
	private double tauxMaxImposition;
	private int borneInf;
	private int borneSup;
	
	public TrancheFiscale(String nom, double tauxMaxImposition, int borneInf, int borneSup) {
		super();
		this.nom = nom;
		this.tauxMaxImposition = tauxMaxImposition;
		this.borneInf = borneInf;
		this.borneSup = borneSup;
	}
	
	@Override
	public String toString() {
		return "TrancheFiscale [nom=" + nom + ", tauxMaxImposition=" + tauxMaxImposition + ", borneInf=" + borneInf
				+ ", borneSup=" + borneSup + "]";
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getTauxMaxImposition() {
		return tauxMaxImposition;
	}
	public void setTauxMaxImposition(double tauxMaxImposition) {
		this.tauxMaxImposition = tauxMaxImposition;
	}
	public int getBorneInf() {
		return borneInf;
	}
	public void setBorneInf(int borneInf) {
		this.borneInf = borneInf;
	}
	public int getBorneSup() {
		return borneSup;
	}
	public void setBorneSup(int borneSup) {
		this.borneSup = borneSup;
	}
	
	

}
