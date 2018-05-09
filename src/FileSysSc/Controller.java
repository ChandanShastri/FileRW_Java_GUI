package FileSysSc;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/* Coding By Chandan Shastri
 * crshastri@gmail.com
 */

public class Controller extends Application {

	// Universal Declarations.

	Stage Window;
	Scene Main,AddFile,SearchWindow;
	int HashValue;
	String RecFile="/home/chandan_shastri/file.txt";
	int confirmation=0;
	String TEMP;
	String Line;
	String T1,T2;
	ArrayList<ScContact>StreamLoader=new ArrayList<ScContact>();


	//--------------------------------------------

	public static void main(String[] args) {
		launch(args);

	}

	// Core Functions - Adding the Content to File.

	public int AddtoFile(String Line)
	{
		try {
		 Writer writer = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream(RecFile, true), "UTF-8"));
		 HashValue=Line.hashCode();

		 writer.write(Line+"^"+HashValue+"\n");


		writer.close();
		try (Stream<String> stream = Files.lines(Paths.get(RecFile))) {
	        stream.forEach(System.out::println);
	}

		return 1;
		}
		catch ( Exception E)
		{
			System.out.println("Error : Cannot Open File");
			return 0;
		}

	}

	// Core Function #2 - Searching the File.

	public String SearchFile(String Item)
	{
		try {
			BufferedReader bb=new BufferedReader(new FileReader(RecFile));
			TEMP="";
			while((Line=bb.readLine())!=null)
			{	T1=Item;
				T1=T1.toUpperCase();
				T2=Line;
				T2=T2.toUpperCase();

				if(T2.contains(T1))
				{
					//System.out.println(Line);
					TEMP=TEMP+"\n"+Line;
				}
			}
			if(TEMP.length()>0)
				return TEMP;
			bb.close();
		}

		catch(Exception e)
		{
			System.out.println("Error in File Access");
		}

		return "The Item : "+Item+" not Found in the Contact List";
	}



	// Beginning of Interface.

	@Override
	public void start(Stage mainstage) throws Exception {

		Label SuccessMsg=new Label("Saved Successfully");
			Window=mainstage;
			Window.setResizable(false);
			GridPane BP=new GridPane();
			HBox HB=new HBox();
			VBox VB=new VBox();

			 HB.setPadding(new Insets(25, 25, 15, 12));
			 HB.setSpacing(10);

			 VB.setPadding(new Insets(15, 12, 15, 12));
			 VB.setSpacing(10);



			// Scene 1 - Configuration.

			BP.setPadding(new Insets(10,10,10,10));


			// Screen 1 Begins Here.
			Label WelcomeTitle=new Label("Welcome to Contacts Application");
			WelcomeTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD,FontPosture.REGULAR, 20));
			WelcomeTitle.setTextFill(Color.RED);


			Label Heading=new Label("Please Select an Option :");
			Heading.setFont(Font.font("Verdana",FontWeight.BOLD,15));



			Button AddC=new Button("Add A New Contact");
			AddC.setPrefSize(200,20);

			AddC.setOnAction(e->{

			Window.setScene(AddFile);
			SuccessMsg.setVisible(false);
			});

			Button SearchC=new Button("Search for Contacts");
			SearchC.setPrefSize(200,20);

			Button ViewAC=new Button("View All Contacts");
			ViewAC.setPrefSize(200,20);
			SearchC.setOnAction(e->{Window.setScene(SearchWindow);
									System.out.println("Click Registered on Search Button");	});

			HB.getChildren().addAll(WelcomeTitle);
			VB.getChildren().addAll(Heading,AddC,SearchC,ViewAC);

			BP.add(HB,0,1);
			BP.add(VB,0,3);
			Main=new Scene(BP,800,400);


