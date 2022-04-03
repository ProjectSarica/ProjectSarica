package me.ci.project.sarica.containers.npcdialog;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.ci.project.sarica.ProjectSarica;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NPCDialogScreen extends ContainerScreen<NPCDialogContainer>
{
    private static final ResourceLocation TEXTURE_RESOURCE_LOCATION = new ResourceLocation(ProjectSarica.MOD_ID, "textures/gui/container/npc_dialog.png");

    private static final Rectangle2d TEXTURE_SIZE = new Rectangle2d(0, 0, 512, 256);
    private static final Rectangle2d GUI_SIZE = new Rectangle2d(0, 0, 276, 166);
    private static final Rectangle2d TITLE_LABEL_SIZE = new Rectangle2d(8, 6, 0, 0);
    private static final Rectangle2d INVENTORY_LABEL_SIZE = new Rectangle2d(107, 72, 0, 0);
    private static final Rectangle2d SCROLLBAR_SIZE = new Rectangle2d(276, 0, 12, 27);
    private static final Rectangle2d DIALOG_OPTION_SIZE = new Rectangle2d(288, 0, 88, 27);

    private int scrollOffset = 0;


    public NPCDialogScreen(NPCDialogContainer container, PlayerInventory inventory, ITextComponent title)
    {
        super(container, inventory, title);
        this.imageWidth = GUI_SIZE.getWidth();
        this.imageHeight = GUI_SIZE.getHeight();
        this.titleLabelX = TITLE_LABEL_SIZE.getX();
        this.titleLabelY = TITLE_LABEL_SIZE.getY();
        this.inventoryLabelX = INVENTORY_LABEL_SIZE.getX();
        this.inventoryLabelY = INVENTORY_LABEL_SIZE.getY();
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderDialogOptions(matrixStack, mouseX, mouseY);
    }


    @Override
    @SuppressWarnings("deprecation")
    protected void renderBg(MatrixStack matrixStack, float pPartialTicks, int pX, int pY)
    {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE_RESOURCE_LOCATION);
        blit(matrixStack, x, y, getBlitOffset(), //
            GUI_SIZE.getX(), GUI_SIZE.getY(), GUI_SIZE.getWidth(), GUI_SIZE.getHeight(), //
            TEXTURE_SIZE.getWidth(), TEXTURE_SIZE.getHeight());
    }


    protected void renderDialogOptions(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.minecraft.getTextureManager().bind(TEXTURE_RESOURCE_LOCATION);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        int scrollPaneX = x + 5;
        int scrollPaneY = y + 17;
    }
}
