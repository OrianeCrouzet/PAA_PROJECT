package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import structure.Agglomeration;
import structure.Ville;

/**
 * Cette classe permet de gerer la lecture d'un fichier texte pour instancier une agglomeration,
 * mais egalement d'ecrire une agglomeration dans un fichier texte pour la sauvegarder.
 */
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class UtilLectureEcritureFichier {

    /**
     * Cette fonction va nous permettre d'écrire dans un fichier .ca toutes les informations
     * que contient une agglomération, c'est-à-dire ses villes les routes qui relient les villes
     * et les recharges qui sont présentes.
     * @param nomFichier le chemin suivi du nom du fichier dans lequel nous voulons "enregistrer" l'agglomération
     * @param villes une liste de villes qui correspond à toutes les villes de l'agglomération
     */
    public static void sauvegarderAgglo(String nomFichier,ArrayList<Ville> villes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            ecrireVille(writer,villes);
            Set<String> addedRoutes = new HashSet<>();
            for (Ville ville : villes) {
                if (ville.getVoisins() != null) {
                    for (Ville v : ville.getVoisins()) {
                        String routeKey = ville.getNom().toUpperCase() + "-" + v.getNom().toUpperCase();
                        String reversedRouteKey = v.getNom().toUpperCase() + "-" + ville.getNom().toUpperCase();
                        if (!addedRoutes.contains(routeKey) && !addedRoutes.contains(reversedRouteKey)) {
                            writer.write("route(" + ville.getNom().toUpperCase() + "," + v.getNom().toUpperCase() + ").");
                            writer.newLine();
                            addedRoutes.add(routeKey);
                        }
                    }
                }
            }
            ecrireBorne(writer,villes);
            System.out.println("Agglom\u00E9ration enregistr\u00E9 dans le fichier" + nomFichier);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cette fonction permet d'ecrire une ville dans le fichier pour la sauvegarde.
     * @param writer Notre BufferedWriter pour ecrire l'information
     * @param villes La liste des villes a ecrire
     * @throws IOException Lorsque l'écriture dans le fichier échoue
     */
    private static void ecrireVille(BufferedWriter writer, ArrayList<Ville> villes) throws IOException {
        for (Ville ville : villes) {
            writer.write("ville(" + ville.getNom().toUpperCase() + ").");
            writer.newLine();
        }
    }

    /**
     * Cette fonction permet d'ecrire les villes qui possedent une borne dans le fichier pour la sauvegarde.
     * @param writer Notre BufferedWriter pour ecrire l'information
     * @param villes La liste des villes
     * @throws IOException Lorsque l'écriture dans le fichier échoue
     */
    private static void ecrireBorne(BufferedWriter writer, ArrayList<Ville> villes) throws IOException {
        for (Ville ville : villes) {
            if(ville.getABorne()){
                writer.write("recharge(" + ville.getNom().toUpperCase() + ").");
                writer.newLine();
            }
        }
    }

    /**
     * Cette fonction va utiliser la variable nomFichier pour acceder au fichier .ca
     * et va y lire le contenu afin de créer une nouvelle instance de Agglomération.
     * La nouvelle instance sera initialisée et aura toutes les villes et les bornes de recharges notées dans le fichier
     * @param nomFichier le chemin suivi du nom du fichier
     * @return Notre agglomeration lue depuis le fichier
     */
    public static Agglomeration chargeAgglo(String nomFichier) throws IOException {
        Agglomeration agglo;
        int cpt = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("ville")){
                    cpt++;
                }
            }
        }


        agglo = new Agglomeration(cpt);
        Map<String, Boolean> rechargeVilles = new HashMap<>();
        int compteurVillesAvecBorne = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("ville")){
                    String[] subStrings = line.split("[()]");
                    agglo.addVille(subStrings[1]);
                    rechargeVilles.put(subStrings[1], false);
                }
                else if(line.contains("route")){
                    String[] subStrings = line.split("[(),]");
                    agglo.addRoute(agglo.getVilleByName(subStrings[1]),agglo.getVilleByName(subStrings[2]));
                }
                else if(line.contains("recharge")){
                    String[] subStrings = line.split("[()]");
                    agglo.addRecharge(agglo.getVilleByName(subStrings[1]));
                    rechargeVilles.replace(subStrings[1], true);
                    compteurVillesAvecBorne ++;
                }
            }

            ajoutBornes(rechargeVilles, compteurVillesAvecBorne, agglo);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return agglo;
    }

    /**
     * Cette fonction permet d'ajouter les bornes selon les deux cas suivants ;
     * - Si on ne lit jamais "recharge(...)" dans le fichier, alors toutes les villes se voient attribuees une borne de recharge.
     * - Si on lit recharge(...) au moins une fois, alors seules les villes lues dans "recharge(...)" auront une borne.
     * @param rechargeVilles Une Map qui contient le nom des villes deja lues, associees avec un boolean en valeur :
     *                          - true si on a lu "recharge(notreVille)"
     *                          - false si on ne lit jamais "recharge(notreVille)"
     * @param compteurVillesAvecBorne Compteur qui nous permet de savoir si on a au moins lu une fois "recharge(...)"
     * @param agglo Notre agglomeration
     */
    private static void ajoutBornes(Map<String, Boolean> rechargeVilles, int compteurVillesAvecBorne, Agglomeration agglo){
        if(compteurVillesAvecBorne == 0){
            for(Ville ville : agglo.getVilles()){
                ville.addBorne();
            }
        }else{
            for(Map.Entry<String, Boolean> entry : rechargeVilles.entrySet()){
                if(!entry.getValue()){
                    agglo.deleteRecharge(agglo.getVilleByName(entry.getKey()));
                }
            }
        }
    }
}
