package simplepets.brainsynder.menu.menuItems.panda;

import simple.brainsynder.api.ItemBuilder;
import simple.brainsynder.utils.ServerVersion;
import simple.brainsynder.utils.SkullType;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.passive.IEntityPandaPet;
import simplepets.brainsynder.menu.menuItems.base.MenuItemAbstract;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.utils.ValueType;

import java.util.ArrayList;
import java.util.List;

@ValueType(type = "boolean", def = "false")
public class PandaSneeze extends MenuItemAbstract<IEntityPandaPet> {

    public PandaSneeze(PetDefault type, IEntityPet entityPet) {
        super(type, entityPet);
    }
    public PandaSneeze(PetDefault type) {
        super(type);
    }

    @Override
    public ItemBuilder getItem() {
        ItemBuilder builder = type.getDataItemByName(getTargetName(), 0);
        builder.withName(formatName(builder, (entity, name) -> {
            name = name.replace("%value%", String.valueOf(entity.isSneezing()));
            return name;
        }));
        return builder;
    }

    @Override
    public List<ItemBuilder> getDefaultItems() {
        List<ItemBuilder> items = new ArrayList<>();
        ItemBuilder builder = ItemBuilder.getSkull(SkullType.PLAYER);
        builder.setTexture("http://textures.minecraft.net/texture/5c2d25e956337d82791fa0e6617a40086f02d6ebfbfd5a6459889cf206fca787");
        builder.withName("&6&lSneezing: &e%value%");
        items.add(builder);
        return items;
    }

    @Override
    public void onLeftClick() {
        entityPet.setSneezing(!entityPet.isSneezing());
    }

    @Override
    public boolean isSupported() {
        return ServerVersion.isEqualNew(ServerVersion.v1_14_R1);
    }

    @Override
    public String getTargetName() {
        return "sneeze";
    }
}
