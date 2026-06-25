package exceptions;

public class UtenteNonPresenteException extends Exception{
    public UtenteNonPresenteException(){
        super("Email sbagliata");
    }
}
