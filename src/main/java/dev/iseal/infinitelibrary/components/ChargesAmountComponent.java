package dev.iseal.infinitelibrary.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ChargesAmountComponent(float amount) {
    public static final Codec<ChargesAmountComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf(
            "charges_amount").forGetter(ChargesAmountComponent::amount)).apply(instance, ChargesAmountComponent::new));
}
