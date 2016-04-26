//importing libraries
import java.util.*;
import java.io.*;
import java.io.File;
import java.util.regex.*;


public class tinyInterpreter{

    private static tinyInterpreter ti;
    private Scanner input;
    private String filename;
    private File file;
    private String[] tmpData;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private List<String> lines;
    private String line;
    private String[] tmpLine;
    private String pat;
    private Pattern pattern;
    private Matcher match;
    private boolean variable;
    private boolean operation;
    private static Map<String, String> variables = new HashMap<String, String>();


    //parsing file
    public void parseFile(String[] tmpData){

        variable = false;
        operation = false;

        for (String s: tmpData){   
            
            //replacing multiple spaces with single space
            s = s.trim().replaceAll(" +", " ");

            //if print statement
            pat = "(^Print)(.*)";
            pattern = Pattern.compile(pat);
            match = pattern.matcher(s);
            if (match.find( )) {
                s = s.replaceAll("\\s","");
                s = s.replaceAll(";","");
                tmpLine = s.split("Print");
                tmpLine[1] = tmpLine[1].trim();
                System.out.println(tmpLine[1]+"="+variables.get(tmpLine[1]));

            }

            //if variable statement
            pat = "(^Let)(.*)";
            pattern = Pattern.compile(pat);
            match = pattern.matcher(s);
            if (match.find( )) {
                s = s.replaceAll("Let","");
                s = s.replaceAll("\\s","");
                s = s.replaceAll(";","");
                tmpLine = s.split("=");
                variables.put(tmpLine[0],tmpLine[1]);

                variable = true;

            }
            else
                variable = false;

            //operation statement
            pat = "(^Print)(.*)";
            pattern = Pattern.compile(pat);
            match = pattern.matcher(s);
            if (match.find( ))
                operation = true;
            else
                operation = false;

            
            if (s.contains("+")){
                String[] tmp = s.split("=");//var
                tmp[1] = tmp[1].replaceAll("\\s","");
                String[] tmp2 = tmp[1].split("\\+");
                Double test = Double.parseDouble(variables.get(tmp2[0]))+Double.parseDouble(variables.get(tmp2[1].replace(";","")));
                variables.put(tmp[0].trim(),test+"");
            }
            if (s.contains("-")){
                String[] tmp = s.split("=");//var
                tmp[1] = tmp[1].replaceAll("\\s","");
                String[] tmp2 = tmp[1].split("\\-");
                Double test = Double.parseDouble(variables.get(tmp2[0]))-Double.parseDouble(variables.get(tmp2[1].replace(";","")));
                variables.put(tmp[0].trim(),test+"");
            }

            if (s.contains("*")){
                String[] tmp = s.split("=");//var
                tmp[1] = tmp[1].replaceAll("\\s","");
                String[] tmp2 = tmp[1].split("\\*");
                Double test = Double.parseDouble(variables.get(tmp2[0]))*Double.parseDouble(variables.get(tmp2[1].replace(";","")));
                variables.put(tmp[0].trim(),test+"");
            }

            if (s.contains("/")){
                String[] tmp = s.split("=");//var
                tmp[1] = tmp[1].replaceAll("\\s","");
                String[] tmp2 = tmp[1].split("\\/");
                Double test = Double.parseDouble(variables.get(tmp2[0]))/Double.parseDouble(variables.get(tmp2[1].replace(";","")));
                variables.put(tmp[0].trim(),test+"");
            }
            

        }

    }

    //reading file
    public void readFile(String filename){

        try{

            file = new File(filename);
            if (file.exists()){

                fileReader = new FileReader(filename);
                bufferedReader = new BufferedReader(fileReader);
                lines = new ArrayList<String>();
                line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }
                bufferedReader.close();

                //making array
                tmpData = lines.toArray(new String[lines.size()]);

                //parsing file
                parseFile(tmpData);

            }
        }
        catch(IOException ioe){
            System.out.println("File doesn't exist!");
        }
    }

    public static void main(String[] args){

        //making objects
        ti = new tinyInterpreter();

        //taking input
        ti.input = new Scanner(System.in);
        System.out.println("Enter filename to be read:");
        ti.filename = ti.input.nextLine();

        //reading file
        ti.readFile(ti.filename);

    }
}