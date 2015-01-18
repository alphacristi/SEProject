package tcp_connection;



/**
 * Created by ADY on 11.01.2015.
 */
public class Main {

    public static void main(String [] args)
         {
          System.out.println("Bla bla bla");
             CallerConnection ex=new CallerConnection("127.0.0.1",8012);
             CaleeConnection ex2=new CaleeConnection("127.0.0.1",8012);



           ex.Open();
             ex2.Open();


             String str="acesta este un exemplu";
             byte[] sir =str.getBytes();

             ex.Write(sir);
             ex.Write(sir);
             ex.Write(sir);

             byte [] received=ex2.Read();
             byte [] received2=ex2.Read();

             String receivedstring=new String(received);
             String receivedstring2=new String(received2);

             System.out.println(receivedstring);
             System.out.println(receivedstring2);

             ex.Close();
             ex2.Close();

         }



}
