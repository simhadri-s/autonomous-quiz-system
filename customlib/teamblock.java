package customlib;
import mainC.MainP;


import java.awt.*;

public class teamblock extends Component{
    private String teamName, tScore="0";
    private Color backgroundColor;
    private Color foregroundColor;
    private int blockEdge, wid=250, high=150, bwid=220, bhigh=100, tbwid=250;
    MainP p=new MainP();
    private boolean selected = false;
 

    public void team(String teamName, String tScore) {
        this.teamName = teamName;
        this.tScore = tScore;
        this.backgroundColor = Color.DARK_GRAY;
        this.foregroundColor = Color.WHITE;
        this.blockEdge = 10;
        setPreferredSize(new Dimension(wid, high));
        
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected=selected;
        if (selected) {
            backgroundColor = new Color(0, 187, 39);
            // Increase size instead of setting a fixed size
            bwid=wid;
            bhigh=high;
            repaint();
        } else {
            backgroundColor = Color.DARK_GRAY;
            // Increase size instead of setting a fixed size
            bwid=220;
            bhigh=100;
            repaint();
        }
    }

    public String findTeam(){
        return teamName;
    }

    public void setblockSize(int wid, int high){
        this.wid = wid;
        this.high = high;
        //setPreferredSize(new Dimension(wid, high));
        repaint();
    }

    public String getTeamNo(){
        return teamName;
    }
    public void writeScore(int intScore){
        int a=Integer.parseInt(this.tScore);
        this.tScore=Integer.toString(a+intScore);
        repaint();
    }

    public void paint(Graphics g){
        //Draw the background
        g.setColor(backgroundColor);
        g.fillRoundRect((getWidth()-bwid)/2, (getHeight()-bhigh)/2, bwid-1, bhigh-1, blockEdge, blockEdge);
    
        //Draw the text
        g.setColor(foregroundColor);
        g.setFont(new Font("Franklin Gothic Book", Font.BOLD,24));
        FontMetrics metrics=g.getFontMetrics();
        String teamdis="Team["+teamName+"]";
        int namex= (tbwid - metrics.stringWidth(teamdis)) / 2;
        int scorex = (tbwid - metrics.stringWidth(tScore)) / 2;
        int y= 60;//(getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(teamdis, namex, y);
        g.drawString(tScore, scorex, y+40);
    }
}
