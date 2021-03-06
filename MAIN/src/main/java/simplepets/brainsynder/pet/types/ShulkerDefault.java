package simplepets.brainsynder.pet.types;

import org.bukkit.Material;
import simple.brainsynder.api.ItemBuilder;
import simple.brainsynder.sound.SoundMaker;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityControllerPet;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.pet.PetData;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.wrapper.EntityWrapper;

public class ShulkerDefault extends PetDefault {
    public ShulkerDefault(PetCore plugin) {
        super(plugin, "shulker", SoundMaker.ENTITY_SHULKER_AMBIENT, EntityWrapper.SHULKER);
    }

    @Override
    public ItemBuilder getDefaultItem() {
        return new ItemBuilder(Material.SHULKER_SHELL).withName("&f&lShulker Pet");
    }

    @Override
    public Class<? extends IEntityPet> getEntityClass() {
        return IEntityControllerPet.class;
    }

    @Override
    public PetData getPetData() {
        return PetData.SHULKER;
    }
}
