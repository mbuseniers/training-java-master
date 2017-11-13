package interfaceProjet;

import java.sql.SQLException;
import java.util.Scanner;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Computer;
import services.CompanyService;
import services.ComputerService;
import utils.Utils;
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	@Autowired
	Utils utils;
	
	public static void main(String[] args) throws SQLException {

		System.out.println("Bienvenue sur le projet Computer Database");

	
		
		ApplicationContext context = new ClassPathXmlApplicationContext("/Beans.xml");
	    Computer obj = (Computer) context.getBean("computer");
	    obj.getId();
	    
		
		boolean continuer = true;
		ComputerService CptService;
		CompanyService companyService=null;

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
			
			int choix=0;//= utils.scanInt();
			
			
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
				
				System.out.println("Liste des Company : ");

				companyService.getCompanies().stream().forEach(System.out::println);
				break;
				
			case 6 : 
				Scanner sc = new Scanner(System.in);
				//companyService; // = CompanyService.getInstance();
				//CptService = ComputerService.getInstance();
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
