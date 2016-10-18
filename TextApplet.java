
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

class Ball {

    int x, y;
    int locx, locy;
    int radius;
    boolean follows, verification;
    int directionx, directiony;
    /*
    public Ball(int x, int y, int locx, int locy, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.locx = locx;
        this.locy = locy;
    }
     */
}

public class TextApplet extends Applet implements ActionListener {

    static final int N = 100;
    public static final int FRAME_PERIOD = 50;
    public Timer timer;
    int counter = 0, counterball = 0;
    Button button1 = new Button("-");
    public int w, h, x, y, auxx, auxy, width[] = new int[N], height;
    public int directionx[] = new int[100], directiony[] = new int[100];
    public SimpleDateFormat formatter;
    public static Label label = new Label("textfield");
    public TextField input = new TextField(30);
    int[] locx = new int[N];
    int[] locy = new int[N];
    public Ball b[] = new Ball[N];
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
        for (int i = 0; i < 99; i++) {
            b[i] = new Ball();
        }
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

        for (int i = 0; i < counterball; i++) {
            if (b[i].follows == false || (b[i].follows == true && auxver)) {
                if (b[i].verification == false) {
                    b[i].verification = true;
                    if (b[i].locx > 175) {
                        b[i].directionx = 1;
                    } else {
                        b[i].directionx = -1;
                    }
                    if (b[i].locy > 100) {
                        b[i].directiony = 1;
                    } else {
                        b[i].directiony = -1;
                    }
                }
                if (b[i].locx > w - b[i].radius + 1) {
                    b[i].locx = w - 1 - b[i].radius;
                }
                if (b[i].locy < 8) {
                    b[i].locy = 9;
                }
                if (b[i].locy > 200) {
                    b[i].locy = 199;
                }
                if (b[i].locx == 0) {
                    b[i].directionx = b[i].directionx * (-1);
                }
                if (b[i].locx == w - b[i].radius) {
                    b[i].directionx = b[i].directionx * (-1);
                }
                if (b[i].locy == 8) {
                    b[i].directiony = b[i].directiony * (-1);
                }
                if (b[i].locy == 200) {
                    b[i].directiony = b[i].directiony * (-1);
                }
                b[i].locx = b[i].locx + b[i].directionx;
                b[i].locy = b[i].locy + b[i].directiony;
            } else {
                b[i].locx = auxx;
                b[i].locy = auxy;
                b[i].verification = false;
            }
            g.fillOval(b[i].locx, b[i].locy, b[i].radius, b[i].radius);
            if (b[i].locx > 1 && b[i].locx < 350 && b[i].locy < 200 && b[i].locy > 0) {
                g.setColor(colorarr[b[i].locx % 3][b[i].locy % 3]);
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
        String auxiliarstring2 = newinput.substring(0, first);
        System.out.println(counterball);
        //b[counterball] = new Ball();
        b[0].follows = true;
        if (auxiliarstring2.matches("ball")) {
            char auxiliarstring[] = newinput.substring(first + 1, second).toCharArray();
            if (auxiliarstring[0] == '1' || auxiliarstring[0] == 'y') {
                b[counterball].follows = true;
            } else {
                b[counterball].follows = false;
            }
            int fourth = newinput.indexOf(' ', third + 1);
            b[counterball].locx = Integer.parseInt(newinput.substring(second + 1, third));
            b[counterball].locy = Integer.parseInt(newinput.substring(third + 1, fourth));
            b[counterball].radius = Integer.parseInt(newinput.substring(fourth + 1));
            if (b[counterball].locx < 3) {
                b[counterball].locx = 3;
            }
            if (b[counterball].locy < 3) {
                b[counterball].locy = 3;
            }
            if (b[counterball].locx > w - b[counterball].radius + 1) {
                b[counterball].locx = w - 1 - b[counterball].radius;
            }
            if (b[counterball].locy > 200) {
                b[counterball].locy = 200;
            }
            if (b[counterball].locx > 175) {
                b[counterball].directionx = 1;
            } else {
                b[counterball].directionx = -1;
            }
            if (b[counterball].locy > 100) {
                b[counterball].directiony = 1;
            } else {
                b[counterball].directiony = -1;
            }

            counterball++;

        } else {
            text[counter] = auxiliarstring2;
            width[counter] = g.getFontMetrics().stringWidth(text[counter]);
            char auxiliarstring[] = newinput.substring(first + 1, second).toCharArray();
            if (auxiliarstring[0] == '1' || auxiliarstring[0] == 'y') {
                follows[counter] = true;
            } else {
                follows[counter] = false;
            }

            locx[counter] = Integer.parseInt(newinput.substring(second + 1, third));
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
}
