package pliki;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("okno.fxml"));

            primaryStage.setTitle("Konwerter");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch  (Exception var19) {
            var19.printStackTrace();
        }
    }
}
