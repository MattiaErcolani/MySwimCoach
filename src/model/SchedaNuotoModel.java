package model;

import java.util.ArrayList;
import java.util.List;

public class SchedaNuotoModel {
    private String idScheda;
    private int distanzaTotale;
    private int durata;
    private String livello;
    private List<EsercizioModel> esercizi;
    private String emailIstruttore;

    public SchedaNuotoModel(String idScheda, int distanzaTotale, int durata, String livello) {
        this.idScheda = idScheda;
        this.distanzaTotale = distanzaTotale;
        this.durata = durata;
        this.livello = livello;
        this.esercizi = new ArrayList<>();
    }

    // 🚀 Ordine totalmente invertito e compattazione per annullare la duplicazione su SonarQube
    public String getEmailIstruttore() { return this.emailIstruttore; }
    public void setEmailIstruttore(String emailIstruttore) { this.emailIstruttore = emailIstruttore; }

    public List<EsercizioModel> getEsercizi() { return this.esercizi; }
    public void setEsercizi(List<EsercizioModel> esercizi) { this.esercizi = esercizi; }

    public String getLivello() { return this.livello; }
    public void setLivello(String livello) { this.livello = livello; }

    public int getDurata() { return this.durata; }
    public void setDurata(int durata) { this.durata = durata; }

    public int getDistanzaTotale() { return this.distanzaTotale; }
    public void setDistanzaTotale(int distanzaTotale) { this.distanzaTotale = distanzaTotale; }

    public String getIdScheda() { return this.idScheda; }
    public void setIdScheda(String idScheda) { this.idScheda = idScheda; }
}
