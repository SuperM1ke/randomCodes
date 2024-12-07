import java.util.Scanner;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class Game
{
    private Fellowship fellowship;
    private Labyrinth labyrinth;
    private Scanner scanner;

    public Game()
    {
        this.labyrinth = new Labyrinth();
        this.scanner = new Scanner(System.in);
    }   

    public void displayWelcome()   // display welcome message
    {
        System.out.println("Welcom to Fellowship: a Java Adventure in Middle Earth! ");
        System.out.println();

        System.out.println("Instructions");
        System.out.println("============");
        System.out.println("The Fellowship team has 4 members. The leader is a hobbit.");
        System.out.println("You will need to choose 3 other members.");
        System.out.println("Your job is to deliver the secret code to the Java Wizard on Mount Api");
        System.out.println();

    }

    public void setupLabyrinth()  // read from the labyrinth file and set labyrinth
    {
        System.out.println("* Setting up Middle Earth... ");
        System.out.println("  Reading cave information from labyrinth.txt ");

        try 
        {
            List<Cave> caves = FileIO.readCaves("labyrinth.txt");
            labyrinth.setCaves(caves);
        }
        catch (IOException e)
        {
            System.out.println("Error reading labyrinth file" + e.getMessage());
        }
        System.out.println();

    }

    public void setFellowship()   // initiliza the fellowship team 
    {
        fellowship = new Fellowship();
        System.out.println("* Setting up the Fellowship team ...");
        System.out.println("Choose the Fellowship members");
        System.out.println("The leader is a hobbit");
        System.out.println("You need to choose 3 more members");
        for (int i = 0; i < 3; i ++)
        {   
            int choice = -1;
            while (choice != 1 && choice != 2)   // only accecpt 1 and 2 choice, validate input
            {   

                System.out.println("Choose #" + ( i+1 ) + " member: ");
                System.out.println("1): Elf \n2): Dwarf");
                if (scanner.hasNextInt())
                {
                    choice =scanner.nextInt();
                    scanner.nextLine();
                }
                else 
                {
                    scanner.next();  // consume the invalid input
                }

                if (choice == 1)
                {
                    fellowship.addMember(new Elf());
                    System.out.println("choice: 1");
                    System.out.println("Added a elf to the fellowship");
                }
                else if(choice == 2)
                {
                    fellowship.addMember(new Dwarf());
                    System.out.println("choice: 2");
                    System.out.println("Added a dwarf to the fellwoship");
                }
                else 
                {
                    System.out.println("invalid choice, try again please choose from 1 and 2");
                    i--;
                }
            }
        }
    }

    public void displayFellowship()   // display fellowship info
    {
        System.out.println("Fellowship member List");
        System.out.println("======================");
        for (int i = 0; i < fellowship.getMembers().size(); i++)
        {
            Creature member =fellowship.getMembers().get(i);
            String codeIndicator = member.hasCode()? "*" : "";
            String weaponIndicator = member.hasWeapon()? "(weapon)" : "";
            String memberInfo = String.format("%s%-8s damage: %-2d %s", codeIndicator, member.getName() +"...", member.getDamagePoints(), weaponIndicator);
            System.out.println((i+1)+ ") " +memberInfo);

        }
        System.out.println("(The * indicates who has the secret code)");
    }

    public void startJourney()  
    {
        displayFellowship();
        System.out.println("Press Enter to start the journey...");
        scanner.nextLine();

        int currentCaveId = 1;
        boolean journeyEnd = false;

        while (!journeyEnd)
        {
            System.out.println("Entered cave" + currentCaveId);
            Cave currentCave = labyrinth.getCave(currentCaveId);
            currentCave.visit();
            
            // check if reach mount api and secret code with, if though, the game end 
            if (currentCave.getId() == 100 )
            {
               if (fellowship.hasCode())
               {
                    System.out.println("The fellowship reached the Mount API with the secret code! ");
                    journeyEnd = true;
                    displayEndSummary(true);
                    break;
               }
               else
               {
                    System.out.println("The fellowship reached the Mount API but no code ");
                    journeyEnd = true;
                    displayEndSummary(false);
                    return;
               }
            }
            
            // if fellowship meet a enemy, fight needed
            if (Math.random() < 0.75)
            {
                Creature enemy = generateEnemy();
                currentCave.setCreature(enemy);
                System.out.println("*** Fighting " + enemy.getName() + " ***");
                fight(enemy);
            }
            else 
            {
                System.out.println("This cave has no creature, members will rest and recover... ");
                for (Creature member : fellowship.getMembers())
                {
                    member.decreaseDamagePoints(1);
                }
            }

            //check if all members alive after fight, if all died, game over
            boolean allMemberDead = true;
            for (Creature member : fellowship.getMembers())
            {
                if (member.isAlive()) 
                {
                    allMemberDead = false;
                    break;
                }
            }

            if(allMemberDead)
            {
                System.out.println("all fellowship member are dead, gameover");
                journeyEnd = true;
                displayEndSummary(false);
                return;
            }
            displayVisitStats(currentCave);
            currentCaveId = chooseNextCave(currentCave); 
        }
    }

    // generate random enemy in the cave if there would be 
    public Creature generateEnemy()
    {
        double rand = Math.random();
        if (rand < 0.33)
        {
            return new Orc();
        }
        else if (rand < 0.66)
        {
            return new Troll();
        }
        else 
        {
            return new Goblin();
        }
    }
    // calculate the winrate
    private double getWinrate(Creature member, Creature enemy)
    {
        int powerDiff = member.getPower() - enemy.getPower();
        int caseValue = Math.max (-1, Math.min(4,powerDiff));
        switch(caseValue)
        {
            case 4:
                return 0.9;
            case 3:
                return 0.8;
            case 2:
                return 0.7;
            case 1:
                return 0.6;
            case 0:
                return 0.5;
            default:
                return 0.4; // when power of enemy greater than member ( case value == -1)
        }
    }
    public void fight(Creature enemy)
    {
        System.out.println("Choose who is going to fight");
        // display all members alive and if has a special weapon 
        for (int i = 0; i < fellowship.getMembers().size(); i++)
        {
            Creature member =fellowship.getMembers().get(i);
            String codeIndicator = member.hasCode()? "*" : "";
            String weaponIndicator = member.hasWeapon()? "(weapon)" : "";
            String memberInfo = String.format("%s%-8s damage: %-2d %s", codeIndicator, member.getName() +"...", member.getDamagePoints(), weaponIndicator);
            System.out.println((i+1)+ ") " +memberInfo);

        }

        int choice = -1;
        while (choice < 1 || choice > fellowship.getMembers().size())
        {
            System.out.println("Choice: ");
            if (scanner.hasNextInt())
            {
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            else
            {
                scanner.next();
            }
            if (choice <1 || choice > fellowship.getMembers().size())
            {
                System.out.println("invalid choice ,please try again");
            }
        }

        Creature chosenMember = fellowship.getMembers().get(choice - 1);
        System.out.println("You have chosen: " + chosenMember.getName());

        // check if a member has a weapon and decide if use it 
        if (chosenMember.hasWeapon())
        {
            String useWeapon = "";
            while (!useWeapon.equalsIgnoreCase("Y") && !useWeapon.equalsIgnoreCase("N"))
            {
                System.out.println(chosenMember.getName() + " has a special weapon \n Use it? (y/n): ");
                useWeapon = scanner.nextLine();
                if (!useWeapon.equalsIgnoreCase("Y") && !useWeapon.equalsIgnoreCase("N"))
                {
                    System.out.println("invalid input,please enter 'y' or 'n' ");
                }
            }
           
            if (useWeapon.equalsIgnoreCase("Y"))
            {
                chosenMember.useWeapon();
                chosenMember.winFights();
                enemy.loseFights();
                enemy.setAlive(false);
                System.out.println("the fellowship team has WON the fight");
                System.out.println(enemy.getName() + " is dead");
                System.out.println("\n Press ENTER to continue ...");
                scanner.nextLine();
            }
            else 
            {
                noWeaponFight(chosenMember, enemy);
            }
        }
        else
        {
            noWeaponFight(chosenMember, enemy);
        }
    }

    // when no weapon used, calculate the result of a fight
    public void noWeaponFight(Creature chosenMember, Creature enemy)
    {
        double winrate = getWinrate(chosenMember, enemy);
        if (Math.random() < winrate)
        {
            System.out.println("The fellowship has WON the fight");
            chosenMember.winFights();
            enemy.loseFights();
            enemy.increaseDamagePoints(4);
            chosenMember.increaseDamagePoints(1);
            // when enemy has the code
            if (enemy.hasCode())
            {
                chosenMember.setHasCode(true);
                enemy.setHasCode(false);
                System.out.println(chosenMember.getName() + " has taken the secret code back!");
            }
        }
        else
        {
            System.out.println("The fellowship has LOST the fight");
            chosenMember.loseFights();
            enemy.winFights();
            chosenMember.increaseDamagePoints(4);
            enemy.increaseDamagePoints(1);
            // when the chosenMember has the code
            if (chosenMember.hasCode())
            {
                chosenMember.setHasCode(false);
                enemy.setHasCode(true);
                System.out.println(enemy.getName() + " has stolen the secret code! ");
            }
        }
    }
    
    public void displayCaves(Cave currentCave)
    {
        System.out.println("cave diagram");
        System.out.println("============");

        // using 4 string symbol to decide what should be displayed on the right position
        String north = currentCave.getNorth() == 100 ? " M " : (currentCave.getNorth() != 0 ? " N " : "---");
        String west = currentCave.getWest() == 100 ? "M" : (currentCave.getWest() != 0 ? "W" : "|");
        String east = currentCave.getEast() == 100 ? "M" : (currentCave.getEast() != 0 ? "E" : "|");
        String south = currentCave.getSouth() == 100 ? " M " : (currentCave.getSouth() != 0 ? " S " : "---");


        System.out.println("    |" + north + "|    " );
        System.out.println("    |   |    ");
        System.out.println("|---|   |---|");
        System.out.println(west + "           " + east );
        System.out.println("|---|   |---|");
        System.out.println("    |   |    ");
        System.out.println("    |" + south +"|    ");

       
    }

    public int chooseNextCave(Cave currentCave)
    {  
        List<String> exits = new ArrayList<>();

        if (currentCave.getNorth() != 0)
        {
            exits.add(currentCave.getNorth() == 100 ? "m" : "n");
        }

        if (currentCave.getEast() != 0)
        {
            exits.add(currentCave.getEast() == 100 ? "m" : "e");
        }

        if (currentCave.getSouth() != 0)
        {
            exits.add(currentCave.getSouth() == 100 ? "m" : "s");
        }

        if (currentCave.getWest() != 0)
        {
            exits.add(currentCave.getWest() == 100 ? "m" : "w");
        }

        int nextCaveId = currentCave.getId();
        displayCaves(currentCave);

        if (exits.size() == 1)
        {
            String onlyExit = exits.get(0);
            
            
            System.out.println("There is only one exit. Taking exit " + onlyExit);

            switch(onlyExit)
            {
                case "n":
                    nextCaveId = currentCave.getNorth();
                    break;
                case "s":
                    nextCaveId = currentCave.getSouth();
                    break;
                case "w":
                    nextCaveId = currentCave.getWest();
                    break;
                case "e":
                    nextCaveId = currentCave.getEast();
                    break;
                case "m":
                    nextCaveId = 100;
                    break;

            }
            
        }

        else 
        {   
            String choice = "";
            while (!exits.contains(choice.toLowerCase()))
            {
                System.out.print("Which exit? (");
                for (int i = 0; i < exits.size(); i++)
                {
                    System.out.print(exits.get(i));
                    if (i < exits.size() -1)
                    {
                        System.out.print(", ");
                    }
                }
                System.out.println(") ");
                
                choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("m") && !fellowship.hasCode())
                {
                    System.out.println("You dont have the secret code, if you still enter mount api, it will result in losing this game, please try again");
                    choice = scanner.nextLine();
                }
                
                if (!exits.contains(choice.toLowerCase()))
                {
                    System.out.println("Invalid choice, please try again");
                }
            }


            switch(choice)
            {
                case "n":
                    nextCaveId = currentCave.getNorth();
                    break;
                case "s":
                    nextCaveId = currentCave.getSouth();
                    break;
                case "e":
                    nextCaveId = currentCave.getEast();
                    break;
                case "w":
                    nextCaveId = currentCave.getWest();
                    break;
                case "m":
                    if (fellowship.hasCode())
                    {
                        nextCaveId = 100;
                    }
                    else{
                        System.out.println("You entered mount api without secret code, you lose, game over");
                        displayEndSummary(false);
                        System.exit(0);
                    }
                    break;
                default:
                    System.out.println("Invalid choice, "); 
            } 

        }
        return nextCaveId;
    }
    // method to display the visited caves
    public void displayVisitStats(Cave currentCave)
    {

        System.out.println("End of cave visit stats");
        System.out.println("=======================");

        // check which member has a code 
        for (Creature member: fellowship.getMembers())
        {
            if (member.hasCode)
            {
                System.out.println("A fellwoship member (" + member.getName() + ") has the secret code ");
                break;
            }
        }
        System.out.println("Fellowship team ( current status ): ");

        //display members
        for (Creature member : fellowship.getMembers()) 
        {
            String codeIndicator = member.hasCode() ? "*" : "";
            String weaponIndicator = member.hasWeapon() ? "(weapon)" : "";
            String memberInfo = String.format("%s%-8s damage: %-2d %s", codeIndicator, member.getName() + "...", member.getDamagePoints(), weaponIndicator);
            System.out.println(memberInfo);
        }

        System.out.println("\n visited caves (historical status):");
        int indexOfCave = 0;
        // display  1) cave 1... orc ......(dead)
        for (Cave cave: labyrinth.getVisitedCaves())
        {
            indexOfCave++;
            System.out.print(indexOfCave + ") cave" + cave.getId() + "..." );
            // check if there is a creature  and display 
            Creature creature = cave.getCreature();
            if (creature != null)
            {
                System.out.print(creature.getName() + "......");
                if (creature.isAlive())
                {
                    System.out.println("damage: " + creature.getDamagePoints() + "\n");
                }
                else 
                {
                    System.out.print("(dead)\n");  // dead creature
                }
            }
            else 
            {
                System.out.print("(no creature)\n");   // if no creature
            }
        }
        System.out.println();

        System.out.println("\n Creatures in caves (current status):");

        for (Cave cave : labyrinth.getCaves())
        {
            Creature creature = cave.getCreature();
            if (creature != null)
            {
                String codeIndicator = creature.hasCode() ? "*" : "";
                System.out.println ("cave " + cave.getId() + ": " + creature.getName() + "... damage: " + creature.getDamagePoints() + codeIndicator);

            }
        }
        System.out.println("The * indicates who has the secret code  ");
    }
    
    public void displayEndSummary(boolean success)
    {
        System.out.println("End of game summary");
        System.out.println("===================");

        if (fellowship.hasCode())
        {
            System.out.println(" * The quese is a success ");
        }
        else{
            System.out.println(" * The quest is failed");
        }

        System.out.println("Number of cave visited: " + labyrinth.getVisitedCaves().size());

        int codeChanges = 0;
        for (Cave cave : labyrinth.getCaves())
        {
            Creature creature = cave.getCreature();
            if (creature != null && creature.hasCode())
            {
                codeChanges ++;
            }
        }

        System.out.println("Number of time that code changed hands" + codeChanges);

        int deadCreatures = 0;
        for (Cave cave : labyrinth.getCaves())
        {
            Creature creature = cave.getCreature();
            if (creature != null && !creature.isAlive())
            {
                deadCreatures ++;
            }
        }

        System.out.println("Number of creatures dead: " + deadCreatures);

        int fightsWon = 0;
        int totalFights = 0;

        for (Creature member : fellowship.getMembers())
        {
            fightsWon += member.getFightsWon();
            totalFights += member.getTotalFights();
        }

        double successRate = ((double)fightsWon / totalFights) * 100;
        System.out.println("Fellowship fight success rate: " + String.format("%.2f",successRate) + "%"); 
    }
}

