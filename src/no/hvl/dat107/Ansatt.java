package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "oblig3", name = "ansatt")
public class Ansatt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ansId;
	private String bNavn;
	private String fNavn;
	private String eNavn;
	private LocalDate ansDato;
	private String stilling;
	private int mndLonn;
	
	
	@OneToMany(mappedBy = "ansatt")
	private List<Prosjektdeltakelse> deltakelser;
	
	@ManyToOne
	@JoinColumn(name = "avd", referencedColumnName = "avdId")
	private Avdeling avdeling;
	
	
	public Ansatt() {
		
	}
	
	public Ansatt(String bNavn, String fNavn, String eNavn, LocalDate ansDato, String stilling, int mndLonn) {
		
		this.bNavn = bNavn;
		this.fNavn = fNavn;
		this.eNavn = eNavn;
		this.ansDato = ansDato;
		this.stilling = stilling;
		this.mndLonn = mndLonn;
	}

	@Override
	public String toString() {
		return "Ansatt [ansId=" + ansId + ", bNavn=" + bNavn + ", fNavn=" + fNavn + ", eNavn=" + eNavn + ", ansDato="
				+ ansDato + ", stilling=" + stilling + ", mndLonn=" + mndLonn + ", prosj=" + deltakelser.toString()
				+ ", avdId=" + avdeling.getAvdId() + "]";
	}
	
	public void leggTilAvdeling(Avdeling avd) {
		avdeling = avd;
	}
	
	public void leggTilProsjektdeltakelse(Prosjektdeltakelse prosjDel) {
		deltakelser.add(prosjDel);
	}
	
	public void fjernProsjektdeltakelse(Prosjektdeltakelse prosjDel) {
		deltakelser.remove(prosjDel);
	}
	
	public Avdeling getAvd() {
		return avdeling;
	}
	
	public String getbNavn() {
		return bNavn;
	}
	public void setbNavn(String bNavn) {
		this.bNavn = bNavn;
	}
	public String getfNavn() {
		return fNavn;
	}
	public void setfNavn(String fNavn) {
		this.fNavn = fNavn;
	}
	public String geteNavn() {
		return eNavn;
	}
	public void seteNavn(String eNavn) {
		this.eNavn = eNavn;
	}
	public LocalDate getAnsDato() {
		return ansDato;
	}
	public void setAnsDato(LocalDate ansDato) {
		this.ansDato = ansDato;
	}
	public String getStilling() {
		return stilling;
	}
	public void setStilling(String stilling) {
		this.stilling = stilling;
	}
	public int getMndLonn() {
		return mndLonn;
	}
	public void setMndLonn(int mndLonn) {
		this.mndLonn = mndLonn;
	}
	public int getAnsId() {
		return ansId;
	}
	
}
