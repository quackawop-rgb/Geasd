package com.geasd.geasd.block;

import net.minecraft.util.StringRepresentable;

public enum PlantKind implements StringRepresentable {
    SHORT_GRASS("short_grass"),
    TALL_GRASS("tall_grass"),
    FERN("fern"),
    LARGE_FERN("large_fern");

    public static final StringRepresentable.EnumCodec<PlantKind> CODEC = StringRepresentable.fromEnum(PlantKind::values);

    private final String name;

    PlantKind(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
