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

import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard implements IScoreboard{
    private org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = scoreboard.registerNewObjective("test", "dummy");
    private Objective tabObjective = scoreboard.registerNewObjective("test1", "dummy");

    public Scoreboard(){
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        tabObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

    @Override
    public void setTabHeader(String header) {

    }

    @Override
    public void setFooter(String footer) {

    }

    @Override
    public void set(Player player) {
        player.setScoreboard(scoreboard);
    }

    @Override
    public void addScore(String identifier, String display, int number) {
        Team team = scoreboard.registerNewTeam(identifier);
        String[] splited = split(display);
        team.setPrefix(splited[0]);
        team.setDisplayName(splited[1]);
        team.setSuffix(splited[2]);
        objective.getScore(identifier).setScore(number);
    }

    @Override
    public void updateScore(String identifier, String newDisplay) {
        Team team = scoreboard.getTeam(identifier);
        String[] splited = split(newDisplay);
        team.setPrefix(splited[0]);
        team.setDisplayName(splited[1]);
        team.setSuffix(splited[2]);
    }

    @Override
    public void addTabPrefixTeam(String team, String prefix) {
        scoreboard.registerNewTeam(team).setPrefix(prefix);
        objective.
    }

    @Override
    public void setTabPrefixPlayer(String team, Player player) {

    }

    public String[] split(String display){
        ArrayList<String> arrayList = new ArrayList<>();
        int size = display.length();
        if (size > 32){
            arrayList.add(display.substring(0, 16));
            arrayList.add(display.substring(16, 32));
            arrayList.add(display.substring(32));
        }else if (size > 16){
            arrayList.add(display.substring(0, 16));
            arrayList.add(display.substring(16));
            arrayList.add("");
        }else {
            arrayList.add("");
            arrayList.add(display);
            arrayList.add("");
        }
        Object[] toReturn = arrayList.stream().toArray();
        return (String[]) toReturn;
    }
}
