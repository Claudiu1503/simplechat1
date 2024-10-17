import ocsf.server.*;
import java.io.*;

/**
 * This class overrides some of the methods in the abstract
 * superclass in order to give more functionality to the server.
 *
 * @version July 2000
 */
public class EchoServer extends AbstractServer
{
  // Class variables *************************************************

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;

  // Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port)
  {
    super(port);
  }

  // Instance methods ************************************************

  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client) {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg); // Forward the message to all clients
  }

  /**
   * This method overrides the one in the superclass. Called
   * when the server starts listening for connections.
   */
  protected void serverStarted() {
    System.out.println("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass. Called
   * when the server stops listening for connections.
   */
  protected void serverStopped() {
    System.out.println("Server has stopped listening for connections.");
  }

  /**
   * This method is called each time a client connects to the server.
   *
   * @param client The client that just connected.
   */
  @Override
  protected void clientConnected(ConnectionToClient client) {
    System.out.println("Clientul s-a conectat: " + client);
  }

  /**
   * This method is called each time a client disconnects from the server.
   *
   * @param client The client that disconnected.
   */
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
    System.out.println("Clientul s-a deconectat: " + client);
  }

  /**
   * This method is called when an exception occurs with a client, such as
   * when the client disconnects abruptly.
   *
   * @param client The client that encountered the exception.
   * @param exception The exception that occurred.
   */

  @Override
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
    System.out.println("Client disconnected :: a plecat la bere:))   " + client);
  }

  // Class methods ***************************************************


  public static void main(String[] args)
  {
    int port = 0; // Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); // Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; // Set port to 5555
    }

    EchoServer sv = new EchoServer(port);

    try
    {
      sv.listen(); // Start listening for connections
    }
    catch (Exception ex)
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
// End of EchoServer class
