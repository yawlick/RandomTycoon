package me.yawlick.tycoon.item;

import me.yawlick.tycoon.item.dropper.Dropper;
import me.yawlick.tycoon.item.dropper.list.FirstDropper;
import me.yawlick.tycoon.item.seller.Seller;
import me.yawlick.tycoon.item.seller.list.FirstSeller;
import me.yawlick.tycoon.item.upgrader.Upgrader;
import me.yawlick.tycoon.item.upgrader.list.FirstUpgrader;
import me.yawlick.tycoon.util.IPaper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemHandler implements IPaper {
    private final List<Dropper> droppers = new CopyOnWriteArrayList<>();
    private final List<Seller> sellers = new CopyOnWriteArrayList<>();
    private final List<Upgrader> upgraders = new CopyOnWriteArrayList<>();

    // Droppers
    public final FirstDropper firstDropper;

    // Sellers
    public final FirstSeller firstSeller;

    // Upgraders
    public final FirstUpgrader firstUpgrader;

    public ItemHandler() {
        this.droppers.addAll(Arrays.asList(
                firstDropper = new FirstDropper()
        ));

        this.sellers.addAll(Arrays.asList(
                firstSeller = new FirstSeller()
        ));

        this.upgraders.addAll(Arrays.asList(
                firstUpgrader = new FirstUpgrader()
        ));
    }

    public List<Dropper> getDroppers() {
        return droppers;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public List<Upgrader> getUpgraders() {
        return upgraders;
    }

    @Nullable public Dropper getDropper(String name) {
        for (Dropper item : droppers) {
            if (item != null && item.name.equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    @Nullable public Seller getSeller(String name) {
        for (Seller item : sellers) {
            if (item != null && item.name.equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    @Nullable public Upgrader getUpgrader(String name) {
        for (Upgrader item : upgraders) {
            if (item != null && item.name.equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    @Nonnull
    public ArrayList<AbstractItem> getAllItems() {
        ArrayList<AbstractItem> list = new ArrayList<>();
        list.addAll(getDroppers());
        list.addAll(getSellers());
        list.addAll(getUpgraders());
        return list;
    }
}
