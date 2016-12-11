package replication;

import leaderboard.LeaderboardState;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import utils.Configurations;
import utils.JSONDeserializationException;
import utils.JSONHelper;
import utils.PropertiesReader;

import java.io.*;
import java.util.Map;

/**
 * @author Alpi
 * @since 31.10.16
 */
public interface Replicator {
  void replicate();

  default void sendLeaderboard() {

    String leaderboard[]= LeaderboardState.get();
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
      for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
        if (gameSession.getPlayers().contains(connection.getKey())) {
          try {
            new PacketLeaderBoard(leaderboard).write(connection.getValue());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
