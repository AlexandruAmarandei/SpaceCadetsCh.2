
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TextApplet extends Applet implements ActionListener {

    static final int N = 100;
    public static final int FRAME_PERIOD = 100;
    public Timer timer;
    int counter = 0;
    Button button1 = new Button("-");
    public int w, h, x, y, auxx, auxy, width[] = new int[N], height;
    public int directionx[] = new int[100], directiony[] = new int[100];
    public SimpleDateFormat formatter;
    public static Label label = new Label("textfield");
    public TextField input = new TextField(30);
    int[] locx = new int[N];
    int[] locy = new int[N];

    String[] text = new String[N];
    boolean verification[] = new boolean[N], follows[] = new boolean[N];
    Color[][] colorarr = {
        {new Color(255, 0, 0), new Color(255, 127, 0), new Color(255, 255, 0)},
        {new Color(127, 255, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
        {new Color(75, 0, 130), new Color(143, 0, 255), new Color(0, 127, 255)}
    };

    @Override
    public void start() {
        final Graphics g = getGraphics();
        setup(g);

        timer = new Timer(true);
        timer.scheduleAtFixedRate(
                new TimerTask() {
            @Override
            public void run() {
                paint(g);
            }
        },
                0, FRAME_PERIOD
        );
    }

    @Override
    public void stop() {
        timer.cancel();
    }

    public void setup(Graphics g) {
        w = getWidth();
        h = getHeight();
        add(input);
        add(button1);
        button1.addActionListener(this);
        input.setBounds(10, 201, 200, 20);
        button1.setBounds(220, 201, 20, 20);
        this.setSize(new Dimension(350, 230));
        setLayout(new BorderLayout());
        Rectangle r = getBounds();
        h = r.height;
        w = r.width;
        height = g.getFontMetrics().getHeight();
    }

    @Override
    public void paint(Graphics g) {

        auxx = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
        auxy = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
        boolean auxver = !(auxx > 0 && auxx < 350 && auxy > 0 && auxy < 200);

        g.clearRect(0, 0, 400, 400);
        for (int i = 0; i < counter; i++) {
            if (follows[i] == false || (follows[i] == true && auxver)) {
                if (verification[i] == false) {
                    verification[i] = true;
                    if (locx[i] > 175) {
                        directionx[i] = 1;
                    } else {
                        directionx[i] = -1;
                    }
                    if (locy[i] > 100) {
                        directiony[i] = 1;
                    } else {
                        directiony[i] = -1;
                    }
                }
                if (locx[i] > w - width[i] + 1) {
                    locx[i] = w - 1 - width[i];
                }
                if (locy[i] < 8) {
                    locy[i] = 9;
                }
                if (locy[i] > 200) {
                    locy[i] = 199;
                }
                if (locx[i] == 0) {
                    directionx[i] = directionx[i] * (-1);
                }
                if (locx[i] == w - width[i]) {
                    directionx[i] = directionx[i] * (-1);
                }
                if (locy[i] == 8) {
                    directiony[i] = directiony[i] * (-1);
                }
                if (locy[i] == 200) {
                    directiony[i] = directiony[i] * (-1);
                }
                locx[i] = locx[i] + directionx[i];
                locy[i] = locy[i] + directiony[i];
            } else {
                locx[i] = auxx;
                locy[i] = auxy;
                verification[i] = false;
            }
            g.drawString(text[i], locx[i], locy[i]);
            if (locx[i] > 1 && locx[i] < 350 && locy[i] < 200 && locy[i] > 0) {
                g.setColor(colorarr[locx[i] % 3][locy[i] % 3]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newinput = input.getText();
        int first = newinput.indexOf(' ');
        int second = newinput.indexOf(' ', first + 1);
        int third = newinput.indexOf(' ', second + 1);
        Graphics g = getGraphics();
        text[counter] = newinput.substring(0, first);
        width[counter] = g.getFontMetrics().stringWidth(text[counter]);
        char auxiliarstring[] = newinput.substring(first + 1, second).toCharArray();
        if (auxiliarstring[0] == '1' || auxiliarstring[0] == 'y') {
            follows[counter] = true;
        } else {
            follows[counter] = false;
        }
        String auxiliarstring2 = newinput.substring(second + 1, third);
        locx[counter] = Integer.parseInt(auxiliarstring2);
        locy[counter] = Integer.parseInt(newinput.substring(third + 1));
        if (locx[counter] < 3) {
            locx[counter] = 3;
        }
        if (locy[counter] < 3) {
            locy[counter] = 3;
        }
        if (locx[counter] > w - width[counter] + 1) {
            locx[counter] = w - 1 - width[counter];
        }
        if (locy[counter] > 200) {
            locy[counter] = 200;
        }
        if (locx[counter] > 175) {
            directionx[counter] = 1;
        } else {
            directionx[counter] = -1;
        }
        if (locy[counter] > 100) {
            directiony[counter] = 1;
        } else {
            directiony[counter] = -1;
        }
        counter++;

    }
}
