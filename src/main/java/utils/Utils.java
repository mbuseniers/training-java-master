package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Company;
import services.CompanyService;

public class Utils {

	/**
	 * 	Gere la saisie d'un entier au clavier
	 * @return int avec pour valeur l'entier saisie, ou 0 en cas de mauvaise saisie
	 */
	public static int scanInt() {

		try {
			Scanner sc = new Scanner(System.in);
			int choix = sc.nextInt();
			return choix;
		} catch (InputMismatchException ime) {
			System.out.println("Valeur saisie non numérique\n" + "ou hors des limites int.");
			return 0;
		}
	}

	/**
	 * 	Gere la selection d'une company
	 * @return int => l'ID de la company choisie
	 */
	public static int chooseCompany() {

		boolean valide = false;
		int choix=0;
		CompanyService cs = CompanyService.getInstance();
		ArrayList<Company> listCompany = cs.getCompanies();

		do {
			try {
				
				listCompany.stream().forEach(System.out::println);

				System.out.println("Saisir l'ID du fabriquant entre 1 et " + listCompany.size() );

				Scanner sc = new Scanner(System.in);
				choix = sc.nextInt();

				if (choix > 0 && choix <= listCompany.size()) {
					valide = true;
				} else {
					System.out.println("Mauvaise saisie de l'ID du fanbriquant");
				}

				// sc.close();
			} catch (InputMismatchException ime) {
				System.out.println("Valeur saisie non numérique\n" + "ou hors des limites int.");
			}

		} while (!valide);

		return choix;
	}
	
	
}
