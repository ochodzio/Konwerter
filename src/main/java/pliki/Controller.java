package pliki;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable {
    public ArrayList<Nauczyciele> nauczyciel = new ArrayList();
    public ArrayList<String> wyniki = new ArrayList();
    public ArrayList<Uczen> uczen = new ArrayList();
    public ArrayList<Klasa> klasy = new ArrayList();
    public ArrayList<String> nazwyplikowps = new ArrayList();
    FileChooser fileChooser = new FileChooser();
    static double ii = 0.0D;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button Button_gen_podglad_PS;
    @FXML
    private Button Button_input_naucz;
    @FXML
    private Button Button_input_ucz;
    @FXML
    private ProgressBar Progress_Bar;
    @FXML
    private Tab Podglad_plik_naucz;
    @FXML
    private Tab Podglad_plik_ucz;
    @FXML
    private Tab Podglad_wynik;
    @FXML
    private Button Button_save;
    @FXML
    private TextArea TA_naucz;
    @FXML
    private TextArea TA_ucz;
    @FXML
    private TextArea TA_wynik;

    public Controller() {
    }

    public void progresbarreset() {
        ii = 0.0D;
        this.Progress_Bar.setProgress(ii);
    }

    public void progresbar() {
        ii += 0.1D;
        this.Progress_Bar.setProgress(ii);
    }

    @FXML
    void genPScom(ActionEvent event) {
        this.progresbarreset();
        this.progresbar();
        Scanner taPostEditN = new Scanner(this.TA_naucz.getText());

        String temp;
        try {
            while(taPostEditN.hasNextLine()) {
                temp = taPostEditN.nextLine();
                Nauczyciele tObj = new Nauczyciele();
                if (temp != null) {
                    tObj.pelnanazwa = temp;
                    String[] parts = temp.split(",");
                    tObj.nazwaprzedmiotu = parts[0];
                    tObj.nazwanauczyciela = parts[1];
                    String[] niewazne = temp.split(" ");
                    tObj.klasa = niewazne[1];
                    this.nauczyciel.add(tObj);
                }
            }
        } catch (Exception var19) {
            var19.printStackTrace();
        }

        taPostEditN.close();
        this.progresbar();
        this.progresbar();
        Scanner taPostEditU = new Scanner(this.TA_ucz.getText());
        int ilkl = 0;
        int i = 0;

        try {
            while(taPostEditU.hasNextLine()) {
                temp = taPostEditU.nextLine();
                Uczen tObj = new Uczen();
                if (!temp.equals("")) {
                    tObj.pelnanazwa = temp;
                    String[] parts = temp.split(",");
                    tObj.imie = parts[0];
                    tObj.nazwisko = parts[1];
                    tObj.email = parts[2];
                    tObj.klasa = parts[3];
                    this.uczen.add(tObj);
                    boolean czyjesttakaklasa = false;
                    Klasa tObjdwa;
                    if (ilkl == 0) {
                        tObjdwa = new Klasa();
                        tObjdwa.klasa = ((Uczen)this.uczen.get(i)).klasa;
                        tObjdwa.listauczniow.add(((Uczen)this.uczen.get(i)).email);
                        this.klasy.add(tObjdwa);
                        ++ilkl;
                        czyjesttakaklasa = true;
                    } else {
                        for(int j = 0; j < ilkl; ++j) {
                            if (((Uczen)this.uczen.get(i)).klasa.equals(((Klasa)this.klasy.get(j)).klasa)) {
                                ((Klasa)this.klasy.get(j)).listauczniow.add(((Uczen)this.uczen.get(i)).email);
                                czyjesttakaklasa = true;
                            }
                        }
                    }

                    if (!czyjesttakaklasa) {
                        tObjdwa = new Klasa();
                        tObjdwa.klasa = ((Uczen)this.uczen.get(i)).klasa;
                        tObjdwa.listauczniow.add(((Uczen)this.uczen.get(i)).email);
                        this.klasy.add(tObjdwa);
                        ++ilkl;
                    }

                    ++i;
                }
            }

            for(int k = 0; k < ilkl; ++k) {
                System.out.print("\n");
                System.out.print(((Klasa)this.klasy.get(k)).klasa);
                System.out.print("\n");
                System.out.print(((Klasa)this.klasy.get(k)).listauczniow);
            }
        } catch (Exception var18) {
            var18.printStackTrace();
        }

        taPostEditU.close();
        this.progresbar();
        this.progresbar();

        try {
            String template1 = "$teacher = Get-MsolUser -Searchstring ";
            char cudz = '"';
            char nowalinia = '\n';
            String template2 = "$group= Get-MsolGroup -Searchstring ";
            String template3 = "Add -TeamUser -GroupId $group.ObjectId -User ";
            String templatenauczyciel1 = "$teacher.UserPrincipalName -Role Owner ";
            System.out.print("\n");
            int k = 0;
            this.progresbar();
            this.progresbar();

            do {
                temp = "\n";

                for(int j = 0; j < this.nauczyciel.size(); ++j) {
                    if (((Klasa)this.klasy.get(k)).klasa.equals(((Nauczyciele)this.nauczyciel.get(j)).klasa)) {
                        System.out.print("\n warunek sie wykonuje");
                        temp = temp + template1 + cudz + ((Nauczyciele)this.nauczyciel.get(j)).nazwanauczyciela + cudz + nowalinia + template2 + cudz + ((Nauczyciele)this.nauczyciel.get(j)).nazwaprzedmiotu + cudz + nowalinia + template3 + templatenauczyciel1 + nowalinia;

                        for(int l = 0; l < ((Klasa)this.klasy.get(k)).listauczniow.size(); ++l) {
                            temp = temp + template3 + cudz + (String)((Klasa)this.klasy.get(k)).listauczniow.get(l) + cudz + nowalinia;
                        }
                    }
                }

                this.wyniki.add(temp);
                this.nazwyplikowps.add(((Klasa)this.klasy.get(k)).klasa);
                System.out.print("\n to do tablicy do plikow test: " + ((Klasa)this.klasy.get(k)).klasa + "\n");
                this.TA_wynik.appendText(temp);
                ++k;
            } while(k < this.klasy.size());

            System.out.print(this.wyniki);
        } catch (Exception var17) {
            var17.printStackTrace();
        }

        this.progresbar();
        this.progresbar();
        this.progresbar();
    }

    @FXML
    void getStudFile(ActionEvent event) throws IOException {
        File file = null;
        file = this.fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            this.TA_ucz.setText((String)null);

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

                for(String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    this.TA_ucz.appendText(temp + '\n');
                }

                reader.close();
            } catch (Exception var8) {
                var8.printStackTrace();
            } finally {
                System.err.println("git");
            }
        }

    }

    @FXML
    void getTeachFile(ActionEvent event) throws IOException {
        File file = null;
        file = this.fileChooser.showOpenDialog(new Stage());
        BufferedReader reader = null;
        if (file != null) {
            this.TA_naucz.setText((String)null);

            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

                for(String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    this.TA_naucz.appendText(temp + '\n');
                }
            } catch (Exception var8) {
                var8.printStackTrace();
            } finally {
                reader.close();
            }
        }

    }

    @FXML
    void zapisz(ActionEvent event) {
        DirectoryChooser dirchooser = new DirectoryChooser();
        File file = dirchooser.showDialog(new Stage());
        if (file != null) {
            String path = file.getAbsolutePath();
            System.out.print(path);

            for(int i = 0; i < this.wyniki.size(); ++i) {
                try {
                    FileOutputStream outputStream = new FileOutputStream(path + '\\' + (String)this.nazwyplikowps.get(i) + ".ps1");
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_16);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write((String)this.wyniki.get(i));
                    bufferedWriter.close();
                } catch (IOException var9) {
                    var9.printStackTrace();
                }
            }
        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fileChooser.setInitialDirectory(new File("C://"));
    }
}
