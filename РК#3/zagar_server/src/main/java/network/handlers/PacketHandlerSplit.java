package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SplitMsg;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandSplit;
import utils.JSONHelper;

import java.io.IOException;
import java.util.Map;

public class PacketHandlerSplit {
  private final Logger log = LogManager.getLogger(PacketHandlerSplit.class);
  public PacketHandlerSplit(@NotNull Session session, @NotNull String json) {
    CommandSplit commandSplit;
    try {
      commandSplit = (CommandSplit) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      log.error("Failed to deserialize split command",ex);
      return;
    }
    MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    if(messageSystem==null)
      return;
    ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
    Player player=null;
    for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
      if(connection.getValue().equals(session)){
        player=connection.getKey();
      }
    }
    Message message = new SplitMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),commandSplit,player);
    messageSystem.sendMessage(message);
  }
}
