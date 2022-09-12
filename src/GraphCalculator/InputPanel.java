package src.GraphCalculator;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;

import javax.swing.JTextField;


public class InputPanel extends JPanel{

    JButton button;
    JTextField field;
    JLabel label;
    

    InputPanel(){
        this.InitComponents();
        this.setLayout(new GridLayout(3,0));
        this.setBounds(10,10,200,100);
        this.add(this.field);
        this.add(this.button);
        this.add(this.label);
    }

    private void InitComponents(){
        this.button = new JButton("CLICK TO CALCULATE");
        this.label = new JLabel("EXPRESSION");
        this.field = new JTextField();
        
    }    
}
