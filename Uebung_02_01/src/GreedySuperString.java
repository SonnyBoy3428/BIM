import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasse, welche das Greedy-Superstring-Verfahren simuliert
 */
public class GreedySuperString {
    //<editor-fold desc="Felder">
    private static String fragment1;
    private static String fragment2;
    private static int besteDeckung;
    private static int indexBesteDeckung;
    //</editor-fold>

    /**
     * Hauptprogramm.
     *
     * @param args Kommandozeilen-Argumente
     */
    public static void main(String[] args) throws IOException{
        File dir = new File(".");

        File unbekannterTextDatei = new File(dir.getCanonicalPath() + File.separator + "src" + File.separator + "unbekannterText.txt");
        List<String> unbekannterText = ladeFragmente(unbekannterTextDatei);
        List<String> unbekannterTextTeilstringfrei = entferneTeilstrings(unbekannterText);
        String superStringUnbekannterText = GreedySuperstring(unbekannterTextTeilstringfrei);
        ausgabedesSuperstrings(superStringUnbekannterText);

        File unbekannteDNA1Datei = new File(dir.getCanonicalPath() + File.separator + "src" + File.separator + "DNAFragmente1.txt");
        List<String> unbekannteDNA1 = ladeFragmente(unbekannteDNA1Datei);
        List<String> unbekannteDNA1Teilstringfrei = entferneTeilstrings(unbekannteDNA1);
        String superStringUnbekannteDNA1 = GreedySuperstring(unbekannteDNA1Teilstringfrei);
        ausgabedesSuperstrings(superStringUnbekannteDNA1);

        File unbekannteDNA2Datei = new File(dir.getCanonicalPath() + File.separator + "src" + File.separator + "DNAFragmente2.txt");
        List<String> unbekannteDNA2 = ladeFragmente(unbekannteDNA2Datei);
        List<String> unbekannteDNA2Teilstringfrei = entferneTeilstrings(unbekannteDNA2);
        String superStringUnbekannteDNA2 = GreedySuperstring(unbekannteDNA2Teilstringfrei);
        ausgabedesSuperstrings(superStringUnbekannteDNA2);

        File unbekannteDNA3Datei = new File(dir.getCanonicalPath() + File.separator + "src" + File.separator + "DNAFragmente3.txt");
        List<String> unbekannteDNA3 = ladeFragmente(unbekannteDNA3Datei);
        List<String> unbekannteDNA3Teilstringfrei = entferneTeilstrings(unbekannteDNA3);
        String superStringUnbekannteDNA3 = GreedySuperstring(unbekannteDNA3Teilstringfrei);
        ausgabedesSuperstrings(superStringUnbekannteDNA3);
    }

    //<editor-fold desc="Laden und Verarbeitung der Fragmente">
    /**
     * Liest eine Datei Zeile fuer Zeile ein.
     *
     * @param datei Datei, welche eingelesen werden soll
     * @throws IOException IOException, welche geworfen werden kann
     */
    private static List<String> ladeFragmente(File datei) throws IOException{
        List<String> fragmente = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(datei));

        String zeile = null;

        //Liest die Datei Zeile fuer Zeile ein
        while((zeile = br.readLine()) != null) {
            fragmente.add(zeile);
        }

