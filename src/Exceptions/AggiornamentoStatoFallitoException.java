package Exceptions;

public class AggiornamentoStatoFallitoException extends RuntimeException {
    public AggiornamentoStatoFallitoException(String message) {
        super("Aggiornamento dello stato della richiesta non riuscito");
    }
}
