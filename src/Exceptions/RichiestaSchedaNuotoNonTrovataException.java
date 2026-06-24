package Exceptions;

public class RichiestaSchedaNuotoNonTrovataException extends Exception {
    public RichiestaSchedaNuotoNonTrovataException(String message) {
        super("Nessuna richiesta di scheda trovata");
    }
}
