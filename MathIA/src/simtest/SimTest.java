package simtest;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * This program was created in 2020 to support my Mathematics Internal
 * Assessment. Inspired by 3Blue1Brown's Youtube video of the digits of pi
 * computed by colliding blocks.
 *
 * @author d.ross2
 */
public class SimTest {

    // the number of digits of pi to compute is equal to mass scale + 1
    private static final int MASS_SCALE = 4;
    // the number of collisions per millisecond to calculate while
    // also maintaining the same simulation speed
    private static final double MOVEMENT_SCALE = 100;
    // the speed of the simulation
    // (multiplies the number of collisions per millisecond)
    private static final int PLAYBACK_SPEED = 10;

    // x coordinate variables
    private static double b1X = 1500;
    private static double b2X = 700;

    // size of the boxes
    private static final int B1_SIZE = 300;
    private static final int B2_SIZE = 200;

    // box mass variables
    private static double b1M = Math.pow(100, MASS_SCALE);
    private static double b2M = 1;

    // box velocity variables 
    private static double b1V = -1 / MOVEMENT_SCALE;
    private static double b2V = 0;

    // collision number variable 
    private static int collNum = 0;

    // JLabels for box velocities and collsion count
    private static JLabel collCount;
    private static JLabel v1Label;
    private static JLabel v2Label;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // create new JFrame
        JFrame frame = new JFrame();
        // set JFrame parameters
        frame.setLayout(null);
        frame.setSize(1900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // create box1 JLabel
        JLabel box1 = new JLabel("Box M. Mass: " + b1M, SwingConstants.CENTER);
        // set box1 parameters
        box1.setOpaque(true);
        box1.setBackground(Color.white);
        box1.setBorder(BorderFactory.createLineBorder(Color.black));
        box1.setBounds((int) b1X, (500 - B1_SIZE) / 2, B1_SIZE, B1_SIZE);

        // create box2 JLabel
        JLabel box2 = new JLabel("Box m. Mass: " + b2M, SwingConstants.CENTER);
        // set box2 parameters
        box2.setOpaque(true);
        box2.setBackground(Color.white);
        box2.setBorder(BorderFactory.createLineBorder(Color.black));
        box2.setBounds((int) b2X, (500 - B2_SIZE) / 2, B2_SIZE, B2_SIZE);

        // create collision count JLabel
        collCount = new JLabel();
        // set collision count JLabel parameters
        collCount.setBounds(0, 0, 300, 20);
        collCount.setText("Collisions: " + collNum);

        // create box1 velocity JLabel
        v1Label = new JLabel();
        // set parameters
        v1Label.setBounds(0, 20, 300, 20);
        v1Label.setText("Box M Velocity: " + b1V * MOVEMENT_SCALE);

        // create box2 velocity JLabel
        v2Label = new JLabel();
        // set parameters
        v2Label.setBounds(0, 40, 300, 20);
        v2Label.setText("Box m Velocity: " + b2V * MOVEMENT_SCALE);

        /**
         * timer repeats every millisecond
         */
        Timer tick = new Timer(1, (ActionEvent e) -> {
            // the number of calculations per millisecond is 
            // the movement scale multiplied by playback speed
            for (int i = 0; i < (MOVEMENT_SCALE * PLAYBACK_SPEED); i++) {

                // the velocities are added to the true X coordinates
                b1X += b1V;
                b2X += b2V;

                // initial velocities
                double b1U = b1V;
                double b2U = b2V;

                /**
                 * Check if there is a collision between box M (b1) and box m
                 * (b2).
                 */
                if (b1X <= (b2X + B2_SIZE)) {
                    // calculate new velocity of box M (b1)
                    b1V = (b1M - b2M) / (b1M + b2M) * b1U
                            + (2 * b2M) / (b1M + b2M) * b2U;
                    // calculate new velocity of box m (b2)
                    b2V = (2 * b1M) / (b1M + b2M) * b1U
                            - (b1M - b2M) / (b1M + b2M) * b2U;
                    // increase number of collisions by 1
                    collNum++;
                    // update collision label
                    collCount.setText("Collisions: " + collNum);
                    // print the velocity values
                    printVelocities();
                }

                // check if there is a collision between box 2 and the wall
                if (b2X <= 0) {

                    // reverse the velocity
                    b2V *= -1;
                    // collision number increases by 1
                    collNum++;
                    // update collision label
                    collCount.setText("Collisions: " + collNum);
                    // print the velocity values
                    printVelocities();
                }

                // update the box velocity labels
                v1Label.setText("Box M Velocity: " + b1V * MOVEMENT_SCALE);
                v2Label.setText("Box m Velocity: " + b2V * MOVEMENT_SCALE);

                // the jframe label coordinates are 
                // a rounded version of the true coordinates
                box1.setLocation((int) b1X, (500 - B1_SIZE) / 2);
                box2.setLocation((int) b2X, (500 - B2_SIZE) / 2);

            }
        });

        // add the components to the JFrame
        frame.add(box1);
        frame.add(box2);
        frame.add(collCount);
        frame.add(v1Label);
        frame.add(v2Label);
        // make the JFrame visible
        frame.setVisible(true);
        // print value labels in output
        System.out.println("Collision #, Box M Velocity, Box m Velocity");
        // print initial velocities
        printVelocities();
        // start the tick timer
        tick.start();
    }

    /**
     * Print velocities of boxes to output.
     */
    static public void printVelocities() {
        // print the current number of collisions, 
        // followed by velocity of box M, followed by velocity of box m
        System.out.println(collNum + ", " + (b1V * MOVEMENT_SCALE)
                + ", " + (b2V * MOVEMENT_SCALE));
        // these printouts were then used to graph 
        // the velocity of box m vs the velocity of box M
    }

}
