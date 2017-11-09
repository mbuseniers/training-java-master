package interfaceProjet;

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.CompanyService;
import services.ComputerService;
import utils.Utils;
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	
	public static void main(String[] args) throws SQLException {

		System.out.println("Bienvenue sur le projet Computer Database");
		boolean continuer = true;
		ComputerService CptService;
		CompanyService companyService;

	    LOGGER.info("Demarrage main");
		
		while(continuer)
		{
			System.out.println("Quel action souhaitez vous effectuer ?");
			System.out.println("1 - Lister les Computer");
			System.out.println("2 - Ajouter un Computer");
			System.out.println("3 - Modifier un Computer");
			System.out.println("4 - Supprimer un Computer");
			System.out.println("5 - Afficher les Company");
			System.out.println("6 - Supprimer une company et les computer liés à cette company");
			System.out.println("7 - Quitter");
			
			int choix= Utils.scanInt();
			
			
			switch(choix) {
			
			case 1 : 
				
				
				break;
				
			case 2 : 
				
				
				break;
				
			case 3 : 
				
				break;
				
			case 4 : 
				
				break;
				
			case 5 :
				CompanyService cs = CompanyService.getInstance();
				
				System.out.println("Liste des Company : ");

				cs.getCompanies().stream().forEach(System.out::println);
				break;
				
			case 6 : 
				Scanner sc = new Scanner(System.in);
				companyService = CompanyService.getInstance();
				CptService = ComputerService.getInstance();
				companyService.getCompanies().stream().forEach(System.out::println);
				
				System.out.println("Saisir le jour : ");
				int numeroCompany = sc.nextInt();
				
				boolean isCompany = companyService.checkIdCompany(numeroCompany);
				
				if(isCompany) {
					
					boolean deleteCompanyOk = companyService.deleteCompanyById(numeroCompany);
					
					if(!deleteCompanyOk) {
						System.out.println("Erreur suppression company et computers OK !");
					}else{
						System.out.println("Suppression company et computers OK !");
					}
					
				}
				else {
					System.out.println("La company n'existe pas");
				}

				
				break;
				
			case 7 : 
				continuer = false;
				System.out.println("Bye Bye");
				break;
			default : 
				System.out.println("Erreur de saisie");
				break;
			
			}
			
			
		}
			

	}

}
