package com.salliemay.uwu.events;

import net.minecraft.network.IPacket;
import net.minecraftforge.eventbus.api.Event;

public class PacketReceived extends Event {
    public final IPacket<?> packet;
    private boolean canceled;

    public PacketReceived(IPacket<?> packet) {
        this.packet = packet;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void cancel() {
        this.canceled = true;
    }
}
