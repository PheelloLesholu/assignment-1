package com.example.imagegalleryapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageGalleryApp extends Application {
    private final String[] thumbnailPaths = {
            "/Images/thumbnailA.jpg",
            "/Images/thumbnailB.jpg",
            "/Images/thumbnailC.jpg",
            "/Images/thumbnailD.jpg"
    };

    private final String[] imagePaths = {
            "/Images/estateA.jpg",
            "/Images/estateB.jpg",
            "/Images/estateC.jpg",
            "/Images/estateD.jpg"
    };

    private ImageView currentImageView;
    private int currentIndex = -1; // Start with no image selected

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // Display thumbnails in a 2x2 grid
        GridPane thumbnailGrid = new GridPane();
        thumbnailGrid.setAlignment(Pos.CENTER);
        thumbnailGrid.setHgap(10);
        thumbnailGrid.setVgap(10);

        int column = 0;
        int row = 0;
        for (int i = 0; i < thumbnailPaths.length; i++) {
            ImageView thumbnail = createThumbnail(thumbnailPaths[i]);
            int index = i;
            thumbnail.setOnMouseClicked(e -> showFullImage(index));
            thumbnailGrid.add(thumbnail, column, row);

            // Update column and row indices
            column++;
            if (column == 2) {
                column = 0;
                row++;
            }
        }

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();

        root.getChildren().add(thumbnailGrid);
    }

    private ImageView createThumbnail(String imagePath) {
        // Load thumbnail from the resources
        Image thumbnail = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(thumbnail);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        return imageView;
    }

    private void showFullImage(int index) {
        // Clear previous image if any
        if (currentImageView != null) {
            currentImageView.setImage(null);
        }

        // Display full-size image
        Image fullImage = new Image(getClass().getResource(imagePaths[index]).toExternalForm());
        currentImageView = new ImageView(fullImage);
        currentImageView.setFitWidth(400);
        currentImageView.setFitHeight(400);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(currentImageView);

        // Add navigation buttons
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(e -> showFullImage((index - 1 + imagePaths.length) % imagePaths.length));

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> showFullImage((index + 1) % imagePaths.length));

        Button backButton = new Button("Back to Thumbnails");
        backButton.setOnAction(e -> {
            currentIndex = -1;
            start(new Stage());
            ((Stage) root.getScene().getWindow()).close(); // Close the current stage
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(previousButton, nextButton, backButton);
        root.getChildren().add(buttonBox);

        // Set the current index to the selected image
        currentIndex = index;

        // Update the scene with the full image and buttons
        Scene scene = new Scene(root, 600, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Full Image");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
