import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;

class Game{
  private boolean gameOver;
  private boolean death;
	private int score;
	private Main main;
	private Board gameBoard;
	private int damage = 0;
	private Room layout[] = new Room[4];
  private Door doors[] = new Door[3];
	private Room currentRoom;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int capacity_min = 0;
	private int capacity_max = 10;
	private int capacity;
  private BufferedImage marineSheet;
  private BufferedImage zombieSheet;
  private int delay = 1000/60;
	private ActionListener updateGame;
  private Timer gameTimer;
  private Player player;
  private Monster zombies[] = new Monster[1];
  private Switch end_switch;

	public Game(Main main){
    //reads in the marine sprite sheet.
    try{
      marineSheet = ImageIO.read(new File("marine.png"));
    }
    catch(FileNotFoundException e){
      System.out.println("File marine.png does not exist");
    }
    catch(EOFException e){
      System.out.println("End of input reached");
    }
    catch(ObjectStreamException e){
      System.out.println("File marine.png is corrupted");
    }
    catch(IOException e){
      System.out.println("Error reading in the file");
    }

    //reads in the zombie sprite sheet.
    try{
      zombieSheet = ImageIO.read(new File("zombie2.png"));
    }
    catch(FileNotFoundException e){
      System.out.println("File zombie2.png does not exist");
    }
    catch(EOFException e){
      System.out.println("End of input reached");
    }
    catch(ObjectStreamException e){
      System.out.println("File zombie2.png is corrupted");
    }
    catch(IOException e){
      System.out.println("Error reading in the file");
    }

		this.main = main;
    // creates player and game board.
    player = new Player(200, 100, marineSheet, this, gameBoard); 
		this.gameBoard = new Board(main, this, player, true);
		//creates items in game.
    Item room1_items[] = new Item[1];
		room1_items[0] = new Gun(100, 100, 10, 5, marineSheet, this, gameBoard);
		Item room2_items[] = new Item[1];
		zombies[0] = new Monster(620, 310, zombieSheet, this, player,
                             gameBoard);
    room2_items[0] = zombies[0];
		Item room3_items[] = new Item[2];
		room3_items[0] = new Gold(150, 350, 5, this, gameBoard);
    tram_switch = new Switch(200, 400, this, gameBoard);
    room3_items[1] = tram_switch;
		Item room4_items[] = new Item[1];
		room4_items[0] = new Key(650, 450, this, gameBoard);
    // creats the room instructions.
    String room1_instructions =
      "<html>" +
      "If you have a key to a room,<br>you can click on the room<br>" +
      "and click 'unlock' to unlock the<br>door to the room." +
      "</html>";
    String room2_instructions =
      "<html>" +
      "The station seems to be infested<br>by some aggressive life<br>" +
      "forms. Click on the monster and<br>click 'attack' to attack<br>" +
      "it." +
      "</html>";
    String room3_instructions =
      "<html>" +
      "Click the on the switch and click<br>'use to activate it. This<br>" +
      "will activate the tram and send<br>you to the main station<br>" +
      "</html>";
		// creates the rooms and items and adds them to the board.
		layout[0] = new Room(30, 30, 300, 200, 200, 100, room1_items, 
                         Color.gray, false, main, room1_instructions);
		layout[1] = new Room(340, 20, 360, 360, 350, 125, room2_items, 
                         Color.gray, false, main, room2_instructions);
		layout[2] = new Room(135, 240, 150, 250, 225, 250, room3_items, 
                         Color.gray, true, main, room3_instructions);
		layout[3] = new Room(580, 390, 150, 150, 655, 400, room4_items, 
                         Color.gray, true, main, "");
		currentRoom = layout[0];
    doors[0] = new Door(330, 100, 10, 50, gameBoard);
    doors[1] = new Door(200, 230, 50, 10, gameBoard);
    doors[2] = new Door(630, 380, 50, 10, gameBoard);
		gameBoard.createRoomsandDoors(layout, doors);
    zombies[0].setRoom(layout[1]);
		zombies[0].setGuard(layout[3]);
		room4_items[0].setGuard(layout[2]);
		gameOver = false;
		score = 0;
		capacity = 0;
    ActionListener updateGame = new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        player.Move();
        for(int i = 0; i < zombies.length; i++)
          zombies[i].Move();
        tram_switch.Update();
        if(reactor_switch != null)
          reactor_switch.Update();
        gameBoard.reDraw();
      }
    };
    gameTimer = new Timer(delay, updateGame);
    gameTimer.start();
    //createMainStation();
	}

  public void newPhase(){
    if(second_half == false){
      second_half = true;
      gameBoard.setDraw(false);
      createMainStation();
    }
    else
      setGameOver();
  }

  public void createMainStation(){
    player = null;
    player = new Player(925, 175, marineSheet, this, gameBoard); 
    this.gameBoard = null;
		this.gameBoard = new Board(main, this, player, true);
		//creates items in second half of game.
    zombies = null;
    zombies = new Monster[4];
    Item room1_items[] = new Item[1];
		room1_items[0] = new Gold(830, 200, 5, this, gameBoard);
		Item room2_items[] = new Item[3];
    BufferedImage doctorSheet;
    BufferedImage doctor_img; 
    try{
      doctorSheet = ImageIO.read(new File("doctor.png"));
      doctor_img = doctorSheet.getSubimage(32, 0, 32, 32);
      room2_items[0] = new Friendly(625, 175, doctor_img, gameBoard);
    }
    catch(FileNotFoundException e){
      System.out.println("File doctor.png does not exist");
    }
    catch(EOFException e){
      System.out.println("End of input reached");
    }
    catch(ObjectStreamException e){
      System.out.println("File doctor.png is corrupted");
    }
    catch(IOException e){
      System.out.println("Error reading in the file");
    }
    BufferedImage patient1_img = zombieSheet.getSubimage(456, 0, 57, 57);
    BufferedImage patient2_img = zombieSheet.getSubimage(399, 0, 57, 57);
    room2_items[1] = new Friendly(560, 255, patient1_img, gameBoard);
    room2_items[2] = new Friendly(680, 220, patient2_img, gameBoard);
		Item room3_items[] = new Item[3];
    zombies[0] = new Monster(250, 50, zombieSheet, this, player, gameBoard);
    zombies[1] = new Monster(225, 100, zombieSheet, this, player,
                             gameBoard);
		room3_items[0] = zombies[0];
    room3_items[1] = zombies[1];
    room3_items[2] = new Beaker(225, 50, main, this, gameBoard);
    Item room4_items[] = new Item[3];
    zombies[2] = new Monster(500, 550, zombieSheet, this, player, 
                             gameBoard);
    zombies[3] = new Monster(580, 550, zombieSheet, this, player,
                             gameBoard);
    room4_items[0] = zombies[2];
    room4_items[1] = zombies[3];
    reactor_switch = new Switch(550, 600, this, gameBoard);
    room4_items[2] = reactor_switch;
    // creats the room instructions.
    String room2_instructions =
      "<html>" +
      "There's a doctor standing in the<br>middle of the room you<br>" +
      "are in. It looks as though he is<br>attending to some patients<br>"+
      "He says that a leak in the<br>station;s nuclear reactor has<br>" +
      "caused many of the station's<br>workers to become sick. Some<br>" +
      "have even become hostile! He<br>has a cure for the sickness<br>" +
      "in his office to the west, but<br>it's currently overrun with<br>"+
      "hostile workers..." +
      "</html>";
    main.updateInstructions(
      "<html>" +
      "You've arrived at the main station.<br> You should look for<br>" +
      "someone who knows what's going on<br>here." +
      "</html>");
		// creates the rooms and items and adds them to the board.
    layout = null;
    layout = new Room[4];
		layout[0] = new Room(760, 150, 250, 150, 925, 275, room1_items, 
                         Color.gray, false, main, "");
		layout[1] = new Room(550, 125, 200, 200, 630, 250, room2_items, 
                         Color.gray, false, main, room2_instructions);
		layout[2] = new Room(220, 30, 320, 240, 520, 225, room3_items, 
                         Color.gray, false, main, "");
		layout[3] = new Room(400, 335, 300, 300, 655, 400, room4_items, 
                         Color.gray, true, main, "");
    // creates doors
    doors = null;
    doors = new Door[3];
    doors[0] = new Door(750, 200, 10, 50, gameBoard);
    doors[1] = new Door(540, 200, 10, 50, gameBoard);
    doors[2] = new Door(580, 325, 50, 10, gameBoard);
		currentRoom = layout[0];
		gameBoard.createRoomsandDoors(layout, doors);
    zombies[0].setRoom(layout[2]);
    zombies[1].setRoom(layout[2]);
    zombies[2].setRoom(layout[3]);
    zombies[3].setRoom(layout[3]);
		room3_items[2].setGuard(layout[3]);
    //Remap the gameBoard to main.
    main.replaceBoard();
    this.currentRoom = layout[0];
    gameBoard.setSelect(925, 275);
    gameBoard.reDraw();
  }

  public Player getPlayer(){
    return player;
  }

	public Room getCurrentRoom(){
		return currentRoom;
	}

	public void Pause(){
		main.deactivateButtons();
	}

	public void Unpause(){
		main.setButtons(gameBoard.getSelectItem());
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
        target.setInstructions();
      }
			currentRoom = target;
    }
		gameBoard.reDraw();	
	}

	public void takeItem(Item target){
    if(target.getVisible()){
		  target.toggleVisible();
		  inventory.add(target);
		  //if item is a weapon, we increase the players damage.
		  if(target.getType() == "gun")
			  damage += target.getDamage();
		  main.updateInventory(target);
		  increaseScore(10);
    }
	}

	public void attackItem(Item target){
		//if players damage is greater than the health of the target, target
		// dies.
		if(target.getHealth() <= damage)
			target.toggleVisible();
		else
			main.updateInstructions(
        "<html>"+
        "Damage too low to attack <br>target. Pick up a weapon to<br>"+
        "attack the target<br>"+
        "</html>");
		increaseScore(10);
	}

  public int getScore(){
		return score;
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

  public void increaseScore(int dtscore){
		score += dtscore;
		main.updateScore(score);
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
