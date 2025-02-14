package tests.structure;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

import structure.Agglomeration;
import structure.Menu;


public class MenuTest {

    /**
     * Test le scénario de la phase 1
     */
    @Test
    public void testToString() {
        Agglomeration agglo;

        String input = """
                1
                -3
                27
                5
                1
                A D
                1
                D C
                1
                B C
                1
                A B
                1
                Z Y
                1
                A Y
                1
                Z A
                1
                B E
                2
                1
                1
                B
                2
                B
                1
                B
                2
                Z
                -1
                2
                D
                2
                C
                2
                B
                2
                E
                1
                Z
                -1
                1
                Z
                -1
                3
                5
                """;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner sc = new Scanner(System.in);
        agglo = Menu.modeManuel(sc);
        Menu.typeResolution(sc,agglo);
    }

    /**
     * Permet d'assurer que le menu continue de fonctionner.
     */
    @Test
    public void initAggloPerso(){
        String input = """
                tabouret
                3
                Paris
                Marseille
                Paris
                Marseille
                MARSEILLE
                Poitiers
                """;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner sc = new Scanner(System.in);
        Menu.nombreVillePersos(sc);
    }
}