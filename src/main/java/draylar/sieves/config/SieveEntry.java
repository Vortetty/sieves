package draylar.sieves.config;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SieveEntry {

    private final String id;

    private final int minimumProgression;
    private final int maximumProgression;

    private final Identifier topTexture;
    private final Identifier legsTexture;
    private final Identifier netTexture;

    private final Map<Identifier, Identifier> lootTables = new HashMap<>();

    public SieveEntry(String id, int minimumProgression, int maximumProgression, Identifier topTexture, Identifier legsTexture, Identifier netTexture) {
        this.id = id;
        this.minimumProgression = minimumProgression;
        this.maximumProgression = maximumProgression;
        this.topTexture = topTexture;
        this.legsTexture = legsTexture;
        this.netTexture = netTexture;
    }

    public Identifier getTopTexture() {
        return topTexture;
    }

    public Identifier getLegsTexture() {
        return legsTexture;
    }

    public Identifier getNetTexture() {
        return netTexture;
    }

    public int getMinimumProgression() {
        return minimumProgression;
    }

    public int getMaximumProgression() {
        return maximumProgression;
    }

    public String getId() {
        return id;
    }

    public SieveEntry addLootTable(Identifier input, Identifier lootTable) {
        lootTables.put(input, lootTable);
        return this;
    }

    public Map<Identifier, Identifier> getLootTables() {
        return lootTables;
    }
}
