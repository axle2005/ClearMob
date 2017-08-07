package io.github.axle2005.clearmob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.immutable.ImmutableDyeableData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableVariantData;
import org.spongepowered.api.data.manipulator.immutable.block.*;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class Util {



	public static void feedback(String type, CommandSource src, Integer removed) {
	    ClearMob instance = ClearMob.getInstance();
	    String message = removed + " "+type+" were removed";
		instance.getLogger().info(message);
		if (src instanceof Player) {
			src.sendMessage(Text.of(message));
		}
	}
	
	public static List<EntityType> getEntityType(List<String> entitys) {
		List<EntityType> listEntityType = new ArrayList<EntityType>(Arrays.asList(EntityTypes.PRIMED_TNT));
		for (String s : entitys) {
			Optional<EntityType> entity = Sponge.getRegistry().getType(EntityType.class, s);

			try {
				entity = Sponge.getRegistry().getType(EntityType.class, s);
				if (entity.isPresent()) {
					listEntityType.add(entity.get());
				}

			} catch (IllegalArgumentException e) {

			}
		}

		return listEntityType;
	}

	public static List<TileEntityType> getTileEntityType(List<String> entitys) {
		List<TileEntityType> listEntityType = new ArrayList<TileEntityType>(Arrays.asList());
		for (String s : entitys) {
			Optional<TileEntityType> entity = null;

			try {
				entity = Sponge.getRegistry().getType(TileEntityType.class, s);
				if (entity.isPresent()) {
					listEntityType.add(entity.get());
				}

			} catch (IllegalArgumentException e) {

			}

		}

		return listEntityType;
	}
	public static List<ItemType> getItemType(List<String> entitys) {
		List<ItemType> listEntityType = new ArrayList<ItemType>(Arrays.asList());
		for (String s : entitys) {
			Optional<ItemType> entity = null;
			if(s.contains(":")){
			    String[] y = s.split(":");
			    if (y.length == 3) {
				s=(y[0]+":"+y[1]);
				
			    }
			}
			try {
				entity = Sponge.getRegistry().getType(ItemType.class, s);
				if (entity.isPresent()) {
					listEntityType.add(entity.get());
				}

			} catch (IllegalArgumentException e) {

			}

		}

		return listEntityType;
	}
	
	public static EntityType getEntityType(String s) {
		return Sponge.getRegistry().getType(EntityType.class, s).get();

	}

	public static TileEntityType getTileEntityType(String s) {
		return Sponge.getRegistry().getType(TileEntityType.class, s).get();

	}

	public static Boolean playerPermCheck(CommandSource src, String perm) {
		if (src instanceof Player && !src.hasPermission(perm)) {
			src.sendMessage(Text.of("You are missing: "+perm+", and can not run this."));
			return false;
		} else
			return true;
	}


}
