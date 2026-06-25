package exceptions;

public class EmailNonValidaException extends Exception{
    public EmailNonValidaException(){
        super("Email non valida");
    }
}