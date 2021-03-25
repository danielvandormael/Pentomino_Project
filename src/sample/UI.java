package sample;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class UI extends Application {

  private static final int WIDTH = 1400;
  private static final int HEIGHT = 800;
  public static int[][][] a;
  public static boolean visibility = true;
  public static boolean change = false;
  public static Search s;
  public static Bruijn bruijn;
  public static Scene scene;
  public static SmartGroup group;
  public static int count;
  public static String[] ab;

  public void start(Stage primaryStage) {
    // while(true){

    // if (Search.news){
    // a=Search.gridm;
    // change =true;
    // }
    group = shape(a);
    Camera camera = new PerspectiveCamera();
    scene = new Scene(group, WIDTH, HEIGHT, true);
    scene.setCamera(camera);

    // Move to center of the screen
    group.translateXProperty().set(530);
    group.translateYProperty().set(380);
    group.translateZProperty().set(-1100);

    // Add keyboard control.
    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
      switch (event.getCode()) {
      case W:
        group.translateZProperty().set(group.getTranslateZ() + 100);
        break;
      case S:
        group.translateZProperty().set(group.getTranslateZ() - 100);
        break;
      case UP:
        group.rotateByX(10);
        break;
      case DOWN:
        group.rotateByX(-10);
        break;
      case LEFT:
        group.translateXProperty().set(group.getTranslateX() - 50);
        break;
      case RIGHT:
        group.translateXProperty().set(group.getTranslateX() + 50);
        break;
      }
    });
    primaryStage.setScene(scene);
    primaryStage.show();
    // change =false;
    // s.all_poss((s.witcs-1),fit);
    // primaryStage.hide();
    // }

    // if (visibility){
    // wa();
    // primaryStage.close();
    // }
    // System.exit(1);
  }

  public SmartGroup shape(int[][][] field) {
    int x = 0;
    int y = 0;
    int z = 0;
    int size = 8;
    SmartGroup group = new SmartGroup();
    Box[][][] truck = new Box[field.length][field[0].length][field[0][0].length];
    for (int i = 0; i < truck.length; i++) {
      for (int j = 0; j < truck[0].length; j++) {
        for (int k = 0; k < truck[0][0].length; k++) {
          // System.out.println(field[i][j][k]);
          truck[i][j][k] = new Box(size, size, size);
          truck[i][j][k].translateXProperty().set(x);
          truck[i][j][k].translateYProperty().set(y);
          truck[i][j][k].translateZProperty().set(z);
          x = x + size;
        }
        x = 0;
        y = y + size;
      }
      y = 0;
      z = z + size;
    }

    for (int i = 0; i < truck.length; i++) {
      for (int j = 0; j < truck[0].length; j++) {
        for (int k = 0; k < truck[0][0].length; k++) {
          // if (field[i][j][k] == -1) {
          // truck[i][j][k].setMaterial(new PhongMaterial(Color.SILVER));
          // group.getChildren().add(truck[i][j][k]);
          // }
          if (field[i][j][k] == 0 || field[i][j][k] == 20) {
            truck[i][j][k].setMaterial(new PhongMaterial(Color.RED));
            group.getChildren().add(truck[i][j][k]);
          }
          if (field[i][j][k] == 1 || field[i][j][k] == 30 ) {
            truck[i][j][k].setMaterial(new PhongMaterial(Color.BLUE));
            group.getChildren().add(truck[i][j][k]);
          }
          if (field[i][j][k] == 2 || field[i][j][k] == 40 ) {
            truck[i][j][k].setMaterial(new PhongMaterial(Color.YELLOW));
            group.getChildren().add(truck[i][j][k]);
          }
        }
      }
    }

    return group;
  }

  /*
   * public UI(int [][][]b,String[] args){ a=b; //launch(args); }
   */
  public static void repaint(int[][][] b, String[] args) {
    a = b;
    launch(args);
  }

  public static void main(String args[]) {

    ab = args;
    event obj = new event();
    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  static class event<radioButton1> extends JFrame {

    JLabel title = new JLabel("Group 3 phase 3");

    Font fnt0 = new Font("arial", Font.BOLD, 45);

    JButton b1 = new JButton("Boxes");

    JButton b2 = new JButton("Pentomino's");

    JRadioButton radioNormalGrid = new JRadioButton("Normal gridsize (2.5 by 8 by 16.5)");
    JRadioButton radioManualGrid = new JRadioButton("Manually set the gridsize");
    ButtonGroup manual = new ButtonGroup();
    JLabel cool = new JLabel("                         ");

    JRadioButton bruijns = new JRadioButton("Using de Bruijns algorithm");
    JRadioButton branching = new JRadioButton("Using the branching algorithm");
    ButtonGroup kind_of_algo = new ButtonGroup();

    handler handle = new handler();

    event(){

      this.title.setAlignmentX(CENTER_ALIGNMENT);
      this.title.setFont(fnt0);
      this.b1.setAlignmentX(CENTER_ALIGNMENT);
      this.b2.setAlignmentX(CENTER_ALIGNMENT);
      this.cool.setAlignmentX(CENTER_ALIGNMENT);
      this.radioNormalGrid.setAlignmentX(BOTTOM_ALIGNMENT);
      this.radioManualGrid.setAlignmentX(BOTTOM_ALIGNMENT);
      this.bruijns.setAlignmentX(BOTTOM_ALIGNMENT);
      this.branching.setAlignmentX(BOTTOM_ALIGNMENT);
      this.bruijns.setSelected(true);
      this.radioNormalGrid.setSelected(true);
      this.manual.add(radioManualGrid);
      this.manual.add(radioNormalGrid);
      this.kind_of_algo.add(branching);      
      this.kind_of_algo.add(bruijns);

      this.setLayout(new BorderLayout());
      this.add(BorderLayout.CENTER, title);
      this.add(cool);
      this.add(b1, BorderLayout.NORTH);
      this.add(BorderLayout.NORTH, b2);
      this.add(radioManualGrid, BorderLayout.SOUTH);
      this.add(BorderLayout.SOUTH, radioNormalGrid);
      this.add(bruijns, BorderLayout.SOUTH);
      this.add(BorderLayout.SOUTH, branching);

      b1.addActionListener(handle);
      b2.addActionListener(handle);

      this.setSize(400, 400);
      this.setVisible(true);
      this.setLayout(new FlowLayout());
    }

    class handler implements ActionListener {

      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
          if (radioManualGrid.isSelected()){
            dispose();
            String f1 = JOptionPane.showInputDialog("What size do you want for y ?");
            String f2 = JOptionPane.showInputDialog("What size do you want for x ?");
            String f3 = JOptionPane.showInputDialog("What size do you want for z ?");
            String s2 = JOptionPane.showInputDialog("How many A-parcels would you like?");
            String s3 = JOptionPane.showInputDialog("How many B-parcels would you like?");
            String s4 = JOptionPane.showInputDialog("How many C-parcels would you like?");
            int a = Integer.parseInt(s2);
            int b = Integer.parseInt(s3);
            int c = Integer.parseInt(s4);
            int y = Integer.parseInt(f1);
            int x = Integer.parseInt(f2);
            int z = Integer.parseInt(f3);
            int[] pos = { a, b, c, (3 * a + 4 * b + 5 * c), (16 * a + 24 * b + 27 * c) };
            if(bruijns.isSelected()){
              bruijn = new Bruijn(ab, false,y,x,z);
              bruijn.all_poss(pos);
            }
            else{
              s = new Search(ab, false,y,x,z);
              s.all_poss(pos);
            }            
          }
          else{
            dispose();
            String s2 = JOptionPane.showInputDialog("How many A-parcels would you like?");
            String s3 = JOptionPane.showInputDialog("How many B-parcels would you like?");
            String s4 = JOptionPane.showInputDialog("How many C-parcels would you like?");
            int a = Integer.parseInt(s2);
            int b = Integer.parseInt(s3);
            int c = Integer.parseInt(s4);
            int[] pos = { a, b, c, (3 * a + 4 * b + 5 * c), (16 * a + 24 * b + 27 * c) };
            if(bruijns.isSelected()){
              bruijn = new Bruijn(ab, false);
              bruijn.all_poss(pos);
            }
            else{
              s = new Search(ab, false);
              s.all_poss(pos);
            }            
          }          
        }

        if (e.getSource() == b2) {
          if (radioManualGrid.isSelected()){
            dispose();
            String f1 = JOptionPane.showInputDialog("What size do you want for y ?");
            String f2 = JOptionPane.showInputDialog("What size do you want for x ?");
            String f3 = JOptionPane.showInputDialog("What size do you want for z ?");
            String s2 = JOptionPane.showInputDialog("How many L");
            String s3 = JOptionPane.showInputDialog("How many T ");
            String s4 = JOptionPane.showInputDialog("How many P");
            int a = Integer.parseInt(s2);
            int b = Integer.parseInt(s3);
            int c = Integer.parseInt(s4);
            int y = Integer.parseInt(f1);
            int x = Integer.parseInt(f2);
            int z = Integer.parseInt(f3);
            int[] pos = { a, b, c, (3 * a + 5 * b + 4 * c), (5 * a + 5 * b + 5 * c) };
            if(branching.isSelected()){
              s = new Search(ab, true, y, x, z);
              s.all_poss(pos);
            }
            else{
              bruijn = new Bruijn(ab, true, y, x, z);
              bruijn.all_poss(pos);
            }
            
          }else{
            dispose();
            String s2 = JOptionPane.showInputDialog("How many L");
            String s3 = JOptionPane.showInputDialog("How many T ");
            String s4 = JOptionPane.showInputDialog("How many P");
            int a = Integer.parseInt(s2);
            int b = Integer.parseInt(s3);
            int c = Integer.parseInt(s4);
            int[] pos = { a, b, c, (3 * a + 5 * b + 4 * c), (5 * a + 5 * b + 5 * c) };
            if(branching.isSelected()){
              s = new Search(ab, true);
              s.all_poss(pos);
            }
            else{
              bruijn = new Bruijn(ab, true);
              bruijn.all_poss(pos);
            }
          }
        }
      }
    }
  }

  public static void wa() {// Arthur
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
  }

  class SmartGroup extends Group {
    Rotate r;
    Transform t = new Rotate();

    void rotateByX(int ang) {
      r = new Rotate(ang, Rotate.X_AXIS);
      t = t.createConcatenation(r);
      this.getTransforms().clear();
      this.getTransforms().addAll(t);
    }

    void rotateByY(int ang) {
      r = new Rotate(ang, Rotate.Y_AXIS);
      t = t.createConcatenation(r);
      this.getTransforms().clear();
      this.getTransforms().addAll(t);
    }
  }
}