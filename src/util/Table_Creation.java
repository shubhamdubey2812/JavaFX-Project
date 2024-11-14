package util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

public class Table_Creation {
	@SuppressWarnings("unchecked")
	public static TableView<Customer> createTable(){
		TableView<Customer> table=new TableView<Customer>();
		table.setPrefHeight(900);
		table.setPrefWidth(800);
		
		//Create Table Column
		TableColumn<Customer, String> index=new TableColumn<>("Index");
		index.setCellValueFactory(new PropertyValueFactory<>("index"));
		index.setPrefWidth(100);
		
		TableColumn<Customer, String> cutomerId=new TableColumn<Customer, String>("Customer Id");
		cutomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		cutomerId.setPrefWidth(150);
		

		TableColumn<Customer, String> firstName=new TableColumn<>("First Name");
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		firstName.setPrefWidth(120);
		
		
		TableColumn<Customer, String> lastName=new TableColumn<>("Last Name");
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		lastName.setPrefWidth(120);
		
		
		TableColumn<Customer, String> company=new TableColumn<>("Company");
		company.setCellValueFactory(new PropertyValueFactory<>("company"));
		company.setPrefWidth(160);
		
		TableColumn<Customer, String> city=new TableColumn<>("City");
		city.setCellValueFactory(new PropertyValueFactory<>("city"));
		city.setPrefWidth(150);
		
		TableColumn<Customer, String> country=new TableColumn<>("Country");
		country.setCellValueFactory(new PropertyValueFactory<>("country"));
		country.setPrefWidth(200);
		
		TableColumn<Customer, String> phone_1=new TableColumn<>("Phone1");
		phone_1.setCellValueFactory(new PropertyValueFactory<>("phone1"));
		phone_1.setPrefWidth(180);
		
		TableColumn<Customer, String> phone_2=new TableColumn<>("Phone2");
		phone_2.setCellValueFactory(new PropertyValueFactory<>("phone2"));
		phone_2.setPrefWidth(180);
		
		
		TableColumn<Customer, String> email=new TableColumn<>("Email");
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		email.setPrefWidth(250);
		
		TableColumn<Customer, String> subscription=new TableColumn<>("Subscription Date");
		subscription.setCellValueFactory(new PropertyValueFactory<>("subscriptionDate"));
		subscription.setPrefWidth(150);
		
		TableColumn<Customer, String> website=new TableColumn<>("Website");
		website.setCellValueFactory(new PropertyValueFactory<>("website"));
		website.setPrefWidth(250);
		table.getColumns().addAll(index,cutomerId,firstName,lastName,company,city,country,phone_1,phone_2,email,subscription,website);
		return table;
	}

}
