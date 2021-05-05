package zoo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for concrete zoo
 */
@Getter
public class Zoo {
    /**
     * List of all animals in the zoo
     */
    private List<AnimalSpecies> zooAnimalSpecies;

    /**
     * Static field for storing state of ALL carnivore animals
     * can be transformed to List for containing states of a concrete type (e.g. Lion)
     */
    @Getter @Setter
    private static AnimalState allCarnivoreState;

    /**
     * Static field for storing state of ALL herbivore animals
     * can be transformed to List for containing states of a concrete type (e. g. Cow)
     */
    @Getter @Setter
    private static AnimalState allHerbivoreState;

    /**
     * Initialization of animals list and default states of animal types
     */
    public Zoo() {

        zooAnimalSpecies = new LinkedList<>();
        allCarnivoreState = AnimalState.CALM;
        allHerbivoreState = AnimalState.CALM;
    }

    public void addAnimals(String source, Formats format){
        switch (format){
            case JSON:
                addAnimalsFromJson(source);
                break;
            case XML:
                addAnimalsFromXml(source);
                break;
            case DATABASE:
                addAnimalsFromDB(source);
                break;
            default:
                throw new IllegalArgumentException("Incorrect parsing format");
        }
    }

    /**
     * Method for adding animals to the zoo from the specified JSON file
     *
     * @param jsonPath path to JSON file with animals info
     */
    private void addAnimalsFromJson(String jsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        File animalsFile = new File(jsonPath);
        try {
            AnimalsDataFile animalsData = mapper.readValue(animalsFile, AnimalsDataFile.class);
            zooAnimalSpecies.addAll(animalsData.getCarnivoreAnimals());
            zooAnimalSpecies.addAll(animalsData.getHerbivoreAnimals());
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new IllegalStateException("File hasn't been parsed");
        }
    }

    /**
     * Method for adding animals to the zoo from the specified XML file
     *
     * @param xmlPath path to XML file with animals info
     */
    private void addAnimalsFromXml(String xmlPath) {
        XmlMapper mapper = new XmlMapper();
        File animalsFile = new File(xmlPath);
        try {
            AnimalsDataFile animalsData = mapper.readValue(animalsFile, AnimalsDataFile.class);
            zooAnimalSpecies.addAll(animalsData.getCarnivoreAnimals());
            zooAnimalSpecies.addAll(animalsData.getHerbivoreAnimals());
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new IllegalStateException("File hasn't been parsed");
        }
    }

    /**
     * Method for adding animals to the zoo from the specified XML file
     *
     * @param connectionFilePath path to Database configuration file
     */
    public void addAnimalsFromDB(String connectionFilePath) {
        Session session = null;
        try {
            Configuration configuration = new Configuration().configure(new File(connectionFilePath));
            configuration.addAnnotatedClass(ZooDBModel.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            session = configuration.buildSessionFactory(builder.build()).openSession();
            zooAnimalSpecies.addAll(session.createQuery("select new zoo.Carnivore(name, amount) from ZooDBModel where type like 'carnivore'").list());
            zooAnimalSpecies.addAll(session.createQuery("select new zoo.Herbivore(name, amount) from ZooDBModel where type like 'herbivore'").list());
        }
        catch (Exception e){
            System.out.println(e.toString());
            throw new IllegalStateException("Failed to connect to database");
        }
        finally {
            if(session != null)
                session.close();
        }
    }

    /**
     * Method for handling user actions with the specified type of animals
     *
     * @param event event to do a certain actions with animals
     * @param animalType type of animals for event
     */
    public void performAction(Events event, AnimalType animalType) {
        updateAllSpeciesCurrentStates();
        switch (animalType) {
            case CARNIVORE:
                for(AnimalSpecies species : zooAnimalSpecies) {
                    if(species instanceof Carnivore) {
                        species.updateState(event);
                    }
                }
                break;
            case HERBIVORE:
                for(AnimalSpecies species : zooAnimalSpecies) {
                    if(species instanceof Herbivore) {
                        species.updateState(event);
                    }
                }
                break;
            default:
                System.out.println("No such animal type in the zoo");
        }
        updateAllSpeciesCurrentStates();
    }

    /**
     * Method for handling user actions with ALL animals
     *
     * @param event event to do a certain actions with ALL animals
     */
    public void performAction(Events event) {
        updateAllSpeciesCurrentStates();
        for(AnimalSpecies species : zooAnimalSpecies) {
            species.updateState(event);
        }
        updateAllSpeciesCurrentStates();
    }

    /**
     * Method for updating current state of ALL animals
     * Used as a way for animals, to be aware of a current state of their type
     */
    private void updateAllSpeciesCurrentStates() {
        for (AnimalSpecies animal : zooAnimalSpecies) {
            animal.updateState(Events.UPDATE_STATE);
        }
    }

    /**
     * NOTE: ONLY A DEBUGGING AND REPRESENTATION METHOD
     * MAY NOT BE USED IN ACTUAL MODEL
     *
     * Used for printing states of concrete animals
     * and states of carnivore/herbivore types in general
     */
    public void printAllStates() {
        System.out.println("\n --- CURRENT ZOO INFO --- ");
        System.out.println("Carnivore state: " + allCarnivoreState);
        System.out.println("Herbivore state: " + allHerbivoreState);
        for (AnimalSpecies animal : zooAnimalSpecies) {
            animal.printDescription();
        }
    }
}
