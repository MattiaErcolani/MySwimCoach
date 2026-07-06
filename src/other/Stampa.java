package other;

@SuppressWarnings("java:S106")
public class Stampa {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET1 = "\u001B[0m";


    private Stampa(){}

    //stampa
    public static void print(String message){
        System.out.print(message);
    }

    //stampa e va a capo
    public static void println(String message){
        System.out.println(message);
    }

    //stampa la guida per ogni pagina in CLI
    public static void printlnBlu(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET1);
    }


    public static void printBlu(String message) {
        System.out.print(ANSI_BLUE + message + ANSI_RESET1);
    }


    //stampa messaggio di errore
    public static void errorPrint(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    // Aggiungi questo metodo dentro la classe Stampa esistente (in fondo)
    public static int leggiInteroSicuro(java.util.Scanner scan, Runnable mostraSchermata) {
        try {
            if (scan.hasNextInt()) {
                int valore = scan.nextInt();
                scan.nextLine(); // Consuma il newline residuo
                return valore;
            } else {
                errorPrint("Input non valido. Per favore, inserisci un numero intero: ");
                scan.nextLine(); // Svuota il buffer dell'input errato
                mostraSchermata.run();
                return -1;
            }
        } catch (Exception e) {
            errorPrint("Errore durante la lettura dell'input.");
            scan.nextLine(); // Protezione da loop infiniti
            mostraSchermata.run();
            return -1;
        }
    }
}