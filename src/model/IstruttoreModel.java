package model;

public class IstruttoreModel extends UtenteLoggatoModel {
    private CredenzialiModel credenziali;
    private String certificate;
    private String swimmingPool;
    private float  serviceFee;
    private int age;

    public IstruttoreModel(){}

    public IstruttoreModel(CredenzialiModel credenziali, String nome, String cognome, boolean isIstruttore,
                           String certificate, String swimmingPool, float serviceFee, int age) {
        // Chiamata al costruttore della classe base
        super(credenziali, nome, cognome, isIstruttore);
        this.certificate = certificate;
        this.swimmingPool = swimmingPool;
        this.serviceFee = serviceFee;
        this.age = age;
    }

    public String getCertificate(){
        return certificate;
    }
    public void setCertificate(String certificate){
        this.certificate = certificate;
    }
    public String getSwimmingPool(){
        return swimmingPool;
    }
    public void setSwimmingPool(String swimmingPool){
        this.swimmingPool = swimmingPool;
    }
    public float getServiceFee(){
        return serviceFee;
    }
    public void setServiceFee(float serviceFee) {
        this.serviceFee = serviceFee;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }



}