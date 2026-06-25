package exceptions;

public class NullException extends Exception{
  public NullException(){
    super("Operazione non da nulla");
  }
}
