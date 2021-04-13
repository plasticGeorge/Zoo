package zoo;

public class Main {

    public static void main(String[] argv) {
        // Create zoo
        Zoo zoo = new Zoo();
        // Add animals to the zoo
        SourceFormat format = null;
        String filePath = null;
        if(argv.length != 2)
            throw new IllegalArgumentException("Invalid number of arguments");
        for(String arg : argv){
            String[] args = arg.split("=");
            switch (args[0]){
                case "-configtype":
                    format = SourceFormat.valueOf(args[1].toUpperCase());
                    break;
                case "-configfile":
                    filePath = args[1];
                    break;
                default:
                    throw new IllegalArgumentException("You can only define \'-configtype\' or \'-configfile\'");
            }
        }
        zoo.addAnimals(filePath, format);

        // Create user action trigger
        ActionTrigger trigger = new ActionTrigger(zoo);

        AnimalType herbivore = AnimalType.HERBIVORE;
        AnimalType carnivore = AnimalType.CARNIVORE;

        // All of the following actions are up to users choice
        zoo.printAllStates();
        trigger.setMorning();
        zoo.printAllStates();

        trigger.visit(herbivore);
        zoo.printAllStates();
//        trigger.visit(carnivore);
        trigger.feedAnimals(herbivore);
        zoo.printAllStates();

        trigger.setNight();
        zoo.printAllStates();

        trigger.setMorning();
        zoo.printAllStates();

        trigger.setThunder();
        zoo.printAllStates();
        trigger.feedAnimals(carnivore);
        zoo.printAllStates();

        trigger.feedAnimals(herbivore);
        zoo.printAllStates();
        trigger.setNight();
        zoo.printAllStates();

        trigger.setMorning();
        zoo.printAllStates();

        trigger.setThunder();
        zoo.printAllStates();

        trigger.drinkingAnimals(herbivore);
        trigger.drinkingAnimals(carnivore);
        zoo.printAllStates();

        trigger.setNight();
        zoo.printAllStates();

        trigger.setMorning();
        zoo.printAllStates();

        trigger.setRain();
        zoo.printAllStates();

        trigger.feedAnimals(carnivore);
        zoo.printAllStates();

        trigger.setNight();
        zoo.printAllStates();
    }
}