package no.hvl.dat107;

@SuppressWarnings("unused")
public class ProsjektdeltakelsePK {

	private int ansatt;
	
	private int prosjekt;
	
	public ProsjektdeltakelsePK() {
		
	}
	
	public ProsjektdeltakelsePK(int ansId, int prosId) {
		this.ansatt = ansId;
		this.prosjekt = prosId;
	}
}
