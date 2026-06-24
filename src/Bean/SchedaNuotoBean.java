package Bean;

import java.util.ArrayList;
import java.util.List;

public class SchedaNuotoBean{
    private String idScheda;
    private int distanzaTotale;
    private int durata;
    private String livello;
    private List<EsercizioBean> esercizi;

    public SchedaNuotoBean(String idScheda, int distanzaTotale, int durata, String livello){
        this.idScheda=idScheda;
        this.distanzaTotale=distanzaTotale;
        this.durata=durata;
        this.livello=livello;
        this.esercizi=new ArrayList<>();
    }

    public String getIdScheda(){
        return idScheda;
    }
    public void setIdScheda(String idScheda){
        this.idScheda=idScheda;
    }

    public int getDistanzaTotale(){
        return distanzaTotale;
    }
    public void setDistanzaTotale(int distanzaTotale){
        this.distanzaTotale=distanzaTotale;
    }

    public int getDurata(){
        return durata;
    }
    public void setDurata(int durata){
        this.durata=durata;
    }

    public String getLivello(){
        return livello;
    }
    public void setLivello(String livello){
        this.livello=livello;
    }

    public List<EsercizioBean> getEsercizi(){
        return esercizi;
    }
    public void setEsercizi(List<EsercizioBean> esercizi){
        this.esercizi=esercizi;
    }
}

