package ascentionVoiture;

import java.util.ArrayList;

public class Ascension {
	
	// Donn�es de la classe config qui seront copi� pour rapidit� d'execution
	@SuppressWarnings("unchecked")
	private ArrayList <Etat> etats = new ArrayList();
	
	private Double[][] Vt;
	private Double[] positions,
					 vitesses;
	
	private Config config;
	private Etat etatCourant,
				 etatDroite,
				 etatGauche;
	private double sommeVal;
	private boolean bloque;
	/**
	 * Contructeur qui prend en compte la config.
	 * @param c
	 */
	public Ascension(Config c) {
		this.config = c;
		sommeVal = 1;
		bloque = false;
		int nb_intervalles_pos = c.getPrecisionPosition()-1;
		int nb_intervalles_vit = c.getPrecisionVitesse()-1;
		
		
		/**
		 *  le tableau pos represente les 32 positions possibles
		 */
		positions = new Double[c.getPrecisionPosition()];
        positions[0]= c.getPosMin();
        Double step = (c.getPosMax()-c.getPosMin())/(nb_intervalles_pos);
        for (int i=1;i<nb_intervalles_pos;i++){
        	positions[i]=positions[i-1]+step;
        }
        positions[nb_intervalles_pos]= c.getPosMax();
        
        /**
         *  le tableau vitesse represente les 32 vitesses possibles
         */
        step = (c.getVitesseMax()-c.getVitesseMin())/nb_intervalles_vit;
        vitesses = new Double[c.getPrecisionVitesse()];
        vitesses[0]= c.getVitesseMin();
        for (int i=1;i<nb_intervalles_vit;i++){
        	vitesses[i]=vitesses[i-1]+step;
        }
        vitesses[nb_intervalles_vit]= c.getVitesseMax();
        
        Vt = new Double[c.getPrecisionPosition()][c.getPrecisionVitesse()];
        
        for(int i = 0; i < c.getPrecisionPosition(); i++){
        	for(int j = 0; j < c.getPrecisionVitesse(); j++){
        		Double val = Math.abs(positions[i]) + Math.abs(vitesses[j]);
        		Vt[i][j] = val;
        		//System.out.println("VT[" + positions[i] + "][" + vitesses[j] + "] : " + val);
        	}
        }
        
        etatCourant = calibrationEtat(c.getStartPos(),c.getStartVit(),"start");
        etats.add(etatCourant);

	}
	private Etat calibrationEtat(double position,double vitesse, String action){
		int indexPos = foundNearestPos(position);
		int indexVit = foundNearestSpeed(vitesse);
		double positionCal = positions[indexPos];
		double vitesseCal = vitesses[indexVit];
		double val = Vt[indexPos][indexVit] +config.getGamma()*sommeVal;
		return new Etat(positionCal,vitesseCal,val,action);
	}
	public void nextStep(){
		double position = etatCourant.getPosition() + etatCourant.getVitesse();
		etatGauche = calibrationEtat(position,etatCourant.getVitesse()+0.001*-1-0.0025*Math.cos(3*position),"Gauche");
		etatDroite = calibrationEtat(position,etatCourant.getVitesse()+0.001*1-0.0025*Math.cos(3*position),"Droite");
		if (etatGauche.isBetterThan(etatDroite)){
				etatCourant = etatGauche;
		}
		else{
				etatCourant = etatDroite;
		}
		etats.add(etatCourant);
		sommeVal += etatCourant.getValue();
		System.out.println(etatCourant.toString());
	}
	public boolean isGoalReached(){
		if(etatCourant.getPosition()==config.getPosMax())return true;
		else return false;
	}
	public boolean isGameOver(){
		if(etatCourant.getPosition()==config.getPosMin() || bloque)return true;
		else return false;
	}
	public void afficheResult(){
		for(Etat e : etats){
			System.out.println(e.toString());
		}
	}
	/**
	 * Fonction qui retourne l'index de la vitesse la plus proche
	 * @param x
	 * @return pos
	 */
	private int foundNearestPos(Double x){
		Double ecartMin=25.00;
		int pos=0;
		
        for (int i=0;i<config.getPrecisionPosition()-1;i++){
        	Double current=Math.abs(positions[i]-x);
        	if(current < ecartMin){
        		ecartMin=current;
        		pos = i;
        	}
        }
		return pos;
	}

	/**
	 * Fonction qui retourne l'index de la vitesse la plus proche
	 * @param x
	 * @return int
	 */
	private int foundNearestSpeed(Double x){
		Double ecartMin=25.00;
		int pos=0;
		
        for (int i=0;i<config.getPrecisionVitesse()-1;i++){
        	Double current=Math.abs(vitesses[i]-x);
        	if(current < ecartMin){
        		ecartMin=current;
        		pos = i;
        	}
        }
		return pos;
	}



}
