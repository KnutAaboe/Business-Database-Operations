package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "oblig3", name = "avdeling")
public class Avdeling {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int avdId;
	private String navn;
	private int sjef;
	
	@OneToMany(mappedBy = "avdeling", fetch = FetchType.EAGER)
	private List<Ansatt> ansatte = new ArrayList<Ansatt>();
	
	public Avdeling() {
		
	}
	
	public Avdeling(String navn, int sjef) {
		this.navn = navn;
		this.sjef = sjef;
	}
	
	public List<Ansatt> hentAlleAnsatte(){
		Ansatt sj = null;
		
		for(int i = 0; i < ansatte.size() && sj != null; i++) {
			if (ansatte.get(i).getAnsId() == sjef) {
				sj = ansatte.remove(i);
			}
		}
		if(sj != null) {
			ansatte.add(0, sj);
		}

		return ansatte;
	}

	public void leggTilAnsatt(Ansatt a) {
		ansatte.add(a);
	}
	
	public void fjernAnsatt(Ansatt a) {
		ansatte.remove(a);
	}
	
	public int getAvdId() {
		return avdId;
	}
	
	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public int getSjef() {
		return sjef;
	}

	public void setSjef(int sjef) {
		this.sjef = sjef;
	}

	@Override
	public String toString() {
		return "Avdeling [avdId=" + avdId + ", navn=" + navn + ", sjef=" + sjef + ", ansatte=" + ansatte + "]";
	}
	
	
	
	
}
