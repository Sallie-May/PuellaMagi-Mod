package com.salliemay.uwu.events;

import net.minecraft.network.IPacket;
import net.minecraftforge.eventbus.api.Event; // Import the Event class

public class PacketSent extends Event {
    public final IPacket<?> packet;
    private boolean canceled;

    public PacketSent(IPacket<?> packet) {
        this.packet = packet;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void cancel() {
        this.canceled = true;
    }
}
