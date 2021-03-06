package mods.fossil.handler;

import mods.fossil.Fossil;
import mods.fossil.fossilEnums.EnumDinoType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class FossilRecipeHandler
{
    public static void addRecipe()
    {
        GameRegistry.addRecipe(new ItemStack(Fossil.skullLantern, 1), new Object[] {"X", "Y", 'X', Fossil.blockSkull, 'Y', Block.torchWood});
        GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 5, 15), new Object[] {"X", 'X', Fossil.blockSkull});
        GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 5, 15), new Object[] {"X", 'X', Fossil.skullLantern});
        GameRegistry.addRecipe(new ItemStack(Fossil.blockcultivateIdle, 1), new Object[] {"XYX", "XWX", "ZZZ", 'X', Block.glass, 'Y', new ItemStack(Item.dyePowder, 1, 2), 'W', Item.bucketWater, 'Z', Item.ingotIron});
        GameRegistry.addRecipe(new ItemStack(Fossil.blockanalyzerIdle, 1), new Object[] {"XYX", "XWX", 'X', Item.ingotIron, 'Y', Fossil.relic, 'W', Fossil.biofossil});
        GameRegistry.addRecipe(new ItemStack(Fossil.blockworktableIdle, 1), new Object[] {"X", "Y", 'X', Item.paper, 'Y', Block.workbench});

        for (int i = 0; i < EnumDinoType.values().length; i++)
            GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[] {"MMM", "SES", "WWW", 'M', Item.bucketMilk, 'S', Item.sugar, 'W', Item.wheat, 'E', EnumDinoType.values()[i].EggItem});
        GameRegistry.addRecipe(new ItemStack(Fossil.skullStick, 1), new Object[] {"X", "Y", 'X', Fossil.blockSkull, 'Y', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Fossil.drum, 1), new Object[] {"ZZZ", "XYX", "XXX", 'X', Block.planks, 'Y', Item.redstone, 'Z', Item.leather});
        GameRegistry.addRecipe(new ItemStack(Fossil.feederIdle, 1), new Object[] {"XYX", "ZAB", "BBB", 'X', Item.ingotIron, 'Y', Block.glass, 'Z', Block.stoneButton, 'A', Item.bucketEmpty, 'B', Block.stone});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemAxe), new Object[] {Item.axeGold, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemAxe), new Object[] {Item.axeDiamond, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemPickaxe), new Object[] {Item.pickaxeGold, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemPickaxe), new Object[] {Item.pickaxeDiamond, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemHoe), new Object[] {Item.hoeGold, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemHoe), new Object[] {Item.hoeDiamond, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemSword), new Object[] {Item.swordGold, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemSword), new Object[] {Item.swordDiamond, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemShovel), new Object[] {Item.shovelGold, Fossil.gem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.gemShovel), new Object[] {Item.shovelDiamond, Fossil.gem});

        for (int i = 0; i < EnumDinoType.values().length; i++)
            GameRegistry.addShapelessRecipe(new ItemStack(Fossil.dinoPedia), new Object[] {Item.book, EnumDinoType.values()[i].DNAItem});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.archNotebook), new Object[] {Item.book, Fossil.relic});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.rawChickenSoup, 1, 0), new Object[] {Item.bucketEmpty, Item.chickenRaw});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.magicConch, 1, 1), new Object[] {new ItemStack(Fossil.magicConch, 1, 0)});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.magicConch, 1, 2), new Object[] {new ItemStack(Fossil.magicConch, 1, 1)});
        GameRegistry.addShapelessRecipe(new ItemStack(Fossil.magicConch, 1, 0), new Object[] {new ItemStack(Fossil.magicConch, 1, 2)});
        GameRegistry.addRecipe(new ItemStack(Fossil.chickenEss, 8), new Object[] {"XXX", "XYX", "XXX", 'X', Item.glassBottle, 'Y', Fossil.cookedChickenSoup});
        GameRegistry.addRecipe(new ItemStack(Fossil.whip, 1), new Object[] {"XXS", "XTS", "TXS", 'T', Item.stick, 'S', Item.silk});
        GameRegistry.addRecipe(new ItemStack(Fossil.volcanicBrick, 4), new Object[] {"VV", "VV", 'V', Fossil.volcanicRock});
        GameRegistry.addRecipe(new ItemStack(Fossil.palaePlanks, 4), new Object[] {"P", 'P', Fossil.palmLog});
        GameRegistry.addRecipe(new ItemStack(Block.woodenButton, 1), new Object[] {"P", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Item.stick, 3), new Object[] { "P", "P", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Block.pressurePlatePlanks, 1), new Object[] { "PP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Item.boat, 1), new Object[] { "PXP", "PPP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Item.bowlEmpty, 3), new Object[] { "PXP", "XPX", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Block.workbench, 1), new Object[] { "PP", "PP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Item.doorWood, 1), new Object[] {"PPX", "PPX", "PPX", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Block.trapdoor, 2), new Object[] { "PPP", "PPP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Block.chest, 1), new Object[] {"PPP", "PXP", "PPP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Item.shovelWood, 1), new Object[] {"P", "S", "S", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.swordWood, 1), new Object[] {"P", "P", "S", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.hoeWood, 1), new Object[] {"PP", "XS", "XS", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.hoeWood, 1), new Object[] {"PP", "SX", "SX", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.axeWood, 1), new Object[] {"PP", "PS", "XS", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.axeWood, 1), new Object[] {"PP", "SP", "SX", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.pickaxeWood, 1), new Object[] {"PPP", "XSX", "XSX", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Block.fenceGate, 1), new Object[] { "SPS", "SPS", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.sign, 3), new Object[] {"PPP", "PPP", "XSX", 'P', Fossil.palaePlanks, 'S', Item.stick});
        GameRegistry.addRecipe(new ItemStack(Item.bed, 1), new Object[] { "WWW", "PPP", 'P', Fossil.palaePlanks, 'W', Fossil.blockanalyzerActive.cloth});
        GameRegistry.addRecipe(new ItemStack(Block.jukebox, 1), new Object[] {"PPP", "PDP", "PPP", 'P', Fossil.palaePlanks, 'D', Item.diamond});
        GameRegistry.addRecipe(new ItemStack(Block.music, 1), new Object[] {"PPP", "PRP", "PPP", 'P', Fossil.palaePlanks, 'R', Item.redstone});
        GameRegistry.addRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"PPP", "CIC", "CRC", 'P', Fossil.palaePlanks, 'R', Item.redstone, 'I', Item.ingotIron, 'C', Block.cobblestone});
        GameRegistry.addRecipe(new ItemStack(Block.bookShelf, 1), new Object[] {"PPP", "BBB", "PPP", 'P', Fossil.palaePlanks, 'B', Item.book});
        GameRegistry.addRecipe(new ItemStack(Fossil.palaeSingleSlab, 6), new Object[] {"PPP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Fossil.palaeStairs, 4), new Object[] {"P  ", "PP ", "PPP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Fossil.palaeStairs, 4), new Object[] {"  P", " PP", "PPP", 'P', Fossil.palaePlanks});
        GameRegistry.addRecipe(new ItemStack(Fossil.ancientWoodSingleSlab, 6), new Object[] {"PPP", 'P', Fossil.ancientWood});
        GameRegistry.addRecipe(new ItemStack(Fossil.ancientWoodStairs, 4), new Object[] {"P  ", "PP ", "PPP", 'P', Fossil.ancientWood});
        GameRegistry.addRecipe(new ItemStack(Fossil.ancientWoodStairs, 4), new Object[] {"  P", " PP", "PPP", 'P', Fossil.ancientWood});
        GameRegistry.addRecipe(new ItemStack(Fossil.ancientStoneSingleSlab, 6), new Object[] {"PPP", 'P', Fossil.ancientStone});
        GameRegistry.addRecipe(new ItemStack(Fossil.ancientStoneStairs, 4), new Object[] {"P  ", "PP ", "PPP", 'P', Fossil.ancientStone});
        GameRegistry.addRecipe(new ItemStack(Fossil.ancientStoneStairs, 4), new Object[] {"  P", " PP", "PPP", 'P', Fossil.ancientStone});
        GameRegistry.addRecipe(new ItemStack(Fossil.feet, 1), new Object[] {"* *", "# #", '#', Fossil.foot, '*', Fossil.claw});
        GameRegistry.addRecipe(new ItemStack(Fossil.femurs, 1), new Object[] {"###", "* *", "# #", '#', Item.bone, '*', Fossil.legBone});
        GameRegistry.addRecipe(new ItemStack(Fossil.ribCage, 1), new Object[] {"# #", " # ", "###", '#', Item.bone});
        GameRegistry.addRecipe(new ItemStack(Fossil.skullHelmet, 1), new Object[] {"#X#", "# #", '#', Item.bone, 'X', Fossil.skull});
        GameRegistry.addSmelting(Fossil.rawChickenSoup.itemID, new ItemStack(Fossil.cookedChickenSoup), 3.0F);
        GameRegistry.addSmelting(EnumDinoType.values()[4].EggItem.itemID, new ItemStack(Fossil.sjl), 3.0F);

        for (int i = 0; i < EnumDinoType.values().length; i++)
            if (i != 4) //Nautilus treated specially
            {
                GameRegistry.addSmelting(EnumDinoType.values()[i].DropItem.itemID, new ItemStack(Fossil.cookedDinoMeat), 3.0F);
            }

        GameRegistry.addSmelting(Fossil.icedMeat.itemID, new ItemStack(Item.beefCooked), 3.0F);
        GameRegistry.addSmelting(Fossil.dodoWing.itemID, new ItemStack(Fossil.dodoWingCooked), 3.0F);
    }
}
