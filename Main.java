package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.InputMismatchException;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.shape.Line;  
import javafx.geometry.Bounds;
import javafx.scene.Node;

public class Main extends Application {
	    private Stage primaryStage;
	    private TextArea resultTextArea;
	    private int[] firstArray;
	    private int[] secondArray;
	    private int[] lcs ;
	    private int[][] DP; // Declare dp as a class-level field
	    private TextField lengthField; // Declare lengthField as a class-level field
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		 Scene scene = new Scene(firstScrren(primaryStage) , 800 ,500);
		 primaryStage.setScene(scene);
		 primaryStage.setTitle("LCS Application");
		 primaryStage.show();
		
		
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public BorderPane firstScrren(Stage stage) {
		System.out.println("←");
		
		 BorderPane borderpane = new BorderPane(); // create a new Pane 
		 Image image1 = new Image("https://etimg.etb2bimg.com/photo/71884644.cms"); // add image to the scene 
		 
		 ImageView imageView = new ImageView(image1);
		 Label label1 = new Label (); // create a label
		 Button button1 = new Button ("Load Data from File"); // button to read from file 
		 Button button2 = new Button ("Enter Data"); // this button show the second  screen 
		 HBox hBox = new HBox(30);

		 button1.setPrefWidth(200);
		 button2.setPrefWidth(200); 
		 
		
		 
		 hBox.setAlignment(Pos.CENTER);
		 hBox.getChildren().addAll(button1 ,button2 );
		 
		 		 
		 label1.setText("\t\t\t\tMax LED Lighting  "); // add text to the label
		 label1.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,30)); // set font to the label
		 
		 
		 imageView.setFitWidth(500);
		 imageView.setFitHeight(400);
		 
		 borderpane.setStyle("-fx-background-color:	DARKGRAY");
		 borderpane.setPadding(new Insets (5 ,5,5,5));
		 borderpane.setCenter(imageView); // add imageView to pane 
		 borderpane.setTop(label1); // add label1 to the pane 
		 borderpane.setBottom(hBox); // add hBox to pane 
		 
		 
		 button1.setOnAction(e->{
			 try {
				readfile(stage);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 });
		 
		 
		 button2.setOnAction(e->{
			 enterInputButton();
		 });
		
		 return borderpane;
		
	}
	
	// this method to display the number of TEDs stage 
	public Pane enterInputButton(){
		
		GridPane gridPane = new GridPane(); // create a new GridPane
   		BorderPane pane = new BorderPane();
   		//primaryStage = new Stage ();
		Label lengthLabel = new Label(" Enter number of LEDs : ");
		lengthField = new TextField();
		Button enterDataButton = new Button ("Enter Data");
		HBox Hbox = new HBox(100);
		VBox Vbox = new VBox(40);

		// Set background image
        Image backgroundImage = new Image("https://img.freepik.com/premium-photo/tungsten-light-bulb-lit-black-background-3d-render-raster-illustration_607202-529.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(200, 100, true, true, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		
		lengthField.setMaxWidth(250);
		Hbox.setAlignment(Pos.CENTER);	
		Hbox.getChildren().addAll(lengthLabel , lengthField);
		
		
		Vbox.setAlignment(Pos.CENTER);
		Vbox.setBackground(new Background(background));
		Vbox.getChildren().addAll(lengthLabel , lengthField , enterDataButton);
		Hbox.getChildren().add(Vbox);
		
	
	
		
		lengthLabel.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,25)); // set font to the label
		lengthLabel.setStyle("-fx-text-fill:white");// set colour to the label font 	
		enterDataButton.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
		enterDataButton.setPrefWidth(200);
		
		pane.setCenter(Vbox);
		
		
		Scene scene = new Scene (pane , 800 ,500);
		primaryStage.setTitle("Enter a Number of LEDs");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		 enterDataButton.setOnAction(enterEvent -> processEnteredData());
		
		
		
		return pane;
		
	}
	
	 private void processEnteredData() {
		 
		 
		 int length = Integer.parseInt(lengthField.getText());
		 firstArray = new int[length];           // set size to the first Array 
		 
		 for (int i = 0 ; i < length ; i++ ) { // full first Array
			 firstArray[i] = i+1;
		 }
		 
		 secondArray = ArrayInputElement(length);  // full second Arry 
		 DP = CalculateDPtable(length);
		 
		 resultStage();
		 
		
	 }
	 
	 
	 
	  // this method to enter the element of the second Array : 
	private int [] ArrayInputElement(int Rang) {
			 int [] Array = new int [Rang];
			 ArrayList<Integer> uniqueElements = new ArrayList<>();
			 
			 for (int i = 0 ; i < Rang ; i++) {
				 int element;
				 
				 do {
					 
					 TextInputDialog dialog = new TextInputDialog();  // TextInputDialog class to enter numbers to second Array
		             dialog.setTitle("Enter Data");
		             dialog.setHeaderText("Enter a Number to  " + (i + 1) +" LED");
		             dialog.setContentText("Element:");
		             dialog.getDialogPane().setPrefWidth(500);
		             dialog.getDialogPane().setPrefHeight(400);
		             dialog.getEditor().setFont(Font.font("Time New Roman", 20));

		             

		                try {
		                    element = Integer.parseInt(dialog.showAndWait().orElse(""));
		                } catch (NumberFormatException ex) {
		                    element = -1;  // Set an invalid value to force re-entry
		                }
		                

		                if (element < 1 || element > Rang ) {  // error message if the number out of the range : 
		                    Alert alert = new Alert(Alert.AlertType.ERROR);
		                    alert.setTitle("Error");
		                    alert.setHeaderText("Invalid Input");
		                    alert.setContentText("Please enter a valid number in the range [1, " + Rang + "].");
		                    alert.showAndWait();
		                }
		                
		                else if (uniqueElements.contains(element)) { // error message  if the number is excite 
		                	Alert alert = new Alert(Alert.AlertType.ERROR);
		                    alert.setTitle("Error");
		                    alert.setHeaderText("Invalid Input");
		                    alert.setContentText("this number is aleady exite try agane please ");
		                    alert.showAndWait();
		             
		                }
					 
					 
					 
				 } while (element < 1 || element > Rang || uniqueElements.contains(element) );
				 
				 Array[i] = element;  // full the Array 
				 uniqueElements.add(element);
				 
				 
				 
			 }
			 
			 return Array ; 
		 }
		 
		 
	 
	     // this method show all result : 
	 
	 private Pane resultStage() {
		 int length = Integer.parseInt(lengthField.getText());
		 HBox Box = new HBox(50);
		 BorderPane pane = new BorderPane();
		 GridPane gridpane = new GridPane();
		 
		 Button show = new Button("Show Length of LCS");
		 Button result = new Button("Show LCS");
		 Button table = new Button ("Show DP Table");
		 Button Highlight = new Button ("Show LEDs");

		 
		 resultTextArea = new TextArea();
		 resultTextArea.setPrefWidth(800);;
		 resultTextArea.setPrefHeight(600);

		 
		 show.setMaxWidth(300);
		 result.setMaxWidth(300);
		 table.setMaxWidth(300);
		 Highlight.setMaxWidth(300);
		 
		 show.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
		 result.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
		 table.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
		 Highlight.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
		 
		 Box.getChildren().addAll(show , result , table , Highlight);
		 

		 gridpane.setAlignment(Pos.CENTER);
    	 gridpane.setVgap(15);
    	 gridpane.setHgap(30);
    	 gridpane.add(resultTextArea, 0, 1);
    	 gridpane.add(Box, 0, 2);
    	 gridpane.setStyle("-fx-background-color:Linear-gradient(#61D8DE , #E839F6) ");
		 
		 Scene scene = new Scene(gridpane , 1000 ,700);
		 
		 primaryStage.setScene(scene);
		 
		 
		 show.setOnAction(e-> ShowLengthOfLCS(length));
		 result.setOnAction(e-> ShowLCS (length) );
		 table.setOnAction(e-> ShowDpTabel(length));
		 Highlight.setOnAction(e-> hiliteButton());
		 
		 return gridpane;
	 }
	 
	// this method to print the length of the LCS :
	 
	 private void ShowLengthOfLCS (int length) { 
		 
		 int LCS_Length = DP[length][length];  // Variable to store the length 
		 resultTextArea.setText("Length of Longest Common Subsequence : "+ LCS_Length); // print the result in the Text Area
		 Font newFont = ((Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15)));
		 resultTextArea.setFont(newFont);
		 
		 
	 }
	 
	 
	 // this method to print the LCS : 
	 private void ShowLCS (int length) {
		   int lcsLength = DP[length][length];
	        lcs = LCSArray(length, lcsLength);
	        resultTextArea.setText("Longest Common Subsequence: " + arrayToString(lcs));
		 System.out.println("mo");
		 
	 }
	 
	 
	 // this method to store LCS Array in string Builder 1
	 private String arrayToString(int[] arr) {
	        StringBuilder sb = new StringBuilder();
	        for (int value : arr) {
	            sb.append(value).append(" , ");
	        }
	        return sb.toString().trim();
	    }
	    
	    
	 
	  // this method return the LCS Array : 
	  private int[] LCSArray(int length, int lcsLength) {
		  
	         lcs = new int[lcsLength];
	        int i = length, j = length, index = lcsLength - 1;
	        System.out.println(i+"\n"+j+"\n"+index);
	        
	        while (i > 0 && j > 0) {
	            if (firstArray[i - 1] == secondArray[j - 1]) {
	                lcs[index] = firstArray[i - 1];
	                i--;
	                j--;
	                index--;
	            } else if (DP[i - 1][j] >= DP[i][j - 1]) {
	                i--;   // up
	            } else {
	                j--;  // left  
	            }
	        }
	        return lcs;
	    }
	  
	  private int [][] CalculqateDPtable(int length){    // this table to calculate DP 
			 int [][] DP = new int [length +1 ][length + 1] ;
			 
			 for (int i = 0  ; i <= length  ; i++) {
				 for (int j = 0 ; j <= length ; j++) {
					 
					 if (i == 0 || j == 0)  // Initial value  
						 DP[i][j] = 0;
					 
					 
					 if (firstArray[i-1] == secondArray[j-1]) {  // if the to number in 2Array are equle 
						 DP[i+1][j+1] = DP[i-1][j-1] + 1 ;
						 
					 }
					 else {   // not equle 
						 DP[i+1][j+1] = Math.max(DP[i-1][j], DP[i][j-1]);
					 }
				 }
			 } 
			 
			 return DP;  // DP array 
			 
			 
		 }


	  private void ShowDpTabel(int length) {
		    StringBuilder table = new StringBuilder("Dynamic Programming Table:\n\n");

		    // Header row with values from the second Array:
		    table.append("\t0\t"); // Empty space in the first column
		    for (int value : secondArray) {
		        table.append(String.format(value + "\t"));
		    }
		    table.append("\n");

		    char[][] movementTable = new char[length + 1][length + 1];

		    // Rows with the first Array and DP values:
		    for (int i = 0; i <= length; i++) {
		        if (i > 0) {
		            table.append(String.format(firstArray[i - 1] + "\t"));
		        } else {
		            table.append("0\t"); // Empty space in the first column
		        }
		        
		        for (int j = 0; j <= length; j++) {
		            if (i > 0 && j > 0) {
		                if (firstArray[i - 1] == secondArray[j - 1]) {
		                    movementTable[i][j] = 'Q'; // Diagonal movement
		                } else if (DP[i - 1][j] >= DP[i][j - 1]) {
		                    movementTable[i][j] = 'U'; // Upward movement
		                } else {
		                    movementTable[i][j] = 'L'; // Leftward movement
		                }
		            }
		            table.append(String.format(DP[i][j] + "\t"));  // display DP tabel 
		        }
		        table.append("\n");
		    }

		    table.append("\n\nMovement Table:\n\n");
		    for (int i = 1; i <= length; i++) {
		        for (int j = 1; j <= length; j++) {
		            table.append(movementTable[i][j] + "\t");
		        }
		        table.append("\n");
		    }

		    resultTextArea.setStyle("-fx-font-family: 'Courier New', monospace;"); // Set a monospace font
		    Font newFont = ((Font.font("Time New Roman", FontWeight.BOLD, 20)));
		    resultTextArea.setFont(newFont);
		    resultTextArea.setText(table.toString());
		}

	  
	

	 private int [][] CalculateDPtable(int length){
		 int [][] DP = new int [length +1 ][length + 1] ;
		 
		 for (int i = 1  ; i <= length  ; i++) {
			 for (int j = 1 ; j <= length ; j++) {
				 
				 
				 if (firstArray[i-1] == secondArray[j-1]) {
					 DP[i][j] = DP[i-1][j-1] + 1 ;
					 
				 }
				 else {
					 DP[i][j] = Math.max(DP[i-1][j], DP[i][j-1]);
				 }
			 }
		 } 
		 
		 return DP;
		 
		 
	 }
	 
	 
	    // read data form file
	 
	 
	 
	 public void readfile(Stage primaryStage) throws ParseException {
		    // choose a file to read information from it
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Open Resource File ");
		    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.txt", "*.*")); // show the file in stage
		    
		    File file = fileChooser.showOpenDialog(primaryStage);
		    ArrayList<Integer> uniqueElements = new ArrayList<>();
		    lengthField = new TextField();

		    if (file != null) { // check if the file has data
		        try {
		            Scanner scanner = new Scanner(file); // read from file
		            if (scanner.hasNextInt()) {
		                int length = scanner.nextInt(); // take length from file
		                scanner.nextLine(); // move to the next line
		                lengthField.setText(String.valueOf(length));

		                firstArray = new int[length];
		                secondArray = new int[length];

		                String[] numbers = scanner.nextLine().split(",");
		                
		                if (numbers.length != length) {
		                    // Display an error if the number of elements in the second line does not match the length
		                    Alert alert = new Alert(Alert.AlertType.ERROR);
		                    alert.setTitle("Error");
		                    alert.setHeaderText("Invalid Input");
		                    alert.setContentText("The number of elements in the second line does not match the specified length.");
		                    alert.showAndWait();
		                    return;
		                }

		                int[] number = new int[length];

		                for (int k = 0; k < length; k++) {
		                    number[k] = Integer.parseInt(numbers[k].trim());
		                }

		                for (int j = 0; j < length; j++) {
		                    firstArray[j] = j + 1;
		                    System.out.print(firstArray[j] + "  ");
		                }

		                for (int i = 0; i < length; i++) {
		                    if (number[i] < 1 || number[i] > length || uniqueElements.contains(number[i])) {
		                        Alert alert = new Alert(Alert.AlertType.ERROR);
		                        alert.setTitle("Error");
		                        alert.setHeaderText("Invalid Input");
		                        alert.setContentText("Your file has not valid numbers. Please try again.");
		                        alert.showAndWait();
		                        return;
		                    } else {
		                        secondArray[i] = number[i];
		                        uniqueElements.add(number[i]);
		                    }
		                }

		                DP = CalculateDPtable(length);
		                resultStage();

		            } else {
		                Alert alert = new Alert(Alert.AlertType.ERROR);
		                alert.setTitle("Error");
		                alert.setHeaderText("Invalid File Format");
		                alert.setContentText("The file does not have the expected format.");
		                alert.showAndWait();
		            }

		            scanner.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        }
		    }
		}


	 
	// the stage with the ligth :
	  private void hiliteButton () {   
		  Text labelText = new Text("The LEDs that gives the expected result:\n\n\n\n");
	      labelText.setFont(Font.font("Time New Roman", 15)); 
		   
		  TextFlow textFlow = new TextFlow();
		  Button back = new Button ("Back");
		  BorderPane pane = new BorderPane();
		  
		 textFlow.getChildren().add(labelText);
		  
		  displayArray(textFlow, firstArray, Color.BLACK); // without change colour
		  displayArraysInTextFlow(textFlow ); // change colour 
	      
		  back.setMaxWidth(400);
		  back.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15)); // font for text 
		  
		  pane.setCenter(textFlow);
		  pane.setBottom(back);
		  pane.setStyle("-fx-background-color:GAINSBORO");

	        Scene scene = new Scene(pane, 1000, 700);
	        primaryStage.setTitle(" expected result");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        back.setOnAction(e-> resultStage());
	    	
	  }
	
	  
	  private void displayArraysInTextFlow(TextFlow textFlow) { 
		    for (int number : secondArray) {
		        VBox vBox = new VBox();
		        Text text = new Text(number + "\t\t  ");
		        vBox.getChildren().add(text);

		        text.setFont(Font.font("Time New Roman", FontWeight.BOLD, 20));

		        // Check if the number is in the first array and change the color
		        if (isNumberInArray(number, lcs)) {
		            text.setFill(Color.RED);  // change the colour 

		            ImageView imageView = new ImageView(new Image("C:\\Users\\Lenovo\\Desktop\\Data Base\\onligt.png"));
		            imageView.setFitWidth(40); // Adjust the size as needed
		            imageView.setFitHeight(40);

		            vBox.getChildren().add(imageView);
		        }
		        
		        else {
		        	// "C:\\Users\\Lenovo\\Desktop\\Data Base\\onligt.png"
		            ImageView defaultImageView = new ImageView(new Image("C:\\Users\\Lenovo\\Pictures\\Screenshots\\Screenshot 2023-12-14 233501.png")); // Default image
		            defaultImageView.setFitWidth(35);
		            defaultImageView.setFitHeight(35);

		            vBox.getChildren().add(defaultImageView);
		        }

		        textFlow.getChildren().add(vBox);
		    }
		}

	  
	    private boolean isNumberInArray(int number, int[] array) {
	        for (int num : array) {
	            if (num == number) {
	                return true;
	            }
	        }
	        return false;
	        
	    }
	    
	    
	    private void displayArray(TextFlow textFlow, int[] array, Color textColor) { // this method 
	        for (int num : array) {
	            Text text = new Text(num + "\t\t\t");
	            text.setFont(Font.font("Time New Roman" , FontWeight.BOLD ,20));
	            text.setFill(textColor);
	            
	            if (isNumberInArray(num, lcs)) {
		            text.setFill(Color.RED);
	            }
	            
	            textFlow.getChildren().add(text);
	        }

	        // Add a new line to separate arrays
	        Text newLineText = new Text("\n\n\n\n\n\n");
	        textFlow.getChildren().add(newLineText);
	    }
		  
	    
}
