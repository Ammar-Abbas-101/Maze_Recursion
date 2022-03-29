package com.ammarabbas;

// ALL IMPORTS
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// ACTUAL CLASS WHERE ALL THE WORK HAPPENS
public class MazeFrame implements ActionListener {     // IMPLEMENTS  ACTIONLISTENER FOR THE BUTTONS

    // Creating class variables that can be accessed by methods in the class
    int rows = methodRows(0);                          // ASKS THE USER FOR HOW MANY ROWS THEY WANT
    int columns = methodColumns(0);                    // ASKS THE USERS FOR HOW MANY COLUMNS THEY WANT
    int input = (rows * columns) + 1;                   // VARIABLE FOR USE IN FOR LOOPS WHEN E.G. FOR MAKING BUTTONS IN A LOOP
    JButton button[] = new JButton[input];               // MAKING THESE MANY BUTTONS
    JButton startButton = new JButton("Start");     // BUTTON FOR STARTING THE PROGRAM
    JFrame frame = new JFrame();                        // MAKES FRAME FOR GUI
    JPanel whitePanel = new JPanel();                   // EXAMPLE PANEL TO COMPARE BUTTON COLOURS
    JPanel greenPanel = new JPanel();                   // EXAMPLE PANEL TO COMPARE CLICKED BUTTONS

    // Creating panels to act as border margins for JFrame
    JPanel centerPanel = new JPanel();
    JPanel northPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JPanel westPanel = new JPanel();

    int xCoordinate = 1;                        // COORDINATE FOR WHERE THE MAZE SHOULD START
    int yCoordinate = 1;                        // COORDINATE FOR WHERE THE MAZE SHOULD START

    public MazeFrame() throws InterruptedException {        // METHOD WHERE EVERYTHING STARTS

        JOptionPane.showMessageDialog(null, "Welcome to this maze program! It is up to you to decide how you want to design the maze. " +
                "\nClick on the buttons to set unaccessible spots." +
                "\nThe program will simply check if there are any solutions for the maze to go from the top left to the bottom right");      // INSTRUCTIONAL MESSAGE

        whitePanel.setBackground(new Color(255, 255, 255));     // MAKING EXAMPLE PANEL FOR COMPARISON
        greenPanel.setBackground(new Color(0, 155, 0));         // MAKING EXAMPLE PANEL FOR COMPARISON

        // CREATING JFRAME
        frame.setSize(720, 720);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(18, 52, 86));


