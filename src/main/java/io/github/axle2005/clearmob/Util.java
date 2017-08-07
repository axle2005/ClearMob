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
	    String message = removed + " "+type+"  were removed";
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
	  private static String getModNameFromName(String name) {
	      String ret = "";
	      if (name.indexOf(':') >= 0) {
	        ret = name.substring(0, name.indexOf(':'));
	      }
	      return ret;
	    }

	    @Nullable
	    private static String getVariantDataMetaFromState(BlockState state) {
	      String ret = null;

	      //TODO there has to be an easier way to do this
	      for (ImmutableDataManipulator<?, ?> m : state.getContainers()) {
	        if (m instanceof ImmutableVariantData) {
	          if (m instanceof ImmutableBigMushroomData) {
	            ret = ((ImmutableBigMushroomData) m).type().get().getName();
	          } else if (m instanceof ImmutableBrickData) {
	            ret = ((ImmutableBrickData) m).type().get().getName();
	          } else if (m instanceof ImmutableDirtData) {
	            ret = ((ImmutableDirtData) m).type().get().getName();
	          } else if (m instanceof ImmutableDisguisedBlockData) {
	            ret = ((ImmutableDisguisedBlockData) m).type().get().getName();
	          } else if (m instanceof ImmutableDoublePlantData) {
	            ret = ((ImmutableDoublePlantData) m).type().get().getName();
	          } else if (m instanceof ImmutableDyeableData) {
	            ret = ((ImmutableDyeableData) m).type().get().getName();
	          } else if (m instanceof ImmutablePlantData) {
	            ret = ((ImmutablePlantData) m).type().get().getName();
	          } else if (m instanceof ImmutablePrismarineData) {
	            ret = ((ImmutablePrismarineData) m).type().get().getName();
	          } else if (m instanceof ImmutableQuartzData) {
	            ret = ((ImmutableQuartzData) m).type().get().getName();
	          } else if (m instanceof ImmutableSandData) {
	            ret = ((ImmutableSandData) m).type().get().getName();
	          } else if (m instanceof ImmutableSandstoneData) {
	            ret = ((ImmutableSandstoneData) m).type().get().getName();
	          } else if (m instanceof ImmutableShrubData) {
	            ret = ((ImmutableShrubData) m).type().get().getName();
	          } else if (m instanceof ImmutableSlabData) {
	            ret = ((ImmutableSlabData) m).type().get().getName();
	          } else if (m instanceof ImmutableStoneData) {
	            ret = ((ImmutableStoneData) m).type().get().getName();
	          } else if (m instanceof ImmutableTreeData) {
	            ret = ((ImmutableTreeData) m).type().get().getName();
	          } else if (m instanceof ImmutableWallData) {
	            ret = ((ImmutableWallData) m).type().get().getName();
	          }
	        }
	      }
	      return ret;
	    }

	    /**
	     * Pulls block meta data from a {@link BlockState}. If you need to pull information from a {@link TileEntity}, use {@link Util#getBlockMetaFromSnapshot(BlockSnapshot)}
	     *
	     * @param state BlockState to pull the meta from
	     *
	     * @return string representing the meta data
	     */
	    @Nullable
	    private static String getBlockMetaFromState(BlockState state) {
	      //if variant is present, set as variant
	      String meta = (state.getTrait("variant").isPresent() ? state.getTraitValue(state.getTrait("variant").get()).get().toString() : null);
	      if (meta == null) {
	        meta = getVariantDataMetaFromState(state);
	      }

	      //Retrieves the variant of the block, some mods such as ic2 and eio dont use the variant field for whatever reason
	      //  Needs to be hardcoded in here to account for it to distinguish blocks
	      //  Should probably add error handling in case data formatting changes...
	      //  There only appears to be one of the 'variant' fields for each block


	      //Appears to be the 'standard' field used when not in variant
	      if (state.getTrait("type").isPresent()) {
	        //apready have a variant, oh shit
	        if (meta != null) {
	          //plugin.getLogger().warn("Block {} has a variant: {} and a type: {}", state.getType().getName(), meta, state.getTraitValue(state.getTrait("type").get()).get().toString());
	        }

	        //IC2 has a different type field for some reason
	        if (getModNameFromName(state.getType().getName()).equals("ic2")) {
	          if (state.getTrait("type").isPresent()) {
	            //type=MetaTeBlock{induction_furnace}
	            String tmp = state.getTraitValue(state.getTrait("type").get()).get().toString();
	            meta = tmp.substring(tmp.indexOf("{") + 1, tmp.length() - 1);
	          }
	        } else {
	          //type=TYPE01
	          meta = state.getTraitValue(state.getTrait("type").get()).get().toString();
	        }
	      }

	      //Only seen on EIO blocks so far
	      if (state.getTrait("kind").isPresent()) {
	        if (meta != null) {
	          //plugin.getLogger().warn("Block {} has a variant/type: {} and a kind: {}", state.getType().getName(), meta, state.getTraitValue(state.getTrait("kind").get()).get().toString());
	        }
	        //kind=FUSED_QUARTZ
	        meta = state.getTraitValue(state.getTrait("kind").get()).get().toString();
	      }
	      return meta;
	    }

	    /**
	     * Parses a {@link BlockSnapshot} and retrieves the meta value. Calling {@link Util#getBlockMetaFromState(BlockState)} is faster if you dont need to check the {@link TileEntity} for mod meta data
	     *
	     * @param snapshot BlockSnapshot to search
	     *
	     * @return string representing meta data
	     */
	    @Nullable
	    public static String getBlockMetaFromSnapshot(BlockSnapshot snapshot) {
	      String meta = null;
	      switch (getModNameFromName(snapshot.getState().getType().getName())) {
	        case "enderio":
	          //retrieves the conduit placed from eio conduits
	          //does not detect when placing another conduit in an existing bundle, does not trigger this event
	          //this assumes that there is only one conduit in the bundle because of that fact
	          if (snapshot.getState().getType().getName().equals("blockConduitBundle")) {
	            if (snapshot.getLocation().isPresent()) {
	              if (snapshot.getLocation().get().getTileEntity().isPresent()) {
	                TileEntity te = snapshot.getLocation().get().getTileEntity().get();
	                try {
	                  //crazypants.enderio.conduit.item.ItemConduit
	                  String tmp = ((DataContainer) te.toContainer().getList(DataQuery.of("UnsafeData", "conduits")).get().get(0)).get(DataQuery.of("conduitType")).get().toString();
	                  meta = tmp.substring(tmp.lastIndexOf('.') + 1);
	                } catch (Exception e) {
	                  //The data format has probably changed
	                  //plugin.getLogger().error("Attempt to get conduit from EIO bundle failed.");
	                  //attempt to get it from the state the normal way
	                  meta = getBlockMetaFromState(snapshot.getState());
	                }
	              }
	            }
	          } else { //not blockConduitBundle
	            meta = getBlockMetaFromState(snapshot.getState());
	          }
	          break;
	        case "botania":
	          if (snapshot.getState().getType().getName().equals("botania:specialFlower")) {
	            if (snapshot.getLocation().isPresent()) {
	              if (snapshot.getLocation().get().getTileEntity().isPresent()) {
	                TileEntity te = snapshot.getLocation().get().getTileEntity().get();
	                try {
	                  meta = (String) te.toContainer().get(DataQuery.of("UnsafeData", "subTileName")).get();
	                } catch (Exception e) {
	                  //The data format has probably changed
	                  //plugin.getLogger().error("Attempt to get flower type from botania flower failed.");
	                  //attempt to get it from the state the normal way
	                  meta = getBlockMetaFromState(snapshot.getState());
	                }
	              }
	            }
	          }
	          break;
	        default:  //not enderio mod
	          meta = getBlockMetaFromState(snapshot.getState());
	          break;
	      }
	      return meta;
	    }

	    //default method, pull from "UnsafeDamage" key
	    @Nullable
	    private static String getItemMetaFromItemStackHelper(ItemStack stack) {
	      String ret = null;
	      if (stack.toContainer().get(DataQuery.of("UnsafeDamage")).isPresent()) {
	        ret = stack.toContainer().get(DataQuery.of("UnsafeDamage")).get().toString();
	      }
	      return ret;
	    }

	    /**
	     * Gets the string meta from an {@link ItemStack}
	     * @param stack ItemStack to pull the meta data from
	     * @return string representing the metadata
	     */
	    @Nullable
	    public static String getItemMetaFromItemStack(ItemStack stack) {
	      String ret = null;
	      switch (getModNameFromName(stack.getItem().getName())) {
	        case "botania":
	          if (stack.getItem().getName().equals("botania:specialFlower")) {
	            if (stack.toContainer().get(DataQuery.of("UnsafeData", "type")).isPresent()) {
	              ret = stack.toContainer().get(DataQuery.of("UnsafeData", "type")).get().toString();
	            }
	          } else {
	            ret = getItemMetaFromItemStackHelper(stack);
	          }
	          break;
	        default:
	          ret = getItemMetaFromItemStackHelper(stack);
	          break;
	      }
	      return ret;
	    }

}
