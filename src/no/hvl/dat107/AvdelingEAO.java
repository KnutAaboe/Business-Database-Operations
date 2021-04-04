package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class AvdelingEAO {
	private EntityManagerFactory emf;
	
	public AvdelingEAO() {
		emf = Persistence.createEntityManagerFactory("firmaPU");
	}
	
	public Avdeling finnAvdelingMedId(int id) {
		EntityManager em = emf.createEntityManager();
		
		try {
			return em.find(Avdeling.class, id);
		} finally {
			em.close();
		}
	}
	
	public List<Ansatt> hentAlleAnsatteFraAvdeling(int avdId){
		EntityManager em = emf.createEntityManager();
		Avdeling avdeling = null;
		
		try {
			
			avdeling = em.find(Avdeling.class, avdId);
			
			if(avdeling != null) {
				
				return avdeling.hentAlleAnsatte();
				
			}else {
				return null;
			}
			
		} finally {
			em.close();
		}
	}
	
	public void leggTilAvdeling(String navn, int sjef) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Ansatt ansSjef;
		
		try {
			tx.begin();
			
			ansSjef = em.find(Ansatt.class, sjef);
			
			Avdeling gamleAvdeling = em.find(Avdeling.class, ansSjef.getAvd().getAvdId());
			
			gamleAvdeling.fjernAnsatt(ansSjef);
			
			Avdeling avdeling = new Avdeling(navn, sjef);
			
			ansSjef.leggTilAvdeling(avdeling);
			
			avdeling.leggTilAnsatt(ansSjef);
			
			em.merge(gamleAvdeling);
			em.merge(ansSjef);
			em.persist(avdeling);
			
			tx.commit();
		} catch(Throwable e) {
			System.out.println("Feilmelding leggTilAvdeling:");
			System.out.println(e.getMessage());
			if(tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
		
	}
}
