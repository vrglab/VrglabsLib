package org.Vrglab.TestClasses;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.Vrglab.Screen.ScreenUtils;
import org.Vrglab.Utils.VLModInfo;

public class TestBlockScreen extends HandledScreen<TestBlockScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(VLModInfo.MOD_ID, "textures/screens/crafting_table.png");

    public TestBlockScreen(TestBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        ScreenUtils.drawBG(width, height, backgroundWidth, backgroundHeight, matrices, this, TEXTURE);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
