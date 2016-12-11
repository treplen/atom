package zagar.network.handlers;

import protocol.CommandAuthFail;
import protocol.CommandAuthOk;
import zagar.Game;
import zagar.util.JSONHelper;

public class PacketHandlerAuthOk {
  public PacketHandlerAuthOk(String json) {

    CommandAuthOk commandAuthOk;
    try {
      commandAuthOk = (CommandAuthOk) JSONHelper.fromSerial(json);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    Game.id = commandAuthOk.getId();
    Game.state = Game.GameState.AUTHORIZED;
  }
}
