import com.sun.opengl.util.Animator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

/**
 * For our purposes only two of the GLEventListeners matter. Those would be
 * init() and display().
 */
public class GunsGLEventListener implements GLEventListener, KeyListener {

    //y position for players to make them move up and down.
    int xPosition_Player1 = 20;
    int yPosition_Player1 = 300; // middle of the screen
    int xPosition_Player2 = 780;
    int yPosition_Player2 = 300; // middle of the screen

    final double ONE_DEGREE = (Math.PI / 180);

    String P1_WhereToFire = "middle";
    String P2_WhereToFire = "middle";

    boolean P1_ready_to_shoot = true;
    boolean P2_ready_to_shoot = false;

    boolean[][] blocks = new boolean[40][30];

    float a = xPosition_Player1;//x axis
    float b = yPosition_Player1;//y axis

    //Remember to use floats for calculating slope
    //of the line the ball follows. Ints will be
    //far too imprecise (i.e. (8/9) == 0).
    //
    //Slope will change on each wall impact.
    //It will be multiplied by -1.
    float slope = 0.0f;
    float x = a; //holds the new 'x' position of ball
    float y = b; //holds the new 'y' position
    boolean movingUp1 = true;
    boolean movingUp2 = true;

    float speed = 0.4f;

    //means that there is a moving bullet in the screen
    boolean isFiring1 = false;
    boolean isFiring2 = false;

    //holds the position of the bullet according to the array index
    int idx = 0;
    int idy = 0;

    GLCanvas glc;

    public void setGLCanvas(GLCanvas glc) {
        this.glc = glc;
    }

