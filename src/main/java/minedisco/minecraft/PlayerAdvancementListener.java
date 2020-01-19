package minedisco.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import minedisco.discord.DiscordBot;

public class PlayerAdvancementListener implements Listener {

    public enum Advancements {
        MINECRAFT_STORY_ROOT("Minecraft"),
        MINECRAFT_STORY_MINE_STONE("Stone Age"),
        MINECRAFT_STORY_UPGRADE_TOOLS("Getting an Upgrade"),
        MINECRAFT_STORY_SMELT_IRON("Acquire Hardware"),
        MINECRAFT_STORY_OBTAIN_ARMOR("Suit Up"),
        MINECRAFT_STORY_LAVA_BUCKET("Hot Stuff"),
        MINECRAFT_STORY_IRON_TOOLS("Isn't It Iron Pick"),
        MINECRAFT_STORY_DEFLECT_ARROW("Not Today, Thank You"),
        MINECRAFT_STORY_FORM_OBSIDIAN("Ice Bucket Challenge"),
        MINECRAFT_STORY_MINE_DIAMOND("Diamonds!"),
        MINECRAFT_STORY_ENTER_THE_NETHER("We Need to Go Deeper"),
        MINECRAFT_STORY_SHINY_GEAR("Cover Me With Diamonds"),
        MINECRAFT_STORY_ENCHANT_ITEM("Enchanter"),
        MINECRAFT_STORY_CURE_ZOMBIE_VILLAGER("Zombie Doctor"),
        MINECRAFT_STORY_FOLLOW_ENDER_EYE("Eye Spy"),
        MINECRAFT_STORY_ENTER_THE_END("The End?"),
        MINECRAFT_NETHER_ROOT("Nether"),
        MINECRAFT_NETHER_FAST_TRAVEL("Subspace Bubble"),
        MINECRAFT_NETHER_FIND_FORTRESS("A Terrible Fortress"),
        MINECRAFT_NETHER_RETURN_TO_SENDER("Return to Sender"),
        MINECRAFT_NETHER_OBTAIN_BLAZE_ROD("Into Fire"),
        MINECRAFT_NETHER_GET_WITHER_SKULL("Spooky Scary Skeleton"),
        MINECRAFT_NETHER_UNEASY_ALLIANCE("Uneasy Alliance"),
        MINECRAFT_NETHER_BREW_POTION("Local Brewery"),
        MINECRAFT_NETHER_SUMMON_WITHER("Withering Heights"),
        MINECRAFT_NETHER_ALL_POTIONS("A Furious Cocktail"),
        MINECRAFT_NETHER_CREATE_BEACON("Bring Home the Beacon"),
        MINECRAFT_NETHER_ALL_EFFECTS("How Did We Get Here?"),
        MINECRAFT_NETHER_CREATE_FULL_BEACON("Beaconator"),
        MINECRAFT_END_ROOT("The End"),
        MINECRAFT_END_KILL_DRAGON("Free the End"),
        MINECRAFT_END_DRAGON_EGG("The Next Generation"),
        MINECRAFT_END_ENTER_END_GATEWAY("Remote Getaway"),
        MINECRAFT_END_RESPAWN_DRAGON("The End... Again..."),
        MINECRAFT_END_DRAGON_BREATH("You Need a Mint"),
        MINECRAFT_END_FIND_END_CITY("The City at the End of the Game"),
        MINECRAFT_END_ELYTRA("Sky's the Limit"),
        MINECRAFT_END_LEVITATE("Great View From Up Here"),
        MINECRAFT_ADVENTURE_ROOT("Adventure"),
        MINECRAFT_ADVENTURE_VOLUNTARY_EXILE("Voluntary Exile"),
        MINECRAFT_ADVENTURE_KILL_A_MOB("Monster Hunter"),
        MINECRAFT_ADVENTURE_TRADE("What a Deal!"),
        MINECRAFT_ADVENTURE_HONEY_BLOCK_SLIDE("Sticky Situation"),
        MINECRAFT_ADVENTURE_OL_BETSY("Ol' Betsy"),
        MINECRAFT_ADVENTURE_SLEEP_IN_BED("Sweet dreams"),
        MINECRAFT_ADVENTURE_HERO_OF_THE_VILLAGE("Hero of the Village"),
        MINECRAFT_ADVENTURE_THROW_TRIDENT("A Throwaway Joke"),
        MINECRAFT_ADVENTURE_SHOOT_ARROW("Take Aim"),
        MINECRAFT_ADVENTURE_KILL_ALL_MOBS("Monsters Hunted"),
        MINECRAFT_ADVENTURE_TOTEM_OF_UNDYING("Postmortal"),
        MINECRAFT_ADVENTURE_SUMMON_IRON_GOLEM("Hired Help"),
        MINECRAFT_ADVENTURE_TWO_BIRDS_ONE_ARROW("Two Birds, One Arrow"),
        MINECRAFT_ADVENTURE_WHOS_THE_PILLAGER_NOW("Who's the Pillager Now?"),
        MINECRAFT_ADVENTURE_ARBALISTIC("Arbalistic"),
        MINECRAFT_ADVENTURE_ADVENTURING_TIME("Adventuring Time"),
        MINECRAFT_ADVENTURE_VERY_VERY_FRIGHTENING("Very Very Frightening"),
        MINECRAFT_ADVENTURE_SNIPER_DUEL("Sniper duel"),
        MINECRAFT_HUSBANDRY_ROOT("Husbandry"),
        MINECRAFT_HUSBANDRY_SAFELY_HARVEST_HONEY("Bee Our Guest"),
        MINECRAFT_HUSBANDRY_BREED_AN_ANIMAL("The Parrots and the Bats"),
        MINECRAFT_HUSBANDRY_TAME_AN_ANIMAL("Best Friends Forever"),
        MINECRAFT_HUSBANDRY_FISHY_BUSINESS("Fishy Business"),
        MINECRAFT_HUSBANDRY_SILK_TOUCH_NEST("Total Beelocation"),
        MINECRAFT_HUSBANDRY_PLANT_SEED("A Seedy Place"),
        MINECRAFT_HUSBANDRY_BRED_ALL_ANIMALS("Two by Two"),
        MINECRAFT_HUSBANDRY_COMPLETE_CATALOGUE("A Complete Catalogue"),
        MINECRAFT_HUSBANDRY_TACTICAL_FISHING("Tactical Fishing"),
        MINECRAFT_HUSBANDRY_BALANCED_DIET("A Balanced Diet"),
        MINECRAFT_HUSBANDRY_BREAK_DIAMOND_HOE("Serious Dedication");

        private final String name;

        Advancements(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private DiscordBot bot;

    public PlayerAdvancementListener(DiscordBot bot) {
        this.bot = bot;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void advancementDone(PlayerAdvancementDoneEvent event) {
        try {
            String adv = event.getAdvancement().getKey().getNamespace().toUpperCase() + "_" + event.getAdvancement().getKey().getKey().replace('/', '_').toUpperCase();
            bot.sendMessageToChannel(
                event.getPlayer().getName() + " has made the advancement [" + Advancements.valueOf(adv) + "]");
        } catch (IllegalArgumentException  e) {
            return;
        }               
    }

}
