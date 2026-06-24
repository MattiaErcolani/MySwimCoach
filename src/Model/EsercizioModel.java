package Model;

public class EsercizioModel {
    private String nome;
    private String stile;
    private int distanza;
    private String info;

    public EsercizioModel(String nome, String stile, int distanza, String info) {
        this.nome = nome;
        this.stile = stile;
        this.distanza = distanza;
        this.info = info;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStile() {
        return stile;
    }

    public void setStile(String stile) {
        this.stile = stile;
    }

    public int getDistanza() {
        return distanza;
    }

    public void setDistanza(int distanza) {
        this.distanza = distanza;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
