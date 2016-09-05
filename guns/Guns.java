package FinalProject;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

/**
 * This is a basic JOGL app. Feel free to reuse this code or modify it.
 */
public class Guns extends JFrame {

    static Animator animator = null;
    GLCanvas glcanvas;

    public static void main(String[] args) {

        Guns app = new Guns();

        //start the animator
        animator.start();
    }

    public Guns() {
        //set the JFrame title
        super("Guns Project");

        //kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //only two JOGL lines of code ... and here they are
        glcanvas = new GLCanvas();
        GunsGLEventListener GunsApp = new GunsGLEventListener();
        glcanvas.addGLEventListener(GunsApp);
        glcanvas.addKeyListener(GunsApp);

        GunsApp.setGLCanvas(glcanvas);
        //create the animator                
        animator = new Animator(glcanvas);
        // OR
                /*
         * animator = new Animator();
         animator.add(glcanvas);
         */
        //OR : to control the speed
                /*
         * animator = new FPSAnimator(1000);
         animator.add(glcanvas);
         */

        //add the GLCanvas just like we would any Component
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(800, 600);

        //center the JFrame on the screen	
        setLocationRelativeTo(null);

        //Show what we have done
        setVisible(true);
    }
}
