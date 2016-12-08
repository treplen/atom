package model;

import utils.IDGenerator;
import utils.SequentialIDGenerator;

/**
 * @author apomosov
 */
public class PlayerCell extends Cell {
  private final int id;
  private DoubleVector velocity;
  private boolean ungovernable;
  private double becameUngovernable;

  public PlayerCell(int id, int x, int y) {
    super(x, y, GameConstants.DEFAULT_PLAYER_CELL_MASS);
    this.id = id;
    ungovernable = false;
  }

  public int getId() {
    return id;
  }

  public DoubleVector getVelocity(){return velocity;}
  public void setVelocity(DoubleVector velocity){this.velocity = velocity;}

  public boolean getUngovernable(){return  ungovernable;}
  public void setUngovernable(boolean ungovernable){
    this.ungovernable = ungovernable;
    becameUngovernable = System.currentTimeMillis();
  }

  public void updateVelocity(double x, double y){
    if(ungovernable){
      if(System.currentTimeMillis() - becameUngovernable > GameConstants.UNGOVERABLE_TIME)
        ungovernable = false;
      return;
    }

    double dx = x-getX();
    double dy = y-getY();
    double r = Math.sqrt(dx*dx + dy*dy);

    dx = dx / r;
    dy = dy / r;

    setVelocity(
            new DoubleVector(
                    dx * (0.5 + 1/getMass()),
                    dy * (0.5 + 1/getMass()))
    );
  }

}
