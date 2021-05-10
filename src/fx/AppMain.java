package fx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		stage.setTitle("로그인");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
