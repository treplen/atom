package zagar.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import zagar.network.packets.PacketEjectMass;
import org.jetbrains.annotations.NotNull;
import zagar.Game;
import zagar.network.packets.PacketSplit;

public class KeyboardListener implements KeyListener {
  @Override
  public void keyPressed(@NotNull KeyEvent e) {
      if (Game.socket != null && Game.socket.session != null) {
        if (Game.socket.session.isOpen()) {
          if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            try {
              new PacketSplit(Game.followX,Game.followY).write();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
          if (e.getKeyCode() == KeyEvent.VK_W) {
            try {
              new PacketEjectMass(Game.followX,Game.followY).write();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
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
         ;
        }
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