// --------------------------------------------------------------------------------------------

			// Scene 2 : File Name addition Window Begins here.

			GridPane BP1=new GridPane();
			HBox HB1=new HBox();
			HBox HB11=new HBox();
			VBox VB1=new VBox();

			 HB1.setPadding(new Insets(25, 25, 15, 12));
			 HB1.setSpacing(10);

			 HB11.setPadding(new Insets(25, 25, 15, 12));
			 HB11.setSpacing(10);

			 VB1.setPadding(new Insets(15, 12, 15, 12));
			 VB1.setSpacing(10);

			BP1.setPadding(new Insets(10,10,10,10));


			SuccessMsg.setFont(Font.font("Verdana",FontWeight.BOLD,15));
			SuccessMsg.setTextFill(Color.GREEN);
			SuccessMsg.setVisible(false);


			Label WelcomeTitle1=new Label("Add a New Contact to File");
			WelcomeTitle1.setFont(Font.font("Times New Roman", FontWeight.BOLD,FontPosture.REGULAR, 20));
			WelcomeTitle1.setTextFill(Color.RED);

			Label Heading1=new Label("Please Fill all the Fields :");
			Heading1.setFont(Font.font("Verdana",FontWeight.BOLD,15));

			Label FName=new Label("First Name :");
			TextField FnameF=new TextField();
			FnameF.setPromptText("Enter Proper First Name");
			FnameF.setOnMouseClicked(e->SuccessMsg.setVisible(false));

			Label Lname=new Label("Last Name :");
			TextField LnameF=new TextField();
			LnameF.setPromptText("Enter Proper Last Name");
			LnameF.setOnMouseClicked(e->SuccessMsg.setVisible(false));

			Label Phone=new Label("Phone Number :");
			TextField PhoneF=new TextField();
			PhoneF.setPromptText("Enter 10 digit Phone number");
			PhoneF.setOnMouseClicked(e->SuccessMsg.setVisible(false));

			Label Email=new Label("Email ID :");
			TextField EmailF=new TextField();
			EmailF.setPromptText("Enter Email ID");
			EmailF.setOnMouseClicked(e->SuccessMsg.setVisible(false));



			Button Save=new Button("Add A New Contact");

			Save.setOnAction(e->{
				if((FnameF.getLength()>0)&&(LnameF.getLength()>0)&&(PhoneF.getLength()>0)&&(EmailF.getLength()>0))
				{
				TEMP=PhoneF.getText();
				if(TEMP.matches("[0-9]*") && TEMP.length()==10)
				{TEMP=EmailF.getText();
				if(TEMP.matches("[[0-9]*[a-z]*[0-9]*]+[@][a-z]+[.][a-z]+"))	
				{	
				confirmation=AddtoFile(FnameF.getText()+"^"+LnameF.getText()+"^"+PhoneF.getText()+"^"+EmailF.getText());
				if(confirmation==1)
				{
					System.out.println("Added a new Contact");
					SuccessMsg.setText("Successfull Added a New Contact");
					SuccessMsg.setTextFill(Color.GREEN);
					SuccessMsg.setVisible(true);
					FnameF.setText("");
					LnameF.setText("");
					PhoneF.setText("");
					EmailF.setText("");
				}
				else
					System.out.println("ERROR - Adding to the File..!");
			}else
			{
				SuccessMsg.setText("Enter Proper Email ID");
				SuccessMsg.setTextFill(Color.RED);
				SuccessMsg.setVisible(true);
			}
				}
				else
				{
					SuccessMsg.setText("Please Enter Proper Mobile Number");
					SuccessMsg.setTextFill(Color.RED);
					SuccessMsg.setVisible(true);
				}
				
				
				}
			else
				{
				SuccessMsg.setText("Add All the Details First..!!!");
				SuccessMsg.setTextFill(Color.RED);
				SuccessMsg.setVisible(true);
				}});
				

			Button Cancel=new Button("Return");

			Cancel.setOnAction(e->{Window.setScene(Main);
									System.out.println("Click Registered on Search Button");	});

			HB1.getChildren().addAll(WelcomeTitle1);
			VB1.getChildren().addAll(Heading1,FName,FnameF,Lname,LnameF,Email,EmailF,Phone,PhoneF);
			HB11.getChildren().addAll(Save,Cancel,SuccessMsg);


			BP1.add(HB1,0,1);
			BP1.add(VB1,0,3);
			BP1.add(HB11,0,4);
		AddFile=new Scene(BP1,800,600);


// -----------------------------------------------------------------------------------------------

		GridPane BP3=new GridPane();
		HBox HB2=new HBox();
		HBox HB21=new HBox();
		VBox VB2=new VBox();


		HB2.setPadding(new Insets(25, 25, 15, 12));
		 HB2.setSpacing(10);

		 HB21.setPadding(new Insets(25, 25, 15, 12));
		 HB21.setSpacing(10);

		 VB2.setPadding(new Insets(25, 25, 15, 12));
		 VB2.setSpacing(10);

		Label SearchTitle=new Label("Enter name or email to Search : ");

		SearchTitle.setFont(Font.font("Verdana", FontWeight.BOLD,20));
		SearchTitle.setTextFill(Color.RED);

		TextField SearchText=new TextField();
		SearchText.setPromptText("Enter the name or Email ID");
		//SearchText.setPrefSize(30, 5);

		Button SearchNow=new Button("Search");
		Button Return=new Button("Return");

		//SearchNow.setPrefSize(25,3);
		SearchNow.setOnAction(e->{
			System.out.println("\nSearch Started\n\n");
			if(SearchText.getText().length()>0)
			{TEMP=SearchFile(SearchText.getText());
			System.out.println(TEMP);
			}
			else
				SearchText.setPromptText("Enter Something Here First...!");
		});
		Return.setOnAction(e->{
			System.out.println("Returning to Home Screen");
			Window.setScene(Main);
		});

		HB21.getChildren().addAll(SearchNow,Return);

		HB2.getChildren().addAll(SearchTitle);
		VB2.getChildren().addAll(SearchText,HB21);

		BP3.add(HB2, 0, 2);
		BP3.add(VB2, 0,4);
		SearchWindow=new Scene(BP3,800,400);




//---------------------------------------------------------------------------



			Window.setScene(Main);
			Window.setTitle("AIET ISE - File Structure Mini Project");
			Window.show();

	}
	private boolean Validate(String text)
    {
        return text.matches("[0-9]*");
    }


}
