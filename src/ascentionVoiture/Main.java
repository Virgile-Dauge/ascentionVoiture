package ascentionVoiture;

import java.io.*;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		Config defaultConfig = new Config("config.json");
		System.out.println(defaultConfig);
		
		BufferedWriter bf = new BufferedWriter(new FileWriter(new File("res.txt")));
		
		Ascension a = new Ascension(defaultConfig);
		System.out.println(a);
		
		ArrayList<String> result = new ArrayList<String>();
		
		Double VtPlusUn, VT;
		Double VitessePrec, PositionPrec;
		Double VitesseSuiv, PositionSuiv;
		int indexPos = a.foundNearestPos(defaultConfig.getStartPos());
		int indexVit = a.foundNearestSpeed(defaultConfig.getStartVit());
		indexVit += 1;
		a.vitesse = Ascension.vitesses[indexVit];
		int indexPosSuiv, indexVitSuiv;
		System.out.println(indexPos);
		System.out.println(indexVit);
		VT = Ascension.Vt[indexPos][indexVit];
		
		a.acc += 1;
		VitessePrec = a.vitesse;
		PositionPrec = a.position;
		a.setNextVitesse();
		a.setNextPosition(VitessePrec);
		VitesseSuiv = a.vitesse;
		PositionSuiv = a.position;
		indexPos = a.foundNearestPos(a.position);
		indexVit = a.foundNearestSpeed(a.vitesse);
		indexPosSuiv = indexPos;
		indexVitSuiv = indexVit;
		VtPlusUn = Ascension.Vt[indexPos][indexVit] * defaultConfig.getGamma();
		
		a.vitesse = VitessePrec;
		a.position = PositionPrec;
		a.acc -= 2;
		a.setNextVitesse();
		a.setNextPosition(VitessePrec);
		indexPos = a.foundNearestPos(a.position);
		indexVit = a.foundNearestSpeed(a.vitesse);
		
		if (Ascension.Vt[indexPos][indexVit] * defaultConfig.getGamma() < VtPlusUn){
			VtPlusUn = Ascension.Vt[indexPos][indexVit] * defaultConfig.getGamma();
			result.add("on accélère à gauche, position : " + Ascension.positions[indexPos] + ", "
					+ " vitesse : " + Ascension.vitesses[indexVit]);
		} else {
			a.acc += 2;
			a.vitesse = VitesseSuiv;
			a.position = PositionSuiv;
			indexPos = indexPosSuiv;
			indexVit = indexVitSuiv;
			result.add("on accélère à droite, position : " + Ascension.positions[indexPos] + ", "
					+ " vitesse : " + Ascension.vitesses[indexVit]);
		}
		
		Double recompense;
		
		while(Math.abs(VtPlusUn + VT) >= defaultConfig.getEpsilon() * (1 - 
				defaultConfig.getGamma()/2 * defaultConfig.getGamma())
				|| a.position == defaultConfig.getPosMax() ){
			/*System.out.println(Math.abs(VtPlusUn + VT));
			System.out.println(defaultConfig.getEpsilon() * (1 - 
					defaultConfig.getGamma()/2 * defaultConfig.getGamma()));
			System.out.println(a.position);
			System.out.println();*/
			VT = VtPlusUn;
			recompense = 0.;
			
			a.acc += 1;
			VitessePrec = a.vitesse;
			PositionPrec = a.position;
			a.setNextVitesse();
			a.setNextPosition(VitessePrec);
			VitesseSuiv = a.vitesse;
			PositionSuiv = a.position;
			indexPos = a.foundNearestPos(a.position);
			indexVit = a.foundNearestSpeed(a.vitesse);
			indexPosSuiv = indexPos;
			indexVitSuiv = indexVit;
			VtPlusUn = Ascension.Vt[indexPos][indexVit] * defaultConfig.getGamma();
			
			if(a.position > 0.55){
				recompense = 0.7 - a.position;
				System.out.println("Good");
			} else if (a.position < -1.15){
				recompense = -1.;
			}
			VtPlusUn += recompense;
			
			a.vitesse = VitessePrec;
			a.position = PositionPrec;
			a.acc -= 2;
			a.setNextVitesse();
			a.setNextPosition(VitessePrec);
			indexPos = a.foundNearestPos(a.position);
			indexVit = a.foundNearestSpeed(a.vitesse);
			
			if(a.position > 0.55){
				recompense = 0.7 - a.position;
			} else if (a.position < -1.15){
				recompense = -1.;
			}
			
			if (Ascension.Vt[indexPos][indexVit] * defaultConfig.getGamma() + recompense 
					< VtPlusUn){
				VtPlusUn = Ascension.Vt[indexPos][indexVit] * defaultConfig.getGamma() + 
						recompense;
				result.add("On accélère à gauche, position : " + Ascension.positions[indexPos] + ", "
						+ " vitesse : " + Ascension.vitesses[indexVit] + "\n\r");
			} else {
				a.vitesse = VitesseSuiv;
				a.position = PositionSuiv;
				indexPos = indexPosSuiv;
				indexVit = indexVitSuiv;
				result.add("On accélère à droite, position : " + Ascension.positions[indexPos] + ", "
						+ " vitesse : " + Ascension.vitesses[indexVit] + "\n\r");
			}
			bf.write(result.get(result.size()-1) + "\n\r");
		}
		
		for(String resultat : result){
			System.out.println(resultat);
		}
		System.out.println("Fin de l'algorithme.");
		System.out.println("Position finale : " + a.position);
	}

}
