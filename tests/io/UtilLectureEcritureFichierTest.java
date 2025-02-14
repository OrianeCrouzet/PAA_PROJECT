package tests.io;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import io.UtilLectureEcritureFichier;
import structure.Agglomeration;

public class UtilLectureEcritureFichierTest {

    /**
     * Permet de tester la sauvegarde de l'agglomeration dans un fichier.
     */
    @Test
    public void testSauvegarderEtChargerAgglo() {
        Agglomeration agglo = new Agglomeration(2);
        agglo.addVille("paris");
        agglo.addVille("marseille");
        agglo.addRoute(agglo.getVilleByName("paris"), agglo.getVilleByName("marseille"));

        String tempFileName = "temp_agglomeration.ca";
        UtilLectureEcritureFichier.sauvegarderAgglo(tempFileName, agglo.getVilles());

        Agglomeration aggloChargee;
        try {
            aggloChargee = UtilLectureEcritureFichier.chargeAgglo(tempFileName);
            assertTrue(new File(tempFileName).delete());
        } catch (IOException e) {
            fail("Erreur lors de la lecture du fichier.");
            return;
        }

        assertEquals(agglo.toString(),aggloChargee.toString());
        assertEquals(agglo.getVilles().size(), aggloChargee.getVilles().size());
        assertEquals(agglo.getVilles().get(0).getNom(), aggloChargee.getVilles().get(0).getNom());
        assertEquals(agglo.getVilles().get(1).getNom(), aggloChargee.getVilles().get(1).getNom());
        assertTrue(agglo.getVilleByIndex(0).getABorne());
        assertTrue(agglo.getVilleByIndex(1).getABorne());
    }

    /**
     * Permet de tester le cas ou le fichier est inexistant.
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void testChargerAggloFichierInexistant() throws IOException {
        String fichierInexistant = "chemin/vers/mon/fichier/inexistant.ca";
        UtilLectureEcritureFichier.chargeAgglo(fichierInexistant);
    }

    /**
     * Permet de tester la lecture correcte des bornes dans le fichier.
     * @throws IOException
     */
    @Test
    public void testLectureBornes() throws IOException{
        Agglomeration agglo1;
        Agglomeration agglo2;
        String test1 = "testLecture.ca";
        String test2 = "testLecture2.ca";

        agglo1 = UtilLectureEcritureFichier.chargeAgglo(test1);
        agglo2 = UtilLectureEcritureFichier.chargeAgglo(test2);

        assertTrue(agglo1.getVilleByName("PARIS").getABorne());
        assertTrue(agglo1.getVilleByName("LYON").getABorne());

        assertTrue(agglo2.getVilleByName("PARIS").getABorne());
        assertFalse(agglo2.getVilleByName("LYON").getABorne());
    }
}
