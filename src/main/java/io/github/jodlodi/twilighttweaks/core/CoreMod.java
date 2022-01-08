package io.github.jodlodi.twilighttweaks.core;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import static net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import static net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;

@MCVersion("1.12.2")
@Name("Twilight Tweaks")
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
            PathMatcher twilightForestPath = modsFolder.getFileSystem().getPathMatcher("glob:twilightforest-1.12.2*universal.jar");
            Optional<Path> tfMatcher1Jar = Files.list(modsFolder).filter(jar -> twilightForestPath.matches(jar.getFileName())).findFirst();
            if (tfMatcher1Jar.isPresent()) {
                loadModJar(new File(tfMatcher1Jar.get().toString()));
            } else FMLLog.log.error("Twilight Forest jar is either missing or renamed!");
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
