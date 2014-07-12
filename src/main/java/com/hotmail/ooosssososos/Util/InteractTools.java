package com.hotmail.ooosssososos.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.material.Sign;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;

public class InteractTools {

    public static BlockAndPosition getTargetPoint(Player player, HashSet<Byte> transparent, int maxDistance) {
        List<Block> lastBlocks = player.getLastTwoTargetBlocks(transparent, maxDistance);
        Block block = lastBlocks.get(1);
        BlockFace face = block.getFace(lastBlocks.get(0));

        Vector plane = new Vector(block.getX(), block.getY(), block.getZ());
        // this is the lower northwest point of the block
        // we need a point (any point) on the correct side of the block
        if (block.getType() == Material.WALL_SIGN) {
            // of course signs are thin, and we don't have hitbox data available via Bukkit
            // so we'll have to cheat...
            Sign s = (Sign) ((org.bukkit.block.Sign) block.getState()).getData();
            switch (s.getFacing()) {
                case EAST:
                    plane.add(new Vector(0.125, 0.0, 0.0));
                    break;
                case WEST:
                    plane.add(new Vector(0.875, 0.0, 0.0));
                    break;
                case NORTH:
                    plane.add(new Vector(0.0, 0.0, 0.875));
                    break;
                case SOUTH:
                    plane.add(new Vector(0.0, 0.0, 0.125));
                    break;
            }
        } else {
            switch (face) {
                case EAST:
                    plane.add(new Vector(1.0, 0.0, 0.0));
                    break;
                case SOUTH:
                    plane.add(new Vector(0.0, 0.0, 1.0));
                    break;
                case UP:
                    plane.add(new Vector(0.0, 1.0, 0.0));
                    break;
            }
        }

        // normal to the block face
        Vector normal = new Vector(face.getModX(), face.getModY(), face.getModZ());

        // get any two points along the line the player is looking
        // player's eye location is an obvious choice
        // second point can be anywhere along the line of sight
        Location loc = player.getEyeLocation();
        Vector eye = new Vector(loc.getX(), loc.getY(), loc.getZ());
        Vector p2 = eye.clone().add(player.getLocation().getDirection());

        Vector isect = isectLinePlane(eye, p2, plane, normal, 0.0000001);
        return new BlockAndPosition(block, face, isect);
    }

    private static Vector isectLinePlane(Vector p0, Vector p1, Vector plane, Vector normal, double epsilon) {
        Vector u = p1.clone().subtract(p0);
        Vector w = p0.clone().subtract(plane);
        double dot = normal.dot(u);
        if (Math.abs(dot) > epsilon) {
            double fac = -normal.dot(w) / dot;
            u.multiply(fac);
            return p0.clone().add(u);
        } else {
            return null;
        }
    }

    public static class FaceCoordinate {
        public double x;
        public double y;

        public FaceCoordinate(double a, double b) {
            x = a;
            y = b;
        }
    }

    public static class BlockAndPosition {
        public final Block block;
        public final BlockFace face;
        public final Vector point;


        public BlockAndPosition(Block block, BlockFace face, Vector point) {
            this.block = block;
            this.face = face;
            this.point = point;
        }
    }


}
