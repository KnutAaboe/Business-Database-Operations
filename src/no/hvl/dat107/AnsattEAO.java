package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AnsattEAO {
	EntityManagerFactory emf;
	
	public AnsattEAO() {
		emf = Persistence.createEntityManagerFactory("firmaPU");
	}
	
	public void foreTimer(int ansId, int prosId, int timer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			ProsjektdeltakelsePK pk = new ProsjektdeltakelsePK(ansId, prosId);
			
			Prosjektdeltakelse pd = em.find(Prosjektdeltakelse.class, pk);
			
			pd.leggTilTimer(timer);
			
			em.merge(pd);
			
			tx.commit();
		} catch(Throwable e) {
			System.out.println("Feilmelding foreTimer");
			System.out.println(e.getMessage());
			if(tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}
	
	public void registrerProsjektdeltakelse(int ansId, int prosId, String rolle) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Ansatt ansatt = em.find(Ansatt.class, ansId);
			Prosjekt prosjekt = em.find(Prosjekt.class, prosId);
			
			ansatt = em.merge(ansatt);
			prosjekt = em.merge(prosjekt);
			
			Prosjektdeltakelse pd = new Prosjektdeltakelse(ansatt, prosjekt, 0, rolle);
			
			ansatt.leggTilProsjektdeltakelse(pd);
			prosjekt.leggTilProsjektdeltakelse(pd);
			
			em.persist(pd);
			
			tx.commit();
		} catch(Throwable e) {
			System.out.println("Feilmelding registrerProsjektdeltakelse");
			System.out.println(e.getMessage());
			if(tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
		
	}
	
	public Ansatt finnAnsattMedId(int id) {
		EntityManager em = emf.createEntityManager();
		
		try {
			return em.find(Ansatt.class, id);
		} finally {
			em.close();
		}
	}
	
	public void byttAvdeling(int ansId, int avdId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Ansatt ansatt = null;
		Avdeling tidlAvd = null;
		
		try {
			ansatt = em.find(Ansatt.class, ansId);
			if(ansatt != null) {
				tidlAvd = em.find(Avdeling.class, ansatt.getAvd().getAvdId());
				
				if(tidlAvd.getSjef() == ansatt.getAnsId()) {
					System.out.println("Ansatt er sjef i avdeling " + tidlAvd.getAvdId() + ": " + tidlAvd.getNavn() + ", og kan derfor ikke bytte avdeling");
				}else {
					tx.begin();
					
					Avdeling nyAvd = em.find(Avdeling.class, avdId);
					
					if(nyAvd != null) {
						ansatt.leggTilAvdeling(nyAvd);
						nyAvd.leggTilAnsatt(ansatt);
					}
					
					em.merge(ansatt);
					em.merge(nyAvd);
					
					tx.commit();
				}
			}
		} catch(Throwable e) {
			System.out.println("Feilmelding byttAvdeling:");
			System.out.println(e.getMessage());
			if(tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}
	
	public Ansatt finnAnsattMedBrukerNavn(String bNavn) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Ansatt ansatt = null;
		
		try {
			tx.begin();
			
			TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.bNavn = :bNavn", Ansatt.class);
			query.setParameter("bNavn", bNavn);
			
			ansatt = query.getSingleResult();
			
			tx.commit();
		} catch (Throwable e) {
			System.out.println("Feilmelding finnAnsattMedBrukerNavn:");
			System.out.println(e.toString());
			if(tx.isActive()) {
				tx.rollback();
			}
		}finally {
			em.close();
		}
		
		return ansatt;
	}
	
	public List<Ansatt> finnAlleAnsatte() {
		List<Ansatt> ansatte = null;
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		String queryString = "SELECT a FROM Ansatt a";
		
		try {
			tx.begin();
			
			TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
			
			ansatte = query.getResultList();
			
			tx.commit();
		} catch(Throwable e) {
			if(tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
		
		return ansatte;
	}
	
	public void oppdaterStilling(int ansId, String stilling) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Ansatt ansatt = null;
		try {
			tx.begin();
			
			ansatt = em.find(Ansatt.class, ansId);
			
			if(!ansatt.equals(null)) {
				ansatt.setStilling(stilling);
				em.merge(ansatt);
			}
			
			tx.commit();
		} catch (Throwable e) {
			System.out.println("Feilmelding oppdaterStilling:");
			System.out.println(e.getMessage());
			
			if(tx.isActive()) {
				tx.rollback();
			}
		}finally {
			em.close();
		}
	}
	
	public void oppdaterLonn(int ansId, int lonn) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Ansatt ansatt = null;
		try {
			tx.begin();
			
			ansatt = em.find(Ansatt.class, ansId);
			
			if(!ansatt.equals(null)) {
				ansatt.setMndLonn(lonn);
				em.merge(ansatt);
			}
			
			tx.commit();
		} catch (Throwable e) {
			System.out.println("Feilmelding oppdaterLonn:");
			System.out.println(e.getMessage());
			
			if(tx.isActive()) {
				tx.rollback();
			}
		}finally {
			em.close();
		}
	}
	
	public void leggTilAnsatt(Ansatt ansatt, int avdId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Avdeling avdeling = null;
		
		try {
			avdeling = em.find(Avdeling.class, avdId);
			
			if(avdeling != null) {
				tx.begin();
				
				ansatt.leggTilAvdeling(avdeling);
				
				em.persist(ansatt);
				
				tx.commit();
			}
			
		} catch(Throwable e) {
			System.out.println("Feilmelding leggTilAnsatt:");
			System.out.println(e.getMessage());
			
			if(tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}
}
