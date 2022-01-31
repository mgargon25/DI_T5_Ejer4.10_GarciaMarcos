/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package appinformes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Marcos García González
 */
public class AppInformes extends Application {
    public static Connection conexion = null;
    @Override
    public void start(Stage primaryStage) {
        //establecemos la conexión con la BD
        conectaBD();
        //Creamos la escena 
        TextField tituloIntro = new TextField("nº producto");
        Button btn = new Button();
        btn.setText("Informe");

        VBox root = new VBox();
        
        // Creamos ID para el root de la ventana principal
        root.setId("Main");
        
        // Creamos el primer menu
        final Menu menu1 = new Menu("Informes");
        
        // Creamos la barra de menu
        MenuBar menuBar = new MenuBar();
        
        // Creamos los objetos del menu encuestas
        MenuItem listadoFacturas = new MenuItem("Listado Facturas");
        MenuItem ventasTotales = new MenuItem("Ventas Totales");
        MenuItem facturasCliente = new MenuItem("Facturas por Cliente");
        MenuItem subinformeListFac = new MenuItem("Subinforme Listado de Facturas");
        
        // Añadimos los objetos al menu
        menu1.getItems().add(listadoFacturas);
        menu1.getItems().add(ventasTotales);
        menu1.getItems().add(facturasCliente);
        menu1.getItems().add(subinformeListFac);
        
        listadoFacturas.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            generaInformeListadoFacturas(tituloIntro); 
            System.out.println("Generando informe");
            }
        });
        
        ventasTotales.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            generaInformeVentasTotales(tituloIntro); 
            System.out.println("Generando informe");
            }
        });
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            generaInformeFacturasClientes(tituloIntro); 
            System.out.println("Generando informe");
            }
        });
        
        subinformeListFac.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            generaInformeListadoFacturas(tituloIntro); 
            System.out.println("Generando informe");
            }
        });
        
        // Añadimos los menus al menuBar
        root.getChildren().add(menuBar);
        root.getChildren().addAll(tituloIntro, btn);
        menuBar.getMenus().addAll(menu1);
        Scene scene = new Scene(root, 600, 500);
        
        primaryStage.setTitle("Obtener Informes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
public void stop() throws Exception {
    try { 
        DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/Test;shutdown=true");
    } catch (Exception ex) {
        System.out.println("No se pudo cerrar la conexion a la BD");
    } 
 }

public void conectaBD(){
    //Establecemos conexión con la BD
    String baseDatos = "jdbc:hsqldb:hsql://localhost/Test";
    String usuario = "sa";
    String clave = "";
 try{
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        conexion = DriverManager.getConnection(baseDatos,usuario,clave);
    }  
 catch (ClassNotFoundException cnfe){
        System.err.println("Fallo al cargar JDBC");
        System.exit(1);
    }
 catch (SQLException sqle){
        System.err.println("No se pudo conectar a BD");
        System.exit(1);
    }
 catch (java.lang.InstantiationException sqlex){
        System.err.println("Imposible Conectar");
        System.exit(1);
    }
 
 catch (Exception ex){
        System.err.println("Imposible Conectar");
        System.exit(1);
    }
 }

public void generaInformeListadoFacturas(TextField tintro) {
 try {
    JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Listado_Facturas.jasper"));
    //Map de parámetros
    //Map parametros = new HashMap();
    //int nproducto = Integer.valueOf(tintro.getText());
    //parametros.put("ParamProducto", nproducto);
 
    //JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
    JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
    JasperViewer.viewReport(jp);
 } catch (JRException ex) {
        System.out.println("Error al recuperar el jasper");
        JOptionPane.showMessageDialog(null, ex);
    }
 }

public void generaInformeVentasTotales(TextField tintro) {
 try {
    JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Ventas_Totales.jasper"));
    //Map de parámetros
    //Map parametros = new HashMap();
    //int nproducto = Integer.valueOf(tintro.getText());
    //parametros.put("ParamProducto", nproducto);
 
    //JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
    JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
    JasperViewer.viewReport(jp);
 } catch (JRException ex) {
        System.out.println("Error al recuperar el jasper");
        JOptionPane.showMessageDialog(null, ex);
    }
 }

public void generaInformeFacturasClientes(TextField tintro) {
 try {
    JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Facturas_Cliente.jasper"));
    //Map de parámetros
    Map parametros = new HashMap();
    int nproducto = Integer.valueOf(tintro.getText());
    parametros.put("AdressID", nproducto);
 
    JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
    JasperViewer.viewReport(jp);
 } catch (JRException ex) {
        System.out.println("Error al recuperar el jasper");
        JOptionPane.showMessageDialog(null, ex);
    }
 }

public void generaSubInformeFacturas(TextField tintro) {
 try {
    JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("SubInformeListadoFacturas.jasper"));
    JasperReport jsr = (JasperReport) JRLoader.loadObject(AppInformes.class.getResource("SubInformeFacturas.jasper"));
    //Map de parámetros
    Map parametros = new HashMap();
    parametros.put("SubInformeFacturas", jsr);
 
    JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
    JasperViewer.viewReport(jp);
 } catch (JRException ex) {
        System.out.println("Error al recuperar el jasper");
        JOptionPane.showMessageDialog(null, ex);
    }
 }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
