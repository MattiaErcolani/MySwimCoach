package exceptions;

public class EmailGiaInUsoException extends Exception {
    public EmailGiaInUsoException() {
        super("Email gia in uso");
    }
}
