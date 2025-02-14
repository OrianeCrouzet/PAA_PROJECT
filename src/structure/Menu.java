package structure;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import io.UtilLectureEcritureFichier;
import resolution.Algorithmes;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class Menu {
    /**
     * Le main essaye de lire le fichier sur la ligne de commande, si le fichier ne comporte aucune erreur
     * alors le programme ce lance dessus, sinon il passe en mode manuel.
     * Si on ne lit aucun fichier sur la ligne de commande, on instancie directement l'agglomeration manuellement.
     */
    public static void main(String[] args) {
        Agglomeration agglo = null;
        if(args.length != 0 ){
            try{
                agglo = UtilLectureEcritureFichier.chargeAgglo(args[0]);
                System.out.println("L'agglomeration lu dans le fichier est : ");
                agglo.afficheAgglomeration();
                System.out.println(agglo);
            }catch (IOException e) {
                System.out.println("Une Erreur est survenu: : " + e.getMessage());
            }
        }
        try (Scanner sc = new Scanner(System.in)) {
            if(agglo == null){
                agglo = modeManuel(sc);
            }
            typeResolution(sc,agglo);
        }
    }

    /** Cette fonction permet de faciliter les test unitaires. Elle permet d'initialiser manuellement l'agglomération.
     * @param sc Le scanner pour lire les entrées utilisateur
     * @return l'agglomération avec les modifications
     */
    public static Agglomeration modeManuel(Scanner sc){
        Agglomeration agglo = null;
        switch(checkEntree(sc, 1, 2, "Pour les noms de villes, vous aimeriez :\n1) Des villes avec des noms suivant l'alphabet \n2) Des villes avec des noms personnalises", Constante.ERREUR_NOMBRE_ENTREE)){
            case 1:
                agglo = initAgglomeration(checkEntree(sc, 1, 26, "\nCombien de villes voulez-vous dans v\u00F4tre agglom\u00E9ration ?", Constante.ERREUR_NOMBRE_ENTREE));
                ajoutRoute(sc, agglo);
                break;
            case 2:
                agglo = nombreVillePersos(sc);
                ajoutRoute(sc, agglo);
                break;
            default:
                break;
        }
        return agglo;
    }


    /**
     * Permet de creer une agglomeration, avec des noms de villes personnalises.
     * @param sc Le scanner pour gérer les entrées de l'utilisateur.
     * @return Une agglomération de la taille saisie par l'utilisateur, avec des noms personnalises pour les villes.
     */
    public static Agglomeration nombreVillePersos(Scanner sc) {
        int saisie = checkEntree(sc,1,-1,"\nCombien de villes voulez-vous dans v\u00F4tre agglom\u00E9ration ?",Constante.ERREUR_NOMBRE_ENTREE);
        Agglomeration aggloTmp = new Agglomeration(saisie);
        for(int i = 0; i<saisie; i++){
            System.out.println("Veuillez ajouter un nom de ville : ");
            String nom = sc.next();
            if(aggloTmp.getVilleByName(nom)==null){
                aggloTmp.addVille(nom);
            }else{
                i--;
                System.out.println("La ville entr\u00E9 existe d\u00E9j\u00E0");
            }
        }
        return aggloTmp;
    }

    /**
     * Fonction pour gérer le deuxième menu. Permet d'ajouter les routes.
     * @param sc Le scanner pour gérer les entrées de l'utilisateur.
     * @param agglo L'agglomération sur laquelle on souhaite interagir.
     */
    public static void ajoutRoute(Scanner sc, Agglomeration agglo) {
        int saisie = -1;
        System.out.println("\u00C0 pr\u00E9sent, veuillez entrer les routes entre les villes :");
        while (saisie != 2) {
            saisie = checkEntree(sc, 1, 2, "\nQue voulez-vous faire ?\n1) ajouter une route \n2) fin", Constante.ERREUR_NOMBRE_ENTREE);
            if (saisie == 1) {
                System.out.println("\nQuelles villes voulez-vous relier? (entrez le nom des 2 villes s\u00E9par\u00E9es par un espace)");
                agglo.addRoute(checkVille(sc, agglo), checkVille(sc, agglo));
            }
        }
    }

    /**
     * Cette fonction permet de resoudre manuellement le probleme par l'utilisateur.
     * @param sc Le scanner pour gérer les entrées de l'utilisateur.
     * @param agglo L'agglomération sur laquelle on souhaite interagir.
     */
    public static void resolutionManuelle(Scanner sc, Agglomeration agglo) {
        int saisie = -1;
        System.out.println("\nPassons \u00E0 pr\u00E9sent aux bornes de recharge : ");
        while (saisie != 3) {
            saisie = checkEntree(sc, 1, 3, "\nQue voulez-vous faire ?\n1) ajouter une zone de recharge\n2) retirer une zone de recharge\n3) fin", Constante.ERREUR_NOMBRE_ENTREE);
            switch(saisie){
                case 1:
                    System.out.println("\nDans quelle ville voulez-vous ajouter une borne?");
                    Ville add = Menu.checkVille(sc, agglo);
                    if(add != null){
                        agglo.addRecharge(add);
                    }
                    break;
                case 2:
                    System.out.println("\nDans quelle ville voulez-vous retirer une borne?");
                    Ville del = Menu.checkVille(sc, agglo);
                    if(del != null) {
                        agglo.deleteRecharge(del);
                    }
                    break;
                default:
                    break;
            }
            System.out.println();
            afficherVilleAvecBorne(agglo);
        }
    }

    /**
     * Permet de choisir le type de resolution (manuelle, algo basique ou algo optimal) et permet aussi de sauvegarder.
     * @param sc Le scanner pour gérer les entrées de l'utilisateur.
     * @param agglo L'agglomération sur laquelle on souhaite interagir.
     */
    public static void typeResolution(Scanner sc, Agglomeration agglo) {
        int saisie = -1;
        System.out.println("\nPassons \u00E0 pr\u00E9sent aux bornes de recharge : ");
        while (saisie != 5) {
            saisie = checkEntree(sc, 1, 5, "\nQue voulez-vous faire ?\n1) r\u00E9soudre manuellement \n2) r\u00E9soudre automatiquement (solution basique)\n3) r\u00E9soudre automatiquement (solution la plus optimale)\n4) Sauvegarder\n5) Fin", Constante.ERREUR_NOMBRE_ENTREE);
            switch (saisie){
                case 1:resolutionManuelle(sc,agglo);break;
                case 2:Algorithmes.modifieAgglomeration(agglo,false);break;
                case 3:Algorithmes.modifieAgglomeration(agglo,true);break;
                case 4:UtilLectureEcritureFichier.sauvegarderAgglo(demandeNomFichier(sc),agglo.getVilles());break;
                default:break;
            }
            System.out.println();
            agglo.afficheAgglomeration();
        }
    }


    /**Cette fonction sert à demander quel nom l'utilisateur veut mettre pour la sauvegarde de son graphe.
     * @param sc Le scanner pour gérer les entrées de l'utilisateur.
     */
    public static String demandeNomFichier(Scanner sc){
        System.out.println("Quel est le nom de fichier que vous voulez mettre?\n");
        return sc.next();
    }

    /**
     * Vérifie que la ville entrée existe dans l'agglomération.
     * @param sc Le scanner pour gérer les entrées de l'utilisateur.
     * @param agglo L'agglomération où l'on souhaite vérifier que le nom de la ville entrée existe.
     * @return La ville liée au nom entré par l'utilisateur ou -1 si l'utilisateur ne veut pas rentrer de ville.
     */
    public static Ville checkVille(Scanner sc, Agglomeration agglo) {
        String saisie = sc.next();
        Ville entre = agglo.getVilleByName(saisie);
        while (entre == null && !(saisie.equals("-1"))) {
            System.out.print("La ville : " + saisie + " , n'existe pas, entrez \u00E0 nouveau une ville ou -1 si vous voulez sortir du menu.");
            saisie = sc.next();
            entre = agglo.getVilleByName(saisie);
        }
        return entre;
    }

    /**
     * Permet de verifier que le nom entre en parametres est bien le nom d'une ville de l'agglomeration.
     * @param saisie Le nom a verifier.
     * @param agglo L'agglomération sur laquelle on souhaite interagir.
     */
    public static Ville checkVille(String saisie, Agglomeration agglo) {
        Ville entre = agglo.getVilleByName(saisie);
        while (entre == null && !(saisie.equals("-1"))) {
            System.out.print("La ville : " + saisie + " , n'existe pas, entrez \u00E0 nouveau une ville ou -1 si vous voulez sortir du menu.");
            entre = agglo.getVilleByName(saisie);
        }
        return entre;
    }

    /**
     * Affiche les villes qui possèdent une borne.
     * @param agglo L'agglomération où l'on souhaite afficher les villes avec des bornes.
     */
    public static void afficherVilleAvecBorne(Agglomeration agglo) {
        System.out.println("Les villes qui contiennent une borne sont : ");
        for (Ville v : agglo.getVilles()) {
            if (v.getABorne()) {
                System.out.print(v.getNom() + " ");
            }
        }
        System.out.println(" ");
    }

    /**
     * Fonction qui va demander de rentrer un entier jusqu'à ce que l'entier soit compris entre le min et le max.
     * @param sc Le scanner pour gérer les entrées de l'utilisateur
     * @param min La valeur minimum que l'entier saisi peut valoir.
     * @param max La valeur maximum que l'entier saisi peut valoir.
     * @param message Message affiché à l'utilisateur avant l'entrée d'une valeur.
     * @param erreur Message d'erreur affiché lors de l'entrée d'unDominant Set of a Graphe mauvaise valeur.
     * @return l'entier saisi par l'utilisateur.
     */
    public static int checkEntree(Scanner sc, int min, int max, String message, String erreur) {
        boolean invalideSaisie = true;
        boolean hasMin = min>0;
        boolean hasMax = max>0;
        int saisie = -1;
        while (invalideSaisie) {
            try {
                System.out.println(message);
                saisie = sc.nextInt();
                if (hasMin && (saisie<min)){
                    System.out.println("Nombre entr\u00E9 invalide, un nombre entre" +min+" et "+max+" est attendu et vous avez entré : " + saisie);
                } else if (hasMax && max < saisie) {
                    System.out.println("Nombre entr\u00E9 invalide, un nombre entre" +min+" et "+max+" est attendu et vous avez entré : " + saisie);
                } else {
                    invalideSaisie = false;
                }
            } catch (InputMismatchException e) {
                System.out.println(erreur);
                erreurNonIntEntree(sc);
            }
        }
        return saisie;
    }

    /**
     * Renvoie une erreur si l'utilisateur entre autre chose qu'un entier.
     * @param sc Le scanner qui permet de récupérer les entrées utilisateurs.
     */
    public static void erreurNonIntEntree(Scanner sc) {
        System.out.println("Vous avez entr\u00E9 : " + sc.next());
    }

    /**
     * Initialise une agglomération de taille nb, qui assigne à chaque ville une lettre différente suivant l'ordre de l'alphabet.
     * @param nb La taille de l'agglomération voulue.
     * @return L'agglomération de la taille demandée par l'utilisateur.
     */
    public static Agglomeration initAgglomeration(int nb) {
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        Agglomeration agglo = new Agglomeration(nb);
        for (int i = 0; i < nb; i++) {
            agglo.addVille(alphabet[i]);
        }
        return agglo;
    }
}
