package zagar.network.handlers;

import protocol.CommandAuthFail;
import protocol.CommandAuthOk;
import zagar.Game;
import zagar.util.JSONHelper;

import java.io.IOException;

public class PacketHandlerAuthOk {
  public PacketHandlerAuthOk(String json) {

    CommandAuthOk commandAuthOk;
    try {
      commandAuthOk = (CommandAuthOk) JSONHelper.fromSerial(json);
    } catch (IOException IOex ){
      IOex.printStackTrace();
      return;
    }catch(ClassNotFoundException CNFex){
      CNFex.printStackTrace();
      return;
    }
    Game.id = commandAuthOk.getId();
    Game.state = Game.GameState.AUTHORIZED;
  }
}