    /**
     * Take care of initialization here.
     */
    public void init(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        GLU glu = new GLU();

        //Let's use a different color than black
        gl.glClearColor(0.725f, 0.722f, 1.0f, 0.0f);

        //Initiate the blocks pattern.
        //Randomize the visible blocks.
        int random;
        for (int i = 15; i < 25; i++) {
            for (int j = 0; j < 30; j++) {
                random = (int) (Math.random() * 3);
                if (random == 0) {
                    blocks[i][j] = false;
                } else {
                    blocks[i][j] = true;
                }
            }
        }

        //this loop is to make full pattern
//        for (int i = 15; i < 25; i++) {
//            for (int j = 0; j < 30; j++) {
//                blocks[i][j] = true;
//            }
//        }

        //Let's make the point 5 pixels wide
        gl.glPointSize(5.0f);

        gl.glViewport(0, 0, 800, 600);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 800, 0, 600);

    }

    /**
     * Take care of drawing here.
     */
    public void display(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        //erase GLCanvas using the clear color
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        //Choose our color for drawing
        float red = 0.1f;
        float green = 0.5f;
        float blue = 0.1f;
        gl.glColor3f(red, green, blue);

        //Draw the two Players.
        gl.glPointSize(5.0f);

        //************ Draw Player1 *******************
        //Draw the outer squre
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(xPosition_Player1 - 17, yPosition_Player1 - 17);
        gl.glVertex2i(xPosition_Player1 + 17, yPosition_Player1 - 17);
        gl.glVertex2i(xPosition_Player1 + 17, yPosition_Player1 + 17);
        gl.glVertex2i(xPosition_Player1 - 17, yPosition_Player1 + 17);
        gl.glEnd();

        //draw the second squre
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(xPosition_Player1 - 11, yPosition_Player1 - 11);
        gl.glVertex2i(xPosition_Player1 + 11, yPosition_Player1 - 11);
        gl.glVertex2i(xPosition_Player1 + 11, yPosition_Player1 + 11);
        gl.glVertex2i(xPosition_Player1 - 11, yPosition_Player1 + 11);
        gl.glEnd();

        //draw the smallest squre of the gunfire weapon
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(xPosition_Player1 - 5, yPosition_Player1 - 5);
        gl.glVertex2i(xPosition_Player1 + 5, yPosition_Player1 - 5);
        gl.glVertex2i(xPosition_Player1 + 5, yPosition_Player1 + 5);
        gl.glVertex2i(xPosition_Player1 - 5, yPosition_Player1 + 5);
        gl.glEnd();

        //draw the weapon's cylinder
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POLYGON);
        //Manually rotate the cylinder of the tank!
        if (P1_WhereToFire.equals("middle")) {
            gl.glVertex2i(xPosition_Player1 + 27, yPosition_Player1 - 3);
            gl.glVertex2i(xPosition_Player1, yPosition_Player1 - 3);
            gl.glVertex2i(xPosition_Player1, yPosition_Player1 + 3);
            gl.glVertex2i(xPosition_Player1 + 27, yPosition_Player1 + 3);
        } else if (P1_WhereToFire.equals("up")) {
            gl.glVertex2i(xPosition_Player1 + 27 - 2, yPosition_Player1 - 3 + 24);
            gl.glVertex2i(xPosition_Player1, yPosition_Player1 - 3);
            gl.glVertex2i(xPosition_Player1, yPosition_Player1 + 3);
            gl.glVertex2i(xPosition_Player1 + 27 - 6, yPosition_Player1 + 3 + 22);
        } else if (P1_WhereToFire.equals("down")) {
            gl.glVertex2i(xPosition_Player1 + 27 - 6, yPosition_Player1 - 3 - 22);
            gl.glVertex2i(xPosition_Player1, yPosition_Player1 - 3);
            gl.glVertex2i(xPosition_Player1, yPosition_Player1 + 3);
            gl.glVertex2i(xPosition_Player1 + 27 - 2, yPosition_Player1 + 3 - 24);
        }
        gl.glEnd();
        //********* Draw Player2 ***************
        //Draw the outer squre
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(xPosition_Player2 - 17, yPosition_Player2 - 17);
        gl.glVertex2i(xPosition_Player2 + 17, yPosition_Player2 - 17);
        gl.glVertex2i(xPosition_Player2 + 17, yPosition_Player2 + 17);
        gl.glVertex2i(xPosition_Player2 - 17, yPosition_Player2 + 17);
        gl.glEnd();

        //draw the second squre
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(xPosition_Player2 - 11, yPosition_Player2 - 11);
        gl.glVertex2i(xPosition_Player2 + 11, yPosition_Player2 - 11);
        gl.glVertex2i(xPosition_Player2 + 11, yPosition_Player2 + 11);
        gl.glVertex2i(xPosition_Player2 - 11, yPosition_Player2 + 11);
        gl.glEnd();

        //draw the smallest squre of the gunfire weapon
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(xPosition_Player2 - 5, yPosition_Player2 - 5);
        gl.glVertex2i(xPosition_Player2 + 5, yPosition_Player2 - 5);
        gl.glVertex2i(xPosition_Player2 + 5, yPosition_Player2 + 5);
        gl.glVertex2i(xPosition_Player2 - 5, yPosition_Player2 + 5);
        gl.glEnd();

        //draw the weapon's cylinder
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POLYGON);
        //Manually rotate the cylinder of the tank!
        if (P2_WhereToFire.equals("middle")) {
            gl.glVertex2i(xPosition_Player2 - 27, yPosition_Player2 - 3);
            gl.glVertex2i(xPosition_Player2, yPosition_Player2 - 3);
            gl.glVertex2i(xPosition_Player2, yPosition_Player2 + 3);
            gl.glVertex2i(xPosition_Player2 - 27, yPosition_Player2 + 3);
        } else if (P2_WhereToFire.equals("up")) {
            gl.glVertex2i(xPosition_Player2 - 27 + 2, yPosition_Player2 - 3 + 24);
            gl.glVertex2i(xPosition_Player2, yPosition_Player2 - 3);
            gl.glVertex2i(xPosition_Player2, yPosition_Player2 + 3);
            gl.glVertex2i(xPosition_Player2 - 27 + 6, yPosition_Player2 + 3 + 22);
        } else if (P2_WhereToFire.equals("down")) {
            gl.glVertex2i(xPosition_Player2 - 27 + 7, yPosition_Player2 - 3 - 22);
            gl.glVertex2i(xPosition_Player2, yPosition_Player2 - 3);
            gl.glVertex2i(xPosition_Player2, yPosition_Player2 + 3);
            gl.glVertex2i(xPosition_Player2 - 27 + 2, yPosition_Player2 + 3 - 24);
        }
        gl.glEnd();

        //Draw the blocks from the blocks array.
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        for (int i = 15; i < 25; i++) {
            for (int j = 0; j < 30; j++) {
                if (blocks[i][j]) {
                    gl.glBegin(GL.GL_POLYGON);
                    gl.glVertex2i(i * 20, 600 - (j * 20));
                    gl.glVertex2i(i * 20 + 17, 600 - (j * 20));
                    gl.glVertex2i(i * 20 + 17, 600 - (j * 20 + 17));
                    gl.glVertex2i(i * 20, 600 - (j * 20 + 17));
                    gl.glEnd();
                }
            }
        }

        //set the colot of the bullet.
        gl.glColor3f(0.0f, 0.0f, 0.0f);

        //reset the new position of the bullet each FPS.
        y = (slope * (x - a) + b);

        //check if the bullet goes out of the screen to right.
        if (x > 800) {
            P1_ready_to_shoot = false;
            isFiring1 = false;
            P2_ready_to_shoot = true;
            isFiring2 = false;

        }

        //check if the bullet goes out of the screen to right.
        if (x < 0) {
            P1_ready_to_shoot = true;
            isFiring1 = false;
            P2_ready_to_shoot = false;
            isFiring2 = false;
        }

        //while a moving bullet for player1 in the screen, search if it needs to reflect.
        if (isFiring1) {
            if (movingUp1) {
                x += speed;
                if (y > 600) {
                    movingUp1 = false;
                    slope *= -1;
                    a = x;
                    b = y;
                }
            }
            if (!movingUp1) {
                x += speed;
                if (y <= 5) {
                    slope *= -1;
                    a = x;
                    b = y;
                    movingUp1 = true;
                }
            }
        }

        //while a moving bullet for player1 in the screen, search if it needs to reflect.
        if (isFiring2) {
            if (movingUp2) {
                x -= speed;
                if (y > 600) {
                    movingUp2 = false;
                    slope *= -1;
                    a = x;
                    b = y;
                }
            }
            if (!movingUp2) {
                x -= speed;
                if (y <= 5) {
                    slope *= -1;
                    a = x;
                    b = y;
                    movingUp2 = true;
                }
            }
        }

        //get the position of the buller and convert it to idx.
        idx = (int) (x / 20);
        idy = (int) ((600 - y) / 20);

        //if the index is true, means there is a block, we remove it.
        if (idy >= 0 && idy < 30 && idx >= 0 && idx < 40) {
            if (blocks[idx][idy] == true) {
                blocks[idx][idy] = false;
                if (isFiring1) {
                    P2_ready_to_shoot = true;
                    isFiring1 = false;
                }
                if (isFiring2) {
                    P1_ready_to_shoot = true;
                    isFiring2 = false;
                }
            } //As long as there are no blocks keep drwaing the bullet don't erase it.
            else {
                if (isFiring1) {
                    gl.glBegin(GL.GL_POINTS);
                    gl.glVertex2d(x, y);
                    gl.glEnd();
                }

                if (isFiring2) {
                    gl.glBegin(GL.GL_POINTS);
                    gl.glVertex2d(x, y);
                    gl.glEnd();
                }
            }
        }
        //kill a player
        if (isFiring1 && (Math.abs(xPosition_Player2 - x) < 17) && (Math.abs(yPosition_Player2 - y) < 17)) {
            System.out.println("Player1 Wins!");
            System.exit(0);
        }
        if (isFiring2 && (Math.abs(xPosition_Player1 - x) < 17) && (Math.abs(yPosition_Player1 - y) < 17)) {
            System.out.println("Player2 Wins!");
            System.exit(0);
        }
    }

    /**
     * Called when the GLDrawable (GLCanvas or GLJPanel) has changed in size.
     */
    public void reshape(
            GLAutoDrawable drawable,
            int x,
            int y,
            int width,
            int height) {
    }

    /**
     * If the display depth is changed while the program is running this method
     * is called. Nowadays this doesn't happen much, unless a programmer has his
     * program do it.
     */
    public void displayChanged(
            GLAutoDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        //if player1 movers up or down, left or right
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (P1_ready_to_shoot && yPosition_Player1 < 599) {
                yPosition_Player1 += 10;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            if (P1_ready_to_shoot && yPosition_Player1 > 1) {
                yPosition_Player1 -= 10;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            if (P1_ready_to_shoot && xPosition_Player1 < 200) {
                xPosition_Player1 += 10;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            if (P1_ready_to_shoot && xPosition_Player1 > 0) {
                xPosition_Player1 -= 10;
            }
        } // to specify the angle of gunfire for player1 
        else if (e.getKeyCode() == KeyEvent.VK_R) {
            if (!isFiring1 && P1_ready_to_shoot) {
                slope = 1;
                a = xPosition_Player1;
                b = yPosition_Player1;
                x = a;
                isFiring1 = true;
                P1_ready_to_shoot = false;
                movingUp1 = true;
                P1_WhereToFire = "up";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
            if (!isFiring1 && P1_ready_to_shoot) {
                slope = 0;
                a = xPosition_Player1;
                b = yPosition_Player1;
                x = a;
                isFiring1 = true;
                P1_ready_to_shoot = false;
                P1_WhereToFire = "middle";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_V) {
            if (!isFiring1 && P1_ready_to_shoot) {
                slope = -1;
                a = xPosition_Player1;
                b = yPosition_Player1;
                x = a;
                isFiring1 = true;
                P1_ready_to_shoot = false;
                movingUp1 = false;
                P1_WhereToFire = "down";
            }

        } //if player2 moves up or down, right or left
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (P2_ready_to_shoot && yPosition_Player2 < 599) {
                yPosition_Player2 += 10;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (P2_ready_to_shoot && yPosition_Player2 > 1) {
                yPosition_Player2 -= 10;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (P2_ready_to_shoot && xPosition_Player2 < 799) {
                xPosition_Player2 += 10;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (P2_ready_to_shoot && xPosition_Player2 > 600) {
                xPosition_Player2 -= 10;
            }
        } // to specify the angle of gunfire for player2 
        else if (e.getKeyCode() == KeyEvent.VK_O) {
            if (!isFiring2 && P2_ready_to_shoot) {
                slope = -1;
                a = xPosition_Player2;
                b = yPosition_Player2;
                x = a;
                isFiring2 = true;
                P2_ready_to_shoot = false;
                movingUp2 = true;
                P2_WhereToFire = "up";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_K) {
            if (!isFiring2 && P2_ready_to_shoot) {
                slope = 0;
                a = xPosition_Player2;
                b = yPosition_Player2;
                x = a;
                isFiring2 = true;
                P2_ready_to_shoot = false;
                P2_WhereToFire = "middle";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            if (!isFiring2 && P2_ready_to_shoot) {
                slope = 1;
                a = xPosition_Player2;
                b = yPosition_Player2;
                x = a;
                isFiring2 = true;
                P2_ready_to_shoot = false;
                movingUp2 = false;
                P2_WhereToFire = "down";
            }
        }
    }
}
