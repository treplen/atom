package zagar.network.handlers;

import zagar.Game;

public class PacketHandlerAuthOk {
  public PacketHandlerAuthOk() {
    //System.out.println("AUTHORIZED");
    Game.state = Game.GameState.AUTHORIZED;
  }
}
