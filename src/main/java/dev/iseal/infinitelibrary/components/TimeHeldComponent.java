package dev.iseal.infinitelibrary.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TimeHeldComponent(int time) {
    public static final Codec<TimeHeldComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("time").forGetter(TimeHeldComponent::time)).apply(instance, TimeHeldComponent::new));
}