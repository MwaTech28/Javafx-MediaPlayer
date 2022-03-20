package mediaplayer;

/**
 *The MIT License

Copyright 2022 MWA TECH

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 * @author EVANS SYSLVESTER MBAWALA
 */

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.InputStream;
import javafx.beans.Observable;
//import java.util.Observable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Bloom;
import javafx.scene.control.Button;
//import javafx.scene.control.
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.applet.*;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import static javafx.application.Application.launch;
import javafx.geometry.Bounds;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class Player  extends Application {
    

    public static void main(String[] args) {
		launch(args);

	}
    private String path;
    private Duration duration;
    private MediaView mediaView;
   
    private MediaPlayer mediaPlayer;
    private Media media;
    private Slider slider;
    private Slider volumeSlider;
    private Label time;
    private ButtonGroup group;   
    private GraphicsDevice vc;
    private DisplayMode dis;
    private static FileChooser chooseFile;
    private int w,h,newW,newH,tw,th,oldW,oldH;
    private String nameg = "";
    private HBox Media;
    private ImageView AudioImage;
    private Timer timer;
    private VBox controls;
    private ProgressBar pb2;
    private RadioMenuItem menuFullScreen,menuNormalScreen;
    private boolean mediatype = true;//CHECK IF MEDIA TYPE IS MP3 OR MP4

    public void start(final Stage primaryStage)throws Exception {

 mediaView = new MediaView();

 MenuBar menuBar = new MenuBar();//CREATE THE FIRST MENU FOR THE PLAYER
 Menu Mfile = new Menu("File");//ADD A FILE MENU
  MenuItem open = new MenuItem("Open File");//SUB MENU FOR FILE
 
 open.setOnAction(e -> getFile(primaryStage));
 Mfile.getItems().add(open);

  Menu Mfullscreen = new Menu("Screen");//CREATE THE SECOND MENU FOR THE PLAYER
menuFullScreen =
new RadioMenuItem("Full Screen");//CREATE A CHECKBOX SUB MENU FOR THE PLAYER
menuNormalScreen =
new RadioMenuItem("Normal Screen");//CREATE A CHECKBOX SUB MENU FOR THE PLAYER

menuNormalScreen.setSelected(true);
ToggleGroup window = new ToggleGroup();

menuFullScreen.setToggleGroup(window);
menuNormalScreen.setToggleGroup(window);

 Mfullscreen.getItems().addAll(menuFullScreen,menuNormalScreen);
 
 menuBar.getMenus().addAll(Mfile,Mfullscreen);
 
 if(menuFullScreen.isSelected()){
  System.out.println("fullscreen");
 }
 
 if(menuNormalScreen.isSelected()){
  System.out.println("normalscreen");
 }
 
  menuFullScreen.setOnAction(e -> wide(primaryStage));
  menuNormalScreen.setOnAction(e -> Normal(primaryStage));
 //GET THE FULL SIZE MONITOR SCREEN OF YOUR COMPUTER
 GraphicsEnvironment ht = GraphicsEnvironment.getLocalGraphicsEnvironment();
	   vc = ht.getDefaultScreenDevice();
           
           //BECAUSE YOUR MONITORE SCREEN WILL TOO WIDE SHRINK THE WIDTH AND HEIGHT A LITTLE BIT
     w = vc.getDisplayMode().getWidth() -500;
     h = vc.getDisplayMode().getHeight()  -500;
 
//GET ALL RAW IMAGE BUTTON RESURCES NEEDED FOR THE PLAYER THE IMAGES ARE PROVIDED WITHIN THIS PROJECT'S SRC FOLDER YOU CAN CREATE YOUR OWN CUSTOM BUTTON IMAGES
Image PlayButtonImage = new Image(getClass().getResourceAsStream("mp3ply.png"));
Image PauseButtonImage = new Image(getClass().getResourceAsStream("mp3pus.png"));
Image ForwadButtonImage = new Image(getClass().getResourceAsStream("mp3frwd.png"));
Image ForwardprsdButtonImage = new Image(getClass().getResourceAsStream("mp3frwdpressd.png"));
Image revesButtonImage = new Image(getClass().getResourceAsStream("mp3rev.png"));
Image revesprsdButtonImage = new Image(getClass().getResourceAsStream("mp3revoff.png"));
Image stopButtonImage = new Image(getClass().getResourceAsStream("mp3stop.png"));
Image stopprsdButtonImage = new Image(getClass().getResourceAsStream("mp3stoppressd.png"));
Image shufButtonImage = new Image(getClass().getResourceAsStream("shuf.png"));
Image shufprsdButtonImage = new Image(getClass().getResourceAsStream("shufpress.png"));
Image hovButtonImage = new Image(getClass().getResourceAsStream("mp3hover.png"));
Image stophovButtonImage = new Image(getClass().getResourceAsStream("stophover.png"));

Image audio = new Image(getClass().getResourceAsStream("audio.png"));//GET AUDIO PLAYER IMAGE

AudioImage = new ImageView(audio);//CREATE AUDIO PLAYER IMAGE
AudioImage.setPreserveRatio(true);
AudioImage.setFitHeight(300);
AudioImage.setFitWidth(300);

//CREATE ALL IMAGE BUTTONS FOR THE PLAYER
final Button playButton = new Button();
GridPane.setConstraints(playButton, 4, 7);
final Button forwardButton = new Button();
GridPane.setConstraints(forwardButton, 6,7);
final Button backButton = new Button();
GridPane.setConstraints(backButton, 2,7);
final Button stopButton = new Button();
GridPane.setConstraints(stopButton, 5,7);
final Button relodButton = new Button();
GridPane.setConstraints(relodButton, 3,7);
final ImageView imageViewPlay = new ImageView(PlayButtonImage);
final ImageView imageViewPause = new ImageView(PauseButtonImage);
final ImageView imageViewforwad = new ImageView(ForwadButtonImage);
final ImageView imageViewforwdpresd = new ImageView(ForwardprsdButtonImage);
final ImageView imageViewreves = new ImageView(revesButtonImage);
final ImageView imageViewrevespresd = new ImageView(revesprsdButtonImage);
final ImageView imageViewstop = new ImageView(stopButtonImage);
final ImageView imageViewstoppresd = new ImageView(stopprsdButtonImage);
final ImageView imageViewshuf = new ImageView(shufButtonImage);
final ImageView imageViewshufpresd = new ImageView(shufprsdButtonImage);
final ImageView imageViewphov = new ImageView(hovButtonImage);
final ImageView imageViewshov = new ImageView(stophovButtonImage);


playButton.setGraphic(imageViewPlay);
playButton.setOnAction(new EventHandler<ActionEvent>() {//PRESS PLAY TO PLAY THE AUDIO IF STOPED OR PAUSED
public void handle(ActionEvent e) {
updateValues();

if(mediaPlayer != null){
Status status = mediaPlayer.getStatus();

if (status == Status.PAUSED
|| status == Status.READY
|| status == Status.STOPPED) {

mediaPlayer.play();
playButton.setGraphic(imageViewPlay);
stopButton.setGraphic(imageViewstop);

} else {
mediaPlayer.pause();
playButton.setGraphic(imageViewPause);
}

}

};


});
playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
public void handle(MouseEvent e) {
    playButton.setGraphic(imageViewphov);
    
}
});
playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
public void handle(MouseEvent e) {
    playButton.setGraphic(imageViewPlay);
}
});

