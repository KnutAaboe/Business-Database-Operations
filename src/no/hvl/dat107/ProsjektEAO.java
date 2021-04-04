package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProsjektEAO {
	EntityManagerFactory emf;
	int timeTotal = 0;
	
	
	public ProsjektEAO() {
		emf  = Persistence.createEntityManagerFactory("firmaPU");
	}
	
	public void skrivUtProsjekt(int prosId) {
		EntityManager em = emf.createEntityManager();
		Prosjekt prosjekt;
		try {
			
			prosjekt = em.find(Prosjekt.class, prosId);
			
			List<Prosjektdeltakelse> deltakelser = prosjekt.getDeltakelser();
			
			System.out.println("Prosjekt: " + prosjekt.getNavn() + ", " + prosjekt.getBeskrivelse());
			System.out.println("Ansatte:");
			
			
			deltakelser.forEach(d -> {
				Ansatt a = d.getAnsatt();
				timeTotal += d.getTimer();
				System.out.println(a.geteNavn() + ", " + a.getfNavn());
				System.out.println(d.getRolle() + ", " + d.getTimer() + " timer");
			});
			
			System.out.println("Totalt " + timeTotal + " timer\n");
			
		} catch(Throwable e) {
			System.out.println("Feilmelding skrivUtProsjekt");
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
	}
	
	
	public void leggTilProsjekt(String navn, String beskrivelse) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Prosjekt prosjekt = new Prosjekt(navn, beskrivelse);
			
			em.persist(prosjekt);
			
			tx.commit();
		} catch(Throwable e) {
			System.out.println("Feilmelding leggTilProsjekt:");
			System.out.println(e.getMessage());
			
			if(tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
		
	}
}
