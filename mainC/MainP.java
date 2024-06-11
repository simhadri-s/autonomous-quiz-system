package mainC;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Timer;
import java.util.TimerTask;
import customlib.teamblock;
import customlib.dreceive;

public class MainP extends JFrame {
    static ReadQ rd= new ReadQ("E:\\programming\\quiz_pgm\\question.txt");
    public static String[] qtext=rd.getQuestions();
    public static MainP mainpObj;
    private int qindex=0;
    private static int selectedTeam=-1;
    private timerclass tm;
    private JLabel line1, line2;
    dreceive d;
    protected int[][] teamNo={
        {1, 2, 3, 4, 5}
    };
    public teamblock tb[][] = new teamblock[teamNo.length][teamNo[0].length];
    JFrame frameRef=this;

    void createW() {
        dreceive d = new dreceive();
    
        // Set frame title, size, and background color
        setTitle("Quiz Admin Page");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        Color frameColor = new Color(31, 31, 31);
        getContentPane().setBackground(frameColor);
        setLayout(new BorderLayout());
    
        // College header JLabel
        ImageIcon headerImg = resizeImage("E:\\programming\\quiz_pgm\\header.png", 666, 106);
        JLabel cHeader = new JLabel(headerImg);
        cHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Create Question Label
        JLabel question = new JLabel("", JLabel.CENTER);
        questionmod(question);
        question.setBorder(BorderFactory.createEmptyBorder(20, 0, 100, 0));
        
        // JPanel for header and question
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(cHeader, BorderLayout.NORTH);
        headerPanel.add(question, BorderLayout.SOUTH);
        headerPanel.setBackground(frameColor);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 250));
        
        // Add headerPanel to the NORTH region
        add(headerPanel, BorderLayout.NORTH);
    
        // Timer JLabel
        add(tm = new timerclass("10"), BorderLayout.CENTER);
    
        // Creating Team Panel
        Container teamcont = new JPanel();
        teamcont.setLayout(new BorderLayout());
        teamcont.add(teampanel());
    
        // Add teamcont to the SOUTH region
        add(teamcont, BorderLayout.SOUTH);
    
        // Set default close operation and make the frame visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        writeScore writeScoreObj = new writeScore();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e){}
            @Override
            public void keyReleased(KeyEvent e){
                char typedchar=e.getKeyChar();
                int typedKey=e.getKeyCode();
                if(typedchar>='a' && typedchar<='z'){
                    switch (typedchar) {
                    case 'd':
                        if (qindex>-1 && qindex<qtext.length){
                            setQuestion(qtext[qindex]);
                            ++qindex;
                            for(int i=0; i<tb.length; i++){
                                for(int j=0; j<tb[i].length; j++){
                                    if(selectedTeam==Integer.parseInt(tb[i][j].getTeamNo())){
                                        deselectTeam(tb[i][j]);
                                        break;
                                    }
                                }
                            }
                            new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    // Background task (e.g., receive data)
                                    d.recieve(mainpObj);
                                    return null;
                                }
                            }.execute();
                        }
                        break;
                
                    case 'a':
                        if(qindex>0){
                            --qindex;
                            setQuestion(qtext[qindex]);
                            SwingUtilities.invokeLater(() -> {
                                question.repaint();
                                question.revalidate();
                            });
                            for(int i=0; i<tb.length; i++){
                                for(int j=0; j<tb[i].length; j++){
                                    if(selectedTeam==Integer.parseInt(tb[i][j].getTeamNo())){
                                        deselectTeam(tb[i][j]);
                                        break;
                                    }
                                }
                            }
                            // Use SwingWorker for background task
                            new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    // Background task (e.g., receive data)
                                    d.recieve(mainpObj);
                                    return null;
                                }
                            }.execute();
                        }
                        break;
                    
                    case 'w':
                    for(int i=0; i<tb.length; i++){
                        for(int j=0; j<tb[i].length; j++){
                            if(selectedTeam==Integer.parseInt(tb[i][j].getTeamNo())){
                                deselectTeam(tb[i][j]);
                                break;
                            }
                        }
                    }
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            // Background task (e.g., receive data)
                            d.recieve(mainpObj);
                            return null;
                        }
                    }.execute();
                    break;
                        
                    
                    default:
                        System.out.print(typedchar);
                    }
                }
                else{
                    System.out.println(KeyEvent.getKeyText(typedKey));
                    switch (KeyEvent.getKeyText(typedKey)) {
                        case "Right":
                            if (selectedTeam>0){
                                for(int i=0; i<tb.length; i++){
                                    for(int j=0; j<tb[i].length; j++){
                                        if(selectedTeam==Integer.parseInt(tb[i][j].getTeamNo())){
                                            tb[i][j].writeScore(10);
                                            tm.stop_time();
                                            writeScoreObj.write(10, Integer.parseInt(tb[i][j].findTeam()), qindex);
                                            deselectTeam(tb[i][j]);
                                            break;
                                        }
                                    }
                                }
                            }
                            break;

                        case "Left":
                            if (selectedTeam>0){
                                for(int i=0; i<tb.length; i++){
                                    for(int j=0; j<tb[i].length; j++){
                                        if(selectedTeam==Integer.parseInt(tb[i][j].getTeamNo())){
                                            tb[i][j].writeScore(20);
                                            writeScoreObj.write(20, Integer.parseInt(tb[i][j].findTeam()), qindex);
                                            tm.stop_time();
                                            deselectTeam(tb[i][j]);
                                        }
                                    }
                                }
                            }
                            break;
                        
                        case "Up":
                            if (selectedTeam>0){
                                for(int i=0; i<tb.length; i++){
                                    for(int j=0; j<tb[i].length; j++){
                                        if(selectedTeam==Integer.parseInt(tb[i][j].getTeamNo())){
                                            tb[i][j].writeScore(5);
                                            writeScoreObj.write(5, Integer.parseInt(tb[i][j].findTeam()), qindex);
                                            tm.stop_time();
                                            
                                            deselectTeam(tb[i][j]);
                                        }
                                    }
                                }
                            }
                            break;

                        case "Down":
                            if (selectedTeam>0){
                                for(int i=0; i<tb.length; i++){
                                    for(int j=0; j<tb[i].length; j++){
                                        if(selectedTeam==Integer.parseInt(tb[i][j].getTeamNo())){
                                            tb[i][j].writeScore(-5);
                                            writeScoreObj.write(-5, Integer.parseInt(tb[i][j].findTeam()), qindex);
                                            tm.stop_time();
                                            deselectTeam(tb[i][j]); 
                                        }
                                    }
                                }
                            }
                            break;

                        case "F5":
                            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(MainP.this);
                            break;
                        
                        case "Escape":
                            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
                            break;
                        default:
                            break;
                    }
                }
                    
            }
        });
    }

    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    //Question Styling
    void questionmod(JLabel question) {
        question.setAlignmentX(CENTER_ALIGNMENT);
        question.setAlignmentY(500);
        question.setForeground(Color.WHITE);
        question.setFont(new Font("Sanserif", Font.BOLD, 35));
        question.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

        // Set layout manager to BoxLayout and add Box.Filler for vertical spacing
        question.setLayout(new BoxLayout(question, BoxLayout.Y_AXIS));
        question.add(Box.createVerticalGlue());

        // Add two JLabels for each line
        line1 = new JLabel("IC Club", JLabel.CENTER);
        line2 = new JLabel("E-Waste QUIZ", JLabel.CENTER);

        // Customize the font and color if needed
        line1.setFont(new Font("Sanserif", Font.BOLD, 35));
        line2.setFont(new Font("Sanserif", Font.BOLD, 35));
        line1.setAlignmentX(CENTER_ALIGNMENT);
        line2.setAlignmentX(CENTER_ALIGNMENT);
        line1.setForeground(Color.WHITE);
        line2.setForeground(Color.WHITE);

        // Add the JLabels to the main JLabel
        question.add(line1);
        question.add(line2);

        question.add(Box.createVerticalGlue());
    }

    void setQuestion(String qString) {
        String s1, s2;
        Font font = new Font("Sanserif", Font.BOLD, 35);
        FontMetrics qmetices = getFontMetrics(font);
        int qwidth = qmetices.stringWidth(qString);
    
        if (qwidth < getWidth() - 20) {
            line1.setText(qString);
            line2.setText("");
        } else {
            int splitIndex = 0;
            int lineWidth = 0;
    
            // Find the index to split the string
            while (splitIndex < qString.length() && lineWidth + qmetices.charWidth(qString.charAt(splitIndex)) < getWidth() - 20) {
                lineWidth += qmetices.charWidth(qString.charAt(splitIndex));
                splitIndex++;
            }
    
            s1 = qString.substring(0, splitIndex);
            s2 = qString.substring(splitIndex);
            line1.setText(s1);
            line2.setText(s2);
        }
    
        SwingUtilities.invokeLater(() -> {
            line1.repaint();
            line2.repaint();
            line1.revalidate();
            line2.revalidate();
        });
    }
    

    JPanel teampanel(){
        JPanel tpanel= new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        tpanel.setBackground(new Color(31, 31, 31));
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        // gbc.insets = new Insets(15, 15, 15, 15);
        for(int i=0; i<teamNo.length; i++){
            for(int j=0; j<teamNo[i].length; j++){
                gbc.gridx=j;
                gbc.gridy=i;
                String teamname=Integer.toString(teamNo[i][j]);
                tb[i][j]= new teamblock();
                tb[i][j].team(teamname,"0");
                tb[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        handleTeamBlockClick((teamblock) e.getSource());
                    }
                });
                tpanel.add(tb[i][j], gbc);
            }
        }
        
        tpanel.setSize(800, 80);
        tpanel.setBorder(BorderFactory.createEmptyBorder(0,0,50, 0));
        return tpanel;
    }

    public void handleTeamBlockClick(teamblock clickedBlock) {
        // Deselect all team blocks
        for (teamblock[] row : tb) {
            for (teamblock block : row) {
                block.setSelected(false);
            }
        }

        // Select the clicked team block
        clickedBlock.setSelected(true);
        tm.runTime("10");
        selectedTeam=Integer.parseInt(clickedBlock.getTeamNo());
    }

    public void remoteSelect(int remoteInput){
        // Deselect all team blocks
         for (teamblock[] row : tb) {
             for (teamblock block : row) {
                 if(Integer.parseInt(block.findTeam())!=remoteInput){
                     block.setSelected(false);
                 } else {
                     block.setSelected(true);
                     selectedTeam=Integer.parseInt(block.getTeamNo());
                     tm.runTime("10");
                 }
             }
         }
        System.out.println(remoteInput);
    }

    void deselectTeam(teamblock t){
        Timer time= new Timer();
        time.schedule(new TimerTask() {
                @Override
                public void run(){
                t.setSelected(false);
                time.cancel();
            }
        }, 500);
        selectedTeam=-1;
    }
    public static void main(String args[]) {
        mainpObj = new MainP();
        mainpObj.createW();
        
        
    }
}

