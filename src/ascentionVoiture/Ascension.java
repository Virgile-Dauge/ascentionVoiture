package ascentionVoiture;

public class Ascension {

	static Double[] positions;
	static Double[] vitesses;
	static Double[][] Vt;
	static int nbElemDiscret=32;
	static int nb_intervalle=(nbElemDiscret-1);
	
	// Données de la classe config qui seront copié pour rapidité d'execution
	Double position=0.00;
	int indexPos=0;
	Double vitesse=0.00;
	int indexVit=0;
	static int accG=-1;
	static int accD=1;
	int acc;
	double gain=0;
	
	// Entier (booleen) pour connaitre si la partie est gagnee
	int gagne=0;
	
	
	/**
	 * Constructeur
	 */
	public Ascension() {
		
		
		positions = new Double[nbElemDiscret];
		vitesses = new Double[nbElemDiscret];
		
		/**
		 *  le tableau pos represente les 32 positions possibles
		 */		
        positions[0]= -1.2;
        Double step = 1.8/nb_intervalle;
        for (int i=1;i<nb_intervalle;i++){
        	positions[i]=positions[i-1]+step;
        }
        positions[nb_intervalle]=(Double) 0.6;
        
        
        /**
         *  le tableau vitesse represente les 32 vitesses possibles
         */
        step = 0.14/nb_intervalle;
        vitesses[0]= -0.07;
        for (int i=1;i<nb_intervalle;i++){
        	vitesses[i]=vitesses[i-1]+step;
        }
        vitesses[nb_intervalle]=(Double) 0.07;
        
        afficherTabPosition();
        afficherTabVitesse();
      
	}
	
	/**
	 * Contructeur qui prend en compte la config.
	 * @param c
	 */
	public Ascension(Config c) {
		
		/**
		 *  le tableau pos represente les 32 positions possibles
		 */
		positions = new Double[c.getPrecisionPosition()];
        positions[0]= c.getPosMin();
        Double step = (c.getPosMax()-c.getPosMin())/nb_intervalle;
        for (int i=1;i<nb_intervalle;i++){
        	positions[i]=positions[i-1]+step;
        }
        positions[nb_intervalle]= c.getPosMax();
        
        /**
         *  le tableau vitesse represente les 32 vitesses possibles
         */
        step = (c.getVitesseMax()-c.getVitesseMin())/nb_intervalle;
        vitesses = new Double[c.getPrecisionVitesse()];
        vitesses[0]= c.getVitesseMin();
        for (int i=1;i<nb_intervalle;i++){
        	vitesses[i]=vitesses[i-1]+step;
        }
        vitesses[nb_intervalle]= c.getVitesseMax();
        
        position= c.getStartPos();
        vitesse= c.getStartVit();
        
        afficherTabPosition();
        afficherTabVitesse();
        
        Vt = new Double[c.getPrecisionPosition()][c.getPrecisionVitesse()];
        
        System.out.println();
        System.out.println("Tableau VT : ");
        
        for(int i = 0; i < c.getPrecisionPosition(); i++){
        	for(int j = 0; j < c.getPrecisionVitesse(); j++){
        		Double val = Math.abs(positions[i]) + Math.abs(vitesses[j]);
        		Vt[i][j] = val;
        		//System.out.println("VT[" + positions[i] + "][" + vitesses[j] + "] : " + val);
        	}
        }
        
	}
	
	// Sert à afficher le tableau representant tous les états de vitesse
	public void afficherTabVitesse(){
		System.out.println("tableau vitesse:");
        for (int i=0;i<nbElemDiscret;i++){
        	System.out.println(i+": "+vitesses[i]);
        }
	}
	
	// Sert à afficher le tableau représentant tous les états de position possibles
	public void afficherTabPosition(){
		System.out.println("tableau positions:");
        for (int i=0;i<nbElemDiscret;i++){
        	System.out.println(i+": "+positions[i]);
        }
	}
	
	
	public void simuleUneRouteRandom(){
		while(checkIfFinished()==0){
			int nb= (int)(Math.random()*2);
			if (nb==1){
				acc =accG;
				System.out.println("on va a gauche");
			}else{
				acc = accD;
				System.out.println("On va a droite");
			}
			//setNextStep();
			majGain();
			// version complete
			//System.out.println("Nouvelle position:"+position+"("+indexPos+") avec une vitesse de "+vitesse+"("+indexVit+"). Le gain est "+gain);
			
			//version etat
			System.out.println("Nouvelle position: ("+indexPos+","+indexVit+") Gain: "+gain);
			
		}
	}
	

	void setNextPosition(Double v){
		position=position+v;
	}
	
	void setNextVitesse(){
		vitesse=vitesse+0.001*acc-0.0025*Math.cos(3*position);
	}
	
	/*void setNextStep(){
		setNextPosition();
		indexPos = foundNearestPos(position);
		position = positions[indexPos];
		setNextVitesse();
		indexVit = foundNearestSpeed(vitesse);
		vitesse = vitesses[indexVit];
	}*/
	

	public void majGain(){
		if(position > 0.55 && position < 0.6){
			gain=gain+0.07-Math.abs(vitesse);
		}
		if(position > -1.2 && position < -1.15){
			gain=gain-1;
		}
	}
	
	/**
	 * fonction qui retourne 1 si la recherche est terminée (voiture dans le ravin ou en haut de la colline)
	 * La fonction set egalement le booleen gagne si la partie est gagnee
	 * @return int
	 */
	int checkIfFinished(){
		if(position < positions[0]){
			gagne=0;
			return 1;
		}
		if(position == positions[nb_intervalle]){
			gagne=1;
			return 1;
		}
		return 0;
	}

	/**
	 * Fonction qui retourne l'index de la vitesse la plus proche
	 * @param x
	 * @return pos
	 */
	int foundNearestPos(Double x){
		Double ecartMin=25.00;
		int pos=0;
		
        for (int i=0;i<nb_intervalle;i++){
        	Double current=Math.abs(positions[i]-x);
        	if(current < ecartMin){
        		ecartMin=current;
        		pos=i;
        	}
        }
		return pos;
	}

	/**
	 * Fonction qui retourne l'index de la vitesse la plus proche
	 * @param x
	 * @return int
	 */
	int foundNearestSpeed(Double x){
		Double ecartMin=25.00;
		int pos=0;
		
        for (int i=0;i<nb_intervalle;i++){
        	Double current=Math.abs(vitesses[i]-x);
        	if(current < ecartMin){
        		ecartMin=current;
        		pos=i;
        	}
        }
		return pos;
	}
	
	// Permet de savoir si la partie est gagne ou non
	public boolean estGagne(){
		if (gagne==1){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Ascension [position=" + position + ", indexPos=" + indexPos + ", vitesse=" + vitesse + ", indexVit="
				+ indexVit + ", acc=" + acc + ", gain=" + gain + ", gagne=" + gagne + "]";
	}
	
	
	
}
