package view;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import controller.Main;
import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Callback;

public class Gui extends Application{

	private String sceneName;
	
	private TextField usernameField;
	private TextField nameSignup;
	private TextField usernameSignup;
	private TextField toField;
	private TextField subjectField;
	private TextField accountField;
	
	private TextField myName;
	private TextField myUsername;
	private PasswordField myPassword;
	
	
	private Text subjectMail;
	private Text senderMail;
	private Text toMail;
	private Text dateMail;
	private Text wait;
	
	private TextArea mailContent;
	private TextArea newMailContent;
	private TextArea replyContent;
	
	private PasswordField passwordField;
	private PasswordField passwordSignup;
	private PasswordField passwordAccount;
	
	private FileChooser filechooser;
	private File file;
	
	private Scene scene;
	private Stage stage;
	
	private Button loginButton;
	private Button signupButton;
	private Button signupUserButton;
	private Button composeButton;
	private Button deleteMailButton;
	private Button inboxButton;
	private Button sentButton;
	private Button spamButton;
	private Button replyButton;
	private Button sendButton;
	private Button addAccountButton;
	private Button menuAdd;
	private Button menuLogout;
	private Button menuAccount;
	private Button readMail;
	private Button chooseButton;
	private Button refresh;

	
	private Button updateMyAccount;
	private Button deleteMyAccount;

	private ToolBar toolbar;
	
	private TableView<Message> mailTable;
	
	private ComboBox<String> accounts;
	private ComboBox<String> providers;
	private ComboBox<String> myAccounts;
	

	public Gui(Main main) {
		
	}

//
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		filechooser = new FileChooser();
		
		usernameField = new TextField();
		nameSignup = new TextField();
		usernameSignup = new TextField();
		passwordField = new PasswordField();
		passwordSignup = new PasswordField();
		passwordAccount = new PasswordField();
		toField = new TextField();
		subjectField = new TextField();
		accountField = new TextField();
		
		myName = new TextField();
		myUsername = new TextField();
		myPassword = new PasswordField();
		
		subjectMail = new Text();
		senderMail = new Text();
		toMail = new Text();
		dateMail = new Text();
		wait = new Text();
		
		wait.setFont(Font.font ("Calibri light", 15));
		wait.setFill(Color.gray(0.2));
		
		mailContent = new TextArea();
		newMailContent = new TextArea();
		replyContent = new TextArea();
		
		mailContent.setEditable(false);
		
		loginButton = new Button("Login");
		signupButton = new Button("Sign up");
		signupUserButton = new Button("Sign up");
		composeButton = new Button("Compose");
		deleteMailButton = new Button("Delete mail");
		inboxButton = new Button("Inbox");
		sentButton = new Button("Sent");
		spamButton = new Button("Spam");
		replyButton = new Button("Reply");
		sendButton = new Button("Send");
		addAccountButton = new Button("Add account");
		menuAdd = new Button();
		menuAccount = new Button();
		menuLogout = new Button();
		readMail = new Button("Read");
		chooseButton = new Button("Add attachment");
		refresh = new Button("Refresh");
		
		updateMyAccount = new Button("Update");
		deleteMyAccount = new Button("Delete email account");
		
		inboxButton.setAlignment(Pos.CENTER_LEFT);
		sentButton.setAlignment(Pos.CENTER_LEFT);
		spamButton.setAlignment(Pos.CENTER_LEFT);
		
		composeButton.setPrefWidth(100);
		inboxButton.setPrefWidth(100);
		sentButton.setPrefWidth(100);
		spamButton.setPrefWidth(100);
		
		inboxButton.setStyle("-fx-background-color: transparent;");
		sentButton.setStyle("-fx-background-color: transparent;");
		spamButton.setStyle("-fx-background-color: transparent;");
		
