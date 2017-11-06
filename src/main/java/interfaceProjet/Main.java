package interfaceProjet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import model.Computer;
import services.CompanyService;
import services.ComputerService;
import utils.Utils;
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	
	public static void main(String[] args) throws SQLException {

		System.out.println("Bienvenue sur le projet Computer Database");
		boolean continuer = true;
		int result=0;
		ComputerService CptService;


	    LOGGER.info("Demarrage main");
		
		while(continuer)
		{
			System.out.println("Quel action souhaitez vous effectuer ?");
			System.out.println("1 - Lister les Computer");
			System.out.println("2 - Ajouter un Computer");
			System.out.println("3 - Modifier un Computer");
			System.out.println("4 - Supprimer un Computer");
			System.out.println("5 - Afficher les Company");
			System.out.println("6 - Quitter");
			
			int choix= Utils.scanInt();
			
			
			switch(choix) {
			
			case 1 : 
				CptService = ComputerService.getInstance();
				
				System.out.println("Liste des Computers : ");
				
				CptService.getComputers().stream().forEach(System.out::println);
				
				break;
				
			case 2 : 
				
				CptService = ComputerService.getInstance();
				result = CptService.addComputer();
				
				if(result == 1 ) {
					System.out.println("Ajout Ok");
				} else {
					System.out.println("Ajout non effectué");
				}
				
				break;
				
			case 3 : 
				
				CptService = ComputerService.getInstance();
				result = CptService.updateComputer();
				
				if(result == 1 ){
					System.out.println("Update Ok");
				} else {
					System.out.println("Update non effectué");
				}
				
				break;
				
			case 4 : 
				
				CptService = ComputerService.getInstance();
				boolean res = CptService.deleteComputer();
				if(res) {
					System.out.println("Delete Ok");
				} else {
					System.out.println("Delete non effectué");
				}
				
				break;
				
			case 5 :
				CompanyService cs = CompanyService.getInstance();
				
				System.out.println("Liste des Company : ");

				cs.getCompanies().stream().forEach(System.out::println);;
				break;
				
			case 6 : 
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
