package src.GraphCalculator;
import javax.swing.JFrame;
import java.awt.event.*;



public class AppFrame extends JFrame{
    InputPanel inputPanel;
    GraphPanel graphPanel;
    
    
    
    public AppFrame(){
        this.inputPanel = new InputPanel();
        this.graphPanel = new GraphPanel();
        this.initiateButton();
        this.add(this.inputPanel);
        this.add(this.graphPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(750,800);
        this.setVisible(true);
        this.setTitle("GRAPH CALCULATOR");

    }

    private void initiateButton(){
        this.inputPanel.button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String expr = inputPanel.field.getText();
                inputPanel.label.setText("y="+expr);
                SetGraphPanel(expr);
            }
        });
    }

    private void SetGraphPanel(String expression){
        this.graphPanel.SetGraphExpression(expression);
    }

    
}
