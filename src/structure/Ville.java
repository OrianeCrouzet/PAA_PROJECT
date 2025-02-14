package structure;

import java.util.ArrayList;

/**
 * Classe representant une Ville.
 * La Ville possede un nom, un borne ou non, et une liste de tous ses voisins.
 */
@SuppressWarnings("ALL")
public class Ville {

    private final String nom;
    private boolean aBorne;
    private final ArrayList<Ville> voisins;

    /**
     * Permet d'initialiser une Ville avec son nom.
     * On vérifie egalement que le nom de la ville n'est pas null, vide ou composé uniquement d'espaces
     * @param nom correspond au nom de la ville
     * @throws IllegalArgumentException si le nom est null
     */
    public Ville(String nom) throws IllegalArgumentException {
        if (nom != null && !nom.isEmpty() && !nom.trim().isEmpty()) {
            this.nom = nom.toLowerCase();
            this.aBorne = true;
            this.voisins = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Le nom de la ville rentr\u00E9e en argument est null");
        }
    }

    /**
     * Ajoute une borne de recharge à la ville courante.
     */
    public void addBorne() {
        if (aBorne){
            System.out.println(capitalize(nom)+" Possede d\u00E9ja une borne");
        }else {
            System.out.println("La borne dans la ville "+capitalize(nom)+" a \u00E9t\u00E9 ajout\u00E9es");
            this.aBorne = true;
        }
    }

    /**
     * On vérifie qu'au moins 1 voisin de la ville courante possède une borne de recharge.
     * La ville ne se verifie pas sois-meme.
     * Si c'est le cas, on peut supprimer la borne de recharge de la ville courante.
     * → Permet de respecter la contrainte d'accessibilité.
     */
    public void deleteBorne() {
        if(this.verifierAccessibilite()){
            this.aBorne = false;
            System.out.println("La borne dans la ville " + capitalize(this.nom) + " a \u00E9t\u00E9 retir\u00E9e");
        }
        else{
            System.out.println("Suppression impossible pour " + capitalize(this.nom) + " afin de respecter la contrainte d'accessibilit\u00E9");
        }
    }

    /**
     * Cette fonction permet de verifier l'accessibilite de la ville courante.
     * @return Un boolean pour savoir si on peut delete la borne ou non.
     */
    public Boolean verifierAccessibilite(){
        Boolean check = false;
        for(Ville voisins : this.voisins){
            if(!voisins.aBorne){
                if (!this.verifierAccessibiliteVoisinsEloignes(voisins)){
                    return false;
                }
            }
            else{
                check = true;
            }
        }
        return check;
    }

    /**
     * Cette fonction verifie l'accessibilite des voisins de la ville courante.
     * @param ville Un voisin de la ville courante.
     * @return Un boolean pour savoir si un des voisins de la ville courante possede toujours un acces a une borne
     * apres suppression de la dite ville.
     */
    public Boolean verifierAccessibiliteVoisinsEloignes(Ville ville){
        for(Ville voisins : ville.voisins){
            if(voisins.aBorne && !voisins.equals(this)){
                return true;
            }
        }
        return false;
    }

    /**
     * Permet d'afficher correctement les informations de la Ville.
     * @return String
     */
    public String toString() {
        return "Ville : " + capitalize(nom) + " Station de recharge : " +aBorne;
    }

    /**
     * Cette fonction permet d'afficher le nom de la Ville avec une majuscule.
     * @param str Le nom dont on veut mettre la premiere lettre en majuscule.
     * @return Le nom modifie.
     */
    public static String capitalize(String str) {
        String premiereLettre = str.substring(0, 1);
        String autresLettres = str.substring(1, str.length());
        premiereLettre = premiereLettre.toUpperCase();
        str = premiereLettre + autresLettres;
        return str;
      }

    /**
     * Permet de recuperer le nom ailleurs dans le code
     * @return String
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Permet de savoir si la Ville a une borne ou non ailleurs dans le code
     * @return boolean
     */
    public boolean getABorne() {
        return this.aBorne;
    }

    /**
     * Permet de recuperer les voisins de la Ville ailleurs dans le code
     * @return ArrayList<Ville>
     */
    public ArrayList<Ville> getVoisins() {
        return this.voisins;
    }
}
