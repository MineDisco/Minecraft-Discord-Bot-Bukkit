package minedisco.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import minedisco.discord.DiscordBot;

public class PlayerAdvancementListener implements Listener {

    public enum Advancements {
        MINECRAFT_ADVENTURE_ADVENTURING_TIME("Adventuring Time"),
        MINECRAFT_ADVENTURE_ARBALISTIC("Arbalistic"),
        MINECRAFT_ADVENTURE_AVOID_VIBRATION("Sneak 100"),
        MINECRAFT_ADVENTURE_BULLSEYE("Bullseye"),
        MINECRAFT_ADVENTURE_FALL_FROM_WORLD_HEIGHT("Caves & Cliffs"),
        MINECRAFT_ADVENTURE_HERO_OF_THE_VILLAGE("Hero of the Village"),
        MINECRAFT_ADVENTURE_HONEY_BLOCK_SLIDE("Sticky Situation"),
        MINECRAFT_ADVENTURE_KILL_ALL_MOBS("Monsters Hunted"),
        MINECRAFT_ADVENTURE_KILL_A_MOB("Monster Hunter"),
        MINECRAFT_ADVENTURE_KILL_MOB_NEAR_SCULK_CATALYST("It Spreads"),       
        MINECRAFT_ADVENTURE_LIGHTNING_ROD_WITH_VILLAGER_NO_FIRE("Surge Protector"),
        MINECRAFT_ADVENTURE_OL_BETSY("Ol' Betsy"),
        MINECRAFT_ADVENTURE_PLAY_JUKEBOX_IN_MEADOWS("Sound of Music"),
        MINECRAFT_ADVENTURE_ROOT("Adventure"),
        MINECRAFT_ADVENTURE_SHOOT_ARROW("Take Aim"),
        MINECRAFT_ADVENTURE_SLEEP_IN_BED("Sweet dreams"),
        MINECRAFT_ADVENTURE_SNIPER_DUEL("Sniper duel"),
        MINECRAFT_ADVENTURE_SPYGLASS_AT_DRAGON("Is It a Plane?"),
        MINECRAFT_ADVENTURE_SPYGLASS_AT_GHAST("Is It a Balloon?"),
        MINECRAFT_ADVENTURE_SPYGLASS_AT_PARROT("Is It a Bird?"),
        MINECRAFT_ADVENTURE_SUMMON_IRON_GOLEM("Hired Help"),
        MINECRAFT_ADVENTURE_THROW_TRIDENT("A Throwaway Joke"),
        MINECRAFT_ADVENTURE_TOTEM_OF_UNDYING("Postmortal"),
        MINECRAFT_ADVENTURE_TRADE("What a Deal!"),
        MINECRAFT_ADVENTURE_TRADE_AT_WORLD_HEIGHT("Star Trader"),
        MINECRAFT_ADVENTURE_TWO_BIRDS_ONE_ARROW("Two Birds, One Arrow"),
        MINECRAFT_ADVENTURE_VERY_VERY_FRIGHTENING("Very Very Frightening"),
        MINECRAFT_ADVENTURE_VOLUNTARY_EXILE("Voluntary Exile"),
        MINECRAFT_ADVENTURE_WALK_ON_POWDER_SNOW_WITH_LEATHER_BOOTS("Light as a Rabbit"),
        MINECRAFT_ADVENTURE_WHOS_THE_PILLAGER_NOW("Who's the Pillager Now?"),
        MINECRAFT_END_DRAGON_BREATH("You Need a Mint"),
        MINECRAFT_END_DRAGON_EGG("The Next Generation"),
        MINECRAFT_END_ELYTRA("Sky's the Limit"),
        MINECRAFT_END_ENTER_END_GATEWAY("Remote Getaway"),
        MINECRAFT_END_FIND_END_CITY("The City at the End of the Game"),
        MINECRAFT_END_KILL_DRAGON("Free the End"),
        MINECRAFT_END_LEVITATE("Great View From Up Here"),
        MINECRAFT_END_RESPAWN_DRAGON("The End... Again..."),
        MINECRAFT_END_ROOT("The End"),
        MINECRAFT_HUSBANDRY_ALLAY_DELIVER_CAKE_TO_NOTE_BLOCK("Birthday Song"),
        MINECRAFT_HUSBANDRY_ALLAY_DELIVER_ITEM_TO_PLAYER("You've Got a Friend in Me"),
        MINECRAFT_HUSBANDRY_AXOLOTL_IN_A_BUCKET("The Cutest Predator"),
        MINECRAFT_HUSBANDRY_BALANCED_DIET("A Balanced Diet"),
        MINECRAFT_HUSBANDRY_BREAK_DIAMOND_HOE("Serious Dedication"),
        MINECRAFT_HUSBANDRY_BRED_ALL_ANIMALS("Two by Two"),
        MINECRAFT_HUSBANDRY_BREED_AN_ANIMAL("The Parrots and the Bats"),
        MINECRAFT_HUSBANDRY_COMPLETE_CATALOGUE("A Complete Catalogue"),
        MINECRAFT_HUSBANDRY_FISHY_BUSINESS("Fishy Business"),
        MINECRAFT_HUSBANDRY_FROGLIGHTS("With Our Powers Combined!"),
        MINECRAFT_HUSBANDRY_KILL_AXOLOTL_TARGET("The Healing Power of Friendship!"),
        MINECRAFT_HUSBANDRY_LEASH_ALL_FROG_VARIANTS("When the Squad Hops into Town"),
        MINECRAFT_HUSBANDRY_MAKE_A_SIGN_GLOW("Glow and Behold!"),
        MINECRAFT_HUSBANDRY_OBTAIN_NETHERITE_HOE("Serious Dedication"),
        MINECRAFT_HUSBANDRY_PLANT_SEED("A Seedy Place"),
        MINECRAFT_HUSBANDRY_RIDE_A_BOAT_WITH_A_GOAT("Whatever Floats Your Goat!"),
        MINECRAFT_HUSBANDRY_ROOT("Husbandry"),
        MINECRAFT_HUSBANDRY_SAFELY_HARVEST_HONEY("Bee Our Guest"),
        MINECRAFT_HUSBANDRY_SILK_TOUCH_NEST("Total Beelocation"),
        MINECRAFT_HUSBANDRY_TACTICAL_FISHING("Tactical Fishing"),
        MINECRAFT_HUSBANDRY_TADPOLE_IN_A_BUCKET("Bukkit Bukkit"),
        MINECRAFT_HUSBANDRY_TAME_AN_ANIMAL("Best Friends Forever"),
        MINECRAFT_HUSBANDRY_WAX_OFF("Wax Off"),
        MINECRAFT_HUSBANDRY_WAX_ON("Wax On"),
        MINECRAFT_NETHER_ALL_EFFECTS("How Did We Get Here?"),
        MINECRAFT_NETHER_ALL_POTIONS("A Furious Cocktail"),
        MINECRAFT_NETHER_BREW_POTION("Local Brewery"),
        MINECRAFT_NETHER_CHARGE_RESPAWN_ANCHOR("Not Quite \"Nine\" Lives"),
        MINECRAFT_NETHER_CREATE_BEACON("Bring Home the Beacon"),
        MINECRAFT_NETHER_CREATE_FULL_BEACON("Beaconator"),
        MINECRAFT_NETHER_DISTRACT_PIGLIN("Oh Shiny"),
        MINECRAFT_NETHER_EXPLORE_NETHER("Hot Tourist Destinations"),
        MINECRAFT_NETHER_FAST_TRAVEL("Subspace Bubble"),
        MINECRAFT_NETHER_FIND_BASTION("Those Were the Days"),
        MINECRAFT_NETHER_FIND_FORTRESS("A Terrible Fortress"),
        MINECRAFT_NETHER_GET_WITHER_SKULL("Spooky Scary Skeleton"),
        MINECRAFT_NETHER_LOOT_BASTION("War Pigs"),
        MINECRAFT_NETHER_NETHERITE_ARMOR("Cover Me in Debris"),
        MINECRAFT_NETHER_OBTAIN_ANCIENT_DEPRIS("Hidden in the Depths"),
        MINECRAFT_NETHER_OBTAIN_BLAZE_ROD("Into Fire"),
        MINECRAFT_NETHER_OBTAIN_CRYING_OBSIDIAN("Who is Cutting Onions?"),
        MINECRAFT_NETHER_RETURN_TO_SENDER("Return to Sender"),
        MINECRAFT_NETHER_RIDE_STRIDER("This Boat Has Legs"),
        MINECRAFT_NETHER_RIDE_STRIDER_IN_OVERWORLD_LAVA("Feels like home"),
        MINECRAFT_NETHER_ROOT("Nether"),
        MINECRAFT_NETHER_SUMMON_WITHER("Withering Heights"),
        MINECRAFT_NETHER_UNEASY_ALLIANCE("Uneasy Alliance"),
        MINECRAFT_NETHER_USE_LODESTONE("Country Lode, Take Me Home"),
        MINECRAFT_STORY_CURE_ZOMBIE_VILLAGER("Zombie Doctor"),
        MINECRAFT_STORY_DEFLECT_ARROW("Not Today, Thank You"),
        MINECRAFT_STORY_ENCHANT_ITEM("Enchanter"),
        MINECRAFT_STORY_ENTER_THE_END("The End?"),
        MINECRAFT_STORY_ENTER_THE_NETHER("We Need to Go Deeper"),
        MINECRAFT_STORY_FOLLOW_ENDER_EYE("Eye Spy"),
        MINECRAFT_STORY_FORM_OBSIDIAN("Ice Bucket Challenge"),
        MINECRAFT_STORY_IRON_TOOLS("Isn't It Iron Pick"),
        MINECRAFT_STORY_LAVA_BUCKET("Hot Stuff"),
        MINECRAFT_STORY_MINE_DIAMOND("Diamonds!"),
        MINECRAFT_STORY_MINE_STONE("Stone Age"),
        MINECRAFT_STORY_OBTAIN_ARMOR("Suit Up"),
        MINECRAFT_STORY_ROOT("Minecraft"),
        MINECRAFT_STORY_SHINY_GEAR("Cover Me With Diamonds"),
        MINECRAFT_STORY_SMELT_IRON("Acquire Hardware"),
        MINECRAFT_STORY_UPGRADE_TOOLS("Getting an Upgrade");

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
            bot.sendMessageToChannel(event.getPlayer().getName() + " ",  "has made the advancement [" + Advancements.valueOf(adv) + "] 🎉🎉🎉");
        } catch (IllegalArgumentException  e) {
            return;
        }               
    }

}
