package ascentionVoiture;

public class Etat {
	private double position, vitesse, value, gain;
	private String action;
	public Etat(){
		this(0,0,0,"default");
	}
	public Etat(double pos,double vit, double val, String a){
		this.position = pos;
		this.vitesse = vit;
		calcGain();
		this.value = gain + val;
		this.action = a;
		
	}
	@Override
	public String toString() {
		return "Etat [position=" + position + ", vitesse=" + vitesse + ", value=" + value + ", action=" + action + "]";
	}
	public boolean isBetterThan(Etat e){
		if(this.gain > e.getGain()){
			return true;
		}else{
			return false;
		}
	}
	public double getGain() {
		return gain;
	}
	public void setGain(double gain) {
		this.gain = gain;
	}
	private void calcGain(){
		if(position > 0.55 && position < 0.6){
			gain = 0.07-Math.abs(vitesse);
		}
		if(position > -1.2 && position < -1.15){
			gain = -1;
		}
	}
	public double getPosition() {
		return position;
	}
	public void setPosition(double position) {
		this.position = position;
	}
	public double getVitesse() {
		return vitesse;
	}
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
