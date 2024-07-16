package gestionnotes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// classe principale de l'application
public class GestionNotes {
    private static Ecole ecole = new Ecole();  // creation de l'objet Ecole
    private static Scanner scanner = new Scanner(System.in); // creation d'un scanner pour la lecture des entrees utilisateur

    // Identifiant de l'administrateur par defaut
    private static final String ADMIN_USERNAME = "admin"; 
    private static final String ADMIN_PASSWORD = "password";

    public static void main(String[] args) {
        // Authentification de l'administrateur
        if (!connexion()) {
            System.out.println("Accès refusé.");
            return;
        }

        // Affichage du menu principal
        System.out.println("***************************************");
        System.out.println("*   Gestion Des Notes Des Etudiants   *");
        System.out.println("*                                     *");
        System.out.println("***************************************");

        while (true) {
            afficherMenu();
            int choix = scanner.nextInt(); // lecture du choix de l'utilisateur
            scanner.nextLine();  // consommer la nouvelle ligne
            switch (choix) {
                case 1:
                    ajouterClasse(); // appel la methode pour ajouter une classe
                    break;
                case 2:
                    ajouterEtudiant(); // appel la methode pour ajouter un etudiant
                    break;
                case 3:
                    ajouterMatiere(); // appel la methode pour ajouter une matiere
                    break;
                case 4:
                    enregistrerNotes(); // appel la methode pour enregistrer une note
                    break;
                case 5:
                    calculerMoyennes(); // appel la methode pour calculer les moyennes
                    break;
                case 6:
                    etablirClassement(); // appel la methode pour afficher le classement
                    break;
                case 7:
                    genererBulletins(); // appel la methode pour générer les bulletins
                    break;
                case 8:
                    System.out.println("Fin de session !"); //quitter l'application
                    return;
                default:
                    System.out.println("Choix invalide."); //gestion de choix non valide
            }
        }
    }

