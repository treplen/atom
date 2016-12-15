package zagar.network.handlers;

import protocol.CommandLeaderBoard;
import zagar.util.JSONDeserializationException;
import zagar.util.JSONHelper;
import zagar.Game;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PacketHandlerLeaderBoard {
  public PacketHandlerLeaderBoard(@NotNull String json) {
    CommandLeaderBoard commandLeaderBoard;
    try {
      commandLeaderBoard = (CommandLeaderBoard) JSONHelper.fromSerial(json);
    } catch (IOException IOex ){
      IOex.printStackTrace();
      return;
    }catch(ClassNotFoundException CNFex){
      CNFex.printStackTrace();
      return;
    }
    Game.leaderBoard = commandLeaderBoard.getLeaderBoard();
  }
}
