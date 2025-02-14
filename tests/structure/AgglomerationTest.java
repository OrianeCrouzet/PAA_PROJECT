package tests.structure;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import resolution.Algorithmes;
import structure.Agglomeration;
import structure.Ville;

public class AgglomerationTest {

    /**
     * Teste la création d'instances Agglomeration avec des tailles spécifiques
     */
    @Test
    public void testConstructeurNormal(){
        Agglomeration agglomeration1 = new Agglomeration(1);
        Agglomeration agglomeration2 = new Agglomeration(26);

        Assert.assertNotNull(agglomeration1);
        Assert.assertNotNull(agglomeration2);
    }

    /**
     * Teste l'ajout d'une ville à l'agglomération
     */
    @Test
    public void testAjoutVille() {
        Agglomeration agglomeration = new Agglomeration(1);
        agglomeration.addVille("Paris");

        Assert.assertEquals(1, agglomeration.getVilles().size());
    }

    /**
     * Teste l'ajout d'une route entre deux villes dans l'agglomération
     */
    @Test
    public void testAjoutRoute() {
        Agglomeration agglomeration = new Agglomeration(2);
        agglomeration.addVille("paris");
        agglomeration.addVille("marseille");
        agglomeration.addRoute(agglomeration.getVilleByName("paris"), agglomeration.getVilleByName("marseille"));
    }

    /**
     * Teste la méthode afficherZonesDeRecharge
     */
    @Test
    public void testAffichageZonesDeRecharge() {
        Agglomeration agglomeration = new Agglomeration(2);
        agglomeration.addVille("paris");
        agglomeration.addVille("marseille");

        Assert.assertEquals("paris marseille ", agglomeration.afficherZonesDeRecharge());
    }

    /**
     * Teste l'ajout, la suppression et la vérification des bornes de recharge
     */
    @Test
    public void testAjoutEtSuppressionRecharge(){
        Agglomeration agglomeration = new Agglomeration(2);
        agglomeration.addVille("paris");
        agglomeration.addVille("marseille");
        agglomeration.addRoute(agglomeration.getVilleByName("paris"), agglomeration.getVilleByName("marseille"));

        Assert.assertTrue(agglomeration.getVilleByName("paris").getABorne());
        agglomeration.deleteRecharge(agglomeration.getVilleByName("paris"));
        Assert.assertFalse(agglomeration.getVilleByName("paris").getABorne());
        agglomeration.addRecharge(agglomeration.getVilleByName("paris"));
        Assert.assertTrue(agglomeration.getVilleByName("paris").getABorne());
    }

    /**
     * Teste la méthode toString de l'agglomération
     */
    @Test
    public void testAffichage() {
        Agglomeration agglomeration = new Agglomeration(2);
        agglomeration.addVille("paris");
        agglomeration.addVille("marseille");
        agglomeration.addRecharge(agglomeration.getVilleByName("paris"));
        agglomeration.addRoute(agglomeration.getVilleByName("paris"), agglomeration.getVilleByName("marseille"));

        String expected = "Co\u00FBt des bornes : 2600$";

        System.out.print(agglomeration);
        Assert.assertEquals(expected, agglomeration.toString());
    }

    /**
     * Teste une initialisation d'agglomeration a 1000 villes, avec ajout de routes et utilisation du deuxieme algorithme.
     */
    @Test
    public void initMilleVille(){
        int nbVille = 1000;
        Agglomeration agglo = new Agglomeration(nbVille);
        for (int i = 0;i<nbVille;i++){
            agglo.addVille(String.valueOf(i));
        }
        for (int i = 0;i<nbVille-4;i +=2){
            agglo.addRoute(agglo.getVilleByName(String.valueOf(i)),agglo.getVilleByName(String.valueOf(i+1)));
            agglo.addRoute(agglo.getVilleByName(String.valueOf(i)),agglo.getVilleByName(String.valueOf(i+2)));
            agglo.addRoute(agglo.getVilleByName(String.valueOf(i+1)),agglo.getVilleByName(String.valueOf(i+3)));
        }
        agglo.addRoute(agglo.getVilleByName(String.valueOf(nbVille-1)), agglo.getVilleByName(String.valueOf(nbVille-2)));
        agglo.addRoute(agglo.getVilleByName(String.valueOf(0)),agglo.getVilleByName(String.valueOf(nbVille-1)));
        agglo.addRoute(agglo.getVilleByName(String.valueOf(0)),agglo.getVilleByName(String.valueOf(nbVille-2)));

        List<Ville> ville = Algorithmes.ensemblesVillesMinimalDominant(agglo);
        System.out.println(ville);
    }
}
