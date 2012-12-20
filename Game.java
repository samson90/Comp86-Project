import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import javax.imageio.*;

class Game{
  private boolean gameOver;//signifies a gameover
  boolean second_half;//signifies to switch to the second board
  private boolean death;//signifies player has died.
  private int score;
  private int gold_count;
  private Main main;
  private Board gameBoard;
  private int damage = 0;
  private int counter = 0;
  private Room layout[] = new Room[4];
  private Door doors[] = new Door[3];
  private Room currentRoom;//player room is in.
  //item possessed by the player
  private ArrayList<Item> inventory = new ArrayList<Item>();
  private int capacity_min = 0;
  private int capacity_max = 10;
  //number of items the player can hold. Can be adjusted using the 
  //scrollbar.
  private int capacity; 
  private BufferedImage marineSheet;//Sprite sheets used for animation
  private BufferedImage zombieSheet;
  private BufferedImage switchSheet;

  private int delay = 1000/60;
  private ActionListener updateGame;
  private Timer gameTimer;
  private int time = 0;
  private Player player;
  private Monster zombies[] = new Monster[3];
  private Gold gold[] = new Gold[20];
  private Switch tram_switch;
  private Switch reactor_switch = null;
  private boolean pause;
  private int difficulty = 2;

  public Game(Main main){
    //reads in the marine sprite sheet.
    marineSheet = readSheet("marine.png");
    zombieSheet = readSheet("zombie.png");
    switchSheet = readSheet("lever.png");
    this.main = main;
    // creates player and game board.
    player = new Player(200, 100, marineSheet, this, gameBoard); 
    this.gameBoard = new Board(main, this, player, true);
    //creates items in game.
    Item room1_items[] = new Item[5];
    room1_items[0] = new Gun(100, 100, 10, 5, marineSheet, this, gameBoard);
    //generates gold in room 1.
    gold[0] = new Gold(180, 136, this, gameBoard, 500);
    gold[1] = new Gold(99, 67, this, gameBoard, 500);
    gold[2] = new Gold(135, 94, this, gameBoard, 500);
    gold[3] = new Gold(156, 33, this, gameBoard, 500);
    for (int i = 0; i < 4; i++)
      room1_items[i + 1] = gold[i];
    //room 2 items
    Item room2_items[] = new Item[7];
    zombies[0] = new Monster(620, 310, zombieSheet, marineSheet, this, 
                             player, gameBoard);
    room2_items[0] = zombies[0];
    gold[4] = new Gold(630, 104, this, gameBoard, 1000);
    gold[5] = new Gold(654, 49, this, gameBoard, 1000);
    gold[6] = new Gold(654, 356, this, gameBoard, 1000);
    gold[7] = new Gold(518, 360, this, gameBoard, 1000);
    gold[8] = new Gold(476, 57, this, gameBoard, 1000);
    gold[9] = new Gold(388, 193, this, gameBoard, 1000);
    for (int i = 0; i < 6; i++)
      room2_items[i + 1] = gold[i + 4];
    // room 3 items
    Item room3_items[] = new Item[5];
    tram_switch = new Switch(200, 400, switchSheet, this, gameBoard);
    room3_items[0] = tram_switch;
    gold[10] = new Gold(218, 286, this, gameBoard, 2000);
    gold[11] = new Gold(193, 347, this, gameBoard, 2000);
    gold[12] = new Gold(153, 421, this, gameBoard, 2000);
    gold[13] = new Gold(255, 424, this, gameBoard, 2000);
    for (int i = 0; i < 4; i ++)
      room3_items[i + 1] = gold[i + 10];
    Item room4_items[] = new Item[9];
    BufferedImage key_img = readSheet("keycard.png").getSubimage(64, 64, 
                                                                 64, 64);
    room4_items[0] = new Key(650, 450, this, gameBoard, key_img);
    zombies[1] = new Monster(890, 550, zombieSheet, marineSheet, this, 
                             player, gameBoard);
    zombies[2] = new Monster(840, 580, zombieSheet, marineSheet, this, 
                             player, gameBoard);
    room4_items[1] = zombies[1];
    room4_items[2] = zombies[2];
    gold[14] = new Gold(804, 470, this, gameBoard, 1500);
    gold[15] = new Gold(631, 606, this, gameBoard, 1500);
    gold[16] = new Gold(766, 609, this, gameBoard, 1500);
    gold[17] = new Gold(900, 452, this, gameBoard, 1500);
    gold[18] = new Gold(688, 466, this, gameBoard, 1500);
    gold[19] = new Gold(673, 502, this, gameBoard, 1500);
    for (int i = 0; i < 6; i++)
      room4_items[i + 3] = gold[i + 14];
    
    // creates the rooms and items and adds them to the board.
    layout[0] = new Room(30, 30, 300, 200, 200, 100, room1_items, 
                         Color.gray, false, main);
    layout[1] = new Room(340, 20, 360, 360, 350, 125, room2_items, 
                         Color.gray, false, main);
    layout[2] = new Room(135, 240, 150, 250, 225, 250, room3_items, 
                         Color.gray, true, main);
    layout[3] = new Room(580, 390, 350, 250, 655, 400, room4_items, 
                         Color.gray, true, main);
    currentRoom = layout[0];
    //creates doors.
    doors[0] = new Door(330, 100, 10, 50, gameBoard);
    doors[1] = new Door(200, 230, 50, 10, gameBoard);
    doors[2] = new Door(630, 380, 50, 10, gameBoard);
    //initializes the gameboard.
    gameBoard.createRoomsandDoors(layout, doors);
    //initializes the enemies and ties keys to doors.
    zombies[0].setRoom(layout[1]);
    zombies[0].setGuard(layout[3]);
    room4_items[0].setGuard(layout[2]);
    zombies[1].setRoom(layout[3]);
    zombies[2].setRoom(layout[3]);

    gameOver = false;
    score = 0;
    gold_count = 0;
    capacity = 0;
    //handles game timer.
    ActionListener updateGame = new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        if (!pause && !gameOver){
          time++;
          player.Move();
          for(int i = 0; i < zombies.length; i++)
            zombies[i].Move();
          for(int i = 0; i < gold.length; i++)
            gold[i].update(); 
          tram_switch.Update();
          if(reactor_switch != null)
            reactor_switch.Update();
          //toggles room locks every 3 seconds.
          if ((time / 100) % 2 == 0){
            for(int i = 0; i < layout.length; i++)
              layout[i].setLock(false);
            for(int i = 0; i < doors.length; i++)
              doors[i].setVisible(true); 
          } 
          else{
            for(int i = 0; i < layout.length; i++)
              layout[i].setLock(true);
            for(int i = 0; i < doors.length; i++)
              doors[i].setVisible(false); 
          } 
          gameBoard.reDraw();
        }
      }
    };
    gameTimer = new Timer(delay, updateGame);
    gameTimer.start();
  }

  //reads in sprite sheet.
  private BufferedImage readSheet(String filename){
    BufferedImage sheet = null;
    try{
      sheet = ImageIO.read(new File(filename));
    }
    catch(FileNotFoundException e){
      System.out.println("File " + filename + " does not exist");
    }
    catch(EOFException e){
      System.out.println("End of input reached");
    }
    catch(ObjectStreamException e){
      System.out.println("File " + filename + " is corrupted");
    }
    catch(IOException e){
      System.out.println("Error reading in the file");
    }
    return sheet;
  }
  
  public Player getPlayer(){
    return player;
  }

  public Room getCurrentRoom(){
    return currentRoom;
  }

  public void Pause(){
    main.deactivateButtons();
    pause = true;
  }

  public void Unpause(){
    main.setButtons(gameBoard.getSelectItem());
    pause = false;
  }

  public void moveRoom(Room target){
    if(target.getLock() == true)
      main.updateInstructions(
        "<html>" +
        "You can't go in because the door<br> to this room is either<br>"+
        "locked or obstructed." +
        "</html>");
    else{
      if(currentRoom != target){
        player.setPosition(target.getstartx(), target.getstarty());
        player.setDestination(target.getstartx(), target.getstarty());
        gameBoard.setSelect(target.getstartx(), target.getstarty());
      }
      currentRoom = target;
    }
    gameBoard.reDraw();	
  }

  public void takeItem(Item target){
    if(target.getVisible()){
      target.setVisible(false);
      //if item is a weapon, we increase the players damage.
      if(target.getType() == "gun")
        damage += target.getDamage();
      if(target.getType() != "gold"){
        inventory.add(target);
        main.updateInventory(target);
        score += 10;
        main.updateScore(score);
      }
      else
        gold_count += 1;
        main.updateGold(gold_count);
    }
  }

  public void attackItem(Item target){
    //if players damage is greater than the health of the target, target
    // dies.
    if(target.getVisible()){
      if(target.getHealth() <= damage)
        target.setVisible(false);
      else
        main.updateInstructions(
          "<html>"+
          "Damage too low to attack <br>target. Pick up a weapon to<br>"+
          "attack the target<br>"+
          "</html>");
      score+=10;
      main.updateScore(score);
    }
  }

  public int getScore(){
    return score;
  }

  public int getGold(){
    return gold_count;
  }

  public int getDifficulty(){
    return difficulty;
  }
 
  public void setDifficulty(int difficulty){
    this.difficulty = difficulty;
  }

  public int getTime(){
    return time;
  }

  public int getNumItems(){
    return inventory.size();
  }

  public int minCapacity(){
    return capacity_min;
  }

  public int maxCapacity(){
    return capacity_max;
  }

  public int getCapacity(){
    return capacity;
  }
	
  public void setCapacity(int capacity){
    this.capacity = capacity;
  }

  public Board getGameBoard(){
    return gameBoard;
  }

  public boolean getGameOver(){
    return gameOver;
  }

  public void setGameOver(){
    gameOver = true;
  }

  public boolean getDeath(){
    return death;
  }
  
  public void setDeath(){
    death = true;
  }
}
