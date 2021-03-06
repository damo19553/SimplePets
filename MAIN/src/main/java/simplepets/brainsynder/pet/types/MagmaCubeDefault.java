package simplepets.brainsynder.pet.types;

import simple.brainsynder.api.ItemBuilder;
import simple.brainsynder.sound.SoundMaker;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.hostile.IEntityMagmaCubePet;
import simplepets.brainsynder.pet.PetData;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.wrapper.EntityWrapper;

public class MagmaCubeDefault extends PetDefault {
    public MagmaCubeDefault(PetCore plugin) {
        super(plugin, "magma_cube", SoundMaker.ENTITY_MAGMACUBE_SQUISH, EntityWrapper.MAGMA_CUBE);
    }

    @Override
    public ItemBuilder getDefaultItem() {
        return ItemBuilder.getSkull(simple.brainsynder.utils.SkullType.PLAYER)
                .setTexture("http://textures.minecraft.net/texture/38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429")
                .withName("&f&lMagma Cube Pet");
    }

    @Override
    public Class<? extends IEntityPet> getEntityClass() {
        return IEntityMagmaCubePet.class;
    }

    @Override
    public PetData getPetData() {
        return PetData.SIZE;
    }
}