class timerclass extends Component {
    int ovalWidth = 250; // Store the width for easy adjustments
    int ovalHeight = 250; // Store the height for easy adjustments
    protected Color foregroundColor= Color.WHITE, backgroundColor;
    private String text;

    timerclass(String text){
        this.text=text;
    }

    void reset(String text){
        this.text=text;
        setBackgroundColor(backgroundColor);
    }

    @Override
   
    public void paint(Graphics g) {
        // Calculate the center coordinates based on the component's size
        int centerX = (getWidth() - ovalWidth) / 2;
        int centerY = 50;

        // Set the color before drawing the oval
        g.setColor(backgroundColor);
        g.fillOval(centerX, centerY, ovalWidth, ovalHeight);

        // Set the color before drawing the text
        g.setColor(foregroundColor);
        g.setFont(new Font("Arial", Font.PLAIN, 100));
        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = (centerY + 250 - metrics.getHeight()/2) / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setForegroundColor(Color color) {
        this.foregroundColor = color;
        repaint();
    }

    private Timer time = new Timer();
    private boolean isTimeRunning=false;
    void runTime(String ctime) {
        if(isTimeRunning){
            time.cancel();
            isTimeRunning=false;
            time= new Timer();
        }
        int totalTime = Integer.parseInt(ctime);
        setBackgroundColor(new Color(51, 51, 51));
        isTimeRunning=true;
        time.schedule(new TimerTask() {
            @Override
            public  void run() {
                // Now schedule the remaining timer tasks for the countdown
                for (int i = totalTime - 1; i >= 0; i--) {
                    final int remainingTime = i;
                    time.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                                text = Integer.toString(remainingTime);
                                repaint();
                                revalidate();
                            });
                            if (remainingTime == 3) {
                                setBackgroundColor(Color.RED);
                            }
                        }
                    }, (totalTime - i) * 1000);
                }
            }
        }, 0);
    }
    void stop_time(){
        time.cancel();
    }
}

class ReadQ {
    private String[] questions;

    public ReadQ(String qFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(qFilePath))) {
            int numberOfLines = (int) reader.lines().count();
            questions = new String[numberOfLines];
            
            try (BufferedReader newReader = new BufferedReader(new FileReader(qFilePath))) {
                int i = 0;
                String line;
                while ((line = newReader.readLine()) != null) {
                    questions[i] = line;
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getQuestions() {
        return questions;
    }
}

class writeScore{
    void write(int score, int team, int round){
        try {
            FileWriter writer = new FileWriter("../quiz_pgm/score.txt", true);
            writer.write("Round: "+ round +"; Team: " + team + "; Score: " + score +";\n");
            writer.close();
        } catch(IOException e){
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}