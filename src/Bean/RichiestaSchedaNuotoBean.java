package Bean;

import java.time.LocalDate;
import Other.StatoRichiestaScheda;

public class RichiestaSchedaNuotoBean {
    private int idRichiesta;
    private String nome;
    private String cognome;
    private String livelloUtente;
    private String emailIstruttore;
    private String emailUser;
    private SchedaNuotoBean scheda;
    private StatoRichiestaScheda status;
    private LocalDate dataRichiesta;
    private String info;

    public RichiestaSchedaNuotoBean() {}
    public RichiestaSchedaNuotoBean(UtenteLoggatoBean istruttore, int idRichiesta, String nome, String cognome,
                                    String livelloUtente, String emailIstruttore, String emailUser, String info, StatoRichiestaScheda status) {
        this.idRichiesta = idRichiesta;
        this.nome = istruttore.getNome();
        this.cognome = istruttore.getCognome();
        this.livelloUtente = livelloUtente;
        this.emailIstruttore = istruttore.getCredenziali().getEmail();
        this.emailUser = emailUser;
        this.scheda = null;
        this.dataRichiesta = LocalDate.now();
        this.info = info;
        this.status = status;
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

    public String getCognome(){
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setLivelloUtente(String livelloUtente){
        this.livelloUtente = livelloUtente;
    }

    public String getLivelloUtente(){
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

    public SchedaNuotoBean getScheda() {
        return scheda;
    }

    public void setScheda(SchedaNuotoBean scheda) {
        this.scheda = scheda;
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
