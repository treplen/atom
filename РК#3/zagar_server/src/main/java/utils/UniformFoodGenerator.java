package utils;

import mechanics.Mechanics;
import model.Field;
import model.Food;
import model.PlayerCell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ticker.Ticker;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author apomosov
 */
public class UniformFoodGenerator  implements FoodGenerator {
  @NotNull
  private final static Logger log = LogManager.getLogger(UniformFoodGenerator.class);

  @NotNull
  private final Field field;
  private final int threshold;
  private final double foodPerSecond;

  public UniformFoodGenerator(@NotNull Field field, double foodPerSecond, int threshold) {
    this.field = field;
    this.threshold = threshold;
    this.foodPerSecond = foodPerSecond;
  }

  public void run() {
    log.info("UniformFoodGenerator " + " started");
     new Thread(()->{
       Ticker ticker = new Ticker(this, (int)Math.ceil(foodPerSecond));
       ticker.loop();
     }).start();
  }

  @Override
  public void tick(long elapsedNanos) {

    if (field.getFoods().size() < threshold) {
      Random random = new Random();
      random.nextInt(field.getWidth() - 2);
      random.nextInt(field.getHeight() - 2);

      Food food = new Food(
              random.nextInt(field.getWidth() - 2),
              random.nextInt(field.getHeight() - 2)
      );
      field.addFood(food);
//      log.info("UniformFoodGenerator ticked, field size now {}", field.getFoods().size());
      //int toGenerate = (int) Math.ceil(foodPerSecond * elapsedNanos / 1_000_000_000.);
    }
  }
}
