package AsciiSnap;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AsciiSnap extends Application {

    private File inputFile;
    private FileChooser fileDialogOpener;
    private Stage stage;
    private CheckBox transparencyCheckbox;
    private boolean isTransparent;
    private Label inputFileLabel;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) 
    {
        this.stage = stage;
        stage.setTitle("ASCII-SNAP");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // Image banner
        Image image = new Image("images/banner.png");
        ImageView bannerImageView = new ImageView(image);
        bannerImageView.setFitWidth(600);
        bannerImageView.setPreserveRatio(true);

        // Buttons
        Button chooseFileButton = new Button("Choose Text File");
        Button convertButton = new Button("Convert to PNG");
        Button quitButton = new Button("Quit");

        chooseFileButton.setOnAction(e -> chooseTextFile());
        convertButton.setOnAction(e -> convertToImage());
        quitButton.setOnAction(e -> stage.close());

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(chooseFileButton, convertButton, quitButton);

        // Checkbox
        transparencyCheckbox = new CheckBox("Enable Transparency");
        transparencyCheckbox.setAlignment(Pos.CENTER);

        // Selected file label
        inputFileLabel = new Label("No file selected");
        inputFileLabel.setAlignment(Pos.CENTER);

        root.getChildren().addAll(bannerImageView, buttonContainer, transparencyCheckbox, inputFileLabel);

        Scene scene = new Scene(root, 600, 450);
        stage.setScene(scene);
        stage.show();
    }

    private void chooseTextFile() 
    {
        fileDialogOpener = new FileChooser();
        fileDialogOpener.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        inputFile = fileDialogOpener.showOpenDialog(stage);

        if (inputFile != null) 
        {
            inputFileLabel.setText(inputFile.getName());
        }
    }

    private void convertToImage() 
    {
        if (inputFile == null) 
        {
            showAlert("Error", "No text file chosen.");
            return;
        }

        try 
        {
            // Read the input text file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) 
            {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            reader.close();
            String text = stringBuilder.toString().trim();

            // New line
            String[] lines = text.split("\n");

            // Calculate the dimensions of the image based on text size
            int lineHeight = 20; //20 point font size
            int maxWidth = 0;
            Font font = new Font(Font.MONOSPACED, Font.PLAIN, lineHeight);
            FontMetrics fontMetrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().getFontMetrics(font);
            int width = 0;
            
            for (String lineText : lines) 
            {
                int lineWidth = fontMetrics.stringWidth(lineText);
                if (lineWidth > maxWidth) 
                {
                    maxWidth = lineWidth;
                }
            }
            
            width = maxWidth + 40; // Left/right margin padding
            int height = (lineHeight * (lines.length - 2)) + 40; // Top/bottom margin padding

            // Create a BufferedImage object
            BufferedImage image;
            Graphics2D graphics2D;
            if (transparencyCheckbox.isSelected()) //Output image is transparent
            {
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                graphics2D = image.createGraphics();
                graphics2D.setColor(new Color(0, 0, 0, 0));
                graphics2D.setComposite(AlphaComposite.Clear);
                graphics2D.fillRect(0, 0, width, height);
                graphics2D.setComposite(AlphaComposite.SrcOver);
            } 
            else //Output image background is white.
            {
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                graphics2D = image.createGraphics();
                graphics2D.setColor(Color.WHITE);
                graphics2D.fillRect(0, 0, width, height);
            }
            graphics2D.setFont(font);
            graphics2D.setColor(Color.BLACK);

            // Writes the text on the image
            int y = lineHeight + 20; //y position
            for (int i = 1; i < lines.length - 1; i++) //Cuts out first/last line
            {
                String lineText = lines[i];
                int lineWidth = fontMetrics.stringWidth(lineText);
                int x = (width - lineWidth) / 2; // Horizontally center line
                graphics2D.drawString(lineText, x, y);
                y += lineHeight;
            }

            // DISPOSE OF THE GRAPHICS2D, NOW!!!!!
            graphics2D.dispose();

            // Prompts user to select where to save the PNG file
            FileChooser saveFileChooser = new FileChooser();
            saveFileChooser.setInitialFileName("output.png");
            File outputFile = saveFileChooser.showSaveDialog(stage);
            if (outputFile != null) 
            {
                // Save the image as a PNG file
                ImageIO.write(image, "png", outputFile);
                showAlert("Success", "Image saved successfully.");
                isTransparent = transparencyCheckbox.isSelected();
            }
            //Nerd error catching stuff (thanks GPT)
            else 
            {
                showAlert("Error", "Image save operation canceled.");
            }
        } 
        catch (IOException e) 
        {
            showAlert("Error", "An error occurred while converting the text to image: " + e.getMessage());
        }
    }

    //The user annoyer algorithm (it annoys the user)
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}