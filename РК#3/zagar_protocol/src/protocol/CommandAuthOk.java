package protocol;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author apomosov
 */
public final class CommandAuthOk extends Command implements Serializable{
  @NotNull
  public static final String NAME = "auth_ok";

  public CommandAuthOk() {
    super(NAME);
  }
}
