package src.GraphCalculator;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{

    Graphics g;

    boolean draw;

    String expression;

    int graphContainerWidth = 750;
    int graphContainerHeight = 750;
    int[] origin = {this.graphContainerWidth/2+10,this.graphContainerHeight/2+10};
    int graphLength = (int)(375*Math.sqrt(2));
    
    GraphPanel(){

        
        this.expression = "";
        
        
        this.setBounds(0,110,this.graphContainerWidth,this.graphContainerHeight);
        
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(this.CheckExpression()){
            int n = 0;

            int currentNumber;
            int nextNumber;

            double[] positiveResults = this.EvaluateExpression(true);
            double[] negativeResults = this.EvaluateExpression(false);

            //draws the positive side of the graph
            for(int j = 0; j < this.graphLength-1; j++){
                currentNumber = (int)positiveResults[j];
                nextNumber = (int)positiveResults[j+1];

                g.drawLine(origin[0]+n,origin[1]-(int)currentNumber,origin[0]+n+1,origin[1]-(int)nextNumber);
                n++;
            }

            n=0;

            //draws the negative side of the graph
            for(int j = 0; j < this.graphLength-1; j++){
                currentNumber = (int)negativeResults[j];
                nextNumber = (int)negativeResults[j+1];

                g.drawLine(origin[0]+n,origin[1]-(int)currentNumber,origin[0]+n-1,origin[1]-(int)nextNumber);
                n--;
            } 
        }
    }

    
    //changes the expression
    public void SetGraphExpression(String expression){
        this.expression = expression;
        this.DrawGraph();
    }

    //redraws the graph
    private void DrawGraph(){
        this.repaint();
    }

    private boolean CheckExpression(){
        int i = this.expression.length();
        
        if(i == 0){
            return false;
        }
        else{
            return true;
        }
    }
    //places numbers from 0 to graphLength for x and evaluates the results
    //returns an array of results
    private double[] EvaluateExpression(boolean x){
        
        int inc;

        if(x){inc = 1;}
        else{inc = -1;}

        double[] arr = new double[(int)(graphLength)];
        Calculator cal = new Calculator();
        for(int i = 0; i*inc < (graphLength)-1; i+=inc){
            String tempExpr = "";
            for(char n:this.expression.toCharArray()){
                if(n == 'X'||n == 'x'){tempExpr += String.valueOf(i);}
                else{tempExpr += n;}
            }
            arr[i*inc] = Double.parseDouble(cal.calculate(tempExpr));
        }
        
        return arr;
    }  
}
