/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.client.gui.implementations;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

import appeng.api.config.FuzzyMode;
import appeng.api.config.RedstoneMode;
import appeng.api.config.SchedulingMode;
import appeng.api.config.Settings;
import appeng.api.config.Upgrades;
import appeng.api.config.YesNo;
import appeng.api.implementations.IUpgradeableHost;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.widgets.ServerSettingToggleButton;
import appeng.client.gui.widgets.SettingToggleButton;
import appeng.container.implementations.UpgradeableContainer;
import appeng.core.localization.GuiText;
import appeng.parts.automation.ExportBusPart;
import appeng.parts.automation.ImportBusPart;

public class UpgradeableScreen<T extends UpgradeableContainer> extends AEBaseScreen<T> {

    protected final UpgradeableContainer cvb;
    protected final IUpgradeableHost bc;

    protected SettingToggleButton<RedstoneMode> redstoneMode;
    protected SettingToggleButton<FuzzyMode> fuzzyMode;
    protected SettingToggleButton<YesNo> craftMode;
    protected SettingToggleButton<SchedulingMode> schedulingMode;

    public UpgradeableScreen(T container, PlayerInventory playerInventory, Text title) {
        super(container, playerInventory, title);
        this.cvb = container;

        this.bc = (IUpgradeableHost) container.getTarget();
        this.backgroundWidth = this.hasToolbox() ? 246 : 211;
        this.backgroundHeight = 184;
    }

    protected boolean hasToolbox() {
        return (this.handler).hasToolbox();
    }

    @Override
    public void init() {
        super.init();
        this.addButtons();
    }

    protected void addButtons() {
        this.redstoneMode = new ServerSettingToggleButton<>(this.x - 18, this.y + 8, Settings.REDSTONE_CONTROLLED,
                RedstoneMode.IGNORE);
        addButton(this.redstoneMode);
        this.fuzzyMode = new ServerSettingToggleButton<>(this.x - 18, this.y + 28, Settings.FUZZY_MODE,
                FuzzyMode.IGNORE_ALL);
        addButton(this.fuzzyMode);
        this.craftMode = new ServerSettingToggleButton<>(this.x - 18, this.y + 48, Settings.CRAFT_ONLY, YesNo.NO);
        addButton(this.craftMode);
        this.schedulingMode = new ServerSettingToggleButton<>(this.x - 18, this.y + 68, Settings.SCHEDULING_MODE,
                SchedulingMode.DEFAULT);
        addButton(this.schedulingMode);
    }

    @Override
    public void drawFG(MatrixStack matrices, final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.textRenderer.method_30883(matrices, this.getGuiDisplayName(this.getName().text()), 8, 6, 4210752);
        this.textRenderer.method_30883(matrices, GuiText.inventory.text(), 8, this.backgroundHeight - 96 + 3, 4210752);

        if (this.redstoneMode != null) {
            this.redstoneMode.set(this.cvb.getRedStoneMode());
        }

        if (this.fuzzyMode != null) {
            this.fuzzyMode.set(this.cvb.getFuzzyMode());
        }

        if (this.craftMode != null) {
            this.craftMode.set(this.cvb.getCraftingMode());
        }

        if (this.schedulingMode != null) {
            this.schedulingMode.set(this.cvb.getSchedulingMode());
        }
    }

    @Override
    public void drawBG(MatrixStack matrices, final int offsetX, final int offsetY, final int mouseX, final int mouseY,
            float partialTicks) {
        this.handleButtonVisibility();

        this.bindTexture(this.getBackground());
        drawTexture(matrices, offsetX, offsetY, 0, 0, 211 - 34, this.backgroundHeight);
        if (this.drawUpgrades()) {
            drawTexture(matrices, offsetX + 177, offsetY, 177, 0, 35, 14 + this.cvb.availableUpgrades() * 18);
        }
        if (this.hasToolbox()) {
            drawTexture(matrices, offsetX + 178, offsetY + this.backgroundHeight - 90, 178, this.backgroundHeight - 90,
                    68, 68);
        }
    }

    protected void handleButtonVisibility() {
        if (this.redstoneMode != null) {
            this.redstoneMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.REDSTONE) > 0);
        }
        if (this.fuzzyMode != null) {
            this.fuzzyMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.FUZZY) > 0);
        }
        if (this.craftMode != null) {
            this.craftMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.CRAFTING) > 0);
        }
        if (this.schedulingMode != null) {
            this.schedulingMode.setVisibility(
                    this.bc.getInstalledUpgrades(Upgrades.CAPACITY) > 0 && this.bc instanceof ExportBusPart);
        }
    }

    protected String getBackground() {
        return "guis/bus.png";
    }

    protected boolean drawUpgrades() {
        return true;
    }

    protected GuiText getName() {
        return this.bc instanceof ImportBusPart ? GuiText.ImportBus : GuiText.ExportBus;
    }

}
