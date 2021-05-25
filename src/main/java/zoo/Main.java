package zoo;

public class Main {

    public static void main(String[] argv) {
        // Create zoo
        Zoo zoo = new Zoo();
        // Add animals to the zoo
        Formats format = Formats.JSON;
        String source = Main.class.getClassLoader().getResource("zooAnimals.json").getPath();
        if(argv.length > 2)
            throw new IllegalArgumentException("Invalid number of arguments");
        for(String arg : argv){
            String[] args = arg.split("=");
            if (args.length == 2) {
                switch (args[0]) {
                    case "-format":
                        format = Formats.valueOf(args[1].toUpperCase());
                        break;
                    case "-source":
                        source = args[1];
                        break;
                    default:
                        throw new IllegalArgumentException("You can only define \'-configtype\' or \'-configfile\'");
                }
            }
            else
                throw new IllegalArgumentException("Invalid state of \'" + args[0] + "\' argument...");
        }
        zoo.addAnimals(source, format);

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