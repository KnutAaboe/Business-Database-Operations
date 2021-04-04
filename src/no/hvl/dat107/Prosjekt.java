package no.hvl.dat107;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "oblig3", name = "prosjekt")
public class Prosjekt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosId;
	private String navn;
	private String beskrivelse;
	
	
	@OneToMany(mappedBy = "prosjekt")
	private List<Prosjektdeltakelse> deltakelser;
	
	public Prosjekt() {
		
	}
	
	public Prosjekt(String navn, String beskrivelse) {
		this.navn = navn;
		this.beskrivelse = beskrivelse;
	}
	
	public void leggTilProsjektdeltakelse(Prosjektdeltakelse prosjDel) {
		deltakelser.add(prosjDel);
	}
	
	public void fjernProsjektdeltakelse(Prosjektdeltakelse prosjDel) {
		deltakelser.remove(prosjDel);
	}

	public int getProsId() {
		return prosId;
	}
	
	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	public List<Prosjektdeltakelse> getDeltakelser(){
		return deltakelser;
	}
	
	
}
