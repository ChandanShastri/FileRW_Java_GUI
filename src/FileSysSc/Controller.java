package FileSysSc;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* Coding By Chandan Shastri
 * crshastri@gmail.com
 */

public class Controller extends Application {

	// Universal Declarations.

	static Stage Window;
	static Scene Main,AddFile,SearchWindow,ShowAll;;
	int HashValue;
	static String RecFile="/home/chandan_shastri/Project/file.txt";
	static String TempFile="/home/chandan_shastri/Project/Tempfile.txt";
	int confirmation=0;
	String TEMP;
	String Line;
	String T1,T2;
	ArrayList<ScContact>StreamLoader=new ArrayList<ScContact>();
	private final static TableView<ScContact> tableView = new TableView<>();
	private final static ObservableList<ScContact>dataList= FXCollections.observableArrayList();
	static TableColumn columnF1=new TableColumn("First Name");;
	static TableColumn columnF2 = new TableColumn("Last Name");
	static TableColumn columnF3 = new TableColumn("Phone Number");
	static TableColumn  columnF4 = new TableColumn("Email ID");
	static ArrayList<String>PhoneList=new ArrayList<String>();
	static ArrayList<String>EmailList=new ArrayList<String>();
	static ArrayList<String>HashList=new ArrayList<String>();
	 
	static Label Header=new Label();
	static Label EmptySpace=new Label();
	String FFF;
	static Label msgs=new Label();
	
	
	static VBox vBox = new VBox();
	static GridPane BPSHA=new GridPane();
	
	static Label SuccessMsg=new Label("Saved Successfully");
	
