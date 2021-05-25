package fx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		StageStore.stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ChangeUser.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		stage.setTitle("The Cheat ");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
