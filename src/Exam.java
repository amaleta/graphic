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
import java.util.HashMap;
import java.util.Map;


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
    private int randomTopThree;
    private int randomRight;
    private int randomLeft;
    private int randomLeftTwo;
    private int randomBottom;
    private int randomBottomTwo;

    private int[] randomAngleOne;

    private int[] randomAngleTwo;
    private int[] randomAngleThree;


    private int nameID = 0; // name ID for picking

    // id from the palette shape inserted into the blueprint
    private int left_idn = 0;
    private int left_two_idn = 0;
    private int right_idn = 0;
    private int top_idn = 0;
    private int top_two_idn = 0;
    private int top_three_idn = 0;
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

    private float redT = defaultRed;
    private float greenT = defaultGreen;
    private float blueT = defaultBlue;

    private float redTopTwo = defaultRed;
    private float greenTopTwo = defaultGreen;
    private float blueTopTwo = defaultBlue;

    private float redTopThree = defaultRed;
    private float greenTopThree = defaultGreen;
    private float blueTopThree = defaultBlue;

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

    private double topThreeX;
    private double topThreeY;
    private double topThreeZ;

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
    private float scaleTopThree = 0.5f;
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

    private int angleTopX = 0;
    private int angleTopY = 0;
    private int angleTopZ = 0;

    private int angleTopTwoX = 0;
    private int angleTopTwoY = 0;
    private int angleTopTwoZ = 0;

    private int angleTopThreeX = 0;
    private int angleTopThreeY = 0;
    private int angleTopThreeZ = 0;

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

    private static final int TOP_ID = 1;
    private static final int TOP_TWO_ID = 2;
    private static final int TOP_THREE_ID = 3;

    // blueprint shapes
    private final static int
            MOUSE = 1,
            CHEESE = 2,
            MOUSE_TRAP = 3
    ;


    // palette shapes
    private final static int MOUSE_ID = 10;
    private final static int CHEESE_ID = 11;
    private final static int MOUSE_TRAP_ID = 12;
    private final static Map<Integer,String> shape = new HashMap<Integer,String>(){
        {
            put(MOUSE, "MOUSE");
            put(MOUSE_ID, "MOUSE");
            put(CHEESE, "CHEESE");
            put(CHEESE_ID, "CHEESE");
            put(MOUSE_TRAP, "MOUSE_TRAP");
            put(MOUSE_TRAP_ID, "MOUSE_TRAP");
        }
    };

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
            "wood3.png",
            "wood3.png",
            "mickeyframe.png",
            "background.jpg"
    };
    private Texture[] textures = new Texture[textureFileNames.length];

    private int baseAngleOfRotationX = 429;
    private int baseAngleOfRotationY = 293;
    private int baseAngleOfVisibleField = 55;


    private int currentAngleOfRotationX = baseAngleOfRotationX;
    private int currentAngleOfRotationY = baseAngleOfRotationY;
    private int currentAngleOfVisibleField = baseAngleOfVisibleField;

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
    private float scaleTopThreeShape;
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
                        top_idn = nameID;
                    } else if (travers == 2) {
                        top_two_idn = nameID;
                    } else if (travers == 3) {
                        top_three_idn = nameID;
                    }
                }
                addButton.setFocusable(false);
            });

            removeButton.addActionListener(e -> {
                if (e.getSource() == removeButton) {
                     if (travers == 1) {
                        top_idn = 0;
                    } else if (travers == 2) {
                        top_two_idn = 0;
                    }else if (travers == 3) {
                         top_three_idn = 0;
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
            camera.lookAt(-5, 10, 3, 0, 10, 10, 10, 10, 0);
            camera.setScale(100);

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
        camera.lookAt(10, -10, 5, 10, 10, 10, 10, 10, 10);
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
        for (int i = 1; i < 4; i++) {
            list.add(i);
        }

        Collections.shuffle(list);
        randomTop = list.get(0);
        randomTopTwo = list.get(1);
        randomTopThree = list.get(2);

        // angles
        randomAngleOne = new int[]{0, ((int) (Math.random() * 72) * 5), 0};
        randomAngleTwo = new int[]{0, ((int) (Math.random() * 72) * 5),0};
        randomAngleThree = new int[]{0, ((int) (Math.random() * 72) * 5),0};

        // default values

        currentAngleOfRotationX = baseAngleOfRotationX;
        currentAngleOfRotationY = baseAngleOfRotationY;
        currentAngleOfVisibleField = baseAngleOfVisibleField;

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
        top_three_idn = 0;
        bottom_idn = 0;
        bottom_two_idn = 0;
        front_idn = 0;
        back_idn = 0;
        addShapeRed = 1f;
        addShapeGreen = 102 / 255f;
        addShapeBlue = 0f;


        redT = defaultRed;
        greenT = defaultGreen;
        blueT = defaultBlue;

        redTopTwo = defaultRed;
        greenTopTwo = defaultGreen;
        blueTopTwo = defaultBlue;

        redTopThree = defaultRed;
        greenTopThree = defaultGreen;
        blueTopThree = defaultBlue;

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
        topX = -0.4;
        topY = 1.5;
        topZ = 0.5;

        scaleTopShape = 1;

        // TOP TWO
        topTwoX = 0.4;
        topTwoY = 1.5;
        topTwoZ = -0.7;

        scaleTopTwoShape = 1;

        // TOP Three
        topThreeX = -0.4;
        topThreeY = 1.5;
        topThreeZ = -2f;

        scaleTopThreeShape = 1;

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
        scaleTopThree = 0.5f;
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

        angleTopX = 0;
        angleTopY = 0;
        angleTopZ = 0;

        angleTopTwoX = 0;
        angleTopTwoY = 0;
        angleTopTwoZ = 0;

        angleTopThreeX = 0;
        angleTopThreeY = 0;
        angleTopThreeZ = 0;

        angleBottomX = 90;
        angleBottomY = 90;
        angleBottomZ = 90;

        angleFrontX = 90;
        angleFrontY = 90;
        angleFrontZ = 90;

        angleBackX = 90;
        angleBackY = 90;
        angleBackZ = 90;

        System.out.println("Top Shape created - id " + randomTop + ", x = " + randomAngleOne[0] + ", y = " + randomAngleOne[1] + ", z = " + randomAngleOne[2] + ", scale = " + scaleTopShape);
        System.out.println("Top Two Shape created - id  " + randomTopTwo + ", x = " + randomAngleTwo[0] + ", y = " + randomAngleTwo[1] + ", z = " + randomAngleTwo[2] + ", scale = " + scaleTopTwoShape);
        System.out.println("Top Three Shape created - id  " + randomTopThree + ", x = " + randomAngleThree[0] + ", y = " + randomAngleThree[1] + ", z = " + randomAngleThree[2] + ", scale = " + scaleTopThreeShape);
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

    public void addShape(GLAutoDrawable drawable, int nameID, int[] angles) {
        GL2 gl = drawable.getGL().getGL2();
        switch (nameID) {
            case CHEESE_ID:
                Shapes.cheese(gl, angles);
                break;
            case MOUSE_ID:
                Shapes.mouse(gl, angles);
                break;
            case MOUSE_TRAP_ID:
                Shapes.mouseTrap(gl, angles);
                break;
        }
    }


    private void addTopShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(topX, topY, topZ);
        gl.glScalef(scaleTop, scaleTop, scaleTop);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        addShape(drawable, nameID, new int[]{angleTopX, angleTopY, angleTopZ});
        gl.glPopMatrix();
    }

    private void addTopTwoShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(topTwoX, topTwoY, topTwoZ);
        gl.glScalef(scaleTopTwo, scaleTopTwo, scaleTopTwo);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        addShape(drawable, nameID, new int[]{angleTopTwoX, angleTopTwoY, angleTopTwoZ});
        gl.glPopMatrix();
    }

    private void addTopThreeShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(topThreeX, topThreeY, topThreeZ);
        gl.glScalef(scaleTopThree, scaleTopThree, scaleTopThree);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        addShape(drawable, nameID, new int[]{angleTopThreeX, angleTopThreeY, angleTopThreeZ});
        gl.glPopMatrix();
    }


    // The buffer is examined in processHits().

    private float getDepth(int offset) {
        long depth = (long) selectBuffer.get(offset); // large -ve number
        return (1.0f + ((float) depth / 0x7fffffff));
        // return as a float between 0 and 1
    } // end of getDepth()

    private String idToString(int nameID) {
        if (nameID == CHEESE_ID)
            return "cheese";
        else if (nameID == MOUSE_ID)
            return "mouse";
        // we should not reach this point
        return "nameID " + nameID;
    } // end of idToString()


    private void pickModels(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        startPicking(drawable);
        palettePicking(drawable);

        /*gl.glPushName(SPHERE_ID);
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
        gl.glPopName();*/

        gl.glPushName(CHEESE_ID);
        paletteCheese(drawable);
        gl.glPopName();


        gl.glPushName(MOUSE_ID);
        paletteMouse(drawable);
        gl.glPopName();

        gl.glPushName(MOUSE_TRAP_ID);
        paletteMouseTrap(drawable);
        gl.glPopName();

        gl.glPushMatrix();
        blueprintPicking(drawable);


        gl.glRotated(currentAngleOfRotationX, 1, 0, 0);
        gl.glRotated(currentAngleOfRotationY, 0, 1, 0);
        gl.glRotated(currentAngleOfVisibleField, 0, 0, 1);

        gl.glPushName(TOP_ID);
        drawTop(drawable);
        gl.glPopName();

        gl.glPushName(TOP_TWO_ID);
        drawTopTwo(drawable);
        gl.glPopName();

        gl.glPushName(TOP_THREE_ID);
        drawTopThree(drawable);
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

        paletteMouse(drawable);
        paletteCheese(drawable);
        paletteMouseTrap(drawable);


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


    private void paletteCheese(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, 2.5f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.cheese(gl, new int[]{0, 0, 0});
        gl.glPopMatrix();
    }

    private void paletteMouse(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        gl.glPushMatrix();
        gl.glTranslated(-3.5f, -4.5f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);


        Shapes.mouse(gl, new int[]{0, 0, 0});
        gl.glPopMatrix();
    }
    private void paletteMouseTrap(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        gl.glPushMatrix();
        gl.glTranslated(-3.5f, 0f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);


        Shapes.mouseTrap(gl, new int[]{0, 0, 0});
        gl.glPopMatrix();
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
        gl.glRotated(currentAngleOfVisibleField, 0, 0, 1);

        gl.glColor3f(1, 1, 1);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textures[0].bind(gl);  // Says which texture to use.

        Shapes.cuboidBox(gl);
        gl.glDisable(GL.GL_TEXTURE_2D);

        drawTop(drawable);
        drawTopTwo(drawable);
        drawTopThree(drawable);

        addTopShape(drawable, top_idn);
        addTopTwoShape(drawable, top_two_idn);
        addTopThreeShape(drawable, top_three_idn);

        gl.glPopMatrix();
    }

    private void drawShape(GLAutoDrawable drawable, int randomShape, int[] angles) {
        GL2 gl = drawable.getGL().getGL2();

        switch (randomShape) {
            case MOUSE:
                Shapes.mouse(gl, angles);
                break;
            case CHEESE:
                Shapes.cheese(gl, angles);
                break;
            case MOUSE_TRAP:
                Shapes.mouseTrap(gl, angles);
                break;
        }
    }


    private void drawTop(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redT, greenT, blueT);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(topX, topY, topZ);
        gl.glScalef(scaleTopShape, scaleTopShape, scaleTopShape);
        drawShape(drawable, randomTop, randomAngleOne);
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
        drawShape(drawable, randomTopTwo, randomAngleTwo);
        gl.glPopMatrix();
    }

    private void drawTopThree(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redTopThree, greenTopThree, blueTopThree);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(topThreeX, topThreeY, topThreeZ);
        gl.glScalef(scaleTopThreeShape, scaleTopThreeShape, scaleTopThreeShape);
        drawShape(drawable, randomTopThree, randomAngleThree);
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

            case TOP_ID:
                redT = 1;
                redTopTwo = 0;
                redTopThree=0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;

            case TOP_TWO_ID:
                redT = 0;
                redTopTwo = 1;
                redTopThree=0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case TOP_THREE_ID:
                redT = 0;
                redTopTwo = 0;
                redTopThree=1;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            // X-rotation
            case KeyEvent.VK_2:

                 if (travers == 1) {
                    if (angleTopX < 360) {
                        angleTopX += angleDelta;
                        System.out.println(angleTopX+ ", random = " + randomAngleOne[0]);
                    }
                } else if (travers == 2) {
                    if (angleTopTwoX < 360) {
                        angleTopTwoX += angleDelta;
                        System.out.println(angleTopTwoX+ ", random = " + randomAngleTwo[0]);
                    }
                } else if (travers == 3) {
                    if (angleTopThreeX < 360) {
                        angleTopThreeX += angleDelta;
                        System.out.println(angleTopThreeX+ ", random = " + randomAngleThree[0]);
                    }
                }
                break;
            case KeyEvent.VK_1:

                if (travers == 1) {
                    if (angleTopX > 0) {
                        angleTopX -= angleDelta;
                        System.out.println(angleTopX+ ", random = " + randomAngleOne[0]);
                    }
                } else if (travers == 2) {
                    if (angleTopTwoX > 0) {
                        angleTopTwoX -= angleDelta;
                        System.out.println(angleTopTwoX+ ", random = " + randomAngleTwo[0]);
                    }
                } else if (travers == 3) {
                    if (angleTopThreeX > 0) {
                        angleTopThreeX -= angleDelta;
                        System.out.println(angleTopThreeX+ ", random = " + randomAngleThree[0]);
                    }
                }

                break;

            // Y-rotation
            case KeyEvent.VK_4:

                 if (travers == 1) {
                    if (angleTopY < 360) {
                        angleTopY += angleDelta;
                        System.out.println(angleTopY + ", random = " + randomAngleOne[1]);
                    }
                } else if (travers == 2) {
                    if (angleTopTwoY < 360) {
                        angleTopTwoY += angleDelta;
                        System.out.println(angleTopTwoY + ", random = " + randomAngleTwo[1]);
                    }
                } else if (travers == 3) {
                    if (angleTopThreeY < 360) {
                        angleTopThreeY += angleDelta;
                        System.out.println(angleTopThreeY + ", random = " + randomAngleThree[1]);
                    }
                }
                break;
            case KeyEvent.VK_3:

                if (travers == 1) {
                    if (angleTopY > 0) {
                        angleTopY -= angleDelta;
                        System.out.println(angleTopY+ ", random = " + randomAngleOne[1]);
                    }
                } else if (travers == 2) {
                    if (angleTopTwoY > 0) {
                        angleTopTwoY -= angleDelta;
                        System.out.println(angleTopTwoY+ ", random = " + randomAngleTwo[1]);
                    }
                }else if (travers == 3) {
                    if (angleTopThreeY > 0) {
                        angleTopThreeY -= angleDelta;
                        System.out.println(angleTopThreeY + ", random = " + randomAngleThree[1]);
                    }
                }

                break;

            // Z-rotation
            case KeyEvent.VK_6:

                if (travers == 1) {
                    if (angleTopZ < 360) {
                        angleTopZ += angleDelta;
                        System.out.println(angleTopZ + ", random = " + randomAngleOne[2]);
                    }
                } else if (travers == 2) {
                    if (angleTopTwoZ < 360) {
                        angleTopTwoZ += angleDelta;
                        System.out.println(angleTopTwoZ + ", random = " + randomAngleTwo[2]);
                    }
                } else if (travers == 3) {
                    if (angleTopThreeZ < 360) {
                        angleTopThreeZ += angleDelta;
                        System.out.println(angleTopThreeZ + ", random = " + randomAngleThree[2]);
                    }
                }
                break;
            case KeyEvent.VK_5:
                if (travers == 1) {
                    if (angleTopZ > 0) {
                        angleTopZ -= angleDelta;
                        System.out.println(angleTopZ+ ", random = " + randomAngleOne[2]);
                    }
                } else if (travers == 2) {
                    if (angleTopTwoZ > 0) {
                        angleTopTwoZ -= angleDelta;
                        System.out.println(angleTopTwoZ+ ", random = " + randomAngleTwo[2]);
                    }
                } else if (travers == 3) {
                    if (angleTopThreeZ > 0) {
                        angleTopThreeZ -= angleDelta;
                        System.out.println(angleTopThreeZ + ", random = " + randomAngleThree[2]);
                    }
                }

                break;

            case KeyEvent.VK_S:
                if (travers == 1) {
                    scaleTop += scaleDelta;
                    System.out.println("top " + scaleTop);
                } else if (travers == 2) {
                    scaleTopTwo += scaleDelta;
                    System.out.println("top2 " + scaleTopTwo);
                } else if (travers == 3) {
                    scaleTopThree += scaleDelta;
                    System.out.println("top3 " + scaleTopThree);
                }

                break;
            case KeyEvent.VK_A:
                System.out.println(travers);
                 if (travers == 1) {
                    scaleTop -= scaleDelta;
                } else if (travers == 2) {
                    scaleTopTwo -= scaleDelta;
                } else if (travers == 3) {
                    scaleTopThree -= scaleDelta;
                }
                break;

            case KeyEvent.VK_W:
                travers++;
                colorShape(travers);
                if (travers == 4) {
                    travers = 0;
                }
                break;
            case KeyEvent.VK_UP:
                currentAngleOfRotationX--;
                break;
            case KeyEvent.VK_DOWN:
                currentAngleOfRotationX++;
                //  System.out.println("x " + currentAngleOfRotationX);
                break;
            case KeyEvent.VK_LEFT:
                currentAngleOfRotationY++;
                //   System.out.println("y " + currentAngleOfRotationY);
                break;
            case KeyEvent.VK_RIGHT:
                currentAngleOfRotationY--;

                break;
            case KeyEvent.VK_OPEN_BRACKET:
                if (currentAngleOfVisibleField > 1) {
                    currentAngleOfVisibleField--;
                }
                //   System.out.println("z " + currentAngleOfVisibleField);
                break;
            case KeyEvent.VK_CLOSE_BRACKET:
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
            if (checkShapes(randomTop,top_idn))
                if (topScaleCheck(scaleTop).equals("appropriate") && rotationCheck(1, angleTopX, angleTopY, angleTopZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        } else if (travers == 2) {
            if (checkShapes(randomTopTwo,top_two_idn))
                if (topTwoScaleCheck(scaleTopTwo).equals("appropriate") && rotationCheck(2, angleTopTwoX, angleTopTwoY, angleTopTwoZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        }else if (travers == 3) {
            if (checkShapes(randomTopThree,top_three_idn))
                if (topThreeScaleCheck(scaleTopThree).equals("appropriate") && rotationCheck(3, angleTopThreeX, angleTopThreeY, angleTopThreeZ).equals("correct")) {
                    writeMatch("Well Done! Correct shape, rotation and scaling.",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        }
    }

    private  boolean checkShapes(int shapeNum, int shapeId) {
        return shape.get(shapeNum).equals(shape.get(shapeId));
    }

    public void printResult(GLAutoDrawable drawable) {
        writeText("RESULT: " + matchedShape() + "/3 shape matched correctly", (int) (windowWidth / 3.5f), windowHeight - 40);

        writeText("Top One: blueprint shape: " +
                        shape.get(randomTop) +
                        " - matched shape: " +
                        shape.get(top_idn) + " Scaling: " +
                        topScaleCheck(scaleTop) +
                        " Rotation: " + rotationCheck(randomTop, angleTopX, angleTopY, angleTopZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 60);

        writeText("Top Two: blueprint shape: " +
                        shape.get(randomTopTwo) +
                        " - matched shape: " +
                        shape.get(top_two_idn) + " Scaling: " +
                        topTwoScaleCheck(scaleTopTwo) +
                        " Rotation: " + rotationCheck(randomTopTwo, angleTopTwoX, angleTopTwoY, angleTopTwoZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 80);
        writeText("Top Two: blueprint shape: " +
                        shape.get(randomTopThree) +
                        " - matched shape: " +
                        shape.get(top_three_idn) + " Scaling: " +
                        topThreeScaleCheck(scaleTopThree) +
                        " Rotation: " + rotationCheck(randomTopThree, angleTopThreeX, angleTopThreeY, angleTopThreeZ),
                (int) (windowWidth / 3.5f),
                windowHeight - 100);

    }

    private int matchedShape() {

        int match = 0;
        if (checkShapes(randomTop,top_idn)) {
            match++;
        }
        if (checkShapes(randomTopTwo,top_two_idn)) {
            match++;
        }
        if (checkShapes(randomTopThree,top_three_idn)) {
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

    public String topThreeScaleCheck(double scale) {

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

    public String rotationCheck(int top, int angleX, int angleY, int angleZ) {

        // shape #:
        // 1 - MOUSE
        // 2 - CHEESE
        // 3 - CONE
        // 4 - CUBE
        // 5 - CUBOID
        // 6 - TETRAHEDRON
        // 7 - RECTANGULAR_PYRAMID
        // 8 - PENTAGON_PYRAMID
        // 9 - HEXAGON_PYRAMID


        String text;
        if (top == 1) {
            // x, y and z
            if (randomAngleOne[0] == angleX && angleY == randomAngleOne[1] && angleZ == randomAngleOne[2]) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (top == 2) {
            // x, y and z
            if (randomAngleTwo[0] == angleX && angleY == randomAngleTwo[1] && angleZ == randomAngleTwo[2]) {
                text = "correct";
            } else {
                text = "incorrect";
            }
        } else if (top == 3) {
            // x, y and z
            if (randomAngleThree[0] == angleX && angleY == randomAngleThree[1] && angleZ == randomAngleThree[2]) {
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
