package ascentionVoiture;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Config {
	private String name;
	private double posMax, posMin, vitesseMax, vitesseMin, startPos, startVit, coefPente, epsilon,gamma;
	private int precisionPosition, precisionVitesse;
	public Config (String path){
		JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;
            posMax = Double.parseDouble((String) jsonObject.get("posMax"));
            posMin = Double.parseDouble((String) jsonObject.get("posMin"));
            vitesseMax = Double.parseDouble((String) jsonObject.get("vitesseMax"));
            vitesseMin = Double.parseDouble((String) jsonObject.get("vitesseMin"));
            startPos = Double.parseDouble((String) jsonObject.get("startPos"));
            startVit = Double.parseDouble((String) jsonObject.get("startVit"));
            coefPente = Double.parseDouble((String) jsonObject.get("coefPente"));
            precisionPosition = Integer.parseInt((String) jsonObject.get("precisionPosition"));
            precisionVitesse = Integer.parseInt((String) jsonObject.get("precisionVitesse"));
            epsilon = Double.parseDouble((String) jsonObject.get("epsilon"));
            gamma = Double.parseDouble((String) jsonObject.get("gamma"));
            name = (String) jsonObject.get("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public double getPosMax() {
		return posMax;
	}
	public void setPosMax(double posMax) {
		this.posMax = posMax;
	}
	public String toString(){
		return "name : "+name+"\n"
				+ "position : ["+posMin+";"+posMin+"]\n"
				+ "vitesse : ["+vitesseMin+";"+vitesseMax+"]\n"
				+ "initial State : v="+startVit+" x="+startPos+"\n"
				+ "coefPente = "+coefPente+"\n"
				+ "precision : "+precisionPosition+" positions, "+precisionVitesse+" vitesses \n"
				+ "epsilon ="+epsilon+"\n"
				+ "gamma = "+gamma;
				
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getEpsilon() {
		return epsilon;
	}
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}
	public double getGamma() {
		return gamma;
	}
	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
	public double getPosMin() {
		return posMin;
	}
	public void setPosMin(double posMin) {
		this.posMin = posMin;
	}
	public double getVitesseMax() {
		return vitesseMax;
	}
	public void setVitesseMax(double vitesseMax) {
		this.vitesseMax = vitesseMax;
	}
	public double getVitesseMin() {
		return vitesseMin;
	}
	public void setVitesseMin(double vitesseMin) {
		this.vitesseMin = vitesseMin;
	}
	public double getStartPos() {
		return startPos;
	}
	public void setStartPos(double startPos) {
		this.startPos = startPos;
	}
	public double getStartVit() {
		return startVit;
	}
	public void setStartVit(double startVit) {
		this.startVit = startVit;
	}
	public double getCoefPente() {
		return coefPente;
	}
	public void setCoefPente(double coefPente) {
		this.coefPente = coefPente;
	}
	public int getPrecisionPosition() {
		return precisionPosition;
	}
	public void setPrecisionPosition(int precisionPosition) {
		this.precisionPosition = precisionPosition;
	}
	public int getPrecisionVitesse() {
		return precisionVitesse;
	}
	public void setPrecisionVitesse(int precisionVitesse) {
		this.precisionVitesse = precisionVitesse;
	} 
	
}
