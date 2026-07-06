package dao;
import exceptions.EmailGiaInUsoException;
import exceptions.UtenteNonPresenteException;
import exceptions.CredenzialiSbagliateException;
import model.CredenzialiModel;
import model.UtenteLoggatoModel;
import other.Stampa;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class UserDAOJSON implements UserDao {
    private static final String FILE_PATH = System.getProperty("user.home") + "/MySwimCoach_users.txt";

    private final Map<String, UtenteLoggatoModel> users = new HashMap<>();

    public UserDAOJSON() {
        loadFromFile();
    }


    public UtenteLoggatoModel loginMethod(CredenzialiModel credenzialiModel) throws CredenzialiSbagliateException, UtenteNonPresenteException {
        UtenteLoggatoModel user = users.get(credenzialiModel.getEmail());
        if (user == null) {
            throw new UtenteNonPresenteException();
        }
        if (!user.getCredenziali().getPassword().equals(credenzialiModel.getPassword())) {
            throw new CredenzialiSbagliateException();
        }
        return user;
    }


    public void registrazioneMethod(UtenteLoggatoModel registrazioneModel) {
        users.put(registrazioneModel.getCredenziali().getEmail(), registrazioneModel);
        saveToFile();
    }


    public void controllaEmailMethod(UtenteLoggatoModel registrazioneModel) throws EmailGiaInUsoException {
        if (users.containsKey(registrazioneModel.getCredenziali().getEmail())) {
            throw new EmailGiaInUsoException();
        }
    }

    //eventualmente modificare fromString
    public void registraIstruttoreMethod(String email,String nome, String cognome){
        UtenteLoggatoModel istruttore = users.get(email);
        if (istruttore != null) {
            istruttore.setNome(nome);
            istruttore.setCognome(cognome);
            istruttore.getIsIstructor();
            saveToFile();
        }
    }
    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 5) {
                    String email = parts[0].trim();
                    String password = parts[1].trim();
                    String nome = parts[2].trim();
                    String cognome = parts[3].trim();
                    boolean isIstructor = Boolean.parseBoolean(parts[4].trim());

                    CredenzialiModel credenziali = new CredenzialiModel(email, password);
                    UtenteLoggatoModel user = new UtenteLoggatoModel(credenziali, nome, cognome, isIstructor);

                    users.put(email, user);
                }
            }
        } catch (IOException e) {
            Stampa.errorPrint("Impossibile caricare gli utenti dal file utenti.");
        }
    }



    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (UtenteLoggatoModel user : users.values()) {
                CredenzialiModel c = user.getCredenziali();

                String line = c.getEmail() + "," +
                        c.getPassword() + "," +
                        user.getNome() + "," +
                        user.getCognome() + "," +
                        user.getIsIstructor();

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            Stampa.errorPrint("Impossibile salvare l'utente sul file utenti.");
        }
    }

    @Override
    public List<UtenteLoggatoModel> getIstruttori() {
        return users.values().stream()
                .filter(u -> u.getIsIstructor())
                .toList();
    }

    @Override
    public UtenteLoggatoModel getUserByEmail(String email) {
        return users.get(email);
    }

}