    // Connexion administrateur
    private static boolean connexion() {
    	System.out.println(" ***************** ");
    	System.out.println(" *  Connexion    * ");
    	System.out.println(" ***************** ");
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Connexion réussie.");
            return true;
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
            return false;
        }
    }

    
    //Affichage des options
    private static void afficherMenu() {
        System.out.println("1. Ajouter une classe");
        System.out.println("2. Ajouter un étudiant");
        System.out.println("3. Ajouter une matière");
        System.out.println("4. Enregistrer des notes");
        System.out.println("5. Calculer les moyennes");
        System.out.println("6. Établir le classement");
        System.out.println("7. Générer les bulletins");
        System.out.println("8. Quitter");
        System.out.print("Choisissez une option : ");
    }

    
    //methode 1 : ajout d'une nouvelle classe 
    private static void ajouterClasse() {
        System.out.print("Nom de la nouvelle classe : ");
        String nom = scanner.nextLine();
        Classe classe = new Classe(nom);
        ecole.ajouterClasse(classe);
     // System.out.println("Classe ajoutée : " + nom); // Message de débogage
    }

  //methode 2 : ajout d'un nouvel etudiant 
    private static void ajouterEtudiant() {
        System.out.print("Nom de la classe : ");
        String nomClasse = scanner.nextLine();
        Classe classe = ecole.trouverClasse(nomClasse);
        if (classe == null) {
            System.out.println("Classe non trouvée.");
       //   System.out.println("Classe non trouvée : " + nomClasse); // Message de débogage
            return;
        }

      //ajout d'un nouvel etudiant a une classe existante
        System.out.print("Nom du nouvel étudiant : ");
        String nomEtudiant = scanner.nextLine();
        Etudiant etudiant = new Etudiant(nomEtudiant);
        classe.ajouterEtudiant(etudiant);
     // System.out.println("Étudiant ajouté : " + nomEtudiant + " à la classe " + nomClasse); // Message de débogage
    }

  
  //methode 3 : ajouter une matiere 
    private static void ajouterMatiere() {
        System.out.print("Nom de la nouvelle matière : ");
        String nom = scanner.nextLine();
        Matiere matiere = new Matiere(nom);
        System.out.print("Nom de la classe : ");
        String nomClasse = scanner.nextLine();
        Classe classe = ecole.trouverClasse(nomClasse);
        if (classe == null) {
            System.out.println("Classe non trouvée.");
        //  System.out.println("Classe non trouvée : " + nomClasse); // Message de débogage
            return;
        }
        classe.ajouterMatiere(matiere);
     // System.out.println("Matière ajoutée : " + nom + " à la classe " + nomClasse); // Message de débogage
    }

  //methode 4 : enregistrer une note pour une matiere et une classe specifique
    private static void enregistrerNotes() {
        System.out.print("Nom de la classe : ");
        String nomClasse = scanner.nextLine();
        Classe classe = ecole.trouverClasse(nomClasse);
        if (classe == null) {
            System.out.println("Classe non trouvée.");
         // System.out.println("Classe non trouvée : " + nomClasse); // Message de débogage
            return;
        }
        System.out.print("Nom de la matière : ");
        String nomMatiere = scanner.nextLine();
        Matiere matiere = null;
        for (Matiere m : classe.getMatieres()) {
            if (m.getNom().equalsIgnoreCase(nomMatiere)) {
                matiere = m;
                break;
            }
        }
        if (matiere == null) {
        //  System.out.println("Matière non trouvée : " + nomMatiere); // Message de débogage
            System.out.println("Matière non trouvée.");
            return;
        }
        for (Etudiant etudiant : classe.getEtudiants()) {
            System.out.print("Note pour " + etudiant.getNom() + ":");
            double note = scanner.nextDouble();
            etudiant.ajouterNote(matiere, note);
        }
        scanner.nextLine(); // consommer la nouvelle ligne
    //  System.out.println("Notes enregistrées pour la classe " + nomClasse + " en " + nomMatiere); // Message de débogage
    }

 // methode 5 : calcule et affichage des moyennes des etudiants
    private static void calculerMoyennes() {
        System.out.print("Nom de la classe : ");
        String nomClasse = scanner.nextLine();
        Classe classe = ecole.trouverClasse(nomClasse);
        if (classe == null) {
        	// System.out.println("Classe non trouvée : " + nomClasse); // Message de débogage
            System.out.println("Classe non trouvée.");
            return;
        }
        for (Etudiant etudiant : classe.getEtudiants()) {
            System.out.println("Étudiant: " + etudiant.getNom());
            for (Matiere matiere : classe.getMatieres()) {
                double moyenne = etudiant.calculerMoyenne(matiere);
                System.out.println("  Moyenne en " + matiere.getNom() + ": " + moyenne);
            }
            double moyenneGenerale = etudiant.calculerMoyenneGenerale();
            System.out.println("  Moyenne générale:" + moyenneGenerale);
        }
    }

  //methode 6: afficher le classement des etudiants pour chaque matiere
    private static void etablirClassement() {
        System.out.print("Nom de la classe : ");
        String nomClasse = scanner.nextLine();
        Classe classe = ecole.trouverClasse(nomClasse);
        if (classe == null) {
        //  System.out.println("Classe non trouvée : " + nomClasse); // Message de débogage
            System.out.println("Classe non trouvée.");
            return;
        }
        System.out.print("Nom de la matière : ");
        String nomMatiere = scanner.nextLine();

     // Utilisation d'un tableau avec une seule case pour stocker la variable matiere
        final Matiere[] matiere = {null};
        for (Matiere m : classe.getMatieres()) {
            if (m.getNom().equalsIgnoreCase(nomMatiere)) {
                matiere[0] = m;
                break;
            }
        }
        if (matiere[0] == null) {
        	 // System.out.println("Matière non trouvée : " + nomMatiere); // Message de débogage
            System.out.println("Matière non trouvée.");
            return;
        }
        List<Etudiant> etudiants = classe.getEtudiants();
      //System.out.println("Liste des étudiants avant tri : "); // Message de débogage
        etudiants.sort((e1, e2) -> Double.compare(e2.calculerMoyenne(matiere[0]), e1.calculerMoyenne(matiere[0])));

        System.out.println("Classement pour la matière " + matiere[0].getNom() + ":");
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            System.out.println((i + 1) + ". " + etudiant.getNom() + " - Moyenne: " + etudiant.calculerMoyenne(matiere[0]));
        }
    }

    //Methode 7: génération des bulletins des etudiants
    private static void genererBulletins() {
        System.out.print("Nom de la classe : ");
        String nomClasse = scanner.nextLine();
        Classe classe = ecole.trouverClasse(nomClasse);
        if (classe == null) {
            System.out.println("Classe non trouvée.");
            return;
        }

        for (Etudiant etudiant : classe.getEtudiants()) {
            String nomFichier = etudiant.getNom().replaceAll("\\s+", "_") + "_Bulletin.txt";
            try (FileWriter writer = new FileWriter(nomFichier)) {
                writer.write("Bulletin de notes pour " + etudiant.getNom() + "\n");
                writer.write("Classe : " + classe.getNom() + "\n\n");

                for (Matiere matiere : classe.getMatieres()) {
                    double moyenne = etudiant.calculerMoyenne(matiere);
                    writer.write("Matière : " + matiere.getNom() + " - Moyenne : " + moyenne + "\n");
                }

                double moyenneGenerale = etudiant.calculerMoyenneGenerale();
                writer.write("\nMoyenne générale : " + moyenneGenerale + "\n");

                System.out.println("Bulletin généré pour " + etudiant.getNom() + " : " + nomFichier);
            } catch (IOException e) {
                System.out.println("Erreur lors de la génération du bulletin pour " + etudiant.getNom());
                e.printStackTrace();
            }
        }
    }
}