	static Button ReTButton=new Button("Return");
	
	
	public static void initTable()
	{
		PhoneList.clear();
		EmailList.clear();
		HashList.clear();
		
		BufferedReader br;
		 
        try {
            br = new BufferedReader(new FileReader(RecFile));
 
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("#");
               
                PhoneList.add(fields[2].toUpperCase());
                EmailList.add(fields[3].toUpperCase());
                HashList.add(fields[4].toUpperCase());
 
            }
 
        } catch (Exception ex) {
            System.out.println("An Error Occured "+ex);
        } 
		
   
	 
	}
	
	
	public static void RESET()
	{
		msgs.setVisible(false);
		initTable();
	}
	
	public static void deleteContact()
	{
		ScContact DelItem = tableView.getSelectionModel().getSelectedItem();
		if(DelItem==null)
		{
			msgs.setText("Select a Contact First");
			msgs.setVisible(true);
			return;
		}
		String DelT=DelItem.getF1()+"#"+DelItem.getF2()+"#"+DelItem.getF3()+"#"+DelItem.getF4();
		System.out.println("Selected Item is : "+DelT);
		File Original = new File(RecFile);
		File Modified = new File(TempFile);
		try {

		BufferedReader reader = new BufferedReader(new FileReader(Original));
		BufferedWriter writer = new BufferedWriter(new FileWriter(Modified));
		
		String lineToRemove = DelT;
		lineToRemove=DelT.trim();
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.contains(lineToRemove))
		    	{
		    		msgs.setText("  Contact of "+DelItem.getF1()+" has been Deleted ");
		    		msgs.setVisible(true);
		    		continue;
		    	}
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
		}
		catch(Exception e)
		{
			System.out.println("\n\nAn Error Occured : "+e);
		}
		
		boolean successful = Modified.renameTo(Original);
		initTable();
		
	}
	
	

	//--------------------------------------------

	public static void main(String[] args) {
		
		initTable();

columnF1.setCellValueFactory(new PropertyValueFactory<>("f1"));

	    
	    columnF2.setCellValueFactory(new PropertyValueFactory<>("f2"));

	    
	    columnF3.setCellValueFactory(new PropertyValueFactory<>("f3"));

	   
	    columnF4.setCellValueFactory(new PropertyValueFactory<>("f4"));
	    
	    columnF1.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF2.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF3.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
	    columnF4.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));

	    tableView.setPrefSize(800, 300);
		 tableView.getColumns().addAll(columnF1, columnF2, columnF3, columnF4);
		 msgs.setFont(Font.font("Verdana",FontWeight.BOLD,15));
		 msgs.setTextFill(Color.RED);
		 
		 Header.setFont(Font.font("Verdana",FontWeight.BOLD,25));
		 Header.setTextFill(Color.BLUE);
		EmptySpace.setText("          ");
		 
	     Button ReTButton=new Button("Return");
	     ReTButton.setTextFill(Color.GREEN);
	     ReTButton.setOnAction(e->{
	    	 	RESET();
	        	Window.setScene(Main);
	        });
	     Button DelButton=new Button("Delete");
	     DelButton.setTextFill(Color.RED);
	     DelButton.setOnAction(e->{
	    	 deleteContact();
	    	 TableViewer();
	        });
	     
	     
	     
	     ShowAll=new Scene(BPSHA, 800,400 );
	     HBox hbv=new HBox();
	     hbv.setSpacing(10);
	     hbv.getChildren().add(EmptySpace);
	     hbv.getChildren().add(ReTButton);
	     hbv.getChildren().add(DelButton);
	     hbv.getChildren().add(msgs);
	     msgs.setVisible(false);
	     
	     vBox.setSpacing(10);
	     vBox.getChildren().add(Header);
	     vBox.getChildren().add(tableView);
	     vBox.getChildren().add(hbv);
	    

	     BPSHA.getChildren().add(vBox);
		launch(args);

	}

	// Core Functions - Adding the Content to File.

	public int AddtoFile(String Line)
	{
		try {
		 Writer writer = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream(RecFile, true), "UTF-8"));
		 HashValue=Line.hashCode();

		 writer.write(Line+"#"+HashValue+"\n");


		writer.close();
		initTable();
		return 1;
		}
		catch(Exception E)
		{
			return 0;
		}
		
		

	}

	
	public static void TableViewer() {
		
		
        
       tableView.getItems().clear();
        
        tableView.setItems(dataList);
        
        ScFileReader();
 
        Header.setText("                                     All Contacts ");
        Window.setScene(ShowAll);
        
        //ShowAll=new Scene(BPSHA, 800,500 );
        
 
	}

	
	// Core Function #2 - Searching the File.

	public String SearchFile(String Item)
	{
		
		 tableView.getItems().clear();
	        
		 String[] Items = Item.split(" ");
		 String[] LineItems;
		 boolean Flag;
	        
		try {
			BufferedReader bb=new BufferedReader(new FileReader(RecFile));
			TEMP="";
			 tableView.getItems().clear();
			while((Line=bb.readLine())!=null)
			{	
				Flag=false;
				T1=Item;
				T1=T1.toUpperCase();
				T2=Line;
				T2=T2.toUpperCase();
				LineItems=Line.split("#");
				
				for (String TX : LineItems)
				{
					TX=TX.toUpperCase();
					for(String FT:Items)
					{
						FT=FT.toUpperCase();
						if(TX.startsWith(FT))
				{
					
					String[] fields = Line.split("#");
	                
	 
	                ScContact record = new ScContact(fields[0], fields[1], fields[2],fields[3],fields[4]);
	                dataList.add(record);
	                
	                Flag=true;
				        
					//TEMP=TEMP+"\n"+Line;
				}
						if(Flag==true)
						{
							break;
						}
				}
					if(Flag==true)
					break;
					}
			}
			
			Header.setText("                                   Search Results ");
			tableView.setItems(dataList);
			if(TEMP.length()>0)
				return TEMP;
			bb.close();
		}

		catch(Exception e)
		{
			System.out.println("Error in File Access - A small exception Occured \n"+e);
		}

		return "The Item : "+Item+" not Found in the Contact List";
	}
	
	
	
	
	//Core Function #3 : Show All Contacts
	public void ShowAllContacts()
	{
		try (Stream<String> stream = Files.lines(Paths.get(RecFile))) {
	        stream.forEach(System.out::println);
	}
		catch( Exception E)
		{
			System.out.println("Error : Cannot Open File");
			
		}
	
		
	}
	
	
	public static int DuplicateChecker(String NC)
	{
		initTable();
		String[] NewContact=NC.split("#");
		String HS=Integer.toString(NC.hashCode());
		
		
		if(HashList.contains(HS))
		{
			SuccessMsg.setText("This Contact Already Exists....!");
			SuccessMsg.setVisible(true);
			return 1;
		}
		else
		if(PhoneList.contains(NewContact[2].toUpperCase()))
		{	SuccessMsg.setText("Phone Number Already Exits....!");
			SuccessMsg.setVisible(true);
			return 1;
		}
		
		else if(EmailList.contains(NewContact[3].toUpperCase()))
		{
			SuccessMsg.setText("Email ID Already Exits....!");
			SuccessMsg.setVisible(true);
			return 1;
		}
		else
		return 0;
	}


	// Beginning of Interface.

	@Override
	public void start(Stage mainstage) throws Exception {
		
		
			Window=mainstage;
			Window.setResizable(false);
			GridPane BP=new GridPane();
			HBox HB=new HBox();
			VBox VB=new VBox();
			

			 HB.setPadding(new Insets(30, 25, 15, 12));
			 HB.setSpacing(10);
			 HB.setPrefSize(500,100);
			 HB.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));

			VB.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
			 
			 VB.setPadding(new Insets(15, 12, 15, 12));
			 VB.setSpacing(10);
			 initTable();


			// Scene 1 - Configuration.
			BP.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
			BP.setPadding(new Insets(10,10,10,10));


			// Screen 1 Begins Here.
			Label WelcomeTitle=new Label("        Welcome to Contacts Application");
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
			ViewAC.setOnAction(e->{
				System.out.println("\nThe Contacts Currently in the File :\n\n");
				
				TableViewer();
				
				//ShowAllContacts();
			});
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
			//LnameF.setPrefSize(40, 100);
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
			
			/*---------------------------------------------------*/
			
			Label FileName=new Label("File Name with Full Path :");
			TextField FilePath=new TextField();
			FilePath.setPromptText("Enter Path to File");
			FilePath.setOnMouseClicked(e->SuccessMsg.setVisible(false));
			
			
			//Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
			
			
			/*-----------------------------------------------------*/
			

			Button Save=new Button("Add A New Contact");
			Save.setTextFill(Color.RED);

			Save.setOnAction(e->{
				if((FnameF.getLength()>0)&&(LnameF.getLength()>0)&&(PhoneF.getLength()>0)&&(EmailF.getLength()>0))
				{
				TEMP=PhoneF.getText();
				if(TEMP.matches("[0-9]*") && TEMP.length()==10)
				{TEMP=EmailF.getText();
				if(TEMP.matches("[[0-9]*[a-z]*[0-9]*]+[@][a-z]+[.][a-z]+"))	
				{	
				TEMP=FnameF.getText()+"#"+LnameF.getText()+"#"+PhoneF.getText()+"#"+EmailF.getText();	
				if(DuplicateChecker(TEMP)==0)
				{	
				confirmation=AddtoFile(TEMP);
				if(confirmation==1)
				{	FFF="/home/chandan_shastri/"+FnameF.getText()+".txt";
					String G=FilePath.getText();
					//System.out.println(FFF+" "+G);
					System.out.println("Added a new Contact");
					SuccessMsg.setText("Successfull Added a New Contact");
					File dirFrom = new File(G);
					File dirTo = new File(FFF);
					
					/*
					try {
					        //copyFile(dirFrom, dirTo);
					        Files.copy(dirFrom.toPath(),dirTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException ex) {
					       // Logger.getLogger(TestJava8.class.getName()).log(Level.SEVERE, null, ex);
					}
					//Files.copy(dirFrom, dirTo, StandardCopyOption.REPLACE_EXISTING);
					*/
					
					
	
					SuccessMsg.setTextFill(Color.GREEN);
					SuccessMsg.setVisible(true);
					FnameF.setText("");
					LnameF.setText("");
					PhoneF.setText("");
					EmailF.setText("");
				}
				else
					System.out.println("ERROR - Adding to the File..!");
				}
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
			Cancel.setTextFill(Color.GREEN);

			Cancel.setOnAction(e->{
				RESET();
				Window.setScene(Main);
			
			});

			HB1.getChildren().addAll(WelcomeTitle1);
			VB1.getChildren().addAll(Heading1,FName,FnameF,Lname,LnameF,Email,EmailF,Phone,PhoneF);
			HB11.getChildren().addAll(Save,Cancel,SuccessMsg);


			BP1.add(HB1,0,1);
			BP1.add(VB1,0,3);
			BP1.add(HB11,0,4);
		AddFile=new Scene(BP1,800,450);


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
		SearchTitle.setTextFill(Color.BLUE);

		TextField SearchText=new TextField();
		SearchText.setPromptText("Enter the name or Email ID");
		//SearchText.setPrefSize(30, 5);

		Button SearchNow=new Button("Search");
		SearchNow.setTextFill(Color.BLUE);
		Button Return=new Button("Return");
		Return.setTextFill(Color.GREEN);

		//SearchNow.setPrefSize(25,3);
		SearchNow.setOnAction(e->{
			System.out.println("\nSearch Started\n\n");
			if(SearchText.getText().length()>=3)
			{
				tableView.getItems().clear();
			SearchFile(SearchText.getText());
			Window.setScene(ShowAll);
			//System.out.println(TEMP);
			}
			else if(SearchText.getText().length()>=1)
			{
				SearchText.setPromptText("Too Short to Search...!!");
				msgs.setText("Too Short to Search...!");
				msgs.setVisible(true);
			}	
			else
			{
				SearchText.setPromptText("Enter Something Here First...!");
				msgs.setText("Enter Something to Search...!");
				msgs.setVisible(true);
			}
		});
		Return.setOnAction(e->{
			System.out.println("Returning to Home ");
			Window.setScene(Main);
		});

		HB21.getChildren().addAll(SearchNow,Return,msgs);

		HB2.getChildren().addAll(SearchTitle);
		VB2.getChildren().addAll(SearchText,HB21);

		BP3.add(HB2, 0, 2);
		BP3.add(VB2, 0,4);
		BP.setAlignment(Pos.CENTER);
		SearchWindow=new Scene(BP3,800,400);

//---------------------------------------------------------------------------



			Window.setScene(Main);
			Window.setTitle("Chandan Shastri's File Structure Mini Project");
			Window.show();

	}
	private boolean Validate(String text)
    {
        return text.matches("[0-9]*");
    }
	
	/*
	public static void copyFile( File from, File to ) throws IOException {
	    Files.copy( from.toPath(), to.toPath() );
	} 
	*/
	private static void ScFileReader() {
		 
        
        String FieldDelimiter = "^";
        dataList.removeAll();
 
        BufferedReader br;
 
        try {
            br = new BufferedReader(new FileReader(RecFile));
 
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("#");
               
 
                ScContact record = new ScContact(fields[0], fields[1], fields[2],fields[3],fields[4]);
                dataList.add(record);
 
            }
 
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
 
    }

}