forwardButton.setGraphic(imageViewforwad);
forwardButton.setOnAction(new EventHandler<ActionEvent>() {//PRESS TO SKIP FORWARD A MEDIA
public void handle(ActionEvent e) {
mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.2));
forwardButton.setGraphic(imageViewforwdpresd);
}
});

backButton.setGraphic(imageViewreves);
backButton.setOnAction(new EventHandler<ActionEvent>() {//PRESS TO SKIP BACKWARDS A MEDIA
public void handle(ActionEvent e) {
mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.5));
backButton.setGraphic(imageViewrevespresd);
}
});

stopButton.setGraphic(imageViewstop);

stopButton.setOnAction(new EventHandler<ActionEvent>() {//PRESS TO STOP A MEDIA FROM PLAYING
public void handle(ActionEvent e) {
mediaPlayer.seek(mediaPlayer.getStartTime());
mediaPlayer.stop();
stopButton.setGraphic(imageViewstoppresd);
playButton.setGraphic(imageViewPause);
}
});

pb2 = new ProgressBar(0);//CTREATE PROGRESS BAR

pb2.setPrefWidth(600);
pb2.setMinWidth(60);
pb2.setMaxHeight(8.0);
pb2.setMaxWidth(Region.USE_PREF_SIZE);

slider = new Slider();

HBox.setHgrow(slider, Priority.ALWAYS);
slider.setMinSize(600,0);

final ProgressBar pb = new ProgressBar(0);

pb.setMinWidth(30);
pb.setPrefWidth(70);
pb.setMaxHeight(6.0);
        pb.setMaxWidth(Region.USE_PREF_SIZE);
 final ProgressIndicator pi = new ProgressIndicator(0);

volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        
        volumeSlider.setValue(50);
        
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                //Status status = mediaPlayer.getStatus();
    
                if (volumeSlider.isValueChanging()) {
                    if(mediaPlayer != null){
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                    }
                }
                pb.setProgress(newValue.doubleValue()/100);
                pi.setProgress(newValue.doubleValue()/100);
            }
        });

        
        relodButton.setStyle(
        "-fx-background-color:#dfedef"
        );
relodButton.setGraphic(imageViewshuf);
relodButton.setOnAction(new EventHandler<ActionEvent>() {
public void handle(ActionEvent e) { 
    mediaPlayer.setOnRepeat(null);
    relodButton.setGraphic(imageViewshufpresd);
   }
        });
time = new Label();
time.setTextFill(Color.BLACK);
time.setPrefWidth(80);
time.setMinWidth(30);


StackPane pane2 = new StackPane();
        pane2.getChildren().addAll(pb2, slider);
        
StackPane pane = new StackPane();

        pane.getChildren().addAll(pb, volumeSlider);
        
        final HBox hb2 = new HBox();
        hb2.setPadding(new Insets(10, 10, 10, 10));
        hb2.setSpacing(5);
        hb2.setAlignment(Pos.BASELINE_CENTER);
        hb2.getChildren().addAll(backButton,relodButton,playButton,stopButton,forwardButton);
        
        final HBox hb3 = new HBox();
        hb3.setPadding(new Insets(10, 10, 10, 10));
        hb3.setSpacing(5);
        hb3.setStyle("-fx-background-color:#dfedef");
        hb3.setAlignment(Pos.BASELINE_CENTER);
        hb3.getChildren().addAll(pane,pane2,time);
        
        controls = new VBox();
        controls.setAlignment(Pos.BOTTOM_CENTER);
        controls.getChildren().addAll(hb2,hb3);
        controls.getStyleClass().add("controls");

        VBox Audio = new VBox();
        Audio.setSpacing(10);
        Audio.setAlignment(Pos.BASELINE_CENTER);

        //FIT THE VIDEO WIDTH AND HEIGHT TO FILL THE WHOLE SCREEN WITHOUT AFFECTING ASPECT RATIO
        float videoPropotion = 1.5f;
            int screenWidth = w;
            int screenHeight = h;

            float screenPropotion = (float) screenHeight / (float) screenWidth;

            if(videoPropotion < screenPropotion){
                th = screenHeight;
                tw = (int) ((float) screenHeight / videoPropotion);
            }else{
                tw = screenWidth;
                th = (int) ((float) screenWidth * videoPropotion);
            }
        
        newW = tw;
     newH = 500;
     oldW = newW;
     
     //REPEAT AGIN TO GET BEST FIT WIDTH AND HEIGHT SIZE
     screenWidth = newW;
            screenHeight = newH;

            screenPropotion = (float) screenHeight / (float) screenWidth;

            if(videoPropotion < screenPropotion){
                th = screenHeight;
                tw = (int) ((float) screenHeight / videoPropotion);
            }else{
                tw = screenWidth;
                th = (int) ((float) screenWidth * videoPropotion);
            }
            
            //SET THE BEST FIT VALUSE FOR WIDHT AND HEIGHT
            mediaView.setFitHeight(th);
            mediaView.setFitWidth(tw);
     
        System.out.println("mediaView H: "+th);
        System.out.println("mediaView W: "+tw);
        
        
        /* iNITIALIZE THE MEDIAPLAYER WITH THIS DEFAULT LAYOUT IN THE CENTER*/
        Label Start1 = new Label("Open a File to Play Media");
        Label Start2 = new Label("Supported formats Mp3 and Mp4");
        
        VBox startText = new VBox();
        startText.setSpacing(10);
        startText.getChildren().addAll(Start1,Start2);
        startText.setAlignment(Pos.CENTER);
        
        HBox wrap = new HBox(startText);
        Audio.setAlignment(Pos.CENTER);

        Media = new HBox();
        Media.setAlignment(Pos.CENTER);
        Media.getChildren().addAll(startText);
        BorderPane bdp = new BorderPane();

        bdp.setTop(menuBar);
        bdp.setCenter(Media);
        bdp.setBottom(controls);
 
 Scene sc = new Scene(bdp,780,560);
 sc.getStylesheets().add("vet.css");
 primaryStage.setScene(sc);
 primaryStage.setTitle("Mwa tech media player");
