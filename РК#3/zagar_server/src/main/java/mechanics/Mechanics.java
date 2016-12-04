package mechanics;

import main.ApplicationContext;
import main.Service;
import matchmaker.MatchMaker;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.ReplicateMsg;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import model.Virus;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandMove;
import protocol.CommandSplit;
import protocol.model.Cell;
import protocol.model.Food;
import ticker.Tickable;
import ticker.Ticker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by apomosov on 14.05.16.
 */
public class Mechanics extends Service implements Tickable {
  @NotNull
  private final static Logger log = LogManager.getLogger(Mechanics.class);

  public Mechanics() {
    super("mechanics");
  }

  @Override
  public void run() {
    log.info(getAddress() + " started");
    try {
      Thread.currentThread().sleep(2_000);
    }catch (Exception e){}
    Ticker ticker = new Ticker(this, 25);
    ticker.loop();
  }

  @Override
  public void tick(long elapsedNanos) {
    try {
      Thread.sleep(40);
    } catch (InterruptedException e) {
      log.error(e);
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }

    eateAll();

 //   log.info("Start replication");
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new ReplicateMsg(this.getAddress());
    messageSystem.sendMessage(message);

    //execute all messages from queue
    messageSystem.execForService(this);
  }

  private void eateAll() {
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
      //ArrayList<model.Food> foodArrayList = new ArrayList<>();

      //try to eate another Players
      for (Player player : gameSession.getPlayers())
        for(Player enemy: gameSession.getPlayers())
          for (PlayerCell playerCell:enemy.getCells())
            if(player.eate(playerCell))
              //foodArrayList.add(food);
              enemy.getCells().remove(playerCell);

      //try to eate Food
      for (Player player : gameSession.getPlayers())
        for(model.Food food:gameSession.getField().getFoods())
          if(player.eate(food))
            //foodArrayList.add(food);
            gameSession.getField().getFoods().remove(food);

      //try to eate Virus
      for (Player player : gameSession.getPlayers())
        for(model.Virus virus:gameSession.getField().getViruses())
          if(player.eate(virus))
            //foodArrayList.add(food);
            gameSession.getField().getViruses().remove(virus);

      //for(model.Food food:foodArrayList) {
        //gameSession.getField().getFoods().remove(food);
      //}
    }
  }

  public void EjectMass (@NotNull  Player player,@NotNull CommandEjectMass commandEjectMass)
  {
    log.info("{} wants to eject mass  <{},{}> (in thread {})",player,commandEjectMass.getDx(),commandEjectMass.getDy(),Thread.currentThread());
  }

  public void Move (@NotNull Player player, @NotNull CommandMove commandMove)
  {
    player.move(commandMove.getDx(),commandMove.getDy());
    //log.info("{} wants to move <{},{}> (in thread {})",player,commandMove.getDx(),commandMove.getDy(),Thread.currentThread());
  }

  public void Split (@NotNull Player player, @NotNull CommandSplit commandSplit)
  {
    //log.info("{} wants to split (in thread {})",player,Thread.currentThread());
  }
}
