/*
 * Copyright  (c) 2021.  Jonathan Bull Contact at jonathan@jbull.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jbull.simplecore.commands;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.messages.Language;
import dev.jbull.simplecore.player.CorePlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Language_Command extends Command {
    public Language_Command(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (args.length == 1){
            CorePlayer corePlayer = Core.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
            try {
                Language language = Language.valueOf(args[0]);
                Core.getInstance().getPlayerManager().updateLanguage(corePlayer, language);
                player.sendMessage("Deine Sprache wurde geändert");
            }catch (IllegalArgumentException exception){
                player.sendMessage("Die gewählte Sprache ist nicht gültig");
            }
        }
    }
}
