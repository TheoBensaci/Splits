package ch.heigvd.data;

import java.util.Arrays;

public record GameEntry(String name, int finalFlagIndex, Flag... flags) {
    public GameEntry(String name, int finalFlagIndex, Flag... flags) {
        this.name = name;
        this.finalFlagIndex = finalFlagIndex;
        this.flags = Arrays.copyOf(flags, flags.length);
    }
}
