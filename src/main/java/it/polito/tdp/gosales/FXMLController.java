package it.polito.tdp.gosales;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.model.Archi;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Retailers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private ComboBox<Retailers> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	Retailers r=this.cmbRivenditore.getValue();
    	if(r==null) {
    		this.txtResult.setText("Scegliere un rivenditore!");
    		return;
    	}
    	this.txtResult.appendText("\n"+this.model.getComponenteConnessa(r).toString());
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtArchi.clear();
    	this.txtVertici.clear();
    	String nazione=this.cmbNazione.getValue();
    	Integer anno=this.cmbAnno.getValue();
    	String m=this.txtNProdotti.getText();
    	if(nazione==null) {
    		this.txtResult.setText("Scegliere una nazione!");
    		return;
    	}
    	if(anno==null) {
    		this.txtResult.setText("Scegliere un anno!");
    		return;
    	}
    	if(m=="") {
    		this.txtResult.setText("Inserire un numero");
    		return;
    	}
    	Integer Mnum=-1;
    	try {
    		Mnum=Integer.parseInt(m);
    	}catch (NumberFormatException nfe) {
    		this.txtResult.setText("Inserire un numero");
    		return;
    	}
    	if(Mnum<0) {
    		this.txtResult.setText("Inserire un numero positivo");
    		return;
    	}
    	this.model.buildGraph(nazione, anno, Mnum);
    	this.txtResult.appendText("Grafo creato con #V= "+this.model.getVsize()+" e #A= "+this.model.getEsize());
    	for(Retailers r:this.model.getVenditori()) {
    		this.txtVertici.appendText(r.getName()+"\n");
    	}
    	for(Archi a:this.model.getArchi()) {
    		this.txtArchi.appendText(a+"\n");
    	}
    	this.cmbRivenditore.setDisable(false);
    	this.btnAnalizzaComponente.setDisable(false);
    	this.cmbRivenditore.getItems().setAll(this.model.getVenditori());
    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbAnno.getItems().setAll(this.model.getAnni());
    	this.cmbNazione.getItems().setAll(this.model.getNazioni());
    }

}
