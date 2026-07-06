package model;

import other.StatoRichiestaScheda;

import java.time.LocalDate;

public class RichiestaSchedaNuotoModel {
    private int idRichiesta;
    private String nome;
    private String cognome;
    private String livelloUtente;
    private String emailIstruttore;
    private String emailUser;
    private StatoRichiestaScheda status;
    private LocalDate dataRichiesta;
    private String info;

    public RichiestaSchedaNuotoModel() {
        // Costruttore vuoto necessario per la deserializzazione e l'inizializzazione tramite setter
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setLivelloUtente(String livelloUtente) {
        this.livelloUtente = livelloUtente;
    }

    public String getLivelloUtente() {
        return livelloUtente;
    }

    public String getEmailIstruttore() {
        return emailIstruttore;
    }

    public void setEmailIstruttore(String emailIstruttore) {
        this.emailIstruttore = emailIstruttore;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public LocalDate getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(LocalDate dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public StatoRichiestaScheda getStatus() {
        return status;
    }

    public void setStatus(StatoRichiestaScheda status) {
        this.status = status;
    }
}