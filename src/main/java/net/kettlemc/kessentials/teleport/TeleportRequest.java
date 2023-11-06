package net.kettlemc.kessentials.teleport;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportRequest {

    // Map of all players who have a teleport request and all the players that want to be teleported to them
    private static final Map<Player, List<Player>> TELEPORT_REQUESTS = new ConcurrentHashMap<>();

    private TeleportRequest() {
    }

    /**
     * Request a teleport from requester to target
     *
     * @param requester The player who is requesting the teleport
     * @param target    The player who the requester will be teleported to
     * @return Whether the request was successful (if the target already has a request from the requester, it will return false)
     */
    public static boolean request(Player requester, Player target) {
        if (!TELEPORT_REQUESTS.containsKey(target)) {
            TELEPORT_REQUESTS.put(target, Collections.synchronizedList(new ArrayList<>()));
        }

        if (TELEPORT_REQUESTS.get(target).contains(requester)) {
            return false;
        }

        TELEPORT_REQUESTS.get(target).add(requester);
        return true;
    }

    /**
     * Returns all players who have an active requests for a player. If the player has no requests, it will return an empty list. The list returned is unmodifiable.
     *
     * @param target The player to check for requests
     * @return A list of all players who have an active request for the target
     */
    public static List<Player> getRequestsFor(Player target) {
        if (!TELEPORT_REQUESTS.containsKey(target)) {
            TELEPORT_REQUESTS.put(target, Collections.synchronizedList(new ArrayList<>()));
        }
        return Collections.unmodifiableList(TELEPORT_REQUESTS.get(target));
    }

    /**
     * Removes a request from a player
     *
     * @param target    The player to remove the request from
     * @param requester The player who requested the teleport
     * @return Whether the request was removed (if the target has no requests from the requester, it will return false)
     */
    public static boolean remove(Player target, Player requester) {
        return TELEPORT_REQUESTS.containsKey(target) && TELEPORT_REQUESTS.get(target).remove(requester);
    }

    /**
     * Completely removes all requests to and from a player
     *
     * @param player The player to remove all requests from
     */
    public static void clear(Player player) {
        TELEPORT_REQUESTS.remove(player);
        TELEPORT_REQUESTS.forEach((key, value) -> value.remove(player));
    }


}
