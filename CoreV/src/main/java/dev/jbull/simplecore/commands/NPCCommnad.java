package dev.jbull.simplecore.commands;

import dev.jbull.simplecore.npc.NPCProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommnad implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyMzQ4ODYyNDU2MiwKICAicHJvZmlsZUlkIiA6ICJjZTM5N2VmMDc5NzM0Y2U1YTNiMTNiZDZlN2ZjOTk3MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJKYXZhRXhjZXB0aW9uREUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU2MGUyNGVkMDExMjIyZTllOGE2NzVlZmQ3MjkwNjE4ZGVmZmE1MTBjNmI0NWI1OTI4MDE3NmU2MzUzZTNmNyIKICAgIH0KICB9Cn0=";
        String signature = "H3+ca3sFqeMxicJJrJsbid0WhIIC1KVSmEI0MusZPDe6J42dgnS/IcgmkW7nJ31aK5x1K4LLkpa8GE6j2g7YdUavpUNKBeLWAv2sT30gLniD6eaPf5TvfL0+2utLLo2qS93sO41x8C6CeTSsvDR0mprvWF5uk3efsxKms2iSPntuqLDeWu8wOmgoqgfPLxrZgt0rkv4olbk/Eva1QYn1DC1Xe2WTmOgOK4P51GdCvFMt+VpfOki83PSDNhmZ0g86QbziRwQcMOlZ/2TgvcqW3cwCu/9TaRmXWqq4dMEPGS3R1Tst/k3Dqjnz1wVz2W/0nKyxF2HKg3kG/0HPlu5VFY3UaQyprORnFgMwCEKGsTqE6Ar7xcJqbaP8R97IFFOJ+CuMhaIhtPeUPMhH9Af+eeC/xn6QRFoxDM+ErfyJAtcRR3lgUG2gn1zHvSZCav+ZxLEZqlXlnkSzPUWn0TJaTgAYzUfdFuC4vuZRoCx60DHJeJmN98hGSneLjeOJRRdkqqhfx4feE/LTioqvhZT+ZW5IR8sVGZTxd3pHNXTuNb9fh9phmsHjWkgRYgfsFvGuJIYH8EBG1j1iTp6r86JZ5307WM8zjNVTGeZ11JnvTV1eroe/wu1Di6QFCD6yraip39S3LAkwGxe5q61KaI9o2WCmABX8Y4PUSotyQLDUaz4=";
        new NPCProvider().spawnNPC(texture, signature, player.getLocation(), "Test", "2");
        return false;
    }
}
