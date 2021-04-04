package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = "";
		
		AnsattEAO ansatteao = new AnsattEAO();
		AvdelingEAO avdelingeao = new AvdelingEAO();
		ProsjektEAO prosjekteao = new ProsjektEAO();
		
		
		String hjelpetekst = "\nFå opp denne teksten: -1\n"
				+ "Avslutt programmet: 0\n"
				+ "Søk etter ansatt med id: 1\n"
				+ "Søk etter ansatt med brukernavn: 2\n"
				+ "Utlisting av alle ansatte: 3\n"
				+ "Oppdater ansatt stilling: 4\n"
				+ "Oppdater ansatt lønn: 5\n"
				+ "Legg inn ny ansatt: 6\n"
				+ "Søk etter avdeling med id: 7\n"
				+ "Utlisting av ansatte i avdeling: 8\n"
				+ "Ansatt bytte avdeling: 9\n"
				+ "Legg inn ny avdeling: 10\n"
				+ "Legg inn nytt prosjekt: 11\n"
				+ "Registrer prosjektdeltakelse: 12\n"
				+ "Føre timer for ansatt i prosjekt: 13\n"
				+ "Utskrift av info for prosjekt: 14\n";
		
		System.out.println(hjelpetekst);
		
		while(!input.equals("0")) {
			
			System.out.println("Skriv inn command:");
			
			input = sc.nextLine();
			
			int ansId, prosId;
			Ansatt ansatt;
			Avdeling avdeling;
			String navn;
			
			
			
			switch(input) {
				case "-1":
					System.out.println(hjelpetekst);
					break;
				case "0":
					System.out.println("Sjallabais");
					break;
				case "1": 
					System.out.println("Skriv inn ansatt id");
					input = sc.nextLine();
					System.out.println(ansatteao.finnAnsattMedId(Integer.parseInt(input)).toString());
					break;
				case "2":
					System.out.println("Skriv inn ansatt brukernavn");
					input = sc.nextLine();
					System.out.println(ansatteao.finnAnsattMedBrukerNavn(input).toString());
					break;
				case "3":
					List<Ansatt> ansatte = ansatteao.finnAlleAnsatte();
					ansatte.forEach(a -> System.out.println(a.toString()));
					break;
				case "4":
					System.out.println("Skriv inn ansatt id");
					input = sc.nextLine();
					ansId = Integer.parseInt(input);
					System.out.println("Skriv inn ny stilling");
					input = sc.nextLine();
					ansatteao.oppdaterStilling(ansId, input);
					break;
				case "5":
					System.out.println("Skriv inn ansatt id");
					input = sc.nextLine();
					ansId = Integer.parseInt(input);
					System.out.println("Skriv inn ny lønn");
					input = sc.nextLine();
					ansatteao.oppdaterLonn(ansId, Integer.parseInt(input));
					break;
				case "6":
					ansatt = new Ansatt();
					System.out.println("Skriv inn brukernavn");
					input = sc.nextLine();
					ansatt.setbNavn(input);
					System.out.println("Skriv inn fornavn");
					input = sc.nextLine();
					ansatt.setfNavn(input);
					System.out.println("Skriv inn etternavn");
					input = sc.nextLine();
					ansatt.seteNavn(input);
					System.out.println("Skriv inn ansattelsesdato (yyyy-mm-dd)");
					input = sc.nextLine();
					ansatt.setAnsDato(LocalDate.parse(input));
					System.out.println("Skriv inn stilling");
					input = sc.nextLine();
					ansatt.setStilling(input);
					System.out.println("Skriv inn lønn");
					input = sc.nextLine();
					ansatt.setMndLonn(Integer.parseInt(input));
					System.out.println("Skriv inn avdeling");
					input = sc.nextLine();
					ansatteao.leggTilAnsatt(ansatt, Integer.parseInt(input));
					break;
				case "7":
					System.out.println("Skriv inn avdeling id");
					input = sc.nextLine();
					System.out.println(avdelingeao.finnAvdelingMedId(Integer.parseInt(input)).toString());
					break;
				case "8":
					System.out.println("Skriv inn avdeling id");
					input = sc.nextLine();
					avdeling = avdelingeao.finnAvdelingMedId(Integer.parseInt(input));
					avdeling.hentAlleAnsatte().forEach(a -> System.out.println(a.toString()));
					break;
				case "9":
					System.out.println("Skriv inn ansatt id");
					input = sc.nextLine();
					ansId = Integer.parseInt(input);
					System.out.println("Skriv inn avdeling id");
					input = sc.nextLine();
					ansatteao.byttAvdeling(ansId, Integer.parseInt(input));
					break;
				case "10":
					System.out.println("Skriv inn navn på avdeling");
					navn = sc.nextLine();
					System.out.println("Skriv inn ansatt id til sjef");
					ansId = Integer.parseInt(sc.nextLine());
					avdelingeao.leggTilAvdeling(navn, ansId);
					break;
				case "11":
					System.out.println("Skriv inn navn på prosjekt");
					navn = sc.nextLine();
					System.out.println("Skriv inn beskrivelse på prosjekt");
					input = sc.nextLine();
					prosjekteao.leggTilProsjekt(navn, input);
					break;
				case "12":
					System.out.println("Skriv inn prosjekt id");
					input = sc.nextLine();
				case "13":
					System.out.println("Skriv inn ansatt id");
					ansId = Integer.parseInt(sc.nextLine());
					System.out.println("Skriv inn prosjekt id");
					prosId = Integer.parseInt(sc.nextLine());
					System.out.println("Skriv inn antall timer");
					int timer = Integer.parseInt(sc.nextLine());
					ansatteao.foreTimer(ansId, prosId, timer);
					break;
				case "14":
					System.out.println("Skriv inn prosjekt id");
					prosId = Integer.parseInt(sc.nextLine());
					prosjekteao.skrivUtProsjekt(prosId);
					break;
				default:
					System.out.println("Ukjent input, prøv igjen");
			}
		}
		
		sc.close();
	}

}
