package model;

import main.ApplicationContext;
import org.jetbrains.annotations.NotNull;
import utils.EatComparator;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author apomosov
 */
public class Player {
  public static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id;
  @NotNull
  private String name;
  @NotNull
  private final List<PlayerCell> cells = new CopyOnWriteArrayList<>();
  @NotNull
  EatComparator eatComparator = new EatComparator();
  private double lastUpdate;


  public Player(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
    lastUpdate = System.currentTimeMillis();
    addCell(new PlayerCell(Cell.idGenerator.next(), 0, 0));
  }

  public void addCell(@NotNull PlayerCell cell) {
    cells.add(cell);
  }

  public void removeCell(@NotNull PlayerCell cell) {
    cells.remove(cell);
  }

  @NotNull
  public String getName() {
    return name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public List<PlayerCell> getCells() {
    return cells;
  }

  public int getId() {
    return id;
  }

  public void updateVelocity(double x, double y){
    for (PlayerCell playerCell:cells) {
      double dx = x-playerCell.getX();
      double dy = y-playerCell.getY();
      double r = Math.sqrt(dx*dx + dy*dy);

      dx = dx / r;
      dy = dy / r;

      if(r < playerCell.getMass()){
        dx*=r/playerCell.getMass();
        dy*=r/playerCell.getMass();
      }

      playerCell.setVelocity(
              new DoubleVector(
                      dx * (0.5 + 1/playerCell.getMass()),
                      dy * (0.5 + 1/playerCell.getMass()))
      );

    }
  }

  public void move(double x, double y){
    updateVelocity(x,y);
    double dTime =  System.currentTimeMillis() - lastUpdate;
    for (PlayerCell playerCell:cells){

      int newX = (int)(playerCell.getX() + dTime*playerCell.getVelocity().getX());
      int newY = (int)(playerCell.getY() + dTime*playerCell.getVelocity().getY());

      if (newX < 0 )
        newX = 0;
      if (newX > GameConstants.FIELD_WIDTH)
        newX = GameConstants.FIELD_WIDTH;

      if (newY < 0 )
        newY = 0;
      if (newY > GameConstants.FIELD_HEIGHT)
        newY = GameConstants.FIELD_HEIGHT;

      playerCell.setX(newX);
      playerCell.setY(newY);
    }
    lastUpdate = System.currentTimeMillis();
  }

  public boolean eate(Cell food){
    for(PlayerCell playerCell: cells) {
      double dx = playerCell.getX() - food.getX();
      double dy = playerCell.getY() - food.getY();

      if ((Math.sqrt(dx*dx+dy*dy) < (playerCell.getMass() + food.getMass()))&&
              eatComparator.compare(playerCell,food)==1
              ) {
        if(food.getClass() != Virus.class){
          playerCell.setMass(playerCell.getMass()+food.getMass());
          return true;
        }else {
          //Eject
          return true;
        }
      }
    }
   return false;
  }

  @NotNull
  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
