package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "oblig3")
@IdClass(ProsjektdeltakelsePK.class)
public class Prosjektdeltakelse {
	
	private int timer;
	private String rolle;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "prosId")
	private Prosjekt prosjekt;
	

	@Id
	@ManyToOne
	@JoinColumn(name = "ansId")
	private Ansatt ansatt;
	
	public Prosjektdeltakelse() {
		
	}
	
	public Prosjektdeltakelse(Ansatt ansatt, Prosjekt prosjekt, int timer, String rolle) {
		this.ansatt = ansatt;
		this.prosjekt = prosjekt;
		this.timer = timer;
		this.rolle = rolle;
		
		ansatt.leggTilProsjektdeltakelse(this);
		prosjekt.leggTilProsjektdeltakelse(this);
	}
	
	@Override
	public String toString() {
		return ansatt.getfNavn() + " " + ansatt.geteNavn() + ", " + prosjekt.getNavn() + ", " + timer + ", " + rolle;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void leggTilTimer(int timer) {
		this.timer += timer;
	}
	
	public Ansatt getAnsatt() {
		return ansatt;
	}
	
	public String getRolle() {
		return rolle;
	}
	
	public void setRolle(String rolle) {
		this.rolle = rolle;
	}
}
