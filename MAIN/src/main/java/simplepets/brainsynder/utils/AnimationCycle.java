package simplepets.brainsynder.utils;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.ambient.IEntityArmorStandPet;

import java.util.*;

public class AnimationCycle {
    private ArrayList<AnimationFrame> frames = new ArrayList<>();
    private ArrayList<UUID> endless = new ArrayList<>();
    private Map<UUID, Boolean> toggle = new HashMap<>();
    private Map<UUID, ArrayList<AnimationFrame>> framesMap = new HashMap<>();
    private BukkitTask runnable;

    public AnimationCycle(MovementFrames frames) {
        this.frames = frames.getFrames();
    }

    public ArrayList<AnimationFrame> getFrames() {
        return this.frames;
    }

    public void register(final IEntityArmorStandPet armor, final long delay) {
        framesMap.putIfAbsent(armor.getUniqueId(), frames);
        toggle.putIfAbsent(armor.getUniqueId(), true);
        if (!endless.contains(armor.getUniqueId())) {
            this.endless.add(armor.getUniqueId());
            runnable = new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    if (!endless.contains(armor.getUniqueId())) {
                        this.cancel();
                        return;
                    }
                    if (!framesMap.containsKey(armor.getUniqueId())) {
                        this.cancel();
                        return;
                    }
                    if (!toggle.containsKey(armor.getUniqueId())) {
                        this.i = 0;
                        return;
                    }
                    if (!toggle.get(armor.getUniqueId())) {
                        this.i = 0;
                        return;
                    }
                    ArrayList<AnimationFrame> frames = framesMap.get(armor.getUniqueId());
                    if (this.i == frames.size()) {
                        this.i = 0;
                    }

                    try {
                        frames.get(this.i).setLocations(armor);
                    } catch (Exception ignored) {
                    }
                    ++this.i;

                }
            }.runTaskTimerAsynchronously(PetCore.get(), 0, delay);
        }
    }

    public boolean isRunning(IEntityArmorStandPet stand) {
        if (endless.contains(stand.getUniqueId())) {
            if (toggle.containsKey(stand.getUniqueId())) {
                return toggle.get(stand.getUniqueId());
            }
        }
        return false;
    }

    public boolean isRegistered(IEntityArmorStandPet stand) {
        return endless.contains(stand.getUniqueId());
    }

    public void toggle(IEntityArmorStandPet armor, boolean var) {
        toggle.put(armor.getUniqueId(), var);
    }

    public void cancelTask(IEntityArmorStandPet armor) {
        runnable.cancel();
        List<UUID> remove = new ArrayList<>();
        for (UUID uuid : endless) {
            if (armor.getUniqueId().equals(uuid)) {
                remove.add(uuid);
            }
        }
        this.endless.removeAll(remove);

    }
}
