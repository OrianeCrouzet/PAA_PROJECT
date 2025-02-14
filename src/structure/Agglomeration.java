package structure;

import java.util.ArrayList;

/**
 * Cette classe permet de representer une agglomeration de Villes.
 */
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class Agglomeration {

    /**
     * "villes" est une liste qui contient toutes les villes de l'agglomeration.
     */
    private final ArrayList <Ville> villes;

    /**
     * Initialise Agglomeration avec l'attribut taille.
     * @param taille Nombre de ville.
     * @throws IllegalArgumentException si la taille est invalide.
     */
    public Agglomeration(int taille) {
        this.villes = new ArrayList<>();
    }
    
    /**
     * Permet d'ajouter une ville à l'agglomération.
     * @param nom Nom de la ville à ajouter
     */
    public void addVille(String nom) {
        this.villes.add(new Ville(nom.toLowerCase()));
    }

    /**
     * Si les deux villes sont bien enregistrées dans l'agglomération : on ajoute dans la liste des voisins
     * de chaque ville son voisin respectif.
     * @param ville1 Première ville
     * @param ville2 Deuxième ville
     */
    public void addRoute(Ville ville1, Ville ville2) {
        int indiceVille1 = this.villes.indexOf(ville1);
        int indiceVille2 = this.villes.indexOf(ville2);
        if (indiceVille1 == indiceVille2) {
            System.out.println("On ne peut pas cr\u00E9er de route vers soi-m\u00EAme");
        } else if (indiceVille1 != -1 && indiceVille2 != -1) {
            ville1.getVoisins().add(ville2);
            ville2.getVoisins().add(ville1);
            System.out.println("La route entre "+Ville.capitalize(ville1.getNom())+"-"+Ville.capitalize(ville2.getNom())+" A bien \u00E9t\u00E9 cr\u00E9er");
        } else {
            throw new IllegalArgumentException("L'une ou les deux villes rentr\u00E9es ne sont pas dans l'agglom\u00E9ration");
        }
    }

    /**
     * Pour afficher à la fin de chaque action de l'utilisateur, les villes qui possèdent un point de recharge.
     * @return String
     */
    public String afficherZonesDeRecharge() {
        StringBuilder sb = new StringBuilder();

        for (Ville v : this.villes) {
            if (v.getABorne()) {
                sb.append(v.getNom()).append(" ");
            }
        }

        return sb.toString();
    }

    /**
     * Permet d'ajouter une borne de recharge. On vérifie avant que la ville fasse bien partie de l'agglomération.
     * @param ville Ville dans laquelle on doit ajouter une borne de recharge
     */
    public void addRecharge(Ville ville) {
        if (ville != null && this.villes.contains(ville)) {
            ville.addBorne();
        } else {
            throw new IllegalArgumentException((ville==null?null:"La ville "+ville.getNom()) + " n'existe pas dans l'agglom\u00E9ration.");
        }
    }

    /**
     * Permet de supprimer une borne de recharge. On vérifie avant que la ville fasse bien partie de l'agglomération.
     * @param ville Ville dans laquelle on doit enlever une borne de recharge
     */
    public void deleteRecharge(Ville ville) {
        if (ville != null && this.villes.contains(ville)) {
            ville.deleteBorne();
        } else {
            throw new IllegalArgumentException((ville==null?null:"La ville "+ville.getNom()) + " n'existe pas dans l'agglom\u00E9ration.");
        }
    }

    /**
     * Permet de récupérer la Ville qui porte le nom rentré en paramètres.
     * @param nom Nom dont on veut vérifier l'appartenance à un objet Ville, et retourner cet objet Ville, null sinon
     */
    public Ville getVilleByName(String nom) {
        if (nom.equals("-1"))return null;
        for (Ville ville : villes) {
            if (nom.equalsIgnoreCase(ville.getNom())) {
                return ville;
            }
        }
        return null;
    }

    /**
     * Permet de retrouver une Ville dans l'agglomeration avec son index connu dans "villes".
     * @param index L'index dans l'agglomeration de la ville que l'on veut recuperer.
     * @return La ville a l'index donne.
     */
    public Ville getVilleByIndex(int index) {
        return villes.get(index);
    }

    /**
     * Pour tenter de se rapprocher au mieux du probleme, nous avons choisi d'afficher le cout
     * des bornes pour l'agglomeration. Le prix arbitraire d'une borne est de 1300$.
     * Cela permet de faire un leger comparatif entre les algorithmes en cas de besoin.
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Co\u00FBt des bornes : ");
        int coutBornes = 0;
        for (Ville v : this.villes) {
            if (v.getABorne()) {
                coutBornes += 1;
            }
        }
        sb.append(coutBornes * 1300);

        return sb.append("$").toString();
    }

    /**
     * Permet d'avoir acces aux Villes de l'agglomeration ailleurs dans le code.
     * @return ArrayList<Ville> La liste des villes de l'agglomération
     */
    public ArrayList<Ville> getVilles() {
        return this.villes;
    }

    /**
     * Permet d'afficher l'agglomeration.
     */
    public void afficheAgglomeration(){
        System.out.println("Les villes pr\u00E9sente dans l'agglom\u00E9ration sont : ");
        for (Ville i: villes) {
            System.out.println(i);
        }
    }

}
