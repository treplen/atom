package model;

/**
 * @author apomosov
 */
public interface GameConstants {
  int MAX_PLAYERS_IN_SESSION = 10;
  int FIELD_WIDTH = 1000;
  int FIELD_HEIGHT = 1000;
  int FOOD_MASS = 10;
  int DEFAULT_PLAYER_CELL_MASS = 150;
  int VIRUS_MASS = 100;
  int FOOD_PER_SECOND_GENERATION = 0;
  int MAX_FOOD_ON_FIELD = 100;
  int NUMBER_OF_VIRUSES = 10;
  int MIN_MASS_FOR_EJECT = 80;
  int UNGOVERABLE_TIME = 500;
  int MAX_DISCONNECTING_TIME = 4_000;
  int JOINING_TIME = 1_000;
  int MIN_MASS_TO_SPLIT = 100;
  double SPLIT_VELOCITY = 0.01;
  int SPLIT_FOOD_LIFETIME = 3_000;
}
