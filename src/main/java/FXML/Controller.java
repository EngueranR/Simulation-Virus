package FXML;


import application.SimuVirus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Controller {

    /**
     * petit theatre associe
     */
    Stage dialogStage;
    /**
     * application associe
     */
    @FXML
    private Button btnLancer;
    @FXML
    private Button btnQuitter;
    @FXML
    private TextField textFieldTour;
    @FXML
    private TextField textFieldNbInfectees;
    @FXML
    private TextField textFieldNbSaines;
    @FXML
    private TextField textFieldNbPers;
    @FXML
    private Label labelNbPers;
    @FXML
    private Label labelNbPersSaines;
    @FXML
    private Label labelNbPersInfectees;
    @FXML
    private Label labelTour;

    private SimuVirus simuVirus;


    @FXML
    public void updateNbInfectees(int nbInfectee) {
        String infectee = Integer.toString(nbInfectee);
        textFieldNbInfectees.setText(infectee);
    }

    public void updateNbSain(int nbSain) {
        String sain = Integer.toString(nbSain);
        textFieldNbSaines.setText(sain);
    }


    public void updateNbPers(int nbPersMax) {
        String persMax = Integer.toString(nbPersMax);
        textFieldNbPers.setText(persMax);
    }

    public void updateTour(int nbTour) {
        String tour = Integer.toString(nbTour);
        textFieldTour.setText(tour);
    }


    public void lancer(ActionEvent e ) {
        this.simuVirus.lancer();
    }

    public void quitter(ActionEvent e ) {
        this.simuVirus.quitter();
    }



    public void setSimuVirus(SimuVirus simuVirus) {
        this.simuVirus = simuVirus;
    }

}

