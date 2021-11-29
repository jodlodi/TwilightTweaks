package io.github.jodlodi.twilighttweaks.core;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Optional;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Twilight Tweaks")
public class CoreMod implements IFMLLoadingPlugin {
    static File modFile;

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        try {
            Path modsFolder = Paths.get("mods");
            Optional<Path> TFJar = Files.list(modsFolder).filter( j -> {
                try (FileSystem system = FileSystems.newFileSystem(j, null)) {
                    Path sexFile = system.getPath("twilightforest");
                    return Files.exists(sexFile);
                } catch (IOException e) {
                    return false;
                }
            }).findFirst();
            if (TFJar.isPresent()) loadModJar(new File(TFJar.get().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        MixinBootstrap.init();
        Mixins.addConfiguration("twilighttweaks.mixin.json");

        modFile = (File) data.get("coremodLocation");
        if (modFile == null) {
            modFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        }
    }

    private void loadModJar(File jar) throws Exception {
        ((LaunchClassLoader)this.getClass().getClassLoader()).addURL(jar.toURI().toURL());
        CoreModManager.getReparseableCoremods().add(jar.getName());
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
