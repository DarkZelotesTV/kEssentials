package net.kettlemc.kessentials.teleport;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class Warp {

    private double x;

    private double y;

    private double z;

    private float yaw;

    private float pitch;

    private @NotNull String worldName;

    private @NotNull String name;

    private @Nullable UUID owner;

    private @Nullable String material;


    @Deprecated
    @JsonCreator
    public Warp() {
    }

    public Warp(@NotNull String name, @NotNull Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.worldName = location.getWorld().getName();
        this.name = name;
    }

    public Warp(@NotNull UUID owner, @NotNull String name, @NotNull Location location) {
        this(name, location);
        this.owner = owner;
    }

    public @NotNull Location location() {
        World world = Bukkit.getWorld(worldName);
        Objects.requireNonNull(world);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public @NotNull String name() {
        return name;
    }

    public @NotNull String worldName() {
        return worldName;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    public @NotNull Warp x(double x) {
        this.x = x;
        return this;
    }

    public @NotNull Warp y(double y) {
        this.y = y;
        return this;
    }

    public @NotNull Warp z(double z) {
        this.z = z;
        return this;
    }

    public @NotNull Warp yaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public @NotNull Warp pitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public @NotNull Warp worldName(@NotNull String worldName) {
        this.worldName = worldName;
        return this;
    }

    public @NotNull Warp name(@NotNull String name) {
        this.name = name;
        return this;
    }

    public @Nullable UUID owner() {
        return owner;
    }

    public @NotNull Warp location(@NotNull Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.worldName = location.getWorld().getName();
        return this;
    }

    public @Nullable World world() {
        return Bukkit.getWorld(worldName);
    }

    public @Nullable String material() {
        return material;
    }

    public @NotNull Warp material(@Nullable String material) {
        this.material = material;
        return this;
    }

    public @Nullable Material item() {
        return material == null ? null : Material.getMaterial(material);
    }

}