        gridMaker();
        centerPanel();
        sidePanels();

    }

    public void programStarter() throws InterruptedException {

        for (int g = 1; g < input; g++) {                   // FOR LOOP TO GO THROUGH ALL BUTTONS
            button[g].removeActionListener(this);           // FINALIZING STATUS OF EACH BUTTON
        }
        eastPanel.remove(startButton);                         // REMOVING START BUTTON AFTER PROGRAM HAS STARTED
        boolean result = move(xCoordinate,yCoordinate);         // STARTS THE MAZE SOLVER

        if (result){                                            // IF RESULT IS TRUE
            System.out.println("The maze was solved!");         // SHOWS THIS MESSAGE
        }
        else{                                                   // IF RESULT IS FALSE
            System.out.println("The maze could not be solved.");    // SHOWS THIS MESSAGE
        }
    }

    public static int methodRows(int Rows) {        // METHOD FOR ASKING USER HOW MANY ROWS THEY WANT
        Rows = Integer.parseInt(JOptionPane.showInputDialog("How many rows would you like for the maze to have?"));
        return Rows;
    }

    public static int methodColumns(int Columns) {      // METHOD FOR ASKING USE HOW MANY COLUMNS THEY WANT
        Columns = Integer.parseInt(JOptionPane.showInputDialog("How many rows would you like for the maze to have?"));
        return Columns;
    }

    public void gridMaker() {
        for (int g = 1; g < input; g++) {               // TO GO THROUGH THE DESIRED NUMBER OF TIMES
            button[g] = new JButton();                  // MAKES A NW BUTTON EACH TIME
            button[g].setBackground(new Color(255, 255, 255));      // SETS COLOUR OF EACH BUTTON
            button[g].addActionListener(this);                      // ACTIONLISTENER IS THE ONE THAT THE CLASS HANDLES
            centerPanel.add(button[g]);                 // BUTTON IS ADDED TO THE PANEL FOR DISPLAY
        }
    }

    public void centerPanel() {
        centerPanel.setLayout(new GridLayout(rows, columns, 5, 5));     // GRIDLAYOUT FOR THE MAZE
        centerPanel.setBackground(new Color(18, 52, 86));           // SETTING BACKGROUND COLOR FOR THE CENTER PANEL
    }

    public void sidePanels() {
        // ADDING BORDER MARGINS TO JFRAME
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(eastPanel, BorderLayout.EAST);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.add(westPanel, BorderLayout.WEST);

        // SETTING COLOURS FOR EACH PANEL
        northPanel.setBackground(new Color(18, 52, 86));
        eastPanel.setBackground(new Color(18, 52, 86));
        southPanel.setBackground(new Color(18, 52, 86));
        westPanel.setBackground(new Color(18, 52, 86));

        // DESGINATING BORDER MARGINS FOR JFRAME
        northPanel.setPreferredSize(new Dimension(0, 30));
        eastPanel.setPreferredSize(new Dimension(100, 0));
        southPanel.setPreferredSize(new Dimension(0, 30));
        westPanel.setPreferredSize(new Dimension(30, 0));

        eastPanel.add(startButton);                 // ADDING START BUTTON ON THE RIGHT SIDE OF THE FRAME
        startButton.addActionListener(new ActionListener() {        // START BUTTON HAS ACTIONLISTENER
                                          @Override
                                          public void actionPerformed(ActionEvent e) {            // THIS IS WHAT SHOULD HAPPEN IF THE START BUTTON IS CLICKED
                                              new Thread(new Runnable() {                 // NEW THREAD FOR MULTITHREADING
                                                  @Override
                                                  public void run() {
                                                      try {
                                                          programStarter();               // CALLS A MEHTOD WHEN START BUTTON IS PRESSED
                                                      } catch (InterruptedException ex) {         // IN CASE OF ERROR
                                                          System.out.println("Something went wrong. Please give this poor child some more time.");
                                                          ex.printStackTrace();
                                                      }
                                                  }
                                              }).start();         // STARTING THE NEW THREAD
                                          }
                                      }
        );
    }

    //------------------------------------------------------------


    //-------------------------------------------------------------

    public boolean Complete(int xCoordinate, int yCoordinate) {     // METHOD TO SEE IF END OF MAZE HAS BEEN REACHED
        int CompleteCoordinate = (xCoordinate * columns) - columns + yCoordinate;       // END COORDINATE STORED IN A VARIABLE
        if (CompleteCoordinate == (input-1)) {          // IF END OF MAZE HAS BEEN REACHED
            return true;            // RETURN TRUE
        } else {
            return false;               // ELSE RETURN FALSE
        }
    }

    public void Tried(int xCoordinate, int yCoordinate) throws InterruptedException {
        int TriedCoordinate = (xCoordinate * columns) - columns + yCoordinate;      // STORING COORDINATE
        Thread.sleep(200);      // ADDING DELAY SO USER CAN UNDERSTAND WHAT'S HAPPENING
        button[TriedCoordinate].setBackground(new Color(0, 155, 0));    // CHANGING BUTTON COLOR TO SHOW THAT IT HAS BEEN TRIED
    }

    public boolean checkValidPosition(int xCoordinate, int yCoordinate) throws InterruptedException {       // METHOD TO SEE IF A SPOT IS VALID
        int ValidPosCoordinate = (xCoordinate * columns) - columns + yCoordinate;       // STORING COORDINATE IN A LOCAL VARIABLE
        if (0 < ValidPosCoordinate && (input) > ValidPosCoordinate) {       // IF COORDINATE IS LARGER THAN 0 AND LOWER THAN 1+END POSITION

            int panelColor = button[ValidPosCoordinate].getBackground().getRed();   // GETS COLOR OF CURRENT SPOT TO USE FOR COMPARISON
            int whiteColor = whitePanel.getBackground().getRed();           // LOCAL VARIABLE TO STORE THE COLOR OF WHITE
            int greenColor = greenPanel.getBackground().getRed();           // LOCAL VARIABLE TO STORE THE COLOR OF GREEN
            if (panelColor == whiteColor) {         // IF THE COLOR OF CURRENT PANEL IS WHITE
                return true;                        // RETURN TRUE
            } else {
                return false;                       // IF COLOR IS NOT WHITE RETURN FALSE
            }
        } else {
            return false;                   // IF COORDINATE IS NOT VALID, RETURN FALSE
        }
    }

    public void partOfPath(int xCoordinate, int yCoordinate) {
        int PathCoordinate = (xCoordinate * columns) - columns + yCoordinate;       // MARK SPOT AS FINAL ANSWER
        button[PathCoordinate].setBackground(new Color(0, 155, 150));       // CHANGINF COLOR TO SHOW PATH
    }

    public boolean move(int xCoordinate, int yCoordinate) throws InterruptedException {     // METHOD WHICH CALLS MANY OTHER METHODS

        boolean done = false;           // SETTING A BOOLEAN TO FALSE TO BE USED IN IF STATEMENTS
        boolean isValid = checkValidPosition(xCoordinate, yCoordinate);     // SEE IF POSITION IS VALID OR NOT
        if (isValid) {          // IF ITS VALID, DO THE FOLLOWING

            Tried(xCoordinate, yCoordinate);                // MARK SPOT AS TRIED

            if (Complete(xCoordinate, yCoordinate)) {           // IF CURRENT SPOT IS FINAL SPOT
                button[(xCoordinate * columns) - columns + yCoordinate].setBackground(new Color(244, 155, 0));  // CHANGE COLOR FOR A SECOND
                Thread.sleep((1000));       // ADD DELAY
                done = true;            // CHANGE BOOLEAN VALUE TO END PROGRAM
            }

            if (!done) {            // IF AT NOT THE FINAL SPOT
                done = move(xCoordinate + 1, yCoordinate);

                /*
                CALL MOVE METHOD WITHIN ITSELF WITH A DIFFERENT COORDINATE THIS TIME (ONE BELOW).
                */
            }

            if (!done) {
                done = move(xCoordinate, yCoordinate + 1);

                /*
                CALL MOVE METHOD WITHIN ITSELF WITH A DIFFERENT COORDINATE THIS TIME (ONE RIGHT).
                */
            }

            if (!done) {
                done = move(xCoordinate - 1, yCoordinate);

                /*
                CALL MOVE METHOD WITHIN ITSELF WITH A DIFFERENT COORDINATE THIS TIME (ONE ABOVE).
                */
            }

            if (!done) {Thread.sleep(2000);
                done = move(xCoordinate, yCoordinate - 1);

                /*
                CALL MOVE METHOD WITHIN ITSELF WITH A DIFFERENT COORDINATE THIS TIME (ONE LEFT).
                */
            }

            if (done) {             // IF DONE IS TRUE MEANING CURRENT SPOT IS AT THE END OF THE MAZE
                partOfPath(xCoordinate, yCoordinate);       // MARK THE TRAIL WITH A DIFFERENT COLOR
            } else {
                done = false;                   // THE MAZE WAS NOT COMPLETED
            }

        }

        return done;        // RETURN THE RESULT OF THE MAZE SOLVER
    }

    @Override
    public void actionPerformed(ActionEvent e) {            // WHAT HAPPENS WHEN THE BUTTONS WITH CLASS ACTIONLISTENER ARE CLICKED

        int whiteColor = whitePanel.getBackground().getRed();       // STORING WHITE COLOR FOR FUTURE REFERENCE
        Object source = e.getSource();              // FIND OUT WHAT WAS CLICKED
        if (source instanceof Component) {          // IF CLICKED
            int panelColor = ((Component) source).getBackground().getRed();     // GET THE COLOR OF THE CLICKED BUTTON
            if (panelColor == whiteColor)                       // IF CLICKED BUTTON IS WHITE
                ((Component) source).setBackground(new Color(134,145,156)); // CHANGE IT TO GREY
            else if (panelColor != whiteColor)                  // IF IT IS ALREADY GREY
                ((Component) source).setBackground(new Color(255,255,255));     // CHANGE IT BACK TO WHITE
        }
    }
}