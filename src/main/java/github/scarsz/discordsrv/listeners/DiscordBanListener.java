/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2017 Austin Shapiro AKA Scarsz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package github.scarsz.discordsrv.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.LangUtil;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class DiscordBanListener extends ListenerAdapter {

    @Override
    public void onGuildBan(GuildBanEvent event) {
        UUID linkedUuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getUser().getId());
        if (linkedUuid == null) {
            DiscordSRV.debug("Not handling ban for user " + event.getUser() + " because they didn't have a linked account");
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(linkedUuid);
        if (!offlinePlayer.hasPlayedBefore()) return;

        if (!DiscordSRV.config().getBoolean("BanSynchronizationDiscordToMinecraft")) {
            DiscordSRV.debug("Not handling ban for user " + event.getUser() + " because doing so is disabled in the config");
            return;
        }

        Bukkit.getBanList(BanList.Type.NAME).addBan(offlinePlayer.getName(), LangUtil.Message.BAN_DISCORD_TO_MINECRAFT.toString(), null, "Discord");
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent event) {
        UUID linkedUuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getUser().getId());
        if (linkedUuid == null) {
            DiscordSRV.debug("Not handling unban for user " + event.getUser() + " because they didn't have a linked account");
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(linkedUuid);
        if (!offlinePlayer.hasPlayedBefore()) return;

        if (!DiscordSRV.config().getBoolean("BanSynchronizationDiscordToMinecraft")) {
            DiscordSRV.debug("Not handling unban for user " + event.getUser() + " because doing so is disabled in the config");
            return;
        }

        Bukkit.getBanList(BanList.Type.NAME).pardon(offlinePlayer.getName());
    }

}