/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenza> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String categoria = this.boxCategoria.getValue();
    	if(categoria == null) {
    		this.txtResult.setText("Scegli una categoria");
    		return;
    	}
    	Integer anno;
    	try {
    		anno = this.boxAnno.getValue();
    		this.model.createGraph(categoria,anno);
    		this.txtResult.appendText("Grafo creato\n");
    		this.txtResult.appendText("#Vertici: "+this.model.nVertici()+"\n");
    		this.txtResult.appendText("#Archi: "+this.model.nArchi()+"\n");
    		this.txtResult.appendText("Elenco degli archi con peso massimo\n");
    		List<Adiacenza> lista = this.model.getListaPesiMassimi();
    		for(Adiacenza a : lista) {
    			this.txtResult.appendText(a.toString()+"\n");
    		}
    		this.boxArco.getItems().addAll(lista);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Scegli un anno");
    		return;
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso...\n");
    	Adiacenza a = this.boxArco.getValue();
    	if(a == null) {
    		this.txtResult.setText("Scegli un arco");
    		return;
    	}
    	List<String> cammino = this.model.getCammino(a);
    	if(cammino.size()==0) {
    		this.txtResult.appendText("nessun cammino trovato");
    		return;
    	}
    	for(String s : cammino) {
    		this.txtResult.appendText(s+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(this.model.getCategorie());
    	this.boxAnno.getItems().addAll(this.model.getAnni());
    }
}
