

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.meteor.KeyEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.Freecam;
import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
import meteordevelopment.orbit.EventHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Party extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> jump = sgGeneral.add(new BoolSetting.Builder()
        .name("jump")
        .description("Makes you jump")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> sneak = sgGeneral.add(new BoolSetting.Builder()
        .name("sneak")
        .description("Makes you sneak")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> sneakTime = sgGeneral.add(new DoubleSetting.Builder()
        .name("sneakTime")
        .description("Time between sneaking and unsneaking")
        .defaultValue(1)
        .min(0.1)
        .build()
    );

    private static boolean sneaking;

    public Party() {
        super(Categories.Misc, "party", "Makes the player dance."); }

    private long lastSneakToggle = 0;

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (sneak.get() && System.currentTimeMillis() - lastSneakToggle >= sneakTime.get() * 1000) {
            sneaking = !sneaking;
            mc.player.setSneaking(sneaking);
            lastSneakToggle = System.currentTimeMillis();
        }

        if (mc.player.isOnGround() && jump.get()) {
            mc.player.jump();
        }
    }

    @Override
    public void onActivate() {
        sneaking = false;
    }

}

