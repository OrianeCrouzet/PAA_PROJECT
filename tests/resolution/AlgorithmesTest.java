package tests.resolution;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import resolution.Algorithmes;
import structure.Agglomeration;
import structure.Menu;
import structure.Ville;

public class AlgorithmesTest{

    /**
     * Agglomeration de petite taille
     */
    private static Agglomeration agglo1;

    /**
     * Agglomeration de taille moyenne
     */
    private static Agglomeration agglo2;

    /**
     * Resultat pour agglo1
     */
    private static ArrayList <Ville> res1;

    /**
     * Resultat pour agglo2
     */
    private static ArrayList <Ville> res2;

    /**
     * Permet d'initialiser les deux agglomerations pour l'ensemble des tests
     */
    @BeforeClass
    public static void beforeEach() {
        agglo1 = Menu.initAgglomeration(3);
        agglo1.addRoute(Menu.checkVille("a", agglo1), Menu.checkVille("b", agglo1));
        agglo1.addRoute(Menu.checkVille("b", agglo1), Menu.checkVille("c", agglo1));

        agglo2 = Menu.initAgglomeration(10);
        agglo2.addRoute(Menu.checkVille("a", agglo2), Menu.checkVille("d", agglo2));
        agglo2.addRoute(Menu.checkVille("a", agglo2), Menu.checkVille("e", agglo2));
        agglo2.addRoute(Menu.checkVille("b", agglo2), Menu.checkVille("c", agglo2));
        agglo2.addRoute(Menu.checkVille("b", agglo2), Menu.checkVille("e", agglo2));
        agglo2.addRoute(Menu.checkVille("c", agglo2), Menu.checkVille("d", agglo2));
        agglo2.addRoute(Menu.checkVille("a", agglo2), Menu.checkVille("f", agglo2));
        agglo2.addRoute(Menu.checkVille("b", agglo2), Menu.checkVille("g", agglo2));
        agglo2.addRoute(Menu.checkVille("d", agglo2), Menu.checkVille("h", agglo2));
        agglo2.addRoute(Menu.checkVille("e", agglo2), Menu.checkVille("i", agglo2));
        agglo2.addRoute(Menu.checkVille("c", agglo2), Menu.checkVille("j", agglo2));
        agglo2.addRoute(Menu.checkVille("g", agglo2), Menu.checkVille("f", agglo2));
        agglo2.addRoute(Menu.checkVille("f", agglo2), Menu.checkVille("j", agglo2));
        agglo2.addRoute(Menu.checkVille("j", agglo2), Menu.checkVille("i", agglo2));
        agglo2.addRoute(Menu.checkVille("i", agglo2), Menu.checkVille("h", agglo2));
        agglo2.addRoute(Menu.checkVille("g", agglo2), Menu.checkVille("h", agglo2));
    }


    /**
     * Permet de tester le premier algorithme (solution non optimale)
     */
    @Test
    public void testEnsembleVillesDominant(){
        res1 = new ArrayList<>();
        res1.add(agglo1.getVilleByName("a"));
        res1.add(agglo1.getVilleByName("c"));
        assertEquals(res1, Algorithmes.ensembleVillesDominant(agglo1));

        res2 = new ArrayList<>();
        res2.add(agglo2.getVilleByName("a"));
        res2.add(agglo2.getVilleByName("b"));
        res2.add(agglo2.getVilleByName("h"));
        res2.add(agglo2.getVilleByName("j"));
        assertEquals(res2, Algorithmes.ensembleVillesDominant(agglo2));
    }

    
    /**
     * Permet de tester le deuxieme algorithme (solution optimale)
     */
    @Test
    public void tesEnsemblesVillesMinimalDominant() {
        res1 = new ArrayList<>();
        res1.add(agglo1.getVilleByName("b"));
        assertEquals(res1, Algorithmes.ensemblesVillesMinimalDominant(agglo1));

        res2 = new ArrayList<>();
        res2.add(agglo2.getVilleByName("a"));
        res2.add(agglo2.getVilleByName("b"));
        res2.add(agglo2.getVilleByName("i"));
        assertEquals(res2, Algorithmes.ensemblesVillesMinimalDominant(agglo2));
    }

    /**
     * Permet de verifier que la fonction modifie bien l'agglomeration selon les
     * resultats des differents algorithmes, cad chaque Ville renvoyee par l'algorithme choisi
     * doit avoir son attribut aBorne = false
     */
    @Test
    public void testModifieAgglomeration(){
        Algorithmes.modifieAgglomeration(agglo1, false);
        assertFalse(agglo1.getVilleByName("b").getABorne());

        Algorithmes.modifieAgglomeration(agglo2, true);
        assertFalse(agglo2.getVilleByName("c").getABorne());
        assertFalse(agglo2.getVilleByName("d").getABorne());
        assertFalse(agglo2.getVilleByName("e").getABorne());
        assertFalse(agglo2.getVilleByName("f").getABorne());
        assertFalse(agglo2.getVilleByName("g").getABorne());
        assertFalse(agglo2.getVilleByName("h").getABorne());
        assertFalse(agglo2.getVilleByName("j").getABorne());
    }

}