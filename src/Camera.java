import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {

    private double eyex, eyey, eyez = 30;
    private double refx, refy, refz;
    private double upx, upy = 1, upz;

    private double xminRequested = -5, xmaxRequested = 5;
    private double yminRequested = -5, ymaxRequested = 5;
    private double zmin = -10, zmax = 10;
    private boolean orthographic;
    private boolean preserveAspect = true;

    private double xminActual, xmaxActual, yminActual, ymaxActual;
    private GLU glu = new GLU();

    public void setLimits(double xmin, double xmax, double ymin, double ymax, double zmin, double zmax) {
        xminRequested = xminActual = xmin;
        xmaxRequested = xmaxActual = xmax;
        yminRequested = yminActual = ymin;
        ymaxRequested = ymaxActual = ymax;
        this.zmin = zmin;
        this.zmax = zmax;
    }

    public void setScale(double limit) {
        setLimits(-limit,limit,-limit,limit,-2*limit,2*limit);
    }


    public double[] getLimits() {
        return new double[] { xminRequested, xmaxRequested, yminRequested, ymaxRequested, zmin, zmax };
    }


    public void lookAt(double eyeX, double eyeY, double eyeZ,
                       double viewCenterX, double viewCenterY, double viewCenterZ,
                       double viewUpX, double viewUpY, double viewUpZ) {
        eyex = eyeX;
        eyey = eyeY;
        eyez = eyeZ;
        refx = viewCenterX;
        refy = viewCenterY;
        refz = viewCenterZ;
        upx = viewUpX;
        upy = viewUpY;
        upz = viewUpZ;
    }

    public void apply(GL2 gl) {
        int[] viewport = new int[4];
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        xminActual = xminRequested;
        xmaxActual = xmaxRequested;
        yminActual = yminRequested;
        ymaxActual = ymaxRequested;
        if (preserveAspect) {
            double viewWidth = viewport[2];
            double viewHeight = viewport[3];
            double windowWidth = xmaxActual - xminActual;
            double windowHeight = ymaxActual - yminActual;
            double aspect = viewHeight / viewWidth;
            double desired = windowHeight / windowWidth;
            if (desired > aspect) { //expand width
                double extra = (desired / aspect - 1.0) * (xmaxActual - xminActual) / 2.0;
                xminActual -= extra;
                xmaxActual += extra;
            } else if (aspect > desired) {
                double extra = (aspect / desired - 1.0) * (ymaxActual - yminActual) / 2.0;
                yminActual -= extra;
                ymaxActual += extra;
            }
        }
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double viewDistance = norm(new double[] {refx-eyex, refy-eyey, refz-eyez});
        if (orthographic) {
            gl.glOrtho(xminActual, xmaxActual, yminActual, ymaxActual, viewDistance-zmax, viewDistance-zmin);
        }
        else {
            double near = viewDistance-zmax;
            if (near < 0.1)
                near = 0.1;
            double centerx = (xminActual + xmaxActual) / 2;
            double centery = (yminActual + ymaxActual) / 2;
            double newwidth = (near / viewDistance) * (xmaxActual - xminActual);
            double newheight = (near / viewDistance) * (ymaxActual - yminActual);
            double x1 = centerx - newwidth / 2;
            double x2 = centerx + newwidth / 2;
            double y1 = centery - newheight / 2;
            double y2 = centery + newheight / 2;
            gl.glFrustum(x1, x2, y1, y2, near, viewDistance-zmin);
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(eyex, eyey, eyez, refx, refy, refz, upx, upy, upz);
    }


    private double norm(double[] v) {
        double norm2 = v[0]*v[0] + v[1]*v[1] + v[2]*v[2];
        if (Double.isNaN(norm2) || Double.isInfinite(norm2) || norm2 == 0)
            throw new NumberFormatException("Vector length zero, undefined, or infinite.");
        return Math.sqrt(norm2);
    }

    private void normalize(double[] v) {
        double norm = norm(v);
        v[0] /= norm;
        v[1] /= norm;
        v[2] /= norm;
    }

    private void applyTransvection(double[] e1, double[] e2) {
        // rotate vector e1 onto e2; must be 3D *UNIT* vectors.
        double[] zDirection = new double[] {eyex - refx, eyey - refy, eyez - refz};
        double viewDistance = norm(zDirection);
        normalize(zDirection);
        double[] yDirection = new double[] {upx, upy, upz};
        double upLength = norm(yDirection);
        double proj = yDirection[0]*zDirection[0] + yDirection[1]*zDirection[1] + yDirection[2]*zDirection[2];
        yDirection[0] = yDirection[0] - proj*zDirection[0];
        yDirection[1] = yDirection[1] - proj*zDirection[1];
        yDirection[2] = yDirection[2] - proj*zDirection[2];
        normalize(yDirection);
        double[] xDirection = new double[] {yDirection[1]*zDirection[2] - yDirection[2]*zDirection[1],
                yDirection[2]*zDirection[0] - yDirection[0]*zDirection[2],
                yDirection[0]*zDirection[1] - yDirection[1]*zDirection[0] };
        e1 = transformToViewCoords(e1, xDirection, yDirection, zDirection);
        e2 = transformToViewCoords(e2, xDirection, yDirection, zDirection);
        double[] e = new double[]{e1[0] + e2[0], e1[1] + e2[1], e1[2] + e2[2]};
        normalize(e);
        double[] temp = new double[3];
        reflectInAxis(e, zDirection, temp);
        reflectInAxis(e1, temp, zDirection);
        reflectInAxis(e, xDirection, temp);
        reflectInAxis(e1, temp, xDirection);
        reflectInAxis(e, yDirection, temp);
        reflectInAxis(e1, temp, yDirection);
        eyex = refx + viewDistance * zDirection[0];
        eyey = refy + viewDistance * zDirection[1];
        eyez = refz + viewDistance * zDirection[2];
        upx = upLength * yDirection[0];
        upy = upLength * yDirection[1];
        upz = upLength * yDirection[2];
    }

    private void reflectInAxis(double[] axis, double[] source, double[] destination) {
        double s = 2 * (axis[0] * source[0] + axis[1] * source[1] + axis[2] * source[2]);
        destination[0] = s * axis[0] - source[0];
        destination[1] = s * axis[1] - source[1];
        destination[2] = s * axis[2] - source[2];
    }

    private double[] transformToViewCoords(double[] v, double[] x, double[] y, double[] z) {
        double[] w = new double[3];
        w[0] = v[0]*x[0] + v[1]*y[0] + v[2]*z[0];
        w[1] = v[0]*x[1] + v[1]*y[1] + v[2]*z[1];
        w[2] = v[0]*x[2] + v[1]*y[2] + v[2]*z[2];
        return w;
    }

}