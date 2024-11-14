package application;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Customer;
import util.CSVReader;
import util.Table_Creation;

public class Main extends Application {
    private TableView<Customer> table = Table_Creation.createTable();
    private ObservableList<Customer> customersData = FXCollections.observableArrayList();
    private ObservableList<Customer> filterData = FXCollections.observableArrayList();
    private int currentPage = 1;
    private int itemsPerPage = 10;
    private TextField itemPerPageFields=new TextField();
    private final int maxPageButton = 10;
    private int startPage = 1;
    private HBox paginationBox = new HBox();
    private VBox contentBox = new VBox();
    private TextField searchText = new TextField();
    private Text totalNumberOfPages=new Text();
    private Text currentNumberOfPages=new Text();
    private  HBox totalPages=new HBox();
    private String label="CSV FILE READER ";

    @Override
    public void start(Stage primaryStage) {
        try {
        	primaryStage.setTitle(label);
            VBox root = new VBox();
            HBox buttonAndSearchFunctionalltyHbox = new HBox(10);
           
            totalPages.setPadding(new Insets(5));
            Label seprate=new Label("/");
            paginationBox.setPadding(new Insets(10));
            totalPages.getChildren().addAll(currentNumberOfPages,seprate,totalNumberOfPages);
            Button openButton = new Button("Open");
            Button closeButton=new Button("Close");
            closeButton.setDisable(true);
            paginationBox.setVisible(false);
            totalPages.setVisible(false);
            contentBox.getChildren().add(table);
            buttonAndSearchFunctionalltyHbox.getChildren().addAll(openButton,closeButton, searchText,itemPerPageFields);
            paginationBox.getChildren().add(totalPages);
            root.getChildren().addAll(buttonAndSearchFunctionalltyHbox, contentBox, paginationBox);
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

            /*
             * This functionality for open csv file
             * if click on open button then user can open csv file
             */
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");
            fileChooser.setInitialDirectory(new File("D:\\Download"));
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
              
            openButton.setOnAction(e -> {
            	
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                	label="File Selected "+file.getAbsolutePath();
                	primaryStage.setTitle(label);
                   // label.setText("File Selected: " + file.getAbsolutePath());
                    closeButton.setDisable(false);
                    List<Customer> customers = CSVReader.readCustomerFromCsv(file);
                    customersData.setAll(customers);
                    filterData.setAll(customers);
                    currentPage = 1;
                    startPage = 1;
                    paginationBox.setVisible(true);
                    totalPages.setVisible(true);
                    openButton.setDisable(true);
                    updatePageContent();
                    createPaginationControl();
                    
                }
            });

            /*
             * This is Search functionality user filter data based on customer name
             */
            searchText.setPromptText("Search by First Name");
            searchText.setOnKeyReleased(e -> {
            	/*
            	 * On search change the current page and change the filter data 
            	 * then show updated data and update pagination button
            	 */
                currentPage = 1;
                updateFilterData();
                updatePageContent();
                createPaginationControl();
            });
            
            /*
             * if enter the value in TextField (ItemPerPageFields) then show data based on value
             */
            itemPerPageFields.setPromptText("Enter Item per page");
            
            itemPerPageFields.setOnAction(e->{
            	String text=itemPerPageFields.getText();
            	Pattern p=Pattern.compile("^-?\\d+$");
            	Matcher m=p.matcher(text);
            	if(m.matches()) {
            		itemsPerPage=Integer.parseInt(text);
            	}
            	else {
            		Alert alert=new Alert(null);
					alert.setAlertType(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("PLease Eneter Correct Number");
					alert.show();
            	}
            	/*
            	 * after click on ItemPerPageFields i have to change current page and update start page
            	 * and update table view data
            	 * and update Pagination Button
            	 */
            	startPage=1;
            	currentPage=1;
             	updatePageContent();
            	createPaginationControl();
            	
            });
            
            /*
             * Close Button responsible for close the file 
             */
              closeButton.setOnAction(e->{
            	  customersData.clear();
            	  table.getItems().clear();
            	  label="File Closed";
            	  primaryStage.setTitle(label);
            	  //label.setText("File Closed");
            	  paginationBox.getChildren().clear();
            	  itemPerPageFields.clear();
            	  totalPages.setVisible(false);
            	  itemsPerPage=10;
            	  currentPage=1;
            	  startPage=1;
            	  closeButton.setDisable(true);
            	  searchText.clear();
            	  openButton.setDisable(false);
            	  
              });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * filter data method
     */
    private void updateFilterData() {
        String textSearch = searchText.getText().toLowerCase();
        filterData.clear();
       
        if (textSearch.isEmpty()) {
          filterData.setAll(customersData);
        	
        } else {
            for (Customer customer : customersData) {
                if (customer.getFirstName().toLowerCase().contains(textSearch)) {
                    filterData.add(customer);
                	
                }
            }
        }
    }
      
    /*
     * this method is create Pagination button based on data
     */
    private void createPaginationControl() {
        paginationBox.getChildren().clear();

        Button prevButton = new Button("Prev");
        prevButton.setDisable(currentPage == 1);
        prevButton.setOnAction(e->{
        	changePage(currentPage-1);
        });
        paginationBox.getChildren().add(prevButton);

        Button nextButton = new Button("Next");
        nextButton.setDisable(currentPage == getTotalPage());
        nextButton.setOnAction(e->{
        	changePage(currentPage+1);
        });

        int endPage = Math.min(startPage + maxPageButton - 1, getTotalPage());
        for (int i = startPage; i <= endPage; i++) {
            Button pageButton = new Button(String.valueOf(i));
            pageButton.setDisable(i == currentPage);
            pageButton.setOnAction(e->{
            	changePage(Integer.parseInt(pageButton.getText()));
            });
            paginationBox.getChildren().add(pageButton);
        }

        paginationBox.getChildren().add(nextButton);
        paginationBox.getChildren().add(totalPages);
    }
       /*
        * by using this method we calculate total page
        */
    private int getTotalPage() {
        return (int) Math.ceil((double) filterData.size() / itemsPerPage);
    }
      
    /*
     * change the page after click on button
     */
    private void changePage(int page) {
        if (page >= 1 && page <= getTotalPage()) {
            currentPage = page;

            if (currentPage < startPage) {
                startPage = currentPage;
            } else if (currentPage >= startPage + maxPageButton) {
                startPage = currentPage - maxPageButton + 1;
            }

              updatePageContent();
             createPaginationControl();
        }
    }
      /*
       * This method is update page data after click on pagination button
       */
    private void updatePageContent() {
        contentBox.getChildren().clear();
        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage,filterData.size());
        ObservableList<Customer> pageDataCustomers = FXCollections.observableArrayList(filterData.subList(start, end));
        table.setItems(pageDataCustomers);
        contentBox.getChildren().add(table);
        totalNumberOfPages.setText(String.valueOf(getTotalPage()));
        currentNumberOfPages.setText(String.valueOf(currentPage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
