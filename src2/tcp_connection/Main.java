package tcp_connection;



/**
 * Created by ADY on 11.01.2015.
 */
public class Main {

    public static void main(String [] args)
         {
       /*    System.out.println("Bla bla bla");
             CallerConnection ex=new CallerConnection("127.0.0.1",8012);
             CaleeConnection ex2=new CaleeConnection("127.0.0.1",8012);*/

             ServerConnection server=new ServerConnection(8012);
             server.Open();

         /*    ex.Open();
             ex2.Open();*/




/*
             ex.Write(sir);

             byte [] received=ex2.Read();

             String receivedstring=new String(received);

             System.out.println(receivedstring);

             ex.Close();
             ex2.Close();
*/
         }

    public void test()
    {
        System.out.println("Bla bla bla");
        CallerConnection ex=new CallerConnection("127.0.0.1",8012);
        CaleeConnection ex2=new CaleeConnection("127.0.0.1",8012);

        ex.Open();
        ex2.Open();

        String str="acesta este un exemplu";
        byte[] sir =str.getBytes();

        ex.Write(sir);

        byte [] received=ex2.Read();

        String receivedstring=new String(received);

        System.out.println(receivedstring);

        ex.Close();
        ex2.Close();

    }

}
