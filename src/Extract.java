/**
 * Created by 201250541 on 2015-04-08.
 */
import java.io.*;
public class Extract {
    public static void main( String args[] )
    {
        if(args.length != 2)
        {
            System.out.println("Vous avez donnée: "+args.length+" Fichier(s) a la ligne de commande alors que nous en voulons seulement que 2 pas plus pas moins");
            System.exit(0);
        }

        File source = new File(args[0]);
        File target = new File(args[1]);

        if(!source.exists())
        {
            System.out.println(args[0]+"n'existe pas");
            System.exit(0);
        }
        else if (source.isDirectory())
        {
            System.out.println(args[0]+"n'existe pas");
            System.exit(0);
        }
        else if( !args[0].toLowerCase().endsWith(".html"))
        {
            System.out.println(args[0]+" n'est pas un fichier HTML valide");
            System.exit(0);
        }

        if(!target.exists())
        {
            System.out.println(args[1]+"n'existe pas");
            System.exit(0);
        }
        else if (target.isDirectory())
        {
            System.out.println(args[1]+"n'existe pas");
            System.exit(0);
        }
        else if( !args[1].toLowerCase().endsWith(".html"))
        {
            System.out.println(args[1]+" n'est pas un fichier HTML valide");
            System.exit(0);
        }

        ShowTime(RipA(ReadFile(args[0])),args[1],args[0]);

    }

    private static String ReadFile(String file)
    {
        System.out.println("ReadFile Begin");
        BufferedReader reader;
        boolean pasFini = true;
        String ligne;
        String fichier="";

        try
        {
            reader = new BufferedReader(
                    new FileReader( file ) );

            while( pasFini )
            {
                ligne = reader.readLine();

                if( ligne != null )
                {
                    fichier += ligne ;
                }
                else
                {
                    pasFini = false;
                }
            }

            reader.close();
        }
        catch( IOException ioe )
        {
            System.err.println( ioe );
            System.exit( 1 );
        }
        System.out.println("ReadFile End");
        return fichier;
    }

    private static  String RipA(String sourceTxt)
    {
        System.out.println("RipA Begin");
        String carry;
        Integer pos;
        String valuePairs="";
        while ((pos =sourceTxt.lastIndexOf("<a href=")) != -1 )
        {
            carry = sourceTxt.substring(pos,sourceTxt.lastIndexOf("</a>")+4);
            sourceTxt = sourceTxt.substring(0,pos);
            valuePairs+=carry.substring(carry.indexOf('"')+1,carry.lastIndexOf('"'));
            valuePairs+=',';
            valuePairs+=carry.substring(carry.indexOf(">")+1,carry.lastIndexOf("</"));
            valuePairs+=';';
        }
        System.out.println("RipA End");
        return valuePairs;

    }
    private static void ShowTime(String data,String target,String sourceName)
    {
        System.out.println("ShowTime Begin");
        String val[] = data.split(";");
        PrintWriter writer;

        try
        {
            writer = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter( target ) ) );
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head><meta charset=\"utf-8\"></head>");
            writer.println("<body>");
            writer.println("Hyperliens estraits du fichier <b>"+sourceName+"<b>");
            writer.println("<ul>");

            for (int i = 0; i < val.length; i++) {
                writer.println("<li><a href=\""+ val[i].split(",")[0]+"\">"+ val[i].split(",")[1]+"</a></li>");
            }


            writer.println("</ul>");
            writer.println("</body>");
            writer.println("</html>");
            writer.close();
        }
        catch( IOException ioe )
        {
            System.err.println( ioe );
            System.exit( 1 );
        }

        System.out.println("ShowTime Begin");
    }
}
