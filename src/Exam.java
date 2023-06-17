import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.sun.opengl.util.BufferUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;


public class Exam extends GLCanvas implements GLEventListener, KeyListener, MouseListener {

    private JCheckBox lightOnOff;
    private JCheckBox ambientLighting;
    private JCheckBox diffuseLighting;
    private JCheckBox specularLighting;
    private JCheckBox ambientLight;

    private JButton removeButton;
    private JButton addButton;
    private JButton finishButton;
    private JButton helpButton;
    private JButton quitButton;
    private JButton newGameButton;

    private JFrame frame;
    private JLabel label;

    private Camera camera;

    private TextRenderer textRenderer;
    private TextRenderer textMatch;

    // global variables for the random position of the shapes on the blueprint
    private int randomFront;
    private int randomBack;
    private int randomTop;
    private int randomTopTwo;
    private int randomRight;
    private int randomLeft;
    private int randomLeftTwo;
    private int randomBottom;
    private int randomBottomTwo;


    private int nameID = 0; // name ID for picking

    // id from the palette shape inserted into the blueprint
    private int left_idn = 0;
    private int left_two_idn = 0;
    private int right_idn = 0;
    private int top_idn = 0;
    private int top_two_idn = 0;
    private int bottom_idn = 0;
    private int bottom_two_idn = 0;
    private int front_idn = 0;
    private int back_idn = 0;

    // color of the palette shape inserted into the blueprint - it will change when the user finished the game
    private float addShapeRed = 1f,
            addShapeGreen = 102 / 255f,
            addShapeBlue = 0f;


    private float defaultRed = 0f;
    private float defaultGreen = 0.5f;
    private float defaultBlue = 0.5f;

    private float redL = defaultRed;
    private float greenL = defaultGreen;
    private float blueL = defaultBlue;

    private float redLeftTwo = defaultRed;
    private float greenLeftTwo = defaultGreen;
    private float blueLeftTwo = defaultBlue;

    private float redT = defaultRed;
    private float greenT = defaultGreen;
    private float blueT = defaultBlue;

    private float redTopTwo = defaultRed;
    private float greenTopTwo = defaultGreen;
    private float blueTopTwo = defaultBlue;

    private float redB = defaultRed;
    private float greenB = defaultGreen;
    private float blueB = defaultBlue;

    private float redBottomTwo = defaultRed;
    private float greenBottomTwo = defaultGreen;
    private float blueBottomTwo = defaultBlue;

    private float redR = defaultRed;
    private float greenR = defaultGreen;
    private float blueR = defaultBlue;

    private float redF = defaultRed;
    private float greenF = defaultGreen;
    private float blueF = defaultBlue;

    private float redBack = defaultRed;
    private float greenBack = defaultGreen;
    private float blueBack = defaultBlue;

    // initial position of the shapes on the blueprint

    // LEFT
    private double leftX;
    private double leftY;
    private double leftZ;

    // LEFT
    private double leftTwoX;
    private double leftTwoY;
    private double leftTwoZ;

    // RIGHT
    private double rightX;
    private double rightY;
    private double rightZ;

    // TOP
    private double topX;
    private double topY;
    private double topZ;

    // TOP TWO
    private double topTwoX;
    private double topTwoY;
    private double topTwoZ;

    // BOTTOM
    private double bottomX;
    private double bottomY;
    private double bottomZ;

    // BOTTOM TWO
    private double bottomTwoX;
    private double bottomTwoY;
    private double bottomTwoZ;

    // FRONT
    private double frontX;
    private double frontY;
    private double frontZ;

    // BACK
    private double backX = 0;
    private double backY = 0;
    private double backZ = -1.5;

    // variable to travers through the blueprint
    private int travers = 0;

    // scaling increment/decrement
    private float scaleDelta = 0.1f;

    // initial scale of the inserted shapes into the blueprint
    private float scaleLeft = 0.5f;
    private float scaleLeftTwo = 0.5f;
    private float scaleRight = 0.5f;
    private float scaleTop = 0.5f;
    private float scaleTopTwo = 0.5f;
    private float scaleBottom = 0.5f;
    private float scaleBottomTwo = 0.5f;
    private float scaleFront = 0.5f;
    private float scaleBack = 0.5f;

    // initial angle of the inserted shapes into the blueprint
    private int angleLeftY = 90;
    private int angleLeftX = 90;
    private int angleLeftZ = 90;

    private int angleLeftTwoY = 90;
    private int angleLeftTwoX = 90;
    private int angleLeftTwoZ = 90;

    private int angleRightX = 90;
    private int angleRightY = 90;
    private int angleRightZ = 90;

    private int angleTopX = 90;
    private int angleTopY = 90;
    private int angleTopZ = 90;

    private int angleTopTwoX = 90;
    private int angleTopTwoY = 90;
    private int angleTopTwoZ = 90;

    private int angleBottomX = 90;
    private int angleBottomY = 90;
    private int angleBottomZ = 90;

    private int angleBottomTwoX = 90;
    private int angleBottomTwoY = 90;
    private int angleBottomTwoZ = 90;

    private int angleFrontX = 90;
    private int angleFrontY = 90;
    private int angleFrontZ = 90;

    private int angleBackX = 90;
    private int angleBackY = 90;
    private int angleBackZ = 90;

    // rotation increment/decrement
    private float rotate = 1;

    // colors of the shapes on the palette
    private float paletteRed = 0.78f;
    private float paletteGreen = 0.20f;
    private float paletteBlue = 0.92f;

    private static final int LEFT_ID = 1;
    private static final int LEFT_TWO_ID = 2;
    private static final int RIGHT_ID = 3;
    private static final int TOP_ID = 4;
    private static final int TOP_TWO_ID = 5;
    private static final int BOTTOM_ID = 6;
    private static final int BOTTOM_TWO_ID = 7;
    private static final int FRONT_ID = 8;
    private static final int BACK_ID = 9;


    // blueprint shapes
    private final static int
            SPHERE = 1,
            CYLINDER = 2,
            CONE = 3,
            CUBE = 4,
            CUBOID = 5,
            TETRAHEDRON = 6,
            RECTANGULAR_PYRAMID = 7,
            PENTAGON_PYRAMID = 8,
            HEXAGON_PYRAMID = 9;


    // palette shapes
    private final static int SPHERE_ID = 10;
    private final static int CUBOID_ID = 11;
    private final static int CYLINDER_ID = 12;
    private final static int CUBE_ID = 13;
    private final static int TETRAHEDRON_ID = 14;
    private final static int CONE_ID = 15;
    private final static int RECTANGULAR_PYRAMID_ID = 16;
    private final static int PENTAGON_PYRAMID_ID = 17;
    private final static int HEXAGON_PYRAMID_ID = 18;

    private final static String[] shape = {" ", "SPHERE", "CYLINDER", "CONE", "CUBE", "CUBOID", "TETRAHEDRON",
            "RECTANGULAR_PYRAMID", "PENTAGON_PYRAMID", "HEXAGON_PYRAMID",
            "SPHERE", "CUBOID", "CYLINDER", "CUBE", "TETRAHEDRON", "CONE",
            "RECTANGULAR_PYRAMID", "PENTAGON_PYRAMID", "HEXAGON_PYRAMID"};

    private GLCanvas canvas;
    private FPSAnimator animator;
    private static final int FPS = 60;
    private int windowWidth = 640;
    private int windowHeight = 480;
    private final String TITLE = "Exam";
    private GLU glu;

    // size of buffer
    private static final int BUFSIZE = 512;
    private IntBuffer selectBuffer;

    private boolean inSelectionMode = false;
    private int xCursor, yCursor;

    private String[] textureFileNames = {
            "disney4.jpg",
            "wood3.png",
            "mickeyframe.png",
            "background.jpg"
    };
    private Texture[] textures = new Texture[textureFileNames.length];

    private int currentAngleOfRotationX = 0;
    private int currentAngleOfRotationY = 0;
    private int currentAngleOfVisibleField = 55;

    private int angleDelta = 5;
    private float aspect;
    private float aspectP;
    private boolean gameFinished = false;
    private boolean newGame = true;

    private float translateX;
    private float translateY;
    private float translateZ;

    private float scale;
    private float scaleLeftShape;
    private float scaleLeftTwoShape;
    private float scaleRightShape;
    private float scaleTopShape;
    private float scaleTopTwoShape;
    private float scaleBottomShape;
    private float scaleBottomTwoShape;
    private float scaleFrontShape;
    private float scaleBackShape;

