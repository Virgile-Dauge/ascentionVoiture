package ascentionVoiture;

import java.io.*;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		Config defaultConfig = new Config("config.json");
		//System.out.println(defaultConfig);

		Ascension a = new Ascension(defaultConfig);
		//System.out.println(a);
		
		while(!a.isGameOver() && !a.isGoalReached()){
			a.nextStep();
		}
		a.afficheResult();
		System.out.println("Fin de l'algorithme.");
	}

}
