package zagar.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zagar.GameThread;
import zagar.network.packets.PacketEjectMass;
import org.jetbrains.annotations.NotNull;
import zagar.Game;

public class KeyboardListener implements KeyListener {
  @NotNull
  private static final Logger log = LogManager.getLogger(KeyboardListener.class);
  @Override
  public void keyPressed(@NotNull KeyEvent e) {
      if (Game.socket != null && Game.socket.session != null) {
        if (Game.socket.session.isOpen()) {
          if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Game.rapidSplit = true;
          }
          if (e.getKeyCode() == KeyEvent.VK_W) {
            try {
              new PacketEjectMass(Game.followX,Game.followY).write();
            } catch (IOException e1) {
              log.error("Failed to send eject mass packet",e1);
            }
            Game.rapidEject = true;
          }
          if (e.getKeyCode() == KeyEvent.VK_T) {
            Game.rapidEject = true;
          }
          if (e.getKeyCode() == KeyEvent.VK_R) {
            if (Game.player.size() == 0) {
              Game.respawn();
            }
          }
        }
      }
  }

  @Override
  public void keyReleased(@NotNull KeyEvent e) {
    if (Game.socket != null && Game.socket.session != null) {
      if (Game.socket.session.isOpen()) {
        if (e.getKeyCode() == KeyEvent.VK_T) {
          Game.rapidEject = false;
        }
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