    public Exam() {

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setAlphaBits(8);
        caps.setDepthBits(24);
        caps.setDoubleBuffered(true);
        caps.setStencilBits(8);

        SwingUtilities.invokeLater(() -> {
            // Create the OpenGL rendering canvas
            canvas = new GLCanvas();
            canvas.setPreferredSize(new Dimension(windowWidth, windowHeight));
            canvas.addGLEventListener(this);
            canvas.addKeyListener(this);
            canvas.addMouseListener(this);
            canvas.setFocusable(true);
            canvas.requestFocus();
            canvas.requestFocusInWindow();

            animator = new FPSAnimator(canvas, FPS, true);

            frame = new JFrame();

            removeButton = new JButton("Remove");
            addButton = new JButton("  Add ");
            finishButton = new JButton("Finish");
            quitButton = new JButton("Quit");
            helpButton = new JButton("Help");
            newGameButton = new JButton("New Game");

            addButton.setPreferredSize(new Dimension(100, 20));
            removeButton.setPreferredSize(new Dimension(100, 20));
            finishButton.setPreferredSize(new Dimension(100, 20));
            helpButton.setPreferredSize(new Dimension(100, 20));
            quitButton.setPreferredSize(new Dimension(100, 20));
            newGameButton.setPreferredSize(new Dimension(100, 20));

            label = new JLabel("Click on the Help button to read game instructions.");

            lightOnOff = new JCheckBox("Turn Light ON/OFF", true);
            ambientLighting = new JCheckBox("Ambient Light", false);
            specularLighting = new JCheckBox("Specular Light", false);
            diffuseLighting = new JCheckBox("Diffuse Light", false);
            ambientLight = new JCheckBox("Global Ambient Light", false);

            JPanel bottom = new JPanel();
            bottom.setLayout(new GridLayout(2, 2));

            JPanel row1 = new JPanel();
            row1.add(removeButton);
            row1.add(addButton);
            row1.add(ambientLight);
            row1.add(lightOnOff);
            row1.add(ambientLighting);
            row1.add(diffuseLighting);
            row1.add(specularLighting);
            bottom.add(row1);

            JPanel row2 = new JPanel();
            row2.add(label);
            row2.add(helpButton);
            row2.add(finishButton);
            row2.add(newGameButton);
            row2.add(quitButton);
            bottom.add(row2);

            frame.add(bottom, BorderLayout.SOUTH);

            ambientLight.setFocusable(false);
            lightOnOff.setFocusable(false);
            ambientLighting.setFocusable(false);
            diffuseLighting.setFocusable(false);
            specularLighting.setFocusable(false);

            addButton.addActionListener(e -> {
                if (e.getSource() == addButton) {
                    if (travers == 1) {
                        left_idn = nameID;
                    } else if (travers == 2) {
                        left_two_idn = nameID;
                    } else if (travers == 3) {
                        right_idn = nameID;
                    } else if (travers == 4) {
                        top_idn = nameID;
                    } else if (travers == 5) {
                        top_two_idn = nameID;
                    } else if (travers == 6) {
                        bottom_idn = nameID;
                    } else if (travers == 7) {
                        bottom_two_idn = nameID;
                    } else if (travers == 8) {
                        front_idn = nameID;
                    } else if (travers == 9) {
                        back_idn = nameID;
                    }
                }
                addButton.setFocusable(false);
            });

            removeButton.addActionListener(e -> {
                if (e.getSource() == removeButton) {

                    if (travers == 1) {
                        left_idn = nameID;
                    } else if (travers == 2) {
                        left_two_idn = 0;
                    } else if (travers == 3) {
                        right_idn = 0;
                    } else if (travers == 4) {
                        top_idn = 0;
                    } else if (travers == 5) {
                        top_two_idn = 0;
                    } else if (travers == 6) {
                        bottom_idn = 0;
                    } else if (travers == 7) {
                        bottom_two_idn = 0;
                    } else if (travers == 8) {
                        front_idn = 0;
                    } else if (travers == 9) {
                        back_idn = 0;
                    }
                }
                removeButton.setFocusable(false);
            });

            finishButton.addActionListener(e -> {
                if (e.getSource() == finishButton) {
                    gameFinished = true;
                    addShapeRed = 0;
                    addShapeGreen = 200 / 255f;
                    addShapeBlue = 1;
                    currentAngleOfVisibleField = 110;
                    translateY = -2;
                }
                finishButton.setFocusable(false);
            });

            helpButton.addActionListener(e -> {
                if (e.getSource() == helpButton) {

                    JOptionPane.showMessageDialog(frame, "Instructions: \n" +
                                    "W - traverse through the blueprint\n" +
                                    "A - reduce the scale of the shape inserted into the blueprint\n" +
                                    "S - increase the scale of the shape inserted into the blueprint\n" +
                                    "Z - increase the scale of the blueprint\n" +
                                    "X - reduce the scale of the blueprint\n" +
                                    "I - move the blueprint (translate) on the z-axis in positive direction\n" +
                                    "O - move the blueprint (translate) on the z-axis in negative direction\n" +
                                    "J - move the blueprint (translate) on the x-axis in positive direction \n" +
                                    "K - move the blueprint (translate) on the x-axis in negative direction\n" +
                                    "N - move the blueprint (translate) on the y-axis in positive direction\n" +
                                    "M - move the blueprint (translate) on the y-axis in negative direction\n" +


                                    "Add Button - after selecting a shape from the palette, you can add it to the selected blueprint shape by the Add button\n" +
                                    "Remove Button - after selecting a shape from the palette, you can remove it from the selected blueprint shape by the Remove button\n" +

                                    "Finish Button - after the game finished, by pressing on the finish button, you can see your results\n" +
                                    "New Game Button - generate a new game\n" +
                                    "Quit Button - quit from the game \n" +
                                    "Light - you can enable/disable different light models by checking/unchecking  the light chekboxes (global ambient light, ambient, diffuse and specular)\n" +


                                    "+ (Numerical Keypad 9)- zoom in\n" +
                                    "- (Numerical Keypad 9)- zoom out\n" +
                                    "Left arrow - negative rotation around the x-axis of the blueprint\n" +
                                    "Right arrow - positive rotation around the x-axis of the blueprint \n" +
                                    "Up arrow - negative rotation around the y-axis of the blueprint\n" +
                                    "Down arrow - positive rotation around the y-axis of the blueprint\n" +
                                    "1 (Numerical Keypad 1) - positive rotation around the x-axis of the shape inserted into the blueprint\n" +
                                    "3 (Numerical Keypad 3)- negative rotation around the x-axis of the shape inserted into the blueprint\n" +
                                    "4 (Numerical Keypad 4)- positive rotation around the y-axis of the shape inserted into the blueprint\n" +
                                    "6 (Numerical Keypad 6)- negative rotation around the y-axis of the shape inserted into the blueprint\n" +
                                    "7 (Numerical Keypad 7)- positive rotation around the z-axis of the shape inserted into the blueprint\n" +
                                    "9 (Numerical Keypad 9)- negative rotation around the y-axis of the shape inserted into the blueprint\n"
                            , "Help", JOptionPane.INFORMATION_MESSAGE);
                }
                helpButton.setFocusable(false);
            });

            quitButton.addActionListener(e -> {
                if (e.getSource() == quitButton) {
                    animator.stop();
                    System.exit(0);

                }
                quitButton.setFocusable(false);
            });

            newGameButton.addActionListener(e -> {
                if (e.getSource() == newGameButton) {
                    newGame = true;
                    gameFinished = false;
                }
                newGameButton.setFocusable(false);
            });

            frame.getContentPane().add(canvas);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    new Thread(() -> {
                        if (animator.isStarted()) animator.stop();
                        System.exit(0);
                    }).start();
                }
            });

            camera = new Camera();
            camera.lookAt(-5, 0, 3, 0, 0, 0, 0, 1, 0);
            camera.setScale(15);

