// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {

      clientUI.display(msg.toString());

  }
  // pentru inchderea sv-ului


  //pentru exceptii
  @Override
  protected void connectionException(Exception exception) {
    System.out.println("Eroare la conexiune");
    System.exit(101);
  }



  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      if (message.startsWith("#")) {
        String[] command = message.split(" ");

        switch (command[0]) {
          case "#quit":
            quit();
            return;

          case "#logoff":
            closeConnection();
            return;

          case "#sethost":
                setHost(command[1]);
            return;

          case "#setport":
            if (!isConnected()) {

                try {
                  int port = Integer.parseInt(command[1]);
                  setPort(port);
                } catch (NumberFormatException e) {
                  clientUI.display("Erroare la port.");
                }
            } else {
              clientUI.display("nu se poate seta portu cand se ruleaza");
            }
            return;

          case "#login":
            if (!isConnected()) {
              openConnection();
            } else {
              clientUI.display("deja conectat");
            }
            return;

          case "#gethost":
            clientUI.display("host: " + getHost());
            return;

          case "#getport":
            clientUI.display( "port: "+ getPort());
            return;

          default:
            clientUI.display("nu se cunoaste comanda");
            return;
        }
      }

      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display("Could not send message to server. Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
