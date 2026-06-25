package cli;

import bean.UtenteLoggatoBean;
import Controller.SchedaNuotoController;
import Model.SchedaNuotoModel;
import Model.EsercizioModel;
import Other.Stampa;
import Pattern.AbstractState;
import Pattern.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class VisualizzaSchedeNuotoIstruttoreCLI extends AbstractState {

    private final SchedaNuotoController controller;
    private final String emailIstruttore;
    private final UtenteLoggatoBean istruttore;

    public VisualizzaSchedeNuotoIstruttoreCLI(UtenteLoggatoBean istruttore) {
        this.controller = new SchedaNuotoController();
        this.istruttore = istruttore;
        this.emailIstruttore = istruttore.getCredenziali().getEmail();
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void action(StateMachineImpl context) {
        Scanner scan = new Scanner(System.in);

        Stampa.printlnBlu("----- LE TUE SCHEDE NUOTO -----");

        List<SchedaNuotoModel> schede = controller.getAllSchede();

        boolean hasSchede = false;
        for (SchedaNuotoModel s : schede) {
            if (!emailIstruttore.equals(s.getEmailIstruttore())) continue;
            hasSchede = true;

            Stampa.println("ID Scheda: " + s.getIdScheda());
            Stampa.println("Livello: " + s.getLivello());
            Stampa.println("Durata: " + s.getDurata() + " minuti");
            Stampa.println("Distanza totale: " + s.getDistanzaTotale() + " metri");

            if (!s.getEsercizi().isEmpty()) {
                Stampa.println("Esercizi:");
                for (int i = 0; i < s.getEsercizi().size(); i++) {
                    EsercizioModel e = s.getEsercizi().get(i);
                    Stampa.println("  " + (i + 1) + ". " + e.getNome() + " | Stile: " + e.getStile() +
                            " | Distanza: " + e.getDistanza() + "m | Info: " + e.getInfo());
                }
            }

            Stampa.println("\nVuoi modificare/eliminare esercizi di questa scheda? (S/N)");
            String scelta = scan.nextLine();
            if (scelta.equalsIgnoreCase("S")) {
                gestisciEsercizi(s, scan);
            }

            Stampa.println("-------------------------------");
        }

        if (!hasSchede) {
            Stampa.println("Non hai ancora creato schede.");
        }

        Stampa.println("\nPremi INVIO per tornare al menu...");
        scan.nextLine();
        goNext(context, new SchedeIstruttoreMenuCLI(istruttore));
    }

    private void gestisciEsercizi(SchedaNuotoModel scheda, Scanner scan) {
        while (true) {
            Stampa.println("1. Aggiungi esercizio");
            Stampa.println("2. Modifica esercizio");
            Stampa.println("3. Elimina esercizio");
            Stampa.println("4. Torna indietro");
            Stampa.print("Scelta: ");
            int op = scan.nextInt();
            scan.nextLine();

            switch (op) {
                case 1 -> {
                    Stampa.print("Nome esercizio: ");
                    String nome = scan.nextLine();
                    Stampa.print("Stile: ");
                    String stile = scan.nextLine();
                    Stampa.print("Distanza: ");
                    int distanza = scan.nextInt();
                    scan.nextLine();
                    Stampa.print("Info: ");
                    String info = scan.nextLine();
                    scheda.getEsercizi().add(new EsercizioModel(nome, stile, distanza, info));
                    Stampa.println("✅ Esercizio aggiunto!");
                }
                case 2 -> {
                    Stampa.print("Numero esercizio da modificare: ");
                    int idx = scan.nextInt() - 1;
                    scan.nextLine();
                    if (idx >= 0 && idx < scheda.getEsercizi().size()) {
                        EsercizioModel e = scheda.getEsercizi().get(idx);
                        Stampa.print("Nuovo nome (" + e.getNome() + "): ");
                        String nome = scan.nextLine();
                        if (!nome.isEmpty()) e.setNome(nome);
                        Stampa.print("Nuovo stile (" + e.getStile() + "): ");
                        String stile = scan.nextLine();
                        if (!stile.isEmpty()) e.setStile(stile);
                        Stampa.print("Nuova distanza (" + e.getDistanza() + "): ");
                        String distanzaStr = scan.nextLine();
                        if (!distanzaStr.isEmpty()) e.setDistanza(Integer.parseInt(distanzaStr));
                        Stampa.print("Nuove info (" + e.getInfo() + "): ");
                        String info = scan.nextLine();
                        if (!info.isEmpty()) e.setInfo(info);
                        Stampa.println("✅ Esercizio modificato!");
                    } else {
                        Stampa.println("❌ Numero esercizio non valido.");
                    }
                }
                case 3 -> {
                    Stampa.print("Numero esercizio da eliminare: ");
                    int idx = scan.nextInt() - 1;
                    scan.nextLine();
                    if (idx >= 0 && idx < scheda.getEsercizi().size()) {
                        scheda.getEsercizi().remove(idx);
                        Stampa.println("✅ Esercizio eliminato!");
                    } else {
                        Stampa.println("❌ Numero esercizio non valido.");
                    }
                }
                case 4 -> { return; }
                default -> Stampa.println("❌ Scelta non valida.");
            }
        }
    }

    @Override
    public void stampaBenvenuto() {
        Stampa.println("👨‍🏫 Visualizzazione e gestione schede per istruttore");
    }
}