package montresor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class BaremeFiscal {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BaremeFiscal.class);

	
	private static final TrancheFiscaleComparator KOMPARATOR = new TrancheFiscaleComparator();
	
	private String nom;
	private List<TrancheFiscale> listTranches;
	
	public BaremeFiscal(String nom) {
		super();
		this.nom = nom;
		this.listTranches = new ArrayList<>();
		
	}
	
	public double getImpotDu(int revenuImposable, int deductionForfaitairePoucentage) {
		int revenu = revenuImposable;
		if (deductionForfaitairePoucentage > 0) {
			revenu = (int) Math.round(revenuImposable * ((100.0 - deductionForfaitairePoucentage) / 100.0));
		}
		
		
		/// Il faut découper le revenu pour savoir ce qui va dns chaque tranche.
		Map<String,Integer> mapTrancheRevenuTranche = 
				new HashMap<>();
		for (TrancheFiscale tf : this.listTranches) {
			int revenuPourLaTranche = 0;
			if (revenu < tf.getBorneInf()) {
				mapTrancheRevenuTranche.put(tf.getNom(), 0);
				continue;
			}
			if (revenu >= tf.getBorneSup()) {
				revenuPourLaTranche = 
						tf.getBorneSup() - tf.getBorneInf();
				if ( tf.getBorneInf() > 0) {
					revenuPourLaTranche++;
				}
			} else {
				revenuPourLaTranche = revenu - tf.getBorneInf();
				if ( tf.getBorneInf() > 0) {
					revenuPourLaTranche++;
				}
			}
			mapTrancheRevenuTranche.put(tf.getNom(), revenuPourLaTranche);
		}
		
		// Ensuite, faire la somme de ce qu'on doit payer tranche par tranche
		double sommeDueAuFisc = 0.0;
		for (TrancheFiscale tf : this.listTranches) {
			sommeDueAuFisc +=
					(mapTrancheRevenuTranche.get(tf.getNom()) * tf.getTauxMaxImposition() / 100.0);	
		}
		return(sommeDueAuFisc);
		
	}
	
	public double getImpotDu(int revenuImposable, int nbParts, int deductionForfaitairePoucentage) {
		int revenu = revenuImposable;
		if (deductionForfaitairePoucentage > 0) {
			revenu = (int) Math.round(revenuImposable * ((100.0 - deductionForfaitairePoucentage) / 100.0));
		}
		int revenuPourDeterminationTranches = (int)Math.round((double)revenu / (double)nbParts);
		
		/// Il faut découper le revenu pour savoir ce qui va dans chaque tranche.
		Map<String,Integer> mapTrancheRevenuTranche = 
				new HashMap<>();
		for (TrancheFiscale tf : this.listTranches) {
			int revenuPourLaTranche = 0;
			if (revenuPourDeterminationTranches < tf.getBorneInf()) {
				mapTrancheRevenuTranche.put(tf.getNom(), 0);
				continue;
			}
			if (revenuPourDeterminationTranches >= tf.getBorneSup()) {
				revenuPourLaTranche = 
						tf.getBorneSup() - tf.getBorneInf();
				if ( tf.getBorneInf() > 0) {
					revenuPourLaTranche++;
				}
			} else {
				revenuPourLaTranche = revenuPourDeterminationTranches - tf.getBorneInf();
				if ( tf.getBorneInf() > 0) {
					revenuPourLaTranche++;
				}
			}
			mapTrancheRevenuTranche.put(tf.getNom(), revenuPourLaTranche);
		}
		
		// Ensuite, faire la somme de ce qu'on doit payer tranche par tranche
		double sommeDueAuFisc = 0.0;
		for (TrancheFiscale tf : this.listTranches) {
			sommeDueAuFisc +=
					( (mapTrancheRevenuTranche.get(tf.getNom()) * nbParts) * tf.getTauxMaxImposition() / 100.0);	
		}
		return(sommeDueAuFisc);
		
	}
	
	public void addTranche(TrancheFiscale tranche) {
		
		this.listTranches.add(tranche);
		Collections.sort(this.listTranches, KOMPARATOR);
		
	}
	
	public boolean verifCompletEtCoherent() {
		int nbTranches = this.listTranches.size();
		
		TrancheFiscale maBelleTranchePrec = null;
		for (int i = 0; i < nbTranches; i++) {
			TrancheFiscale maBelleTranche = this.listTranches.get(i);
			if (maBelleTranchePrec != null) {
				if (maBelleTranche.getBorneInf() != (1+maBelleTranchePrec.getBorneSup())) {
					LOGGER.error("Tranches du barême fiscal " + this.getNom()+ " incohérentes! Problème sur les bornes.");
					return(false);
				}
				if (maBelleTranchePrec.getTauxMaxImposition() < maBelleTranche.getTauxMaxImposition()) {
					LOGGER.error("Tranches du barême fiscal " + this.getNom()+ " incohérentes! Problème sur les taux.");
					return(false);
				}
			}
			
		}
		
		return(true);
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(System.lineSeparator());
		sb.append("Barème fiscal ");
		sb.append(nom);
		sb.append(":");
		sb.append(System.lineSeparator());
		for (TrancheFiscale tf : this.listTranches) {
			sb.append(tf.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<TrancheFiscale> getListTranches() {
		return listTranches;
	}

	public void setListTranches(List<TrancheFiscale> listTranches) {
		this.listTranches = listTranches;
	}
	
	

}
