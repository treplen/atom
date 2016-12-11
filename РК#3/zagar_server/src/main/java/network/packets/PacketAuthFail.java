package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import protocol.CommandAuthFail;
import utils.JSONHelper;

import java.io.IOException;

public class PacketAuthFail {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketAuthFail.class);
  @NotNull
  private final String login;
  @NotNull
  private final String token;
  @NotNull
  private final String cause;

  public PacketAuthFail(@NotNull String login, @NotNull String token, @NotNull String cause) {
    this.login = login;
    this.token = token;
    this.cause = cause;
  }

  public void write(@NotNull Session session) throws IOException {
    String msg = JSONHelper.toSerial(new CommandAuthFail(login, token, cause));
    log.info("Sending [" + msg + "]");
    try {
      session.getRemote().sendString(msg);
    } catch (WebSocketException ex) {
      log.error("Failed to send", ex);
    }
    try {
      session.disconnect();
    } catch (WebSocketException ex) {
    }
  }
}
