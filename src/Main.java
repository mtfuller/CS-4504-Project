import edu.kennesaw.cs4504.services.TCPPeer;
import edu.kennesaw.cs4504.views.LauncherDialog;
import edu.kennesaw.cs4504.views.SetupDialog;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Thomas on 9/29/2017.
 */
public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LauncherDialog launcherDialog = new LauncherDialog();
        TCPPeer application = launcherDialog.showAndWait().get();
        application.run();
    }
}