		inboxButton.setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                inboxButton.setStyle("-fx-background-color: rgba(255,255,255,0.5);");
            }
        });
		
		inboxButton.setOnMouseExited(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
            	inboxButton.setStyle("-fx-background-color: transparent;");
            }
        });
		
		sentButton.setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                sentButton.setStyle("-fx-background-color: rgba(255,255,255,0.5);");
            }
        });
		
		sentButton.setOnMouseExited(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
            	sentButton.setStyle("-fx-background-color: transparent;");
            }
        });

		spamButton.setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                spamButton.setStyle("-fx-background-color: rgba(255,255,255,0.5);");
            }
        });
		
		spamButton.setOnMouseExited(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
            	spamButton.setStyle("-fx-background-color: transparent;");
            }
        });
		
		menuAdd.setTooltip(new Tooltip("Add new email account"));
		menuLogout.setTooltip(new Tooltip("Logout"));
		menuAccount.setTooltip(new Tooltip("My account"));
		
		menuAdd.setGraphic(new ImageView("view/email-icon.png"));
		menuLogout.setGraphic(new ImageView("view/arrow-back-1-icon.png"));
		menuAccount.setGraphic(new ImageView("view/contacts-icon.png"));
		
		toolbar = new ToolBar();
		toolbar.getItems().addAll(menuAdd, menuAccount, menuLogout);
		
		mailTable = new TableView<>();
		
		accounts = new ComboBox<>();
		providers = new ComboBox<>();
		myAccounts = new ComboBox<>();
		
		loginWindow();
		
		stage.setTitle("Email Integration Application");
	}
	
	public void myAccountWindow(){
		Text text = new Text("My account");
		text.setFont(Font.font("Calibri light", 20));
		
		Text name = new Text("Name");
		name.setFont(Font.font("Calibri light", 15));
		
		Text username = new Text("Username");
		username.setFont(Font.font("Calibri light", 15));
		
		Text password = new Text("Password");
		password.setFont(Font.font("Calibri light", 15));
		
		Text eaccounts = new Text("Email accounts");
		eaccounts.setFont(Font.font("Calibri light", 15));
		
		HBox hbox = new HBox(20);
		VBox left = new VBox(20);
		VBox right = new VBox(10);
	
		left.getChildren().addAll(name, username, password, eaccounts);
		right.getChildren().addAll(myName, myUsername, myPassword, myAccounts);
		hbox.getChildren().addAll(left, right);
		
		VBox vbox = new VBox();
		VBox vboxSend = new VBox(20);
		
		String image = Gui.class.getResource("blurry.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center;");
		vbox.setPadding(new Insets(50));
		vbox.setPrefSize(500, 500);
		
		vboxSend.setPadding(new Insets(20));
		vboxSend.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		
		vboxSend.getChildren().addAll(text, hbox, deleteMyAccount, updateMyAccount);
		
		vbox.getChildren().add(vboxSend);
		
		vbox.setAlignment(Pos.CENTER);
		vboxSend.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		left.setAlignment(Pos.CENTER);
		right.setAlignment(Pos.CENTER);
		
		Scene newScene = new Scene(vbox, 600, 400);
		setScene(newScene);
	}
	public void addAccountWindow(){
		sceneName = "addaccount";
		
		accountField.setPromptText("Your email account");
		accountField.setPrefWidth(150);
		
		providers = new ComboBox<String>(FXCollections.observableArrayList("Gmail", "Yahoo", "Hotmail"));
		providers.setPromptText("Select provider");
		providers.setPrefWidth(150);
		
		VBox vbox = new VBox();
		VBox vboxSend = new VBox(20);
		
		vbox.setAlignment(Pos.CENTER);
		vboxSend.setAlignment(Pos.CENTER_LEFT);
		
		String image = Gui.class.getResource("blurry.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center;");
		vbox.setPadding(new Insets(50));
		vbox.setPrefSize(300, 500);
		
		vboxSend.setPadding(new Insets(20));
		vboxSend.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		vboxSend.setPrefSize(300, 500);
		
		Text text = new Text("Add new mail account");
		text.setFont(Font.font("Calibri light", 20));
		
		vboxSend.getChildren().addAll(text, providers, accountField, passwordAccount, addAccountButton);
		vbox.getChildren().add(vboxSend);
		
		Scene newScene = new Scene(vbox, 300, 500);
		setScene(newScene);
	}
	
	public void sendMailWindow(){
		sceneName = "sendmail";
		file = null;
		
		toField.setPromptText("To");
		subjectField.setPromptText("Subject");
		newMailContent.setPromptText("Message");
		
		accounts.setPromptText("Select account");
		
		chooseButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				file = filechooser.showOpenDialog(stage);
			}
			
		});
		
		VBox vbox = new VBox();
		VBox vboxSend = new VBox(20);
		
		vbox.setAlignment(Pos.CENTER);
		vboxSend.setAlignment(Pos.CENTER_LEFT);
		
		String image = Gui.class.getResource("blurry.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center;");
		vbox.setPadding(new Insets(50));
		vbox.setPrefSize(500, 500);
		
		//vboxOpt.setPrefSize(200, 600);
		vboxSend.setPadding(new Insets(20));
		vboxSend.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		
		Text text = new Text("New mail");
		text.setFont(Font.font("Calibri light", 20));
		
		vboxSend.getChildren().addAll(text, accounts, toField, subjectField, newMailContent, chooseButton, sendButton);
		vbox.getChildren().add(vboxSend);
		
		Scene newScene = new Scene(vbox, 500, 500);
		setScene(newScene);
	}
	public void mailWindow(){
		sceneName = "mail";
//		menuSetting.getItems().addAll(menuAdd, menuLogout, menuExit);
//		menuBar.getMenus().add(menuSetting);
		
		subjectMail.setFont(Font.font ("Calibri light", 20));
		senderMail.setFont(Font.font ("Calibri light", 15));
		toMail.setFont(Font.font ("Calibri light", 15));
		dateMail.setFont(Font.font ("Calibri light", 15));
		
		Scene newScene = new Scene(new VBox(), 1000, 600);
		
		VBox vbox = new VBox();
		VBox vboxUser = new VBox(20);
		HBox hboxContent = new HBox(10);
		VBox vboxOpt = new VBox(20);
		VBox vboxMail = new VBox(20);
		HBox hboxInfo = new HBox(10);
		
		vbox.setAlignment(Pos.CENTER);
		vboxUser.setAlignment(Pos.CENTER);
		hboxContent.setAlignment(Pos.CENTER);
		vboxOpt.setAlignment(Pos.TOP_LEFT);
		vboxMail.setAlignment(Pos.TOP_LEFT);
		hboxInfo.setAlignment(Pos.TOP_LEFT);
		
		String image = Gui.class.getResource("blurry.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center;");
		vbox.setPadding(new Insets(50));
		vbox.setPrefSize(800, 600);
		
		vboxOpt.setPrefSize(200, 600);
		vboxOpt.setPadding(new Insets(20));
		vboxOpt.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		
		vboxMail.setPrefSize(600, 600);
		vboxMail.setPadding(new Insets(20));
		vboxMail.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		
		hboxInfo.getChildren().addAll(senderMail, toMail, dateMail);
		vboxOpt.getChildren().addAll(composeButton, inboxButton, sentButton, spamButton);
		vboxMail.getChildren().addAll(subjectMail, hboxInfo, mailContent, replyButton);
		hboxContent.getChildren().addAll(vboxOpt, vboxMail);
		vboxUser.getChildren().add(hboxContent);
		vbox.getChildren().add(vboxUser);
		
		((VBox) newScene.getRoot()).getChildren().addAll(toolbar, vbox);

		setScene(newScene);
	}
	@SuppressWarnings("unchecked")
	public void userWindow(){
		sceneName = "user";
		
		mailTable.setMinWidth(400);
		mailTable.setMaxHeight(600);
		
		
		
		TableColumn fromColumn = new TableColumn("From");
	    fromColumn.setMinWidth(200);
	    fromColumn.setCellValueFactory(new Callback<CellDataFeatures<Message, String>, ObservableValue<String>>() {
	        @Override public ObservableValue<String> call(CellDataFeatures<Message, String> m) {
	          try {
	            // m.getValue() returns the Message instance for a particular TableView row
	            return new ReadOnlyObjectWrapper<String>(Arrays.toString(m.getValue().getFrom()));
	          } catch (MessagingException ex) {
	            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	          }
	        }
	    });  
	    
	    TableColumn toColumn = new TableColumn("To");
	    toColumn.setMinWidth(200);
	    toColumn.setCellValueFactory(new Callback<CellDataFeatures<Message, String>, ObservableValue<String>>() {
	        @Override public ObservableValue<String> call(CellDataFeatures<Message, String> m) {
	          try {
	            // m.getValue() returns the Message instance for a particular TableView row
	            return new ReadOnlyObjectWrapper<String>(Arrays.toString(m.getValue().getRecipients(Message.RecipientType.TO)));
	          } catch (MessagingException ex) {
	            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	          }
	        }
	    });  
	    
		TableColumn subjectCol = new TableColumn("Subject");
	    subjectCol.setMinWidth(100);
	    subjectCol.setCellValueFactory(
	      new PropertyValueFactory<Message, String>("subject")
	    );
	    
	    TableColumn dateCol = new TableColumn("Sent date");
	    dateCol.setMinWidth(100);
	    dateCol.setCellValueFactory(
	      new PropertyValueFactory<Message, String>("sentDate")
	    );
	    
	    
		mailTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		mailTable.getColumns().addAll(fromColumn, toColumn, subjectCol, dateCol);
		
		
		Scene newScene = new Scene(new VBox(), 1000, 600);
		
		VBox vbox = new VBox();
		VBox vboxUser = new VBox(20);
		HBox hboxContent = new HBox(10);
		VBox vboxOpt = new VBox(20);
		VBox vboxMail = new VBox(20);
		
		vbox.setAlignment(Pos.CENTER);
		vboxUser.setAlignment(Pos.CENTER);
		hboxContent.setAlignment(Pos.CENTER);
		vboxOpt.setAlignment(Pos.TOP_LEFT);
		vboxMail.setAlignment(Pos.TOP_LEFT);
		
		String image = Gui.class.getResource("blurry.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center;");
		vbox.setPadding(new Insets(50));
		vbox.setPrefSize(1000, 600);
		
		vboxOpt.setPrefSize(200, 600);
		vboxOpt.setPadding(new Insets(20));
		vboxOpt.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		
		vboxMail.setPrefSize(800, 600);
		vboxMail.setPadding(new Insets(20));
		vboxMail.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		
		vboxOpt.getChildren().addAll(composeButton, inboxButton, sentButton, spamButton);
		vboxMail.getChildren().addAll(mailTable, deleteMailButton, readMail, refresh);
		hboxContent.getChildren().addAll(vboxOpt, vboxMail);
		vboxUser.getChildren().addAll(wait, hboxContent);
		vbox.getChildren().add(vboxUser);
		
		((VBox) newScene.getRoot()).getChildren().addAll(toolbar, vbox);

		setScene(newScene);
	}
	
	public void signupWindow(){
		sceneName = "signup";
		
		VBox vbox = new VBox();
		VBox vboxSignup = new VBox(20);
		HBox hbox = new HBox(20);
		VBox vboxLabels = new VBox(15);
		VBox vboxFields = new VBox(10);
		
		Text signup = new Text("Sign up for free");
		signup.setFont(Font.font ("Calibri light", 20));
		
		vbox.setAlignment(Pos.CENTER);
		vboxSignup.setAlignment(Pos.CENTER);
		vboxLabels.setAlignment(Pos.CENTER);
		vboxFields.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		
		vboxLabels.getChildren().addAll(new Label("Name"), new Label("Username"), new Label("Password"));
		vboxFields.getChildren().addAll(nameSignup, usernameSignup, passwordSignup);
		hbox.getChildren().addAll(vboxLabels, vboxFields);
		vboxSignup.getChildren().addAll(signup, hbox, signupUserButton);
		vbox.getChildren().add(vboxSignup);
		
		vboxSignup.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		vboxSignup.setPadding(new Insets(20));
		
		String image = Gui.class.getResource("blurry.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center;");
		vbox.setPadding(new Insets(50));
		
		Scene newScene = new Scene(vbox, 480, 360);
		setScene(newScene);
		
	}
	public void loginWindow(){
		sceneName = "login";
		
		VBox vboxLog = new VBox(20);
		HBox hboxUsername = new HBox(10);
		HBox hboxPassword = new HBox(10);
		HBox hboxSignup = new HBox(10);

		vboxLog.setAlignment(Pos.CENTER);
		hboxUsername.setAlignment(Pos.CENTER);
		hboxPassword.setAlignment(Pos.CENTER);
		hboxSignup.setAlignment(Pos.CENTER);

		Text welcome = new Text("Welcome");
		Label usernameLabel = new Label("Username");
		Label passwordLabel = new Label("Password");
		
		Text info = new Text("Don't have an account? Sign up for free");
		info.setFont(Font.font("Calibri light"));
		info.setFill(Color.gray(0.2));

		welcome.setFont(Font.font ("Calibri light", 20));
		
		hboxSignup.getChildren().addAll(info, signupButton);
		hboxUsername.getChildren().addAll(usernameLabel, usernameField);
		hboxPassword.getChildren().addAll(passwordLabel, passwordField);

		vboxLog.getChildren().addAll(welcome, hboxUsername, hboxPassword, loginButton, hboxSignup);

		vboxLog.setStyle("-fx-background-color: rgba(255,255,255,0.5);" + "-fx-background-radius: 10;");
		vboxLog.setPrefSize(400, 300);
		vboxLog.setPadding(new Insets(20));
		
		VBox vbox = new VBox();
		
		vbox.getChildren().add(vboxLog);
		
		String image = Gui.class.getResource("blurry.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center;");
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(50));
		
		Scene loginScene = new Scene(vbox, 480, 360);

		setScene(loginScene);
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
	/********************Setters and getters*************/
	public void setScene(Scene newScene){
		scene = newScene;
		stage.setScene(scene);
		stage.show();
	}
	
	public String getTo(){
		return toField.getText();
	}
	
	public String getSubject(){
		return subjectField.getText();
	}
	
	public String getMailContent(){
		return newMailContent.getText();
	}
	
	public String getLoginUsername(){
		return usernameField.getText();
	}
	
	public String getLoginPassword(){
		return passwordField.getText();
	}
	
	public String getSignupName(){
		return nameSignup.getText();
	}
	
	public String getSignupUsername(){
		return usernameSignup.getText();
	}
	
	public String getSignupPassword(){
		return passwordSignup.getText();
	}
	
	public void clearSendMailWindow(){
		toField.clear();
		subjectField.clear();
		newMailContent.clear();
	}
	
	public void clearSignupWindow(){
		usernameSignup.clear();
		nameSignup.clear();
		passwordSignup.clear();
	}
	public void clearTables(){
		mailTable.getColumns().clear();
	}
	
	public String getAddress(){
		return accountField.getText();
	}
	
	public String getProvider(){
		return providers.getValue();
	}
	
	public String getPassword(){
		return passwordAccount.getText();
	}
	
	public void setInboxTable(ArrayList<Message> messages){
		ObservableList<Message> m = FXCollections.observableArrayList(messages);
		mailTable.setItems(m);
	}
	
	public String getAccount(){
		return accounts.getValue();
	}
	
	public void addAccounts(ArrayList<String> value){
		ObservableList<String> v = FXCollections.observableArrayList(value);
		accounts.setItems(v);
		myAccounts.setItems(v);
	}
	
	public Message getSelectedMail(){
		return mailTable.getSelectionModel().getSelectedItem();
	}
	
	public void setReadMail(String sender, String to, String subject, String date, String content){
		senderMail.setText(sender);
		toMail.setText(to);
		subjectMail.setText(subject);
		dateMail.setText(date);
		mailContent.setText(content);
	}
	
	public String getReplyFrom(){
		return senderMail.getText();
	}
	
	public String getReplyTo(){
		return toMail.getText();
	}
	
	public String getReplySubject(){
		return subjectMail.getText();
	}
	
	public String getReplyContent(){
		return replyContent.getText();
	}
	
	public void setWaitMessage(String string){
		wait.setText(string);
	}
	
	public String getScene(){
		return this.sceneName;
	}
	
	public File getAttachment(){
		return file;
	}
	
	public void setFromField(String from){
		accounts.setValue(from);
	}
	
	public void setToField(String to){
		toField.setText(to);
	}
	
	public void setSubject(String subject){
		subjectField.setText(subject);
	}
	
	public void setMyUsername(String text){
		myUsername.setText(text);
	}
	
	public void setMyName(String text){
		myName.setText(text);
	}
	
	public String getMyName(){
		return myName.getText();
	}
	
	public String getMyUsername(){
		return myUsername.getText();
	}
	
	public String getMyPassword(){
		return myPassword.getText();
	}
	
	public String getMySelectedAccount(){
		return myAccounts.getValue();
	}
	/***************Listeners**************************/
	public void addListenerRefresh(EventHandler<ActionEvent> act){
		refresh.setOnAction(act);
	}
	public void addListenerDeleteMail(EventHandler<ActionEvent> act){
		deleteMailButton.setOnAction(act);
	}
	public void addListenerLoginButton(EventHandler<ActionEvent> act){
		loginButton.setOnAction(act);
	}
	public void addListenerSendMailButton(EventHandler<ActionEvent> act){
		sendButton.setOnAction(act);
	}
	public void addListenerInboxButton(EventHandler<ActionEvent> act){
		inboxButton.setOnAction(act);
	}

	public void addListenerSentButton(EventHandler<ActionEvent> act){
		sentButton.setOnAction(act);
	}
	
	public void addListenerSpamButton(EventHandler<ActionEvent> act){
		spamButton.setOnAction(act);
	}
	
	public void addListenerComposeButton(EventHandler<ActionEvent> act){
		composeButton.setOnAction(act);
	}
	
	public void addListenerMenuAddAccount(EventHandler<ActionEvent> act){
		menuAdd.setOnAction(act);
	}
	
	public void addListenerLogout(EventHandler<ActionEvent> act){
		menuLogout.setOnAction(act);
	}
	
	public void addListenerMyAccount(EventHandler<ActionEvent> act){
		menuAccount.setOnAction(act);
	}
	
	public void addListenerSignup(EventHandler<ActionEvent> act){
		signupButton.setOnAction(act);
	}
	
	public void addListenerAddUser(EventHandler<ActionEvent> act){
		signupUserButton.setOnAction(act);
	}
	
	public void addListenerAddAccount(EventHandler<ActionEvent> act){
		addAccountButton.setOnAction(act);
	}
	
	public void addListenerReadMail(EventHandler<ActionEvent> act){
		readMail.setOnAction(act);
	}
	
	public void addListenerReplyMail(EventHandler<ActionEvent> act){
		replyButton.setOnAction(act);
	}
	
	public void addListenerUpdateMyAccount(EventHandler<ActionEvent> act){
		updateMyAccount.setOnAction(act);
	}
	
	public void addListenerDeleteEmailAccount(EventHandler<ActionEvent> act){
		deleteMyAccount.setOnAction(act);
	}
	
	/**********************Messages******************************/
	public void ErrorMessage(String title, String header, String content){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		alert.showAndWait();
	}
	
	public void SuccesMessage(String title, String header, String content){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		alert.showAndWait();
	}
}
