package com.luvoos.benet.util;

import org.cloudburstmc.protocol.bedrock.data.skin.AnimationData;
import org.cloudburstmc.protocol.bedrock.data.skin.ImageData;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;

import java.util.ArrayList;
import java.util.List;

public class SkinUtil {
    public static SerializedSkin createCustomSkin() {
        // Initialize the required byte arrays with actual data
        byte[] skinImageBytes = new byte[8192]; // Replace with actual skin image data (64x64)
        byte[] capeImageBytes = new byte[2048]; // Replace with actual cape image data (64x32)

        // Use factory method to create ImageData instances
        ImageData skinImageData = ImageData.of(64, 64, skinImageBytes);
        ImageData capeImageData = ImageData.of(64, 32, capeImageBytes);

        // Initialize the animations list, replace with actual animation data if any
        List<AnimationData> animations = new ArrayList<>();

        // Example strings for geometryData and animationData
        String geometryData = ""; // Replace with actual geometry data
        String animationData = ""; // Replace with actual animation data

        // Logging to debug
        System.out.println("Skin ID: customSkinId");
        System.out.println("Skin Image Data: " + (skinImageData != null ? "Initialized" : "Null"));
        System.out.println("Animations: " + animations.size());
        System.out.println("Cape Image Data: " + (capeImageData != null ? "Initialized" : "Null"));
        System.out.println("Geometry Data: " + geometryData);
        System.out.println("Animation Data: " + animationData);

        return SerializedSkin.of(
                "customSkinId",    // Skin ID
                "",                // PlayFab ID
                "geometry.humanoid.custom", // Skin Resource Patch
                skinImageData,     // Skin Image Data
                animations,        // Animations
                capeImageData,     // Cape Image Data
                geometryData,      // Geometry Data
                animationData,     // Animation Data
                true,              // Premium Skin
                false,             // Persona Skin
                false,             // Cape On Classic
                "",                // Cape ID
                "customSkinId"     // Full Skin ID
        );
    }
}
