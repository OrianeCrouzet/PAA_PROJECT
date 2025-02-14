package tests.structure;

import static org.junit.Assert.*;

import org.junit.Test;

import structure.Ville;

public class VilleTest {

    private Ville ville;

    /**
     * Teste le constructeur sans paramètre de la classe Ville
     */
    @Test
    public void testConstructeurNormal() {
        ville = new Ville("Paris");

        assertEquals("paris", ville.getNom());
        assertTrue(ville.getABorne());
        assertNotNull(ville.getVoisins());
        assertTrue(ville.getVoisins().isEmpty());
    }

    /**
     * Teste le constructeur avec un nom de ville null (doit lever une IllegalArgumentException)
     */
    @Test
    public void testConstructeurNull() {
        assertThrows(IllegalArgumentException.class, () -> new Ville(null));
    }

    /**
     * Teste la méthode addBorne de la classe Ville
     */
    @Test
    public void testAddBorne() {
        ville = new Ville("Paris");

        assertTrue(ville.getABorne());
        ville.addBorne();
        assertTrue(ville.getABorne());
    }

    /**
     * Teste la méthode deleteBorne de la classe Ville
     */
    @Test
    public void testDeleteBorne(){
        ville = new Ville("Paris");
        ville.getVoisins().add(new Ville("Marseille"));

        assertTrue(ville.getABorne());
        ville.deleteBorne();
        assertFalse(ville.getABorne());
    }

    /**
     * Teste la méthode toString de la classe Ville
     */
    @Test
    public void testToString() {
        ville = new Ville("Paris");
        String expected = "Ville : Paris Station de recharge : true";
        assertEquals(expected, ville.toString());
    }
}
