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

package dev.jbull.simplecore.scoreboard;

import org.bukkit.entity.Player;

public interface IScoreboard {

    void setTabHeader(String header);

    void setFooter(String footer);

    void set(Player player);

    void addScore(String identifier, String display, int number);

    void updateScore(String identifier, String newDisplay);

    void addTabPrefixTeam(String team, String prefix);

    void setTabPrefixPlayer(String team, Player player);

}
