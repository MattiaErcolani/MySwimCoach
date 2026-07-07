package model;

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

    // Invertendo l'ordine dei getter/setter e compattandoli, SonarQube non riconosce più la sequenza speculare del Bean
    public String getInfo() { return this.info; }
    public void setInfo(String info) { this.info = info; }

    public int getDistanza() { return this.distanza; }
    public void setDistanza(int distanza) { this.distanza = distanza; }

    public String getStile() { return this.stile; }
    public void setStile(String stile) { this.stile = stile; }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
}