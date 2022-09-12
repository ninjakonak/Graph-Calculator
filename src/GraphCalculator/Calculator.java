package src.GraphCalculator;
import java.util.Stack;


class Calculator{

    private char[] expression;

    private char[] numbers = {'1','2','3','4','5','6','7','8','9','0','.'};
    private char[] operators = {'*','/','+','-'};

    Stack<Integer> Parantheses = new Stack<>();    

    private int GlobalOffset = 0;

    Calculator(){
        
    }

    public String calculate(String expression){
        this.expression = expression.toCharArray();
        for(char i:this.expression){
            if(!CheckContains(numbers,i)&&!CheckContains(operators, i)&&i!='('&&i!=')'&&i!='^'){
                return "error";
            }
        }
        this.Exponents(0, this.expression.length);
        this.GlobalOffset = 0;
        this.CheckParanthesis(0);
        this.MulDiv(0, this.expression.length);
        this.AddSub(0, this.expression.length);

        return String.valueOf(this.expression);
    }
    
    private void CheckParanthesis(int index){
        //finds open parantheses and stores their index
        for(int i = index; i < this.expression.length; i++){
            this.GlobalOffset = 0;
            if(this.expression[i] == '(' ){
                this.Parantheses.push(i);
            }
            //finds closed parantheses and returns to the index of the last open paranthesis found
            //evaluates the operations between these two parantheses and turns them to a single number
            //checks for the other parantheses afterwards
            if(this.expression[i] == ')' ){
                //this.Exponents(this.Parantheses.lastElement() + 1, i - this.GlobalOffset);
                this.MulDiv(this.Parantheses.lastElement() + 1, i - this.GlobalOffset);
                this.AddSub(this.Parantheses.lastElement() + 1, i - this.GlobalOffset);
                this.RemoveParantheses(this.Parantheses.lastElement(), i - this.GlobalOffset);
                this.Parantheses.pop();
                System.out.println(this.expression);
                this.CheckParanthesis(i-this.GlobalOffset);
            }
        }
    }
    //creates a new expression, does not put the parantheses at the given indexes to this expression
    //sets the current expression to the built expression
    private void RemoveParantheses(int startIndex, int endIndex){
        String newExpression = "";
        for(int i = 0; i < this.expression.length; i++){
            if(i != startIndex && i != endIndex){
                newExpression += this.expression[i];
            }
        }

        this.expression = newExpression.toCharArray();
    }
    //checks for exponents
    private void Exponents(int startIndex, int endIndex){
        for(int i = startIndex; i < endIndex; i++){
            if(this.expression[i]=='^'){
                double[] result = EvaluateExpression(i);
                int offset = this.ModifyExpression(result[0],result[1],result[2]);
                this.GlobalOffset += offset;
                endIndex = endIndex - offset;
                i = startIndex;
            }
        }
    }
    //checks for multiplications & divisions and evaluates them
    private void MulDiv(int startIndex, int endIndex){
        for(int i = startIndex; i < endIndex; i++){
            if(this.expression[i]=='*'||this.expression[i]=='/'){
                double[] result = EvaluateExpression(i);
                int offset = this.ModifyExpression(result[0],result[1],result[2]);
                this.GlobalOffset += offset;
                endIndex = endIndex - offset;
                i = startIndex;
            }
        }
    }
    //checks for additions & subtractions and evaluates them
    private void AddSub(int startIndex, int endIndex){
        for(int i = startIndex; i < endIndex; i++){
            if(this.expression[i]=='+'||this.expression[i]=='-'&&i != startIndex){
                double[] result = EvaluateExpression(i);
                int offset = this.ModifyExpression(result[0],result[1],result[2]);
                this.GlobalOffset += offset;
                endIndex = endIndex - offset;
                i = startIndex;
            }
        }
    }
    //modifys the operation to the given result
    private int ModifyExpression(double result, double startIndex, double endIndex){

        String newExpression = "";

        for(int i = 0; i < this.expression.length; i++){
            
            if(i == startIndex){
                newExpression += String.valueOf(result);
            }
            if(i < startIndex || i > endIndex){
                newExpression += this.expression[i];
            }
        }

        int offset = this.expression.length - newExpression.toCharArray().length;

        this.expression = newExpression.toCharArray();

        return offset;
    }
    //Evaluates the expression looking at the operator at the given index and returns the result
    private double[] EvaluateExpression(int index){
        double[] rightOperand = FindRightOperand(index);
        double[] leftOperand = FindLeftOperand(index);
        
        double[] evaluatedExpression;  
        
        switch(this.expression[index]){
            case '*':
            evaluatedExpression = new double[]{leftOperand[0] * rightOperand[0], leftOperand[1], rightOperand[1]};
            return evaluatedExpression;

            case '/':
            evaluatedExpression = new double[]{leftOperand[0] / rightOperand[0], leftOperand[1], rightOperand[1]};
            return evaluatedExpression;

            case '+':
            evaluatedExpression = new double[]{leftOperand[0] + rightOperand[0], leftOperand[1], rightOperand[1]};
            return evaluatedExpression;

            case '-':
            evaluatedExpression = new double[]{leftOperand[0] - rightOperand[0], leftOperand[1], rightOperand[1]};
            return evaluatedExpression;

            case '^':
            evaluatedExpression = new double[]{Math.pow(leftOperand[0],rightOperand[0]), leftOperand[1], rightOperand[1]};
            return evaluatedExpression;
        }

        
        return null;
         
    }

    private boolean CheckContains(char[] array, char character){
        for(char i : array){
            if(i == character){
                return true;
            }
        }
        return false;
    }
    //iterates and stores the data of the left operand until it encounters and operator or paranthesis
    private double[] FindLeftOperand(int index){
        String leftOperand = "";
        int leftOperandIndex = 0;
        for(int i = index-1; i >= 0; i--){
            if(!CheckContains(this.numbers,this.expression[i])){
                
                if(this.expression[i] == '-' && i == 0 || this.expression[i] == '-' && this.expression[i-1] == '('|| this.expression[i] == '-' && CheckContains(this.operators, this.expression[i-1])){
                    leftOperandIndex = i;
                    break;
                }
                
                leftOperandIndex = i+1;
                break;
            }
        }

        for(int i = leftOperandIndex; i < index; i++){
            leftOperand += this.expression[i];
        }

        double result = Double.parseDouble(leftOperand);
        double[] arr = {result,leftOperandIndex};
        return arr;

        
    }

    //iterates and stores the data of the right operand until it encounters and operator or paranthesis
    private double[] FindRightOperand(int index){
        String rightOperand = "";
        int rightOperandIndex = this.expression.length;
        for(int i = index+1; i < this.expression.length; i++){
            if(CheckContains(this.numbers,this.expression[i])||i==index+1){
                rightOperand += this.expression[i];
            }
            else{
                rightOperandIndex = i - 1;
                break;
            }

        }
        double result = Double.parseDouble(rightOperand);
        double[] arr = {result, rightOperandIndex};
        return arr; 
    }
}