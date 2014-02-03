package info.tregmine.boxfill;

import info.tregmine.Tregmine;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import info.tregmine.api.TregminePlayer;

public class AllButReplacer extends AbstractFiller
{
    private History history;
    private TregminePlayer player;
    private MaterialData match;
    private MaterialData item;
    private SavedBlocks currentJob;

    protected int x, minX, maxX;
    protected int y, minY, maxY;
    protected int z, minZ, maxZ;

    public AllButReplacer(Tregmine plugin, History history, TregminePlayer player,
            Block block1, Block block2, MaterialData match, MaterialData item,
            int workSize)
    {
        super(plugin, block1, block2, workSize);

        this.history = history;
        this.player = player;
        this.match = match;
        this.item = item;
        this.currentJob = new SavedBlocks();
    }

    @Override
    public void changeBlock(Block block)
    {
        Block b1 = player.getFillBlock1();
        Block b2 = player.getFillBlock2();

        maxX =
                Math.max(b1.getLocation().getBlockX(), b2.getLocation()
                        .getBlockX());
        minX =
                Math.min(b1.getLocation().getBlockX(), b2.getLocation()
                        .getBlockX());
        x = minX;

        maxY =
                Math.max(b1.getLocation().getBlockY(), b2.getLocation()
                        .getBlockY());
        minY =
                Math.min(b1.getLocation().getBlockY(), b2.getLocation()
                        .getBlockY());
        y = minY;

        maxZ =
                Math.max(b1.getLocation().getBlockZ(), b2.getLocation()
                        .getBlockZ());
        minZ =
                Math.min(b1.getLocation().getBlockZ(), b2.getLocation()
                        .getBlockZ());
        z = minZ;

        World world = b1.getLocation().getWorld();

        for(int x = minX; x <= maxX; x++) {
            for(int y = minY; y <= maxY; y++) {
                for(int z = minZ; z <= maxZ; z++) {
                    if (world.getBlockAt(x,y,z).equals(match)){
                        continue;
                    }
                    world.getBlockAt(x, y, z).setType(item.getItemType());
                    world.getBlockAt(x, y, z).setData(item.getData());
                    player.sendMessage(ChatColor.GREEN + "Yep");
                }
            }
        }
    }

    /*
     * 
        if (block.getTypeId() == match.getItemTypeId()
                && (match.getData() == 0 || block.getData() == match.getData())) {

            currentJob.addBlock(block.getState());

            block.setType(item.getItemType());
            block.setData(item.getData());
        }
    }
     */
    @Override
    public void finished()
    {
        history.set(player, currentJob);
    }
}
