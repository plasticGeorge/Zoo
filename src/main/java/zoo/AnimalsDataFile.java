package zoo;

import lombok.Getter;

import java.util.List;

/**
 * Class for JSON and XML deserialization.
 * JSON or XML contains information about animals of each type
 */
@Getter
public class AnimalsDataFile {

    /**
     *  List containing info about carnivore type animals
     */
    private List<Carnivore> carnivoreAnimals;
    /**
     * List containing info about herbivore type animals
     */
    private List<Herbivore> herbivoreAnimals;
}