primaryStage .show();

primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {//USE ESCAPE KEY FOR RETURNING TO NORMAL SIZE AFTER FULL SCREEN
    public void handle(KeyEvent key) {
        if (key.getCode() == KeyCode.ESCAPE) {
            System.out.println("Key Pressed: " + key.getCode());
            menuBar.setVisible(true);
                controls.setVisible(true);
                menuNormalScreen.setSelected(true);
            key.consume();
        }
    }
});

  Media.setOnMouseClicked(new EventHandler<MouseEvent>() {//DOUBLE CLICK EVENT FOR A FULL SCREEN SIZED PLAYER
    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                System.out.println("Double clicked");
                primaryStage.setFullScreen(true);
                controls.setVisible(false);
                menuFullScreen.setSelected(true);
            }
        }
    }
});

     sc.widthProperty().addListener(new ChangeListener<Number>() {//CONTROL THE SIZE OF THE VIDEO MEDIA VIEW WHILE MAINTAINING IT'S ASPECT RATION
     
         @Override
         public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth){
             
             if(mediatype == true){
           System.out.println("scroll Width: "+newWidth);
           System.out.println("og Width: "+tw);
           int inc = newWidth.intValue() - tw;
           System.out.println("new inc Width: "+inc);
           newW = tw + inc;//GET NEW WEIDTH BY INCREASING THE WINDOW WIDTH
           
           System.out.println("new Width: "+newW);
       
           //GET VIDEO WIDTH AND HEIGHT TO FILL THE WHOLE SCREEN WITHOUT AFFECTING ASPECT RATIO USING THE NEW WIDTH
           float videoPropotion = 1.5f;
            int screenWidth = newW;
            int screenHeight = newH;
            float screenPropotion = (float) screenHeight / (float) screenWidth;

            if(videoPropotion < screenPropotion){
                th = screenHeight;
                tw = (int) ((float) screenHeight / videoPropotion);
                System.out.println("videoPropotion less than screenPropotion");
            }else{
                tw = screenWidth;
                th = (int) ((float) screenWidth * videoPropotion);
                System.out.println("videoPropotion greater than screenPropotion");
            }
            
        System.out.println("new height: "+th);

        mediaView.setFitWidth(tw);
           
         }
         }
         
     });
     
     sc.heightProperty().addListener(new ChangeListener<Number>() {
     
         @Override
         public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight){
           System.out.println("scroll Height: "+newHeight);
           
           System.out.println("controls Height: "+controls.getHeight());       

           if(mediatype == true){
           int inc = newHeight.intValue() - 500;
           
            newH = 500 + inc;//GET NEW HEIGHT BY INCREASING THE WINDOW HEIGHT
           
            //GET VIDEO WIDTH AND HEIGHT TO FILL THE WHOLE SCREEN WITHOUT AFFECTING ASPECT RATIO USING THE NEW HEIGHT
            float videoPropotion = 1.5f;
            int screenWidth = newW;
            int screenHeight = newH;

            float screenPropotion = (float) screenHeight / (float) screenWidth;

            if(videoPropotion > screenPropotion){
                th = screenHeight;
                tw = (int) ((float) screenHeight / videoPropotion);
            }else{
                tw = screenWidth;
                th = (int) ((float) screenWidth * videoPropotion);
            }
            
        mediaView.setFitHeight(th);
           
         }
         }
         
     });

    }
 
    protected void updateValues() {
  if (time != null && slider != null && volumeSlider != null) {
     Platform.runLater(new Runnable() {
        public void run() {
          Duration currentTime = mediaPlayer.getCurrentTime();
          time.setText(formatTime(currentTime, media.getDuration()));
          slider.setDisable(media.getDuration().isUnknown());
          if (!slider.isDisabled() 
            && media.getDuration().greaterThan(Duration.ZERO) 
            && !slider.isValueChanging()) {
              slider.setValue(currentTime.divide(media.getDuration()).toMillis()* 100.0);
          }

          if (!volumeSlider.isValueChanging()) {
            volumeSlider.setValue((int)Math.round(mediaPlayer.getVolume() 
                  * 100));
          }
        }
     });
  }
}
    
    private void wide(Stage primaryStage){
     System.out.println("fullscreen");
     primaryStage.setFullScreen(true);
                controls.setVisible(false);
                menuFullScreen.setSelected(true);
    }
    
    private void Normal(Stage primaryStage){
     System.out.println("fullscreen");
     primaryStage.setFullScreen(false);
     controls.setVisible(true);
     menuNormalScreen.setSelected(true);
    }
    
    private void getFile(Stage s){
    
        ExtensionFilter ff = new FileChooser.ExtensionFilter("Mp3 files_","mp3","mp4");
         
         chooseFile = new FileChooser();
         chooseFile.setSelectedExtensionFilter(ff);
      File f = chooseFile.showOpenDialog(s);

      if(f != null || !f.toString().equals("")){
         String[] cut = f.getAbsolutePath().split("[.]");//GET THE EXTENTION OF THE FILE
String ext = cut[cut.length - 1];
System.out.println("the new extention -> "+ext);
 
    if(ext.equals("mp3")){//CHOSE THIS ACTION IF THE MEDIA SELECTED IS AN AUDIO
        
        createNewplay(f);
        
       System.out.println("full file2 -> "+f.getName());//GET THE NAME OF THE FILE TO DSIPALY IT AS A TITILE

       nameg = "Now Playing: "+f.getName();
       Label nameLabel = new Label(nameg);
       
       VBox Audio = new VBox();
        Audio.setSpacing(10);
        Audio.getChildren().addAll(AudioImage,nameLabel);
        Audio.setAlignment(Pos.BASELINE_CENTER);
        
        HBox wrap = new HBox(Audio);
        Audio.setAlignment(Pos.CENTER);
        
        Media.getChildren().remove(0);
        Media.getChildren().add(wrap);
   
   }else if(ext.equals("mp4")){//CHOSE THIS ACTION IF THE MEDIA SELECTED IS A VIDEO
       
       createNewplay(f);
       
       Media.getChildren().remove(0);
    Media.getChildren().add(mediaView);
   }else{
   
        //Creating a dialog
      Dialog<String> dialog = new Dialog<String>();
      //Setting the title
      dialog.setTitle("Dialog");
      ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
      //Setting the content of the dialog
      dialog.setContentText("The file you chose does not support the formats Mp3, Mp4");
      //Adding buttons to the dialog pane
      dialog.getDialogPane().getButtonTypes().add(type);
      //show the dialog
      dialog.showAndWait();
   
   }
 
      }
        
    }
    
    private void createNewplay(File f){
        
        if(mediaPlayer != null){
            
             mediaPlayer.stop();
      mediaPlayer.dispose();
      mediaPlayer = null;
        }
      
      media = null;
      
      media = new Media(f.toURI().toString());
      
      mediaPlayer = new MediaPlayer(media);
      
 mediaPlayer.setAutoPlay(true);
 mediaView.setMediaPlayer(mediaPlayer);
 
mediaPlayer.currentTimeProperty().addListener(new ChangeListener() {

public void changed(ObservableValue observable, Duration oldValue, Duration newValue) {
updateValues();
}

    @Override
    public void changed(ObservableValue ov, Object t, Object t1) {
       updateValues(); 
    }

});

slider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                Status status = mediaPlayer.getStatus();

                if (slider.isValueChanging()) {
                  mediaPlayer.seek(media.getDuration().multiply(slider.getValue() / 100.0));
                }
                pb2.setProgress(newValue.doubleValue()/100);
            }
        });
    
    }
    
    private static String formatTime(Duration elapsed, Duration duration) {//CALCULATE TIME PASSED TIME WHILE MEDIA IS PLAYING
   int intElapsed = (int)Math.floor(elapsed.toSeconds());
   int elapsedHours = intElapsed / (60 * 60);
   if (elapsedHours > 0) {
       intElapsed -= elapsedHours * 60 * 60;
   }
   int elapsedMinutes = intElapsed / 60;
   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
                           - elapsedMinutes * 60;
 
   if (duration.greaterThan(Duration.ZERO)) {
      int intDuration = (int)Math.floor(duration.toSeconds());
      int durationHours = intDuration / (60 * 60);
      if (durationHours > 0) {
         intDuration -= durationHours * 60 * 60;
      }
      int durationMinutes = intDuration / 60;
      int durationSeconds = intDuration - durationHours * 60 * 60 - 
          durationMinutes * 60;
      if (durationHours > 0) {
         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
            elapsedHours, elapsedMinutes, elapsedSeconds,
            durationHours, durationMinutes, durationSeconds);
      } else {
          return String.format("%02d:%02d/%02d:%02d",
            elapsedMinutes, elapsedSeconds,durationMinutes, 
                durationSeconds);
      }
      } else {
          if (elapsedHours > 0) {
             return String.format("%d:%02d:%02d", elapsedHours, 
                    elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes, 
                    elapsedSeconds);
            }
        }
    } 
    
}
