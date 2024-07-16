package gestionnotes;

import java.util.*;
import java.util.List;
import java.util.ArrayList;

//la classe Ecole contient les classes

public class Ecole {
    private List<Classe> classes; 

    public Ecole() {
        this.classes = new ArrayList<>();
    }

    public void ajouterClasse(Classe classe) {
        classes.add(classe);
       // System.out.println("Classe ajoutée : " + classe.getNom()); // Message de débogage
    }

    public Classe trouverClasse(String nomClasse) {
       // System.out.println("Recherche de la classe : " + nomClasse); // Message de débogage
        for (Classe c : classes) {
           // System.out.println("Classe trouvée : " + c.getNom()); // Message de débogage
            if (c.getNom().equalsIgnoreCase(nomClasse)) {
               // System.out.println("Classe correspondante trouvée : " + c.getNom()); // Message de débogage
                return c;
            }
        }
       // System.out.println("Classe non trouvée : " + nomClasse); // Message de débogage
        return null;
    }

    public List<Classe> getClasses() {
        return classes;
    }

    
}