        return fragmente;
    }

    /**
     * Entfernt alle Teilstrings aus der Fragmentliste
     *
     * @param fragmentListe Fragmentliste, aus welcher die Teilstrings entfernt werden muessen
     * @return Liste mit Teilstring-freien Fragmenten
     */
    private static List<String> entferneTeilstrings(List<String> fragmentListe){
        List<String> teilstringFreieListe = new ArrayList<String>();

        Set<String> uniqueFragmentListe = new HashSet<String>(fragmentListe);

        for(String fragment : uniqueFragmentListe){
            // Nur wenn das Fragment eine Laenge groesser 0 hat, muss es betrachtet werden
            if(fragment.length() > 0){
                List<String> fragmenteMitTeilstrings = new ArrayList<String>();

                for(String vergleichsFragment : uniqueFragmentListe){
                    // Wenn Vergleichsfragment eine Teilstring beinhaltet merken
                    if(vergleichsFragment.length() > 0 && vergleichsFragment.contains(fragment)){
                        fragmenteMitTeilstrings.add(vergleichsFragment);
                    }
                }

                // Wenn es keine weiteren Fragmente mit Teilstrings gibt, dann zu Teilstring-freien Liste hinzufuegen
                if(fragmenteMitTeilstrings.size() == 1){
                    teilstringFreieListe.add(fragmenteMitTeilstrings.get(0));
                }
            }
        }

        return teilstringFreieListe;
    }
    //</editor-fold>

    //<editor-fold desc="Der Greedy-Substring-Algorithmus">
    /**
     * Fuehrt den Greedy-Superstring-Algorithmus durch
     * @param fragmentListe Fragmentliste, die bearbeitet wird
     * @return Superstring
     */
    private static String GreedySuperstring(List<String> fragmentListe){
        List<Integer> fragmentIndizes;

        // Laeuft solange mehrere Fragmente vorhanden sind
        while(fragmentListe.size() > 1){

            findeBesteDeckung(fragmentListe);

            String gemergetesFragment = mergeFragmente();

            // Loesche einzelfragmente
            fragmentListe.remove(fragment1);
            fragmentListe.remove(fragment2);

            // Fuege gemergetes, ganzes Fragment hinzu
            fragmentListe.add(gemergetesFragment);
        }

        return fragmentListe.get(0);
    }

    /**
     * Findet die beste Deckungsrate
     *
     * @param fragmentListe Fragmentliste
     */
    private static void findeBesteDeckung(List<String> fragmentListe){
        fragment1 = null;
        fragment2 = null;
        besteDeckung = 0;
        indexBesteDeckung = 0;

        for(String fragment : fragmentListe) {
            for (String vergleichsFragment : fragmentListe) {

                // Das gleiche Fragment darf nicht mit sich selbst verglichen werden
                if (!fragment.equals(vergleichsFragment)) {
                    int indexAktuelleDeckung = 0;
                    int indexVergleichsFragment = 0;

                    int aktuelleDeckung = 0;

                    // Prueft jeden Buchstaben auf Ueberlappung
                    for (int k = 0; k < fragment.length(); k++) {
                        if (fragment.charAt(k) == vergleichsFragment.charAt(indexVergleichsFragment)) {
                            // Nur der Start der Ueberlappung wird gespeichert
                            if(indexAktuelleDeckung == 0){
                                indexAktuelleDeckung = k;
                            }

                            aktuelleDeckung++;
                            indexVergleichsFragment++;
                        } else {
                            indexAktuelleDeckung = 0;
                            indexVergleichsFragment = 0;
                            aktuelleDeckung = 0;

                            //Da index von vergleichsFragment wieder auf 0 gesetzt ist, muss nochmal geprueft werden
                            if(fragment.charAt(k) == vergleichsFragment.charAt(indexVergleichsFragment)){
                                indexAktuelleDeckung = k;
                                aktuelleDeckung++;
                                indexVergleichsFragment++;
                            }
                        }
                    }

                    //Nur bessere Ueberlappungen merken
                    if (aktuelleDeckung > besteDeckung) {
                        besteDeckung = aktuelleDeckung;
                        indexBesteDeckung = indexAktuelleDeckung;

                        fragment1 = new String(fragment);
                        fragment2 = new String(vergleichsFragment);
                    }
                }
            }
        }
    }

    /**
     * Merged das aktuelle Fragment mit dem optimalen Fragment
     *
     * @return Das gemergete Fragment
     */
    private static String mergeFragmente(){
        String gemergetesFragment = null;

        if(besteDeckung != 0){
            int indexRest = ((fragment1.length() - 1) - indexBesteDeckung) + 1;
            gemergetesFragment = fragment1 + fragment2.substring(indexRest);
        } else {
            System.out.println("Wir raten.");

            //Wenn keine Ueberlappung ermittelt wurde einfach mergen
            gemergetesFragment = fragment1 + fragment2;
        }

        return gemergetesFragment;
    }
    //</editor-fold>

    //<editor-fold desc="Ausgabemethoden">
    /**
     * Gibt einen Superstring aus.
     * @param superString Der Superstring der ausgegeben werden soll.
     */
    private static void ausgabedesSuperstrings(String superString){
        System.out.println("Superstring: " + superString);
    }
    //</editor-fold>
}
