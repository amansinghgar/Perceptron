
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import perceptron.classifier.Perceptron;
 class Explore {
	private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    ArrayList<double[]> inputs = new ArrayList<double[]>();
    public double sse=0,ssr=0,ssto=0;
    Perceptron perceptron = new Perceptron();
    public static void main(String[] args)
    {
    	Explore ex = new Explore();
    	try {
    		ex.TrainingSet();
    		ex.TestSet();
    	}
    	catch (Exception e) {
            
            System.out.println("Exception occurred");
         }
    }
    
   
    
   public void TrainingSet() throws Exception {
    	
    	
    	 
        String csvFile = "C:\\Users\\amaz\\Desktop\\abalone.data.csv";

        Scanner scanner = new Scanner(new File(csvFile));
       
        while (scanner.hasNext()) {
            List<String> line = parseLine(scanner.nextLine());
            double[] doubles = new double[8];
            for ( int i = 0 ; i <8; ++i)
                {
                 double val = Double.parseDouble(line.get(i));
                 doubles[i] = val;
                }
            inputs.add(doubles);
        }
        scanner.close();
     
		
        perceptron.train(inputs);
        

    }
    
    public void TestSet() throws Exception {
    	double tempact[]=new double[3000];
    	double temppre[]=new double[3000];
    	
    	double avgact=0,avgpre=0;
    	int count=0;
        String csvFile = "C:\\Users\\amaz\\Desktop\\test.csv";
        PrintStream o = new PrintStream(new File("aaaa.txt")); 
        Scanner scanner = new Scanner(new File(csvFile));
        System.setOut(o);
        System.out.println("Length-0f-Shell  Diameter-Shell  Height-Of-Shell  Whole-weight  Shucked-weight  Viscera-weight  Shell-weight  Rings-Act-Value  Predicted-Value");
        while (scanner.hasNext()) {
        	
            List<String> line = parseLine(scanner.nextLine());
            double[] input = new double[7];
            for ( int i = 0 ; i <8; ++i)
                {
                 double val = Double.parseDouble(line.get(i));
                 if(i<7)
                 input[i] = val;
                 System.out.print("\t"+val+"\t" );
                 avgact+=val;
                 tempact[count]=val;
                }
          
            double output = perceptron.classify(input);
            System.out.println(" : " + output+" ;");
            avgpre+=output;
            
      
            temppre[count]=output;
            sse+=((output-tempact[count])*(output-tempact[count]));
              count++;
        }
        avgact/=count++;
        avgpre/=count++;
        for(int i=0;i<count;i++)
        {
        	ssr+=((temppre[i]-avgact)*(temppre[i]-avgact));
        	ssto+=((tempact[i]-avgpre)*(tempact[i]-avgpre));
        }
        System.out.println(" \n\n\n\n\n "+sse+"\t"+ssr+"\t"+ssto);
        
        scanner.close();
        

    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

       
        //deleted

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}