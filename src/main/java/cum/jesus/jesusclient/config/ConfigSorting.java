package cum.jesus.jesusclient.config;

import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.SortingBehavior;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ConfigSorting extends SortingBehavior {
    public Comparator<Category> getCategoryComparator() {
        return Comparator.comparingInt(o -> this.categories.indexOf(o.getName()));
    }

    private final List<String> categories = Arrays.asList(new String[] { "Combat", "Skyblock", "Player", "Macros", "Movement", "Funny", "Other", "Dev" });
}