            frame.setTitle(TITLE);
            frame.pack();
            frame.setVisible(true);
            animator.start(); // start the animation loop
        });


    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context

        gl.glClearColor(0.95f, 0.95f, 1f, 0);


        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1);
        gl.glMateriali(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 100);


        float ambient[] = {0.1f, 0.1f, 0.1f, 1};
        float[] diffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, specular, 0);

        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
        gl.glDepthFunc(GL2.GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
        gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        glu = GLU.createGLU(gl); // get GL Utilities

        for (int i = 0; i < textureFileNames.length; i++) {
            try {
                URL textureURL;
                textureURL = getClass().getClassLoader().getResource("textures/" + textureFileNames[i]);
                if (textureURL != null) {
                    BufferedImage img = ImageIO.read(textureURL);
                    ImageUtil.flipImageVertically(img);
                    textures[i] = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);
                    textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                    textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        textures[0].enable(gl);

        textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 12));
        textMatch = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);


        if (inSelectionMode) {
            pickModels(drawable);
        } else { // normal rendering
            palette(drawable);
            drawBlueprint(drawable);
            drawBackground(drawable);
        }

        camera.apply(gl);
        lights(gl);

        float zero[] = {0, 0, 0, 1};

        if (ambientLight.isSelected()) {
            gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, new float[]{0.1F, 0.1F, 0.1F, 1}, 0);
        } else {
            gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, zero, 0);
        }

        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[]{0.1F, 0.1F, 0.1F, 1}, 0);

        if (gameFinished) {

            printResult(drawable);
        }

        if (!gameFinished) {

            printMatch(drawable);
        }


        if (newGame) {
            newGame();
            newGame = false;
        }
    }

    private void drawBackground(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glViewport(0, 0, windowWidth, windowHeight);

        aspect = (float) windowHeight / ((float) windowWidth);

        gl.glOrtho((float) -10 / 2, (float) 10 / 2,
                (-10 * aspect) / 2,
                (10 * aspect) / 2, 0, 100);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        gl.glPushMatrix();
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
        textures[3].bind(gl);  // Says which texture to use.
        gl.glTranslated(0, 0, -100);
        gl.glScalef(1.75f, 1, 1);
        gl.glColor3f(1f, 1f, 1f);

        double radius = 3.25;
        gl.glBegin(GL2.GL_POLYGON);
        gl.glNormal3f(0, 0, 1);

        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(-radius, radius);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(-radius, -radius);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(radius, -radius);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(radius, radius);
        gl.glEnd();

        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glEnd();
        gl.glPopMatrix();

    }

    public void newGame() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        randomFront = list.get(0);
        randomBack = list.get(1);
        randomTop = list.get(2);
        randomTopTwo = list.get(3);
        randomRight = list.get(4);
        randomLeft = list.get(5);
        randomLeftTwo = list.get(6);
        randomBottom = list.get(7);
        randomBottomTwo = list.get(8);


        // default values

        currentAngleOfRotationX = 0;
        currentAngleOfRotationY = 0;
        currentAngleOfVisibleField = 55;

        translateX = 0;
        translateY = 0;
        translateZ = 0;

        scale = 1;

        nameID = 0;
        left_idn = 0;
        left_two_idn = 0;
        right_idn = 0;
        top_idn = 0;
        top_two_idn = 0;
        bottom_idn = 0;
        bottom_two_idn = 0;
        front_idn = 0;
        back_idn = 0;
        addShapeRed = 1f;
        addShapeGreen = 102 / 255f;
        addShapeBlue = 0f;

        redL = defaultRed;
        greenL = defaultGreen;
        blueL = defaultBlue;

        redLeftTwo = defaultRed;
        greenLeftTwo = defaultGreen;
        blueLeftTwo = defaultBlue;

        redT = defaultRed;
        greenT = defaultGreen;
        blueT = defaultBlue;

        redTopTwo = defaultRed;
        greenTopTwo = defaultGreen;
        blueTopTwo = defaultBlue;

        redB = defaultRed;
        greenB = defaultGreen;
        blueB = defaultBlue;

        redBottomTwo = defaultRed;
        greenBottomTwo = defaultGreen;
        blueBottomTwo = defaultBlue;

        redR = defaultRed;
        greenR = defaultGreen;
        blueR = defaultBlue;

        redF = defaultRed;
        greenF = defaultGreen;
        blueF = defaultBlue;

        redBack = defaultRed;
        greenBack = defaultGreen;
        blueBack = defaultBlue;

        // LEFT
        leftX = -1.25;
        leftY = 0.5;
        leftZ = 0;

        scaleLeftShape = 0.5f;

        // LEFT TWO
        leftTwoX = -1.25;
        leftTwoY = -0.5;
        leftTwoZ = 0;

        scaleLeftTwoShape = 0.5f;

        // RIGHT
        rightX = 1.4;
        rightY = 0;
        rightZ = 0.5;

        scaleRightShape = 0.75f;

        // TOP
        topX = -0.5;
        topY = 1.5;
        topZ = 0.5;

        scaleTopShape = 1;

        // TOP TWO
        topTwoX = 0.5;
        topTwoY = 1.5;
        topTwoZ = -0.5;

        scaleTopTwoShape = 1;

        // BOTTOM
        bottomX = 1;
        bottomY = -1.63;
        bottomZ = 0.5f;

        scaleBottomShape = 1.25f;

        // BOTTOM TWO
        bottomTwoX = -1;
        bottomTwoY = -1.63;
        bottomTwoZ = -0.5f;

        scaleBottomTwoShape = 1.25f;

        // FRONT
        frontX = -0.25;
        frontY = 0;
        frontZ = 1.75;

        scaleFrontShape = 1.5f;

        // BACK
        backX = 0;
        backY = 0;
        backZ = -1.9;

        scaleBackShape = 1.75f;

        travers = 0;

        scaleDelta = 0.05f;
        scaleLeft = 0.5f;
        scaleLeftTwo = 0.5f;
        scaleRight = 0.5f;
        scaleTop = 0.5f;
        scaleTopTwo = 0.5f;
        scaleBottom = 0.5f;
        scaleBottomTwo = 0.5f;
        scaleFront = 0.5f;
        scaleBack = 0.5f;


        angleLeftY = 90;
        angleLeftX = 90;
        angleLeftZ = 90;

        angleLeftTwoY = 90;
        angleLeftTwoX = 90;
        angleLeftTwoZ = 90;

        angleRightX = 90;
        angleRightY = 90;
        angleRightZ = 90;

        angleTopX = 90;
        angleTopY = 90;
        angleTopZ = 90;

        angleTopTwoX = 90;
        angleTopTwoY = 90;
        angleTopTwoZ = 90;

        angleBottomX = 90;
        angleBottomY = 90;
        angleBottomZ = 90;

        angleFrontX = 90;
        angleFrontY = 90;
        angleFrontZ = 90;

        angleBackX = 90;
        angleBackY = 90;
        angleBackZ = 90;
    }

    private void startPicking(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        selectBuffer = BufferUtil.newIntBuffer(BUFSIZE);
        gl.glSelectBuffer(BUFSIZE, selectBuffer);
        gl.glRenderMode(GL2.GL_SELECT); // switch to selection mode
        gl.glInitNames(); // make an empty name stack
        gl.glMatrixMode(GL2.GL_MODELVIEW); // restore model view
    } // end of startPicking()

    public void palettePicking(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        int[] viewport = new int[4];
        float[] projMatrix = new float[16];
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        viewport[0] = 0;
        viewport[1] = 0;
        viewport[2] = windowWidth / 3;
        viewport[3] = windowHeight;
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projMatrix, 0);
        glu.gluPickMatrix((double) xCursor, (double) (viewport[3] - yCursor),
                1.0, 1.0, viewport, 0);

        gl.glMultMatrixf(projMatrix, 0); // following code from "OpenGL Distilled"
        gl.glOrtho((float) -10 / 2, (float) 10 / 2,
                (-10 * aspectP) / 2,
                (10 * aspectP) / 2, 1, 11);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(-1, 2, 5.0,
                0.0, 0.0, 0.0,
                0.0, 1.0, 0.0);

    }

    public void blueprintPicking(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        int[] viewport = new int[4];
        viewport[0] = windowWidth / 8;
        viewport[1] = 0;
        viewport[2] = windowWidth;
        viewport[3] = windowHeight;
        float[] projMatrix = new float[16];
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projMatrix, 0);
        // System.out.println(viewport[3]);
        glu.gluPickMatrix((double) xCursor, (double) (viewport[3] - yCursor), 1.0, 1.0, viewport, 0);
        // System.out.println(viewport[3] - yCursor);
        gl.glMultMatrixf(projMatrix, 0); // following code from "OpenGL Distilled"
        glu.gluPerspective(currentAngleOfVisibleField,
                1.f * windowWidth / windowHeight, 1, 100);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    private void endPicking(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        // restore original projection matrix
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glFlush();
        // return to normal rendering mode, and process hits
        int numHits = gl.glRenderMode(GL2.GL_RENDER);
        processHits(numHits, drawable);
        inSelectionMode = false;
    } // end of endPicking()

    public void processHits(int numHits, GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (numHits == 0)
            return; // no hits to process

        // storage for the name ID closest to the viewport
        int selectedNameID = 0; // dummy initial values
        float smallestZ = -1.0f;
        boolean isFirstLoop = true;
        int offset = 0;
        /* iterate through the hit records, saving the smallest z value and the name ID associated with it */


        for (int i = 0; i < numHits; i++) {
            int numNames = selectBuffer.get(offset);
            offset++;
            // minZ and maxZ are taken from the Z buffer
            float minZ = getDepth(offset);
            offset++;

            // store the smallest z value
            if (isFirstLoop) {
                smallestZ = minZ;
                isFirstLoop = false;
            } else {
                if (minZ < smallestZ)
                    smallestZ = minZ;
            }
            float maxZ = getDepth(offset);
            offset++;

            for (int j = 0; j < numNames; j++) {
                nameID = selectBuffer.get(offset);
                System.out.print(idToString(nameID) + "\n");


                if (j == (numNames - 1)) {
// if the last one (the top element on the stack)
                    if (smallestZ == minZ) // is this the smallest min z?
                        selectedNameID = nameID; // then store it's name ID
                }
                offset++;
            }
        }
    } // end of processHits()

    public void addShape(GLAutoDrawable drawable, int nameID) {
        GL2 gl = drawable.getGL().getGL2();
        switch (nameID) {
            case SPHERE_ID:
                Shapes.uvSphere(gl);
                break;
            case CUBOID_ID:
                Shapes.cuboid(gl);
                break;
            case CYLINDER_ID:
                Shapes.uvCylinder(gl);
                break;
            case CUBE_ID:
                Shapes.cube(gl);
                break;
            case TETRAHEDRON_ID:
                Shapes.tetrahedron(gl);
                break;
            case CONE_ID:
                Shapes.uvCone(gl);
                break;
            case RECTANGULAR_PYRAMID_ID:
                Shapes.rectangularPyramid(gl);
                break;
            case PENTAGON_PYRAMID_ID:
                Shapes.pentagonPyramid(gl);
                break;
            case HEXAGON_PYRAMID_ID:
                Shapes.hexagonPyramid(gl);
                break;
        }
    }

    private void addLeftShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(leftX, leftY, leftZ);
        gl.glScalef(scaleLeft, scaleLeft, scaleLeft);
        gl.glRotatef(angleLeftZ, 0, 0, rotate);
        gl.glRotatef(angleLeftY, 0, rotate, 0);
        gl.glRotatef(angleLeftX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();

    }

    private void addLeftTwoShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(leftTwoX, leftTwoY, leftTwoZ);
        gl.glScalef(scaleLeftTwo, scaleLeftTwo, scaleLeftTwo);
        gl.glRotatef(angleLeftTwoZ, 0, 0, rotate);
        gl.glRotatef(angleLeftTwoY, 0, rotate, 0);
        gl.glRotatef(angleLeftTwoX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();

    }

    private void addRightShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(rightX, rightY, rightZ);
        gl.glScalef(scaleRight, scaleRight, scaleRight);
        gl.glRotatef(angleRightZ, 0, 0, rotate);
        gl.glRotatef(angleRightY, 0, rotate, 0);
        gl.glRotatef(angleRightX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addTopShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(topX, topY, topZ);
        gl.glScalef(scaleTop, scaleTop, scaleTop);
        gl.glRotatef(angleTopZ, 0, 0, rotate);
        gl.glRotatef(angleTopY, 0, rotate, 0);
        gl.glRotatef(angleTopX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addTopTwoShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(topTwoX, topTwoY, topTwoZ);
        gl.glScalef(scaleTopTwo, scaleTopTwo, scaleTopTwo);
        gl.glRotatef(angleTopTwoZ, 0, 0, rotate);
        gl.glRotatef(angleTopTwoY, 0, rotate, 0);
        gl.glRotatef(angleTopTwoX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addBottomShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(bottomX, bottomY, bottomZ);
        gl.glScalef(scaleBottom, scaleBottom, scaleBottom);
        gl.glRotatef(angleBottomZ, 0, 0, rotate);
        gl.glRotatef(angleBottomY, 0, rotate, 0);
        gl.glRotatef(angleBottomX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addBottomTwoShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(bottomTwoX, bottomTwoY, bottomTwoZ);
        gl.glScalef(scaleBottomTwo, scaleBottomTwo, scaleBottomTwo);
        gl.glRotatef(angleBottomTwoZ, 0, 0, rotate);
        gl.glRotatef(angleBottomTwoY, 0, rotate, 0);
        gl.glRotatef(angleBottomTwoX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addFrontShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(frontX, frontY, frontZ);
        gl.glScalef(scaleFront, scaleFront, scaleFront);
        gl.glRotatef(angleFrontZ, 0, 0, rotate);
        gl.glRotatef(angleFrontY, 0, rotate, 0);
        gl.glRotatef(angleFrontX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addBackShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(backX, backY, backZ);
        gl.glScalef(scaleBack, scaleBack, scaleBack);
        gl.glRotatef(angleBackZ, 0, 0, rotate);
        gl.glRotatef(angleBackY, 0, rotate, 0);
        gl.glRotatef(angleBackX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }


    // The buffer is examined in processHits().

    private float getDepth(int offset) {
        long depth = (long) selectBuffer.get(offset); // large -ve number
        return (1.0f + ((float) depth / 0x7fffffff));
        // return as a float between 0 and 1
    } // end of getDepth()

    private String idToString(int nameID) {
        if (nameID == LEFT_ID)
            return "left";
        else if (nameID == LEFT_TWO_ID)
            return "left_two";
        else if (nameID == RIGHT_ID)
            return "right";
        else if (nameID == TOP_ID)
            return "top";
        else if (nameID == TOP_TWO_ID)
            return "top_two";
        else if (nameID == BACK_ID)
            return "back";
        else if (nameID == BOTTOM_ID)
            return "bottom";
        else if (nameID == BOTTOM_TWO_ID)
            return "bottom_two";
        else if (nameID == FRONT_ID)
            return "front";
        else if (nameID == SPHERE_ID)
            return "palette_sphere";
        else if (nameID == CUBOID_ID)
            return "palette_cuboid";
        else if (nameID == TETRAHEDRON_ID)
            return "palette_tetrahedron";
        else if (nameID == CUBE_ID)
            return "palette_cube";
        else if (nameID == CONE_ID)
            return "palette_cone";
        else if (nameID == CYLINDER_ID)
            return "palette_cylinder";
        else if (nameID == RECTANGULAR_PYRAMID_ID)
            return "palette_RECTANGULAR_PYRAMID";
        else if (nameID == PENTAGON_PYRAMID_ID)
            return "palette_PENTAGON_PYRAMID";
        else if (nameID == HEXAGON_PYRAMID_ID)
            return "palette_HEXAGON_PYRAMID";
        // we should not reach this point
        return "nameID " + nameID;
    } // end of idToString()


    private void pickModels(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        startPicking(drawable);
        palettePicking(drawable);

        gl.glPushName(SPHERE_ID);
        paletteSphere(drawable);
        gl.glPopName();

        gl.glPushName(CUBOID_ID);
        paletteCuboid(drawable);
        gl.glPopName();

        gl.glPushName(CYLINDER_ID);
        paletteCylinder(drawable);
        gl.glPopName();

        gl.glPushName(TETRAHEDRON_ID);
        paletteTetrahedron(drawable);
        gl.glPopName();

        gl.glPushName(CUBE_ID);
        paletteCube(drawable);
        gl.glPopName();

        gl.glPushName(CONE_ID);
        paletteCone(drawable);
        gl.glPopName();

        gl.glPushName(RECTANGULAR_PYRAMID_ID);
        paletteRectangularPyramid(drawable);
        gl.glPopName();

        gl.glPushName(PENTAGON_PYRAMID_ID);
        palettePentagonPyramid(drawable);
        gl.glPopName();

        gl.glPushName(HEXAGON_PYRAMID_ID);
        paletteHexagonPyramid(drawable);
        gl.glPopName();

        gl.glPushMatrix();
        blueprintPicking(drawable);


        gl.glRotated(currentAngleOfRotationX, 1, 0, 0);
        gl.glRotated(currentAngleOfRotationY, 0, 1, 0);

        gl.glPushName(LEFT_ID);
        drawLeft(drawable);
        gl.glPopName();

        gl.glPushName(LEFT_TWO_ID);
        drawLeftTwo(drawable);
        gl.glPopName();

        gl.glPushName(RIGHT_ID);
        drawRight(drawable);
        gl.glPopName();

        gl.glPushName(TOP_ID);
        drawTop(drawable);
        gl.glPopName();

        gl.glPushName(TOP_TWO_ID);
        drawTopTwo(drawable);
        gl.glPopName();

        gl.glPushName(BOTTOM_ID);
        drawBottom(drawable);
        gl.glPopName();

        gl.glPushName(BOTTOM_TWO_ID);
        drawBottomTwo(drawable);
        gl.glPopName();

        gl.glPushName(FRONT_ID);
        drawFront(drawable);
        gl.glPopName();

        gl.glPushName(BACK_ID);
        drawBack(drawable);
        gl.glPopName();


        gl.glPopMatrix();
        gl.glPopMatrix();
        endPicking(drawable);
    } // end of pickModels()


    private void setObserver() {

        glu.gluLookAt(-5, 0f, 3f,
                0, 0, 0,
                0, 1, 0);
    }

    private void palette(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glViewport(0, 0, windowWidth / 3, windowHeight);

        aspectP = (float) windowHeight / ((float) windowWidth / 3);

        gl.glOrtho((float) -10 / 2, (float) 10 / 2,
                (-10 * aspectP) / 2,
                (10 * aspectP) / 2, 1, 11);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

        paletteBackground(drawable);
        gl.glLoadIdentity();

        glu.gluLookAt(-1, 2, 5.0,
                0.0, 0.0, 0.0,
                0.0, 1.0, 0.0);


        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);


        palettePentagonPyramid(drawable);
        paletteSphere(drawable);
        paletteCuboid(drawable);
        paletteCylinder(drawable);
        paletteCube(drawable);
        paletteTetrahedron(drawable);
        paletteCone(drawable);
        paletteDog(drawable);


        paletteRectangularPyramid(drawable);
        paletteHexagonPyramid(drawable);


    }

    private void paletteBackground(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
        textures[2].bind(gl);  // Says which texture to use.
        gl.glTranslated(-1.5f, -1f, -7);
        gl.glScalef(3.5f, 5, 0);
        gl.glColor3f(1f, 1f, 1f);
        Shapes.square(gl, 2, true);

        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glEnd();
        gl.glPopMatrix();
    }

    private void paletteSphere(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, 2.5f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.uvSphere(gl);
        gl.glPopMatrix();
    }


    private void paletteCone(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-1.25f, 2.5f, 0);
        gl.glRotatef(-90, 1, 0, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.uvCone(gl);
        gl.glPopMatrix();
    }

    private void paletteCylinder(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(0.5f, 2.25f, 0);
        gl.glRotatef(-90, 1, 0, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.uvCylinder(gl);
        gl.glPopMatrix();
    }

    public void paletteCuboid(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-1.25f, 0, 0);
        gl.glScalef(0.8f, 0.8f, 0.8f);
        Shapes.cuboid(gl);
        gl.glPopMatrix();
    }


    public void paletteCube(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, 0, 0);
        Shapes.cube(gl);
        gl.glPopMatrix();
    }

    private void paletteTetrahedron(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(0.5f, 0, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        gl.glRotatef(30, 0, 1, 0);
        Shapes.tetrahedron(gl);
        gl.glPopMatrix();
    }


    private void paletteRectangularPyramid(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, -2.5f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.rectangularPyramid(gl);
        gl.glPopMatrix();
    }

    private void palettePentagonPyramid(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-2f, -1.5f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.pentagonPyramid(gl);
        gl.glPopMatrix();
    }

    private void paletteHexagonPyramid(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, -4.5f, 0);
        gl.glRotatef(45, 0, 1, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.hexagonPyramid(gl);
        gl.glPopMatrix();
    }
    private void paletteDog(GLAutoDrawable drawable){
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        Shapes.dog(gl);
    }

    private void drawBlueprint(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(windowWidth / 8, 0, windowWidth, windowHeight);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(currentAngleOfVisibleField, 1.f * windowWidth / windowHeight, 1, 100);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        setObserver();

        gl.glPushMatrix();
        gl.glTranslated(translateX, translateY, translateZ);
        gl.glScalef(scale, scale, scale);
        gl.glRotated(currentAngleOfRotationX, 1, 0, 0);
        gl.glRotated(currentAngleOfRotationY, 0, 1, 0);

        gl.glColor3f(1, 1, 1);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textures[0].bind(gl);  // Says which texture to use.
        gl.glEnable(GL.GL_TEXTURE_2D);
        Shapes.cube(gl, 2, true);
        gl.glDisable(GL.GL_TEXTURE_2D);

        drawTop(drawable);
        drawTopTwo(drawable);
        drawRight(drawable);
        drawLeft(drawable);
        drawLeftTwo(drawable);
        drawBottom(drawable);
        drawBottomTwo(drawable);
        drawFront(drawable);
        drawBack(drawable);

        addLeftShape(drawable, left_idn);
        addLeftTwoShape(drawable, left_two_idn);
        addRightShape(drawable, right_idn);
        addTopShape(drawable, top_idn);
        addTopTwoShape(drawable, top_two_idn);
        addBottomShape(drawable, bottom_idn);
        addBottomTwoShape(drawable, bottom_two_idn);
        addFrontShape(drawable, front_idn);
        addBackShape(drawable, back_idn);

        gl.glPopMatrix();
    }



    private void drawShape(GLAutoDrawable drawable, int randomShape) {
        GL2 gl = drawable.getGL().getGL2();

        switch (randomShape) {
            case SPHERE:
                Shapes.uvSphere(gl);
                break;
            case CYLINDER:
                Shapes.uvCylinder(gl);
                break;
            case CONE:
                Shapes.uvCone(gl);
                break;
            case CUBE:
                Shapes.cube(gl);
                break;
            case CUBOID:
                Shapes.cuboid(gl);
                break;
            case TETRAHEDRON:
                Shapes.tetrahedron(gl);
                break;
            case RECTANGULAR_PYRAMID:
                Shapes.rectangularPyramid(gl);
                break;
            case PENTAGON_PYRAMID:
                Shapes.pentagonPyramid(gl);
                break;
            case HEXAGON_PYRAMID:
                Shapes.hexagonPyramid(gl);
                break;
        }
    }

    private void drawLeft(GLAutoDrawable drawable) {

        if (randomLeft == CUBOID) {
            leftX = -1.5f;
        }

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redL, greenL, blueL);
        gl.glTranslated(leftX, leftY, leftZ);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glScalef(scaleLeftShape, scaleLeftShape, scaleLeftShape);
        drawShape(drawable, randomLeft);
        gl.glPopMatrix();
    }

    private void drawLeftTwo(GLAutoDrawable drawable) {

        if (randomLeftTwo == CUBOID) {
            leftTwoX = -1.5f;
        }

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redLeftTwo, greenLeftTwo, blueLeftTwo);
        gl.glTranslated(leftTwoX, leftTwoY, leftTwoZ);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glScalef(scaleLeftTwoShape, scaleLeftTwoShape, scaleLeftTwoShape);
        drawShape(drawable, randomLeftTwo);
        gl.glPopMatrix();
    }


    private void drawRight(GLAutoDrawable drawable) {

        if (randomRight == CUBOID) {
            rightX = 1.75f;
        }

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redR, greenR, blueR);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(rightX, rightY, rightZ);
        gl.glScalef(scaleRightShape, scaleRightShape, scaleRightShape);

        drawShape(drawable, randomRight);
        gl.glPopMatrix();
    }


    private void drawTop(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redT, greenT, blueT);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(topX, topY, topZ);
        gl.glScalef(scaleTopShape, scaleTopShape, scaleTopShape);

        drawShape(drawable, randomTop);
        gl.glPopMatrix();
    }

    private void drawTopTwo(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redTopTwo, greenTopTwo, blueTopTwo);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(topTwoX, topTwoY, topTwoZ);
        gl.glScalef(scaleTopTwoShape, scaleTopTwoShape, scaleTopTwoShape);

        drawShape(drawable, randomTopTwo);
        gl.glPopMatrix();
    }

    private void drawBottom(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redB, greenB, blueB);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(bottomX, bottomY, bottomZ);
        gl.glScalef(scaleBottomShape, scaleBottomShape, scaleBottomShape);
        drawShape(drawable, randomBottom);
        gl.glPopMatrix();
    }

    private void drawBottomTwo(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redBottomTwo, greenBottomTwo, blueBottomTwo);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(bottomTwoX, bottomTwoY, bottomTwoZ);
        gl.glScalef(scaleBottomTwoShape, scaleBottomTwoShape, scaleBottomTwoShape);
        drawShape(drawable, randomBottomTwo);
        gl.glPopMatrix();
    }

    private void drawFront(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redF, greenF, blueF);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(frontX, frontY, frontZ);
        gl.glScalef(scaleFrontShape, scaleFrontShape, scaleFrontShape);

        drawShape(drawable, randomFront);
        gl.glPopMatrix();
    }

    private void drawBack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redBack, greenBack, blueBack);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(backX, backY, backZ);
        gl.glScalef(scaleBackShape, scaleBackShape, scaleBackShape);

        drawShape(drawable, randomBack);
        gl.glPopMatrix();
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        windowWidth = width;
        windowHeight = height;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void colorShape(int travers) {
        switch (travers) {
            case LEFT_ID:
                redL = 1;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case LEFT_TWO_ID:
                redL = 0;
                redLeftTwo = 1;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case RIGHT_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 1;
                redF = 0;
                redBack = 0;
                break;
            case TOP_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 1;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;

            case TOP_TWO_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 1;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case BOTTOM_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 1;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case BOTTOM_TWO_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 1;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case FRONT_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 1;
                redBack = 0;
                break;
            case BACK_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 1;
                break;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            // X-rotation
            case KeyEvent.VK_NUMPAD1:

                if (travers == 1) {
                    if (angleLeftX < 360) {
                        angleLeftX += angleDelta;
                        System.out.println(angleLeftX);
                    }
                } else if (travers == 2) {
                    if (angleLeftTwoX < 360) {
                        angleLeftTwoX += angleDelta;
                        System.out.println(angleLeftTwoX);
                    }
                } else if (travers == 3) {
                    if (angleRightX < 360) {
                        angleRightX += angleDelta;
                        System.out.println(angleRightX);
                    }
                } else if (travers == 4) {
                    if (angleTopX < 360) {
                        angleTopX += angleDelta;
                        System.out.println(angleTopX);
                    }
                } else if (travers == 5) {
                    if (angleTopTwoX < 360) {
                        angleTopTwoX += angleDelta;
                        System.out.println(angleTopTwoX);
                    }
                } else if (travers == 6) {
                    if (angleBottomX < 360) {
                        angleBottomX += angleDelta;
                        System.out.println(angleBottomX);
                    }
                } else if (travers == 7) {
                    if (angleBottomTwoX < 360) {
                        angleBottomTwoX += angleDelta;
                        System.out.println(angleBottomTwoX);
                    }
                } else if (travers == 8) {
                    if (angleFrontX < 360) {
                        angleFrontX += angleDelta;
                        System.out.println(angleFrontX);
                    }
                } else if (travers == 9) {
                    if (angleBackX < 360) {
                        angleBackX += angleDelta;
                        System.out.println(angleBackX);
                    }
                }
                break;
            case KeyEvent.VK_NUMPAD3:

                if (travers == 1) {
                    if (angleLeftX > 0) {
                        angleLeftX -= angleDelta;
                        System.out.println(angleLeftX);
                    }
                } else if (travers == 2) {
                    if (angleLeftTwoX > 0) {
                        angleLeftTwoX -= angleDelta;
                        System.out.println(angleLeftTwoX);
                    }
                } else if (travers == 3) {
                    if (angleRightX > 0) {
                        angleRightX -= angleDelta;
                        System.out.println(angleRightX);
                    }
                } else if (travers == 4) {
                    if (angleTopX > 0) {
                        angleTopX -= angleDelta;
                        System.out.println(angleTopX);
                    }
                } else if (travers == 5) {
                    if (angleTopTwoX > 0) {
                        angleTopTwoX -= angleDelta;
                        System.out.println(angleTopTwoX);
                    }
                } else if (travers == 6) {
                    if (angleBottomX > 0) {
                        angleBottomX -= angleDelta;
                        System.out.println(angleBottomX);
                    }
                } else if (travers == 7) {
                    if (angleBottomTwoX > 0) {
                        angleBottomTwoX -= angleDelta;
                        System.out.println(angleBottomTwoX);
                    }
                } else if (travers == 8) {
                    if (angleFrontX > 0) {
                        angleFrontX -= angleDelta;
                        System.out.println(angleFrontX);
                    }
                } else if (travers == 9) {
                    if (angleBackX > 0) {
                        angleBackX -= angleDelta;
                        System.out.println(angleBackX);
                    }
                }

                break;

            // Y-rotation
            case KeyEvent.VK_NUMPAD4:

                if (travers == 1) {
                    if (angleLeftY < 360) {
                        angleLeftY += angleDelta;
                        System.out.println(angleLeftY);
                    }
                } else if (travers == 2) {
                    if (angleLeftTwoY < 360) {
                        angleLeftTwoY += angleDelta;
                        System.out.println(angleLeftTwoY);
                    }
                } else if (travers == 3) {
                    if (angleRightY < 360) {
                        angleRightY += angleDelta;
                        System.out.println(angleRightY);
                    }
                } else if (travers == 4) {
                    if (angleTopY < 360) {
                        angleTopY += angleDelta;
                        System.out.println(angleTopY);
                    }
                } else if (travers == 5) {
                    if (angleTopTwoY < 360) {
                        angleTopTwoY += angleDelta;
                        System.out.println(angleTopTwoY);
                    }
                } else if (travers == 6) {
                    if (angleBottomY < 360) {
                        angleBottomY += angleDelta;
                        System.out.println(angleBottomY);
                    }
                } else if (travers == 7) {
                    if (angleBottomTwoY < 360) {
                        angleBottomTwoY += angleDelta;
                        System.out.println(angleBottomTwoY);
                    }
                } else if (travers == 8) {
                    if (angleFrontY < 360) {
                        angleFrontY += angleDelta;
                        System.out.println(angleFrontY);
                    }
                } else if (travers == 9) {
                    if (angleBackY < 360) {
                        angleBackY += angleDelta;
                        System.out.println(angleBackY);
                    }
                }
                break;
            case KeyEvent.VK_NUMPAD6:

                if (travers == 1) {
                    if (angleLeftY > 0) {
                        angleLeftY -= angleDelta;
                        System.out.println(angleLeftY);
                    }
                } else if (travers == 2) {
                    if (angleLeftTwoY > 0) {
                        angleLeftTwoY -= angleDelta;
                        System.out.println(angleLeftTwoY);
                    }
                } else if (travers == 3) {
                    if (angleRightY > 0) {
                        angleRightY -= angleDelta;
                        System.out.println(angleRightY);
                    }
                } else if (travers == 4) {
                    if (angleTopY > 0) {
                        angleTopY -= angleDelta;
                        System.out.println(angleTopY);
                    }
                } else if (travers == 5) {
                    if (angleTopTwoY > 0) {
                        angleTopTwoY -= angleDelta;
                        System.out.println(angleTopTwoY);
                    }
                } else if (travers == 6) {
                    if (angleBottomY > 0) {
                        angleBottomY -= angleDelta;
                        System.out.println(angleBottomY);
                    }
                } else if (travers == 7) {
                    if (angleBottomTwoY > 0) {
                        angleBottomTwoY -= angleDelta;
                        System.out.println(angleBottomTwoY);
                    }
                } else if (travers == 8) {
                    if (angleFrontY > 0) {
                        angleFrontY -= angleDelta;
                        System.out.println(angleFrontY);
                    }
                } else if (travers == 9) {
                    if (angleBackY > 0) {
                        angleBackY -= angleDelta;
                        System.out.println(angleBackY);
                    }
                }

                break;

            // Z-rotation
            case KeyEvent.VK_NUMPAD7:

                if (travers == 1) {
                    if (angleLeftZ < 360) {
                        angleLeftZ += angleDelta;
                        System.out.println(angleLeftZ);
                    }
                } else if (travers == 2) {
                    if (angleLeftTwoZ < 360) {
                        angleLeftTwoZ += angleDelta;
                        System.out.println(angleLeftTwoZ);
                    }
                } else if (travers == 3) {
                    if (angleRightZ < 360) {
                        angleRightZ += angleDelta;
                        System.out.println(angleRightZ);
                    }
                } else if (travers == 4) {
                    if (angleTopZ < 360) {
                        angleTopZ += angleDelta;
                        System.out.println(angleTopZ);
                    }
                } else if (travers == 5) {
                    if (angleTopTwoZ < 360) {
                        angleTopTwoZ += angleDelta;
                        System.out.println(angleTopTwoZ);
                    }
                } else if (travers == 6) {
                    if (angleBottomZ < 360) {
                        angleBottomZ += angleDelta;
                        System.out.println(angleBottomZ);
                    }
                } else if (travers == 7) {
                    if (angleBottomTwoZ < 360) {
                        angleBottomTwoZ += angleDelta;
                        System.out.println(angleBottomTwoZ);
                    }
                } else if (travers == 8) {
                    if (angleFrontZ < 360) {
                        angleFrontZ += angleDelta;
                        System.out.println(angleFrontZ);
                    }
                } else if (travers == 9) {
                    if (angleBackZ < 360) {
                        angleBackZ += angleDelta;
                        System.out.println(angleBackZ);
                    }
                }
                break;
            case KeyEvent.VK_NUMPAD9:

                if (travers == 1) {
                    if (angleLeftZ > 0) {
                        angleLeftZ -= angleDelta;
                        System.out.println(angleLeftZ);
                    }
                } else if (travers == 2) {
                    if (angleLeftTwoZ > 0) {
                        angleLeftTwoZ -= angleDelta;
                        System.out.println(angleLeftZ);
                    }
                } else if (travers == 3) {
                    if (angleRightZ > 0) {
                        angleRightZ -= angleDelta;
                        System.out.println(angleRightZ);
                    }
                } else if (travers == 4) {
                    if (angleTopZ > 0) {
                        angleTopZ -= angleDelta;
                        System.out.println(angleTopZ);
                    }
                } else if (travers == 5) {
                    if (angleTopTwoZ > 0) {
                        angleTopTwoZ -= angleDelta;
                        System.out.println(angleTopTwoZ);
                    }
                } else if (travers == 6) {
                    if (angleBottomZ > 0) {
                        angleBottomZ -= angleDelta;
                        System.out.println(angleBottomZ);
                    }
                } else if (travers == 7) {
                    if (angleBottomTwoZ > 0) {
                        angleBottomTwoZ -= angleDelta;
                        System.out.println(angleBottomTwoZ);
                    }
                } else if (travers == 8) {
                    if (angleFrontZ > 0) {
                        angleFrontZ -= angleDelta;
                        System.out.println(angleFrontZ);
                    }
                } else if (travers == 9) {
                    if (angleBackZ > 0) {
                        angleBackZ -= angleDelta;
                        System.out.println(angleBackZ);
                    }
                }

                break;

            case KeyEvent.VK_S:

                if (travers == 1) {
                    scaleLeft += scaleDelta;
                    System.out.println(scaleLeft + "Left scale");
                } else if (travers == 2) {
                    scaleLeftTwo += scaleDelta;
                } else if (travers == 3) {
                    scaleRight += scaleDelta;
                } else if (travers == 4) {
                    scaleTop += scaleDelta;
                } else if (travers == 5) {
                    scaleTopTwo += scaleDelta;
                } else if (travers == 6) {
                    scaleBottom += scaleDelta;
                } else if (travers == 7) {
                    scaleBottomTwo += scaleDelta;
                } else if (travers == 8) {
                    scaleFront += scaleDelta;
                } else if (travers == 9) {
                    scaleBack += scaleDelta;
                }

                break;
            case KeyEvent.VK_A:
                System.out.println(travers);
                if (travers == 1) {
                    scaleLeft -= scaleDelta;
                } else if (travers == 2) {
                    scaleLeftTwo -= scaleDelta;
                } else if (travers == 3) {
                    scaleRight -= scaleDelta;
                } else if (travers == 4) {
                    scaleTop -= scaleDelta;
                } else if (travers == 5) {
                    scaleTopTwo -= scaleDelta;
                } else if (travers == 6) {
                    scaleBottom -= scaleDelta;
                } else if (travers == 7) {
                    scaleBottomTwo -= scaleDelta;
                } else if (travers == 8) {
                    scaleFront -= scaleDelta;
                } else if (travers == 9) {
                    scaleBack -= scaleDelta;
                    System.out.println(scaleBack);
                    System.out.println(travers);
                }

                break;

            case KeyEvent.VK_W:
                travers++;
                colorShape(travers);
                if (travers == 10) {
                    travers = 0;
                }
                break;
            case KeyEvent.VK_UP:
                currentAngleOfRotationX--;
                break;
            case KeyEvent.VK_DOWN:
                currentAngleOfRotationX++;
                break;
            case KeyEvent.VK_LEFT:
                currentAngleOfRotationY++;
                break;
            case KeyEvent.VK_RIGHT:
                currentAngleOfRotationY--;

                break;
            case KeyEvent.VK_ADD:
                if (currentAngleOfVisibleField > 1) {
                    currentAngleOfVisibleField--;
                }
                break;
            case KeyEvent.VK_SUBTRACT:
                if (currentAngleOfVisibleField < 179) {
                    currentAngleOfVisibleField++;
                }
                break;

            case KeyEvent.VK_Z:
                scale += 0.1;
                break;

            case KeyEvent.VK_X:
                scale -= 0.1;
                break;

            case KeyEvent.VK_J:
                translateX += 0.1;
                break;
            case KeyEvent.VK_K:
                translateX -= 0.1;
                break;

            case KeyEvent.VK_M:
                translateY += 0.1;
                break;
            case KeyEvent.VK_N:
                translateY -= 0.1;
                break;
            case KeyEvent.VK_I:
                translateZ += 0.1;
                break;
            case KeyEvent.VK_O:
                translateZ -= 0.1;
                break;
            case KeyEvent.VK_ESCAPE:
                animator.stop();
                System.exit(0);
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: { // left button
                xCursor = e.getX();
                yCursor = e.getY();
                inSelectionMode = true;
                break;
            }
            case MouseEvent.BUTTON3: { // right button
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    private void lights(GL2 gl) {
        gl.glColor3d(0.5, 0.5, 0.5);
        float zero[] = {0, 0, 0, 1};
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, zero, 0);

        if (lightOnOff.isSelected())
            gl.glDisable(GL2.GL_LIGHTING);
        else
            gl.glEnable(GL2.GL_LIGHTING);

        float ambient[] = {0.1f, 0.1f, 0.1f, 1};
        float[] diffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};

        if (ambientLighting.isSelected()) {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, ambient, 0);
            gl.glEnable(GL2.GL_LIGHT0);
        } else {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
            gl.glDisable(GL2.GL_LIGHT0);
        }

        if (diffuseLighting.isSelected()) {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, diffuse, 0);
            gl.glEnable(GL2.GL_LIGHT1);
        } else {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
            gl.glDisable(GL2.GL_LIGHT1);
        }

        if (specularLighting.isSelected()) {
            float[] shiness = {5.0f};
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, specular, 0);
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shiness, 0);
            gl.glEnable(GL2.GL_LIGHT2);
        } else {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
            gl.glDisable(GL2.GL_LIGHT2);
        }

        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0); // Turn off emission color!
    }

    public void writeText(String tekst, int x, int y) {
        textRenderer.beginRendering(windowWidth, windowHeight);
        textRenderer.setColor(0.3f, 0.3f, 0.5f, 1);
        textRenderer.draw(tekst, x, y);
        textRenderer.endRendering();
    }

    public void writeMatch(String text, int x, int y) {
        textMatch.beginRendering(windowWidth, windowHeight);
        textMatch.setColor(0.3f, 0.3f, 0.5f, 1);
        textMatch.draw(text, x, y);
        textMatch.endRendering();
    }

    public void printMatch(GLAutoDrawable drawable) {

        if (travers == 1) {
            if (shape[randomLeft].equals(shape[left_idn]))
                if (leftScaleCheck(scaleLeft).equals("appropriate") && rotationCheck(randomLeft, angleLeftX, angleLeftY, angleLeftZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        } else if (travers == 2) {
            if (shape[randomLeftTwo].equals(shape[left_two_idn]))
                if (leftTwoScaleCheck(scaleLeftTwo).equals("appropriate") && rotationCheck(randomLeftTwo, angleLeftTwoX, angleLeftTwoY, angleLeftTwoZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        } else if (travers == 3) {

            if (shape[randomRight].equals(shape[right_idn]))
                if (rightScaleCheck(scaleRight).equals("appropriate")
                        && rotationCheck(randomRight, angleRightX, angleRightY, angleRightZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        } else if (travers == 4) {
            if (shape[randomTop].equals(shape[top_idn]))
                if (topScaleCheck(scaleTop).equals("appropriate")
                        && rotationCheck(randomTop, angleTopX, angleTopY, angleTopZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }

        } else if (travers == 5) {
            if (shape[randomTopTwo].equals(shape[top_two_idn]))
                if (topTwoScaleCheck(scaleTopTwo).equals("appropriate")
                        && rotationCheck(randomTopTwo, angleTopTwoX, angleTopTwoY, angleTopTwoZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }

        } else if (travers == 6) {
            if (shape[randomBottom].equals(shape[bottom_idn]))
                if (bottomTwoScaleCheck(scaleBottom).equals("appropriate")
                        && rotationCheck(randomBottom, angleBottomX, angleBottomY, angleBottomZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }

        } else if (travers == 7) {
            if (shape[randomBottomTwo].equals(shape[bottom_two_idn]))
                if (bottomTwoScaleCheck(scaleBottomTwo).equals("appropriate")
                        && rotationCheck(randomBottomTwo, angleBottomTwoX, angleBottomTwoY, angleBottomTwoZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }

        } else if (travers == 8) {
            if (shape[randomFront].equals(shape[front_idn]))
                if (frontScaleCheck(scaleFront).equals("appropriate")
                        && rotationCheck(randomFront, angleFrontX, angleFrontY, angleFrontZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }

        } else if (travers == 9) {
            if (shape[randomBack].equals(shape[back_idn]))
                if (backScaleCheck(scaleBack).equals("appropriate")
                        && rotationCheck(randomBack, angleBackX, angleBackY, angleBackZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        }
    }

    public void printResult(GLAutoDrawable drawable) {
        writeText("RESULT: " + matchedShape() + "/9 shape matched correctly", (int) (windowWidth / 3.5f), windowHeight - 40);

        writeText("Left One: blueprint shape: " +
                        shape[randomLeft] +
                        " - matched shape: " +
                        shape[left_idn] + " Scaling: " +
                        leftScaleCheck(scaleLeft) +
                        " Rotation: " + rotationCheck(randomLeft, angleLeftX, angleLeftY, angleLeftZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 60);

        writeText("Left Two: blueprint shape: " +
                        shape[randomLeftTwo] +
                        " - matched shape: " +
                        shape[left_two_idn] + " Scaling: " +
                        leftTwoScaleCheck(scaleLeftTwo) +
                        " Rotation: " + rotationCheck(randomLeftTwo, angleLeftTwoX, angleLeftTwoY, angleLeftTwoZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 80);

        writeText("Right: blueprint shape: " +
                        shape[randomRight] +
                        " - matched shape: " +
                        shape[right_idn] + " Scaling: " +
                        rightScaleCheck(scaleRight) +
                        " Rotation: " + rotationCheck(randomRight, angleRightX, angleRightY, angleRightZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 100);

        writeText("Top One: blueprint shape: " +
                        shape[randomTop] +
                        " - matched shape: " +
                        shape[top_idn] + " Scaling: " +
                        topScaleCheck(scaleTop) +
                        " Rotation: " + rotationCheck(randomTop, angleTopX, angleTopY, angleTopZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 120);

        writeText("Top Two: blueprint shape: " +
                        shape[randomTopTwo] +
                        " - matched shape: " +
                        shape[top_two_idn] + " Scaling: " +
                        topTwoScaleCheck(scaleTopTwo) +
                        " Rotation: " + rotationCheck(randomTopTwo, angleTopTwoX, angleTopTwoY, angleTopTwoZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 140);

        writeText("Bottom One: blueprint shape: " +
                        shape[randomBottom] +
                        " - matched shape: " +
                        shape[bottom_idn] + " Scaling: " +
                        bottomScaleCheck(scaleBottom) +
                        " Rotation: " + rotationCheck(randomBottom, angleBottomX, angleBottomY, angleBottomZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 160);

        writeText("Bottom Two: blueprint shape: " +
                        shape[randomBottomTwo] +
                        " - matched shape: " +
                        shape[bottom_two_idn] + " Scaling: " +
                        bottomTwoScaleCheck(scaleBottomTwo) +
                        " Rotation: " + rotationCheck(randomBottomTwo, angleBottomTwoX, angleBottomTwoY, angleBottomTwoZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 180);

        writeText("Front: blueprint shape: " +
                        shape[randomFront] +
                        " - matched shape: " +
                        shape[front_idn] + " Scaling: " +
                        frontScaleCheck(scaleFront) +
                        " Rotation: " + rotationCheck(randomFront, angleFrontX, angleFrontY, angleFrontZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 200);

        writeText("Back: blueprint shape: " +
                        shape[randomBack] +
                        " - matched shape: " +
                        shape[back_idn] + " Scaling: " +
                        backScaleCheck(scaleBack) +
                        " Rotation: " + rotationCheck(randomBack, angleBackX, angleBackY, angleBackZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 220);
    }

    private int matchedShape() {

        int match = 0;

        if (shape[randomLeft].equals(shape[left_idn])) {
            match++;
        }
        if (shape[randomLeftTwo].equals(shape[left_two_idn])) {
            match++;
        }
        if (shape[randomRight].equals(shape[right_idn])) {
            match++;
        }
        if (shape[randomTop].equals(shape[top_idn])) {
            match++;
        }
        if (shape[randomTopTwo].equals(shape[top_two_idn])) {
            match++;
        }
        if (shape[randomBottom].equals(shape[bottom_idn])) {
            match++;
        }
        if (shape[randomBottomTwo].equals(shape[bottom_two_idn])) {
            match++;
        }
        if (shape[randomFront].equals(shape[front_idn])) {
            match++;
        }
        if (shape[randomBack].equals(shape[back_idn])) {
            match++;
        }
        return match;
    }

    public String leftScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 0.5) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String leftTwoScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 0.5) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String rightScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 0.75) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String topScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 1.0) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }


    public String topTwoScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 1.0) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String bottomScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 1.25) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String bottomTwoScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 1.25) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String frontScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 1.5) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String backScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 1.75) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

    public String rotationCheck(int shape, int angleX, int angleY, int angleZ) {

        // shape #:
        // 1 - SPHERE
        // 2 - CYLINDER
        // 3 - CONE
        // 4 - CUBE
        // 5 - CUBOID
        // 6 - TETRAHEDRON
        // 7 - RECTANGULAR_PYRAMID
        // 8 - PENTAGON_PYRAMID
        // 9 - HEXAGON_PYRAMID


        String text;
        if (shape == 1) {
            text = "correct";
        } else if (shape == 2) {
            // x and y
            if ((angleX == 0 || angleX == 360 || angleX == 180) && (angleY == 0 || angleY == 360 || angleY == 180)) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (shape == 6) {
            // x, y and z
            if ((angleX == 180 && angleY == 180 && angleZ == 180) ||
                    ((angleX == 0 || angleX == 360 || angleX == 180) &&
                            (angleY == 0 || angleY == 360 || angleY == 180)
                            && (angleZ == 0 || angleZ == 360))) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (shape == 3) {
            // x, y and z
            if ((angleX == 180 && angleY == 180) || ((angleX == 0 || angleX == 360) && (angleY == 0 || angleY == 360))) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (shape == 5) {
            // x, y and z
            if (((angleX == 0 || angleX == 360 || angleX == 180 || angleX == 270 || angleX == 90) &&
                    (angleY == 0 || angleY == 360 || angleY == 180) &&
                    (angleZ == 0 || angleZ == 360 || angleZ == 180))) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (shape == 4) {
            // x, y and z
            if (((angleX == 0 || angleX == 360 || angleX == 180 || angleX == 270 || angleX == 90) &&
                    (angleY == 0 || angleY == 360 || angleY == 180 || angleY == 270 || angleY == 90) &&
                    (angleZ == 0 || angleZ == 360 || angleZ == 180 || angleZ == 270 || angleZ == 90))) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (shape == 7) {
            // x, y and z
            if (((angleX == 0) &&
                    (angleY == 0 || angleY == 90 || angleY == 180 || angleY == 270 || angleY == 360) &&
                    (angleZ == 0 || angleZ == 360)) ||
                    ((angleX == 90) && (angleY == 90) && (angleZ == 90)) ||
                    ((angleX == 90) && (angleY == 270) && (angleZ == 270)) ||
                    ((angleX == 180) && (angleY == 0 || angleY == 90 || angleY == 180 || angleY == 270 || angleY == 360) && (angleZ == 180))) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (shape == 8) {
            // x, y and z
            if (((angleY == 0 || angleY == 360) &&
                    (angleX == 0 || angleX == 360) &&
                    (angleZ == 0 || angleZ == 360))) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (shape == 9) {
            // x, y and z
            if (((angleY == 0 || angleY == 360 || angleY == 180) &&
                    (angleX == 0 || angleX == 360) &&
                    (angleZ == 0 || angleZ == 360))) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else {
            text = "incorrect";
        }
        return text;
    }

    public static void main(String[] args) {
        new Exam();
    }
}
