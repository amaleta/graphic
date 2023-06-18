import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Shapes {

    private static GLUT glut = new GLUT();
    private static GLU glu = new GLU();


    public static void mouse(GL2 gl, int[] angles) {
        renderMouse(gl, angles);
    }

    public static void cheese(GL2 gl, int[] angles) {
        renderCheese(gl, angles);
    }

    public static void mouseTrap(GL2 gl, int[] angles) {
        renderMouseTrap(gl, angles);
    }

    public static void cube(GL2 gl) {
        cube(gl, 1, true);
    }

    public static void cuboid(GL2 gl) {
        cuboid(gl, 1, true);
    }

    public static void tetrahedron(GL2 gl) {
        tetrahedron(gl, 1, true);
    }

    public static void rectangularPyramid(GL2 gl) {
        rectangularPyramid(gl, 1, true);
    }

    public static void pentagonPyramid(GL2 gl) {
        pentagonPyramid(gl, 1, true);
    }

    public static void hexagonPyramid(GL2 gl) {
        hexagonPyramid(gl, 1, true);
    }


    public static void square(GL2 gl) {
        square(gl, 1, true);
    }

    public static void uvSphere(GL2 gl) {
        uvSphere(gl, 0.5, 16, 16, true);
    }

    public static void uvCylinder(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslated(0, 0, -0.5);
        uvCylinder(gl, 0.5, 1, 16, 10, 5, true);
        gl.glPopMatrix();
    }

    public static void uvCone(GL2 gl) {
        gl.glTranslated(0, 0, -0.5);
        uvCone(gl, 0.5, 1, 16, 10, 5, true);
    }


    public static void uvSphere(GL2 gl, double radius, int slices, int stacks, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2)
            throw new IllegalArgumentException("Number of stacks must be at least 2.");
        for (int j = 0; j < stacks; j++) {
            double latitude1 = (Math.PI / stacks) * j - Math.PI / 2;
            double latitude2 = (Math.PI / stacks) * (j + 1) - Math.PI / 2;
            double sinLat1 = Math.sin(latitude1);
            double cosLat1 = Math.cos(latitude1);
            double sinLat2 = Math.sin(latitude2);
            double cosLat2 = Math.cos(latitude2);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2 * Math.PI / slices) * i;
                double sinLong = Math.sin(longitude);
                double cosLong = Math.cos(longitude);
                double x1 = cosLong * cosLat1;
                double y1 = sinLong * cosLat1;
                double z1 = sinLat1;
                double x2 = cosLong * cosLat2;
                double y2 = sinLong * cosLat2;
                double z2 = sinLat2;
                gl.glNormal3d(x2, y2, z2);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * (j + 1));
                gl.glVertex3d(radius * x2, radius * y2, radius * z2);
                gl.glNormal3d(x1, y1, z1);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * j);
                gl.glVertex3d(radius * x1, radius * y1, radius * z1);
            }
            gl.glEnd();
        }
    }

    public static void uvCylinder(GL2 gl, double radius, double height,
                                  int slices, int stacks, int rings, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        if (height <= 0)
            throw new IllegalArgumentException("Height must be positive.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2)
            throw new IllegalArgumentException("Number of stacks must be at least 2.");
        for (int j = 0; j < stacks; j++) {
            double z1 = (height / stacks) * j;
            double z2 = (height / stacks) * (j + 1);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2 * Math.PI / slices) * i;
                double sinLong = Math.sin(longitude);
                double cosLong = Math.cos(longitude);
                double x = cosLong;
                double y = sinLong;
                gl.glNormal3d(x, y, 0);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * (j + 1));
                gl.glVertex3d(radius * x, radius * y, z2);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * j);
                gl.glVertex3d(radius * x, radius * y, z1);
            }
            gl.glEnd();
        }
        if (rings > 0) { // draw top and bottom
            gl.glNormal3d(0, 0, 1);
            for (int j = 0; j < rings; j++) {
                double d1 = (1.0 / rings) * j;
                double d2 = (1.0 / rings) * (j + 1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {
                    double angle = (2 * Math.PI / slices) * i;
                    double sin = Math.sin(angle);
                    double cos = Math.cos(angle);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5 * (1 + cos * d1), 0.5 * (1 + sin * d1));
                    gl.glVertex3d(radius * cos * d1, radius * sin * d1, height);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5 * (1 + cos * d2), 0.5 * (1 + sin * d2));
                    gl.glVertex3d(radius * cos * d2, radius * sin * d2, height);
                }
                gl.glEnd();
            }
            gl.glNormal3d(0, 0, -1);
            for (int j = 0; j < rings; j++) {
                double d1 = (1.0 / rings) * j;
                double d2 = (1.0 / rings) * (j + 1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {
                    double angle = (2 * Math.PI / slices) * i;
                    double sin = Math.sin(angle);
                    double cos = Math.cos(angle);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5 * (1 + cos * d2), 0.5 * (1 + sin * d2));
                    gl.glVertex3d(radius * cos * d2, radius * sin * d2, 0);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5 * (1 + cos * d1), 0.5 * (1 + sin * d1));
                    gl.glVertex3d(radius * cos * d1, radius * sin * d1, 0);
                }
                gl.glEnd();
            }
        }
    }

    public static void uvCone(GL2 gl, double radius, double height,
                              int slices, int stacks, int rings, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        if (height <= 0)
            throw new IllegalArgumentException("Height must be positive.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2)
            throw new IllegalArgumentException("Number of stacks must be at least 2.");
        for (int j = 0; j < stacks; j++) {
            double z1 = (height / stacks) * j;
            double z2 = (height / stacks) * (j + 1);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2 * Math.PI / slices) * i;
                double sinLong = Math.sin(longitude);
                double cosLong = Math.cos(longitude);
                double x = cosLong;
                double y = sinLong;
                double nz = radius / height;
                double normLength = Math.sqrt(x * x + y * y + nz * nz);
                gl.glNormal3d(x / normLength, y / normLength, nz / normLength);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * (j + 1));
                gl.glVertex3d((height - z2) / height * radius * x, (height - z2) / height * radius * y, z2);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * j);
                gl.glVertex3d((height - z1) / height * radius * x, (height - z1) / height * radius * y, z1);
            }
            gl.glEnd();
        }
        if (rings > 0) {
            gl.glNormal3d(0, 0, -1);
            for (int j = 0; j < rings; j++) {
                double d1 = (1.0 / rings) * j;
                double d2 = (1.0 / rings) * (j + 1);
                gl.glBegin(GL2.GL_QUAD_STRIP);
                for (int i = 0; i <= slices; i++) {
                    double angle = (2 * Math.PI / slices) * i;
                    double sin = Math.sin(angle);
                    double cos = Math.cos(angle);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5 * (1 + cos * d2), 0.5 * (1 + sin * d2));
                    gl.glVertex3d(radius * cos * d2, radius * sin * d2, 0);
                    if (makeTexCoords)
                        gl.glTexCoord2d(0.5 * (1 + cos * d1), 0.5 * (1 + sin * d1));
                    gl.glVertex3d(radius * cos * d1, radius * sin * d1, 0);
                }
                gl.glEnd();
            }
        }
    }

    public static void cube(GL2 gl, double side, boolean makeTexCoords) {
        gl.glPushMatrix();
        gl.glRotatef(-90, -1, 0, 0);  // This puts the textures in the orientation I want.
        gl.glPushMatrix();
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);  // Each side of the cube is a transformed square.
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(270, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(90, -1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(-90, -1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }


    public static void cuboid(GL2 gl, double side, boolean makeTexCoords) {
        gl.glPushMatrix();
        gl.glScalef(2, 1f, 1);
        gl.glRotatef(-90, -1, 0, 0);  // This puts the textures in the orientation I want.
        gl.glPushMatrix();
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);  // Each side of the cube is a transformed square.
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(270, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(90, -1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(-90, -1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }


    public static void square(GL2 gl, double side, boolean makeTexCoords) {
        double radius = side / 2;
        gl.glBegin(GL2.GL_POLYGON);
        gl.glNormal3f(0, 0, 1);
        if (makeTexCoords)
            gl.glTexCoord2d(0, 1);
        gl.glVertex2d(-radius, radius);
        if (makeTexCoords)
            gl.glTexCoord2d(0, 0);
        gl.glVertex2d(-radius, -radius);
        if (makeTexCoords)
            gl.glTexCoord2d(1, 0);
        gl.glVertex2d(radius, -radius);
        if (makeTexCoords)
            gl.glTexCoord2d(1, 1);
        gl.glVertex2d(radius, radius);
        gl.glEnd();
    }

    public static void cuboidBox(GL2 gl) {
        float scale = 2f;

        gl.glColor3f(0.400f, 0.140f, 0.100f);
        // Draw the front face (rectangle)
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f * scale, 0.3f * scale, 0.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.3f * scale, 0.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.5f * scale, 0.5f * scale);
        gl.glVertex3f(-0.5f * scale, 0.5f * scale, 0.5f * scale);
        gl.glEnd();

        // Draw the back face (rectangle)
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f * scale, 0.3f * scale, -1.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.3f * scale, -0.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.5f * scale, -1.5f * scale);
        gl.glVertex3f(-0.5f * scale, 0.5f * scale, -1.5f * scale);
        gl.glEnd();

        // Draw the left face (rectangle)
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f * scale, 0.3f * scale, -1.5f * scale);
        gl.glVertex3f(-0.5f * scale, 0.3f * scale, 0.5f * scale);
        gl.glVertex3f(-0.5f * scale, 0.5f * scale, 0.5f * scale);
        gl.glVertex3f(-0.5f * scale, 0.5f * scale, -1.5f * scale);
        gl.glEnd();

        // Draw the right face (square)
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(0.5f * scale, 0.3f * scale, -1.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.3f * scale, 0.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.5f * scale, 0.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.5f * scale, -1.5f * scale);
        gl.glEnd();

        // Draw the top face (rectangle)
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f * scale, 0.5f * scale, -1.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.5f * scale, -1.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.5f * scale, 0.5f * scale);
        gl.glVertex3f(-0.5f * scale, 0.5f * scale, 0.5f * scale);
        gl.glEnd();

        // Draw the bottom face (square)
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f * scale, 0.3f * scale, -1.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.3f * scale, -1.5f * scale);
        gl.glVertex3f(0.5f * scale, 0.3f * scale, 0.5f * scale);
        gl.glVertex3f(-0.5f * scale, 0.3f * scale, 0.5f * scale);
        gl.glEnd();
    }

    public static void tetrahedron(GL2 gl, double side, boolean makeTexCoords) {
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);

        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.0f, -0.5f, -0.5f);

        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glVertex3f(0.0f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);

        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.0f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);

        gl.glEnd();
    }


    public static void rectangularPyramid(GL2 gl, double side, boolean makeTexCoords) {
        gl.glPushMatrix();
        gl.glBegin(GL2.GL_TRIANGLES);           // Begin drawing the pyramid with 4 triangles
        // Front
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);

        // Right
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0.5f, -0.5f, -0.5f);

        // Back
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);

        // Left
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glEnd();
        gl.glPopMatrix();


        gl.glPushMatrix();
        gl.glBegin(GL2.GL_QUADS);

        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);

        gl.glEnd();
        gl.glPopMatrix();


    }


    public static void pentagonPyramid(GL2 gl, double side, boolean makeTexCoords) {
        gl.glPushMatrix();
        gl.glBegin(GL2.GL_TRIANGLES);
        // 1
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(-0.25f, -0.5f, 0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0.25f, -0.5f, 0.5f);

        // 2
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.25f, -0.5f, 0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0.5f, -0.5f, -0.25f);

        // 3
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.5f, -0.5f, -0.25f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0.0f, -0.5f, -0.5f);

        // 4
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, -0.5f, -0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(-0.5f, -0.5f, -0.25f);

        // 5
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(-0.5f, -0.5f, -0.25f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(-0.25f, -0.5f, 0.5f);

        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(-0.25f, -0.5f, 0.5f);
        gl.glVertex3f(0.25f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, -0.25f);
        gl.glVertex3f(0.0f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, -0.25f);
        gl.glEnd();
        gl.glPopMatrix();

    }

    public static void hexagonPyramid(GL2 gl, double side, boolean makeTexCoords) {

        gl.glPushMatrix();
        gl.glBegin(GL2.GL_TRIANGLES);


        // 1
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(-0.25f, -0.5f, 0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0.25f, -0.5f, 0.5f);

        // 2
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(0.25f, -0.5f, 0.5f);
        gl.glNormal3f(0, 0, -1.0f);
        gl.glVertex3f(0.5f, -0.5f, 0.0f);

        // 3
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(0.5f, -0.5f, 0.0f);
        gl.glNormal3f(0, 0, -1.0f);
        gl.glVertex3f(0.25f, -0.5f, -0.5f);

        // 4
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1.0f);
        gl.glVertex3f(0.25f, -0.5f, -0.5f);
        gl.glNormal3f(0, 0, -1.0f);
        gl.glVertex3f(-0.25f, -0.5f, -0.5f);

        // 5
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(-0.25f, -0.5f, -0.5f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(-0.5f, -0.5f, 0f);

        // 6
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.5f, 0.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(-0.5f, -0.5f, 0f);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(-0.25f, -0.5f, 0.5f);


        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glBegin(GL2.GL_POLYGON);

        gl.glVertex3f(-0.25f, -0.5f, 0.5f);
        gl.glVertex3f(0.25f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.0f);
        gl.glVertex3f(0.25f, -0.5f, -0.5f);
        gl.glVertex3f(-0.25f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, 0.0f);

        gl.glEnd();
        gl.glPopMatrix();

    }

    private static void renderMouseTrap(GL2 gl, int[] angles) {
        float scale = 1f;

        // Set the color of the mouse trap

        // Draw the base
        gl.glRotatef(angles[0], 1, 0, 0);
        gl.glRotatef(angles[1], 0, 1, 0);
        gl.glRotatef(angles[2], 0, 0, 1);


        // Clear The Screen And The Depth Buffe

        // Base of the trap (rectangle)
        // Base of the trap (3D rectangle)
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.45f, 0.0f);
        gl.glScalef(1.0f, 0.1f, 0.5f); // Scale to make it look like a flat rectangle
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();

        // Spring mechanism (cylinder)
        GLUquadric quad = glu.gluNewQuadric();
        gl.glTranslatef(0.0f, -0.35f, 0.0f); // Position the spring on top of the base
        glu.gluCylinder(quad, 0.05f, 0.05f, 0.2f, 10, 10);


        // Cheese platform (small 3D rectangle)
        gl.glPushMatrix();
        gl.glTranslatef(0.25f, 0f, 0f); // Position the cheese platform on top of the spring
        gl.glScalef(0.2f, 0.05f, 0.2f); // Scale to make it look like a small flat rectangle
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();

        //1// Grey color
        gl.glPushMatrix();
        gl.glRotatef(1 * 90.0f, 0.0f, 1.0f, 0.0f);
        gl.glRotatef( 90f, 0f, 0f, 1f); // Rotate to each side
        gl.glTranslatef(0.0f, 0f, 0.05f); // Position on the edge of the base
        gl.glScalef(0.01f, 0.4f, 0.01f); // Scale to make it look like a thin tall rectangle
        glut.glutSolidCube(1.0f); // Draw rectangle
        gl.glPopMatrix();
        //2
        gl.glPushMatrix();
        gl.glRotatef(2 * 90.0f, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(70, 0.0f, 0f, -1.0f);// Rotate to each side
        gl.glTranslatef(0f, 0.25f, 0.2f); // Position on the edge of the base
        gl.glScalef(0.01f, 0.6f, 0.01f);  // Scale to make it look like a thin tall rectangle
        glut.glutSolidCube(1.0f); // Draw rectangle
        gl.glPopMatrix();
        gl.glPushMatrix();

        //3
        gl.glRotatef(3 * 90.0f, 0.0f, 1.0f, 0.0f);
        gl.glRotatef( 90f, 0f, 0f, 1f);
        // Rotate to each side
        gl.glTranslatef(0.20f, 0.00f, 0.53f);
        gl.glScalef(0.01f, 0.4f, 0.01f);
        glut.glutSolidCube(1.0f); // Draw rectangle
        gl.glPopMatrix();
        gl.glPushMatrix();

        //4
        gl.glRotatef(0, 0f, 1.0f, 0.0f);
        gl.glRotatef( 70.0f, 0f, 0f, 1f);// Rotate to each side
        gl.glTranslatef(0f, 0.25f, 0.2f); // Position on the edge of the base
        gl.glScalef(0.01f, 0.6f, 0.01f); // Scale to make it look like a thin tall rectangle
        glut.glutSolidCube(1.0f); // Draw rectangle
        gl.glPopMatrix();


    }

    private static void renderCheese(GL2 gl, int[] angles) {
        float scale = 1f;
        // Set the color of the cheese (yellowish-orange)
        //   gl.glColor3f(1.0f, 0.78f, 0.0f);

        gl.glRotatef(angles[0], 1, 0, 0);
        gl.glRotatef(angles[1], 0, 1, 0);
        gl.glRotatef(angles[2], 0, 0, 1);

        // Front face
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f(-0.5f * scale, -0.5f * scale, 0.5f * scale);
        gl.glVertex3f(0.5f * scale, -0.5f * scale, 0.5f * scale);
        gl.glVertex3f(0.0f, 0.5f * scale, 0.5f * scale);
        gl.glEnd();

        // Back face
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f(-0.5f * scale, -0.5f * scale, -0.5f * scale);
        gl.glVertex3f(0.5f * scale, -0.5f * scale, -0.5f * scale);
        gl.glVertex3f(0.0f, 0.5f * scale, -0.5f * scale);
        gl.glEnd();

        // Side faces
        gl.glBegin(GL2.GL_QUADS);
        // Side 1
        gl.glVertex3f(-0.5f * scale, -0.5f * scale, 0.5f * scale);
        gl.glVertex3f(-0.5f * scale, -0.5f * scale, -0.5f * scale);
        gl.glVertex3f(0.0f, 0.5f * scale, -0.5f * scale);
        gl.glVertex3f(0.0f, 0.5f * scale, 0.5f * scale);
        // Side 2
        gl.glVertex3f(0.5f * scale, -0.5f * scale, 0.5f * scale);
        gl.glVertex3f(0.5f * scale, -0.5f * scale, -0.5f * scale);
        gl.glVertex3f(0.0f, 0.5f * scale, -0.5f * scale);
        gl.glVertex3f(0.0f, 0.5f * scale, 0.5f * scale);
        gl.glEnd();

        // Side faces with holes
        //      gl.glColor3f(1.0f, 0.78f, 0.0f); // Color for the holes
        float radius = 0.25f * scale / 2; // Radius of the holes
        int numHoles = 6; // Number of holes
        float angleStep = 360.0f / numHoles;
        float yPos = -0.25f * scale; // Y position of the holes
        for (int i = 0; i < numHoles; i++) {
            float angle = i * angleStep;
            float xPos = (float) Math.cos(Math.toRadians(angle)) * 0.25f * scale; // X position of the holes
            float zPos = (float) Math.sin(Math.toRadians(angle)) * 0.25f * scale; // Z position of the holes

            gl.glPushMatrix();
            gl.glTranslatef(xPos, yPos, zPos);
            glut.glutSolidSphere(radius, 20, 20);
            gl.glPopMatrix();

        }
        for (int i = 0; i < numHoles / 2; i++) {
            float angle = i * angleStep * 2 + 30;
            float xPos = (float) Math.cos(Math.toRadians(angle)) * 0.15f * scale; // X position of the holes
            float zPos = (float) Math.sin(Math.toRadians(angle)) * 0.15f * scale; // Z position of the holes

            gl.glPushMatrix();
            gl.glTranslatef(xPos, yPos + 0.3f, zPos);
            glut.glutSolidSphere(radius, 20, 20);
            gl.glPopMatrix();
        }

        float xPos = (float) Math.cos(Math.toRadians(0)) * 0f * scale; // X position of the holes
        float zPos = (float) Math.sin(Math.toRadians(0)) * 0f * scale; // Z position of the holes

        gl.glPushMatrix();
        gl.glTranslatef(xPos, yPos + 0.5f, zPos);
        glut.glutSolidSphere(radius, 20, 20);


        gl.glPopMatrix();
    }

    private static void renderMouse(GL2 gl, int[] angles) {
        // Draw the body
        gl.glRotatef(angles[0], 1, 0, 0);
        gl.glRotatef(angles[1], 0, 1, 0);
        gl.glRotatef(angles[2], 0, 0, 1);

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glScalef(0.5f, 0.25f, 0.25f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        // Draw the head
        gl.glPushMatrix();
        gl.glTranslatef(0.5f, 0.0f, 0.0f);
        gl.glScalef(0.25f, 0.25f, 0.25f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        // Draw the tail
        gl.glPushMatrix();
        gl.glTranslatef(-0.65f, 0.0f, 0.0f);
        gl.glScalef(0.2f, 0.05f, 0.05f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        // Draw the ears
        gl.glPushMatrix();
        gl.glTranslatef(0.5f, 0.25f, -0.25f);
        gl.glScalef(0.1f, 0.1f, 0.1f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.5f, 0.25f, 0.25f);
        gl.glScalef(0.1f, 0.1f, 0.1f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        // Draw the tongue
        gl.glPushMatrix();
        gl.glTranslatef(0.7f, -0.2f, 0f);
        gl.glScalef(0.1f, 0.05f, 0.05f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        // Draw the eyes
        gl.glPushMatrix();
        gl.glTranslatef(0.7f, 0.15f, 0.1f);/////
        gl.glScalef(0.05f, 0.05f, 0.05f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.7f, 0.15f, -0.1f);//////
        gl.glScalef(0.05f, 0.05f, 0.05f);
        glut.glutSolidSphere(1.0, 20, 20);
        gl.glPopMatrix();

        // Draw the legs
        gl.glPushMatrix();
        gl.glTranslatef(-0.25f, -0.3f, 0.15f);
        gl.glScalef(0.05f, 0.3f, 0.05f);
        glut.glutSolidCube(1.3f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(-0.25f, -0.3f, -0.15f);
        gl.glScalef(0.05f, 0.3f, 0.05f);
        glut.glutSolidCube(1.3f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.25f, -0.3f, 0.15f);
        gl.glScalef(0.05f, 0.3f, 0.05f);
        glut.glutSolidCube(1.3f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.25f, -0.3f, -0.15f);
        gl.glScalef(0.05f, 0.3f, 0.05f);
        glut.glutSolidCube(1.3f);
        gl.glPopMatrix();
    }
}