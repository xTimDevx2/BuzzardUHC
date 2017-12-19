package me.xtimdevx.buzzarduhc.utils;

import me.xtimdevx.buzzarduhc.scenarios.Scenario;

/**
 * Created by xTimDevx on 11/10/2017.
 */
public class ScenarioUtils {

    public static String getShortScenario(Scenario scenario) {
        switch (scenario.getName()) {
            case "CUTCLEAN":
                return "CC";
            case "BALANCE":
                return "BAL";
            case "BALDCHICKEN":
                return "BC";
            case "BIOMEPARANOIA":
                return "BIP";
            case "BLOODDIAMONDS":
                return "BD";
            case "BOWLESS":
                return "BL";
            case "CHARITYCHUMP":
                return "CHC";
            case "CRAFTCLEAN":
                return "CRC";
            case "DIAMONDLESS":
                return "DL";
            case "ENCHANTPARANOIA":
                return "EP";
            case "FASTSMELTING":
                return "FS";
            case "FIRELESS":
                return "FL";
            case "HASTEYBOYS":
                return "HB";
            case "MELEEFUN":
                return "MF";
            case "MOLES":
                return "M";
            case "NOFALL":
                return "NF";
            case "PARANOIA":
                return "PAR";
            case "RODLESS":
                return "RL";
            case "SUPERHEROES":
                return "SH";
            case "SWITCHERORES":
                return "SO";
            case "TEAMINVENTORY":
                return "TI";
            case "TIMBER":
                return "T";
            case "TIMEBOMB":
                return "TB";
            case "TRIPLEORES":
                return "TO";
            case "WEAKESTLINK":
                return "WL";
            case "BESTPVE":
                return "BPVE";
            case "ACHIEVEMENTPARANOIA":
                return "AP";
            default:
                return "?";
        }
    }
}
