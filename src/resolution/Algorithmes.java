package resolution;

import java.util.ArrayList;

import structure.Agglomeration;
import structure.Ville;

/**
 * Cette classe rassemble les deux algorithmes utilisables pour résoudre le problème d'accessibilité.
 * - Un algorithme qui permet de toujours trouver une solution, mais cette solution n'est pas forcément minimale.
 * - Un algorithme permettant de trouver une solution la plus optimale possible, c'est-à-dire avec le moins de bornes de rechargement possible.
 */
public class Algorithmes {

    /**
     * Cette methode permet de trouver un ensemble dominant dans un graphe (donc ici, dans notre agglomeration).
     * On vérifie pour chaque sommet si la contrainte d'accessibilité est respectée.
     * Cette methode permet de toujours avoir une solution, mais cette solution n'est pas minimale.
     *
     * @param agglo Notre agglomeration de Villes
     * @return Une liste avec notre solution de Villes dominantes
     */
    public static ArrayList<Ville> ensembleVillesDominant(Agglomeration agglo)
    {
        ArrayList<Ville> villesNonDominees = new ArrayList<>(agglo.getVilles());
        ArrayList<Ville> solution = new ArrayList<>();

        for (Ville v : agglo.getVilles()) {
            if (villesNonDominees.contains(v)) {
                solution.add(v);
                villesNonDominees.remove(v);
                for (Ville voisin : v.getVoisins()) {
                    if (villesNonDominees.contains(voisin))
                    {
                        villesNonDominees.remove(voisin);
                    }
                }
            }
        }
        return solution;
    }

    
    /**
     * Cet algorithme est basé sur la methode de couverture par sommets (Vertex Cover) et permet
     * de trouver un ensemble dominant minimal :
     * Tant qu'il reste des villes non dominées, on continue le processus.
     * La meilleure ville est ajoutée à l'ensemble ensembleDominant.
     * On retire la meilleure ville ainsi que ses voisins de l'ensemble villesNonDominees, car ils sont désormais
     * dominés par la meilleure ville ajoutée à l'ensemble dominant.
     * @param agglo Le ArrayList contenant nos villes dans Agglomeration
     * @return ArrayList<Ville> Un ArrayList contenant toutes les villes qui appartiennent à la solution optimale
     */
    public static ArrayList<Ville> ensemblesVillesMinimalDominant(Agglomeration agglo) {
        ArrayList<Ville> ensembleDominant = new ArrayList<>();
        ArrayList<Ville> villesNonDominees = new ArrayList<>(agglo.getVilles());

        while (!villesNonDominees.isEmpty()) {
            Ville meilleureVille = trouverMeilleureVille(villesNonDominees);
            ensembleDominant.add(meilleureVille);

            villesNonDominees.remove(meilleureVille);

            if(meilleureVille == null){
                throw new NullPointerException();
            }
            for (Ville voisin : meilleureVille.getVoisins()) {
                villesNonDominees.remove(voisin);
            }
        }

        return ensembleDominant;
    }

    
    /**
     * Cette methode permet de choisir une "meilleure" ville :
     * On cherche la ville qui a le plus grand nombre de voisins non dominés. Cette ville est sélectionnée comme
     * la "meilleure" pour être ajoutée à l'ensemble dominant.
     * @param villesNonDominees Notre ArrayList de villes encore non dominees
     * @return Ville La "meilleure" Ville
     */
    private static Ville trouverMeilleureVille(ArrayList<Ville> villesNonDominees) {
        Ville meilleureVille = null;
        int maxVoisinsNonDomines = -1;

        for (Ville ville : villesNonDominees) {
            int voisinsNonDomines = compterVoisinsNonDomines(ville, villesNonDominees);
            if (voisinsNonDomines > maxVoisinsNonDomines) {
                maxVoisinsNonDomines = voisinsNonDomines;
                meilleureVille = ville;
            }
        }

        return meilleureVille;
    }

    
    /**
     * Cette methode permet simplement de compter le nombre de voisins dominés de la Ville entree en paramètres.
     * @param ville La Ville dont on doit compter les voisins non dominés
     * @param villesNonDominees Le ArrayList contenant toutes les villes non dominees
     * @return int Le nombre de voisins non dominés
     */
    private static int compterVoisinsNonDomines(Ville ville, ArrayList<Ville> villesNonDominees) {
        int count = 0;
        for (Ville voisin : ville.getVoisins()) {
            if (villesNonDominees.contains(voisin)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Cette methode permet de directement modifier l'agglomeration apres avoir fait appel a un des deux algorithmes.
     * - si opti == true, on applique l'algorithme optimal
     * - si opti == false, on applique le premier algorithme basique
     * @param agglo Notre agglomeration a modifier
     * @param opti Le boolean pour savoir quel algorithme l'utilisateur veut appliquer
     */
    public static void modifieAgglomeration(Agglomeration agglo,boolean opti){
        ArrayList<Ville> solution;
        if(opti){
            solution = ensemblesVillesMinimalDominant(agglo);
        }else{
            solution = ensembleVillesDominant(agglo);
        }
        for (Ville v : agglo.getVilles()){
            if(!solution.contains(v)){
                v.deleteBorne();
            }
        }
    }
}