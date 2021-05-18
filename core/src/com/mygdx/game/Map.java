package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.sun.rowset.internal.WebRowSetXmlReader;

import java.util.ArrayList;


// Карта мира
public class Map {
    static ArrayList<Region> regions;
    static final float height = 2048, width = 4096;
    static public void createRegions() {

        // Строковые ресурсы
        I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("properties/region_names"));

        regions = new ArrayList<>();
        // Регионы Океании
        Region Alaska = new Region(bundle.get("alaska"), bundle.get("oceania") );
        Alaska.setCoordinates(-2048, 569, -1474, 1021);
        Alaska.setPopulation(200000);
        Alaska.addResource("oil", 2);
        Alaska.addResource("coal", 2);
        Alaska.addResource("fish", 2);
        Alaska.addResource("wood", 4);
        Alaska.setWaterAccess(true);
        Alaska.setLandAccess(true);
        regions.add(Alaska);

        Region WesternCanada = new Region(bundle.get("westernCanada"), bundle.get("oceania"));
        WesternCanada.setCoordinates(-1471, 569, -1160,  1021);
        WesternCanada.setPopulation(4300000);
        WesternCanada.addResource("fish", 1);
        WesternCanada.addResource("cattle", 1);
        WesternCanada.addResource("crops", 1);
        WesternCanada.addResource("wood", 5);
        WesternCanada.addResource("oil", 2);
        WesternCanada.addResource("gas", 2);
        WesternCanada.addResource("coal", 3);
        WesternCanada.setLandAccess(true);
        WesternCanada.setWaterAccess(true);
        regions.add(WesternCanada);

        Region EasternCanada = new Region(bundle.get("easternCanada"), bundle.get("oceania"));
        EasternCanada.setCoordinates(-1159, 569, -633, 1021);
        EasternCanada.setPopulation(13482000);
        EasternCanada.addResource("fish", 2);
        EasternCanada.addResource("cattle", 1);
        EasternCanada.addResource("crops", 2);
        EasternCanada.addResource("wood", 4);
        EasternCanada.addResource("oil", 3);
        EasternCanada.addResource("iron", 3);
        EasternCanada.addResource("coal", 2);
        EasternCanada.setLandAccess(true);
        EasternCanada.setWaterAccess(true);
        regions.add(EasternCanada);

        Region GreenlandAndIceland = new Region(bundle.get("greenland"), bundle.get("oceania"));
        GreenlandAndIceland.setCoordinates(-633, 569, -193, 1021);
        GreenlandAndIceland.setPopulation(389491);
        GreenlandAndIceland.addResource("fish", 5);
        GreenlandAndIceland.addResource("cattle", 2);
        GreenlandAndIceland.setWaterAccess(true);
        GreenlandAndIceland.setLandAccess(true);
        regions.add(GreenlandAndIceland);

        Region Hawaii = new Region(bundle.get("hawaii"), bundle.get("oceania"));
        Hawaii.setCoordinates(-2048, 172, -1341, 566);
        Hawaii.setPopulation(598000);
        Hawaii.addResource("fish", 3);
        Hawaii.addResource("cattle", 1);
        Hawaii.addResource("crops", 2);
        Hawaii.addResource("wood", 1);
        Hawaii.setLandAccess(true);
        Hawaii.setWaterAccess(true);
        regions.add(Hawaii);

        Region WestCoast = new Region(bundle.get("westCoast"), bundle.get("oceania"));
        WestCoast.setCoordinates(-1341, 342, -1208, 566);
        WestCoast.setPopulation(22354000);
        WestCoast.addResource("fish", 3);
        WestCoast.addResource("cattle", 3);
        WestCoast.addResource("crops", 3);
        WestCoast.addResource("wood", 2);
        WestCoast.addResource("oil", 1);
        WestCoast.setWaterAccess(true);
        WestCoast.setLandAccess(true);
        regions.add(WestCoast);

        Region MidWest = new Region(bundle.get("midWest"), bundle.get("oceania"));
        MidWest.setCoordinates(-1207, 402, -900, 566);
        MidWest.setPopulation(53796000);
        MidWest.addResource("fish", 1);
        MidWest.addResource("cattle", 4);
        MidWest.addResource("crops", 5);
        MidWest.addResource("wood", 2);
        MidWest.addResource("oil", 1);
        MidWest.addResource("iron", 2);
        MidWest.addResource("coal", 4);
        MidWest.setLandAccess(true);
        MidWest.setWaterAccess(false);
        regions.add(MidWest);

        Region EastCost = new Region(bundle.get("eastCoast"), bundle.get("oceania"));
        EastCost.setCoordinates(-900, 257, -593, 566);
        EastCost.setPopulation(78902000);
        EastCost.addResource("fish", 4);
        EastCost.addResource("cattle", 5);
        EastCost.addResource("crops", 5);
        EastCost.addResource("wood", 3);
        EastCost.addResource("oil", 1);
        EastCost.addResource("gas", 2);
        EastCost.addResource("iron", 1);
        EastCost.addResource("coal", 3);
        EastCost.setWaterAccess(true);
        EastCost.setLandAccess(true);
        regions.add(EastCost);

        Region SouthernStates = new Region(bundle.get("southernStates"), bundle.get("oceania"));
        SouthernStates.setCoordinates(-1207, 342,  -901, 399);
        SouthernStates.setPopulation(24271000);
        SouthernStates.addResource("cattle", 4);
        SouthernStates.addResource("crops", 5);
        SouthernStates.addResource("wood", 1);
        SouthernStates.addResource("oil", 3);
        SouthernStates.addResource("gas", 2);
        SouthernStates.addResource("coal", 1);
        SouthernStates.setLandAccess(true);
        SouthernStates.setWaterAccess(false);
        regions.add(SouthernStates);

        Region Mexico = new Region(bundle.get("mexico"), bundle.get("oceania"));
        Mexico.setCoordinates(-1341, 120, -900, 340);
        Mexico.setPopulation(37234000);
        Mexico.addResource("fish", 5);
        Mexico.addResource("cattle", 4);
        Mexico.addResource("crops", 4);
        Mexico.addResource("wood", 2);
        Mexico.addResource("oil", 1);
        Mexico.addResource("gas", 1);
        Mexico.addResource("iron", 1);
        Mexico.setWaterAccess(true);
        Mexico.setLandAccess(true);
        regions.add(Mexico);

        Region Caribbean = new Region(bundle.get("caribbean"), bundle.get("oceania"));
        Caribbean.setCoordinates(-900, 120 , -595, 253);
        Caribbean.setPopulation(15673000);
        Caribbean.addResource("fish", 4);
        Caribbean.addResource("cattle", 3);
        Caribbean.addResource("crops", 2);
        Caribbean.addResource("wood", 1);
        Caribbean.addResource("aluminum", 3);
        Caribbean.setLandAccess(true);
        Caribbean.setWaterAccess(true);
        regions.add(Caribbean);

        Region NorthOfLatin = new Region(bundle.get("northOfLatin"), bundle.get("oceania"));
        NorthOfLatin.setCoordinates(-1009, 8, -594, 116);
        NorthOfLatin.setPopulation(43553000);
        NorthOfLatin.addResource("fish", 3);
        NorthOfLatin.addResource("cattle", 4);
        NorthOfLatin.addResource("crops", 1);
        NorthOfLatin.addResource("wood", 2);
        NorthOfLatin.addResource("oil", 5);
        NorthOfLatin.addResource("gas", 2);
        NorthOfLatin.addResource("aluminum", 3);
        NorthOfLatin.setWaterAccess(true);
        NorthOfLatin.setLandAccess(true);
        regions.add(NorthOfLatin);

        Region Andes = new Region(bundle.get("andes"), bundle.get("oceania"));
        Andes.setCoordinates(-1009, -688, -761, 6);
        Andes.setPopulation(18350000);
        Andes.addResource("fish", 4);
        Andes.addResource("cattle", 3);
        Andes.addResource("crops", 2);
        Andes.addResource("wood", 2);
        Andes.addResource("gas", 1);
        Andes.addResource("coal", 2);
        Andes.setWaterAccess(true);
        Andes.setLandAccess(true);
        regions.add(Andes);

        Region Brazil = new Region(bundle.get("brazil"), bundle.get("oceania"));
        Brazil.setCoordinates(-760, -302, -431, 6);
        Brazil.setPopulation(72180000);
        Brazil.addResource("fish", 2);
        Brazil.addResource("cattle", 4);
        Brazil.addResource("crops", 4);
        Brazil.addResource("wood", 5);
        Brazil.addResource("oil", 2);
        Brazil.addResource("gas", 2);
        Brazil.addResource("iron", 2);
        Brazil.addResource("coal", 1);
        Brazil.addResource("aluminum", 2);
        Brazil.setLandAccess(true);
        Brazil.setWaterAccess(true);
        regions.add(Brazil);

        Region SoutheastOfLatin = new Region(bundle.get("southeastOfLatin"), bundle.get("oceania"));
        SoutheastOfLatin.setCoordinates(-760, -688, -431, -305);
        SoutheastOfLatin.setPopulation(23457000);
        SoutheastOfLatin.addResource("fish", 2);
        SoutheastOfLatin.addResource("cattle", 5);
        SoutheastOfLatin.addResource("crops", 4);
        SoutheastOfLatin.addResource("wood", 1);
        SoutheastOfLatin.addResource("oil", 1);
        SoutheastOfLatin.setWaterAccess(true);
        SoutheastOfLatin.setLandAccess(true);
        regions.add(SoutheastOfLatin);

        Region SoutheastOfPacific = new Region(bundle.get("southeastOfPacific"),
                bundle.get("oceania"));
        SoutheastOfPacific.setCoordinates(-1341,-688 , -1013, 118);
        SoutheastOfPacific.setPopulation(0);
        SoutheastOfPacific.addResource("fish", 2);
        SoutheastOfPacific.setLandAccess(false);
        SoutheastOfPacific.setWaterAccess(true);
        regions.add(SoutheastOfPacific);

        Region SouthOfPacific = new Region(bundle.get("southOfPacific"), bundle.get("oceania"));
        SouthOfPacific.setCoordinates(-2048, -686, -1341, 170);
        SouthOfPacific.setPopulation(0);
        SouthOfPacific.addResource("fish", 1);
        SouthOfPacific.setWaterAccess(true);
        SouthOfPacific.setLandAccess(false);
        regions.add(SouthOfPacific);

        Region CentralAtlantic = new Region(bundle.get("centralAtlantic"), bundle.get("oceania"));
        CentralAtlantic.setCoordinates(-594, 8,  -261, 566);
        CentralAtlantic.setPopulation(0);
        CentralAtlantic.addResource("fish", 3);
        CentralAtlantic.setWaterAccess(true);
        CentralAtlantic.setLandAccess(false);
        regions.add(CentralAtlantic);

        Region SouthAtlantic = new Region(bundle.get("southAtlantic"), bundle.get("oceania"));
        SouthAtlantic.setCoordinates(-431, -688,  -30, 6);
        SouthAtlantic.setPopulation(0);
        SouthAtlantic.addResource("fish", 3);
        SouthAtlantic.setWaterAccess(true);
        SouthAtlantic.setLandAccess(false);
        regions.add(SouthAtlantic);

        Region WesternEurope = new Region(bundle.get("westernEurope"), bundle.get("eurasia"));
        WesternEurope.setCoordinates(-263, 385, -89 , 566);
        WesternEurope.setPopulation(44156000);
        WesternEurope.addResource("fish", 5);
        WesternEurope.addResource("cattle", 4);
        WesternEurope.addResource("crops", 5);
        WesternEurope.addResource("wood", 1);
        WesternEurope.addResource("coal", 1);
        WesternEurope.setLandAccess(true);
        WesternEurope.setWaterAccess(true);
        regions.add(WesternEurope);

        Region Britain = new Region(bundle.get("britain"), bundle.get("oceania"));
        Britain.setCoordinates(-192, 569, -70, 667);
        Britain.setPopulation(49345000);
        Britain.addResource("fish", 5);
        Britain.addResource("cattle", 4);
        Britain.addResource("crops", 2);
        Britain.addResource("wood", 1);
        Britain.addResource("oil", 1);
        Britain.addResource("coal", 1);
        Britain.setWaterAccess(true);
        Britain.setLandAccess(true);
        regions.add(Britain);

        Region CentralEurope = new Region(bundle.get("centralEurope"), bundle.get("eurasia"));
        CentralEurope.setCoordinates(-89, 395, 98, 567);
        CentralEurope.setPopulation(180132000);
        CentralEurope.addResource("fish", 5);
        CentralEurope.addResource("cattle", 4);
        CentralEurope.addResource("crops", 4);
        CentralEurope.addResource("wood", 4);
        CentralEurope.addResource("oil", 1);
        CentralEurope.addResource("coal", 2);
        CentralEurope.addResource("aluminum", 1);
        CentralEurope.setWaterAccess(true);
        CentralEurope.setLandAccess(true);
        regions.add(CentralEurope);

        Region NorthWestAfrica = new Region(bundle.get("northwestAfrica"), bundle.get("eurasia"));
        NorthWestAfrica.setCoordinates(-260, 180, -87, 382);
        NorthWestAfrica.setPopulation(26780000);
        NorthWestAfrica.addResource("fish", 3);
        NorthWestAfrica.addResource("cattle", 3);
        NorthWestAfrica.addResource("crops", 2);
        NorthWestAfrica.addResource("oil", 1);
        NorthWestAfrica.addResource("gas", 1);
        NorthWestAfrica.addResource("iron", 3);
        NorthWestAfrica.addResource("coal", 2);
        NorthWestAfrica.setWaterAccess(true);
        NorthWestAfrica.setLandAccess(true);
        regions.add(NorthWestAfrica);

        Region Egypt = new Region(bundle.get("egypt"), bundle.get("eurasia"));
        Egypt.setCoordinates(129, 180, 264, 390);
        Egypt.setPopulation(26630000);
        Egypt.addResource("fish", 3);
        Egypt.addResource("cattle", 2);
        Egypt.addResource("crops", 2);
        Egypt.addResource("oil", 1);
        Egypt.addResource("coal", 1);
        Egypt.setWaterAccess(true);
        Egypt.setLandAccess(true);
        regions.add(Egypt);

        Region CentralAfrica = new Region(bundle.get("centralAfrica"), bundle.get("eurasia"));
        CentralAfrica.setCoordinates(-33, -150, 262, 177);
        CentralAfrica.setPopulation(103361000);
        CentralAfrica.addResource("fish", 2);
        CentralAfrica.addResource("cattle", 1);
        CentralAfrica.addResource("crops", 1);
        CentralAfrica.addResource("wood", 5);
        CentralAfrica.addResource("oil", 2);
        CentralAfrica.addResource("gas", 1);
        CentralAfrica.addResource("iron", 1);
        CentralAfrica.setLandAccess(true);
        CentralAfrica.setWaterAccess(true);
        regions.add(CentralAfrica);

        Region WesternAfrica = new Region(bundle.get("westernAfrica"), bundle.get("eurasia"));
        WesternAfrica.setCoordinates(-263, 10, -32, 178);
        WesternAfrica.setPopulation(77765000);
        WesternAfrica.addResource("fish", 3);
        WesternAfrica.addResource("cattle", 3);
        WesternAfrica.addResource("crops", 1);
        WesternAfrica.addResource("wood", 2);
        WesternAfrica.addResource("aluminum", 3);
        WesternAfrica.addResource("iron", 2);
        WesternAfrica.setWaterAccess(true);
        WesternAfrica.setLandAccess(true);
        regions.add(WesternAfrica);

        Region NorthAfrica = new Region(bundle.get("northAfrica"), bundle.get("eurasia"));
        NorthAfrica.setCoordinates(-89, 180, 129, 391);
        NorthAfrica.setPopulation(7890000);
        NorthAfrica.addResource("fish", 3);
        NorthAfrica.addResource("cattle", 2);
        NorthAfrica.addResource("crops", 1);
        NorthAfrica.addResource("oil", 3);
        NorthAfrica.addResource("gas", 2);
        NorthAfrica.addResource("iron", 1);
        NorthAfrica.setLandAccess(true);
        NorthAfrica.setWaterAccess(true);
        regions.add(NorthAfrica);

        Region EasternAfrica = new Region(bundle.get("eastAfrica"), bundle.get("eurasia"));
        EasternAfrica.setCoordinates(263, -151, 487, 109);
        EasternAfrica.setPopulation(4329000);
        EasternAfrica.addResource("fish", 2);
        EasternAfrica.addResource("cattle", 3);
        EasternAfrica.addResource("crops", 3);
        EasternAfrica.addResource("wood", 1);
        EasternAfrica.setLandAccess(true);
        EasternAfrica.setWaterAccess(true);
        regions.add(EasternAfrica);

        Region MiddleEast = new Region(bundle.get("middleEast"), bundle.get("eurasia"));
        MiddleEast.setCoordinates(263, 113, 487, 389);
        MiddleEast.setPopulation(36917000);
        MiddleEast.addResource("fish", 3);
        MiddleEast.addResource("cattle", 2);
        MiddleEast.addResource("crops", 2);
        MiddleEast.addResource("oil", 5);
        MiddleEast.addResource("gas", 4);
        MiddleEast.addResource("iron", 1);
        MiddleEast.setWaterAccess(true);
        MiddleEast.setLandAccess(true);
        regions.add(MiddleEast);

        Region SouthAfrica = new Region(bundle.get("southAfrica"), bundle.get("oceania"));
        SouthAfrica.setCoordinates(-30, -440, 306, -153);
        SouthAfrica.setPopulation(35719000);
        SouthAfrica.addResource("fish", 3);
        SouthAfrica.addResource("cattle", 3);
        SouthAfrica.addResource("crops", 2);
        SouthAfrica.addResource("oil", 2);
        SouthAfrica.addResource("coal", 1);
        SouthAfrica.setLandAccess(true);
        SouthAfrica.setWaterAccess(true);
        regions.add(SouthAfrica);

        Region WestOfSouthernOcean = new Region(bundle.get("westOfSouthernOcean"),
                bundle.get("oceania"));
        WestOfSouthernOcean.setCoordinates(-32, -687, 476, -445);
        WestOfSouthernOcean.setPopulation(0);
        WestOfSouthernOcean.addResource("fish", 2);
        WestOfSouthernOcean.setWaterAccess(true);
        WestOfSouthernOcean.setLandAccess(false);
        regions.add(WestOfSouthernOcean);

        Region Madagascar = new Region(bundle.get("madagascar"), bundle.get("oceania"));
        Madagascar.setCoordinates(306, -440, 487, -155);
        Madagascar.setPopulation(5287000);
        Madagascar.addResource("fish", 1);
        Madagascar.addResource("cattle", 3);
        Madagascar.addResource("crops", 1);
        Madagascar.setWaterAccess(true);
        Madagascar.setLandAccess(true);
        regions.add(Madagascar);

        Region EastOfSouthernOcean = new Region(bundle.get("eastOfSouthernOcean"),
                bundle.get("oceania"));
        EastOfSouthernOcean.setCoordinates(478, -685, 948, -444);
        EastOfSouthernOcean.setPopulation(0);
        EastOfSouthernOcean.addResource("fish", 2);
        EastOfSouthernOcean.setLandAccess(false);
        EastOfSouthernOcean.setWaterAccess(true);
        regions.add(EastOfSouthernOcean);

        Region NorthernEurope = new Region(bundle.get("northernEurope"), bundle.get("eurasia"));
        NorthernEurope.setCoordinates(-72, 569, 185, 824);
        NorthernEurope.setPopulation(29546000);
        NorthernEurope.addResource("fish", 5);
        NorthernEurope.addResource("cattle", 3);
        NorthernEurope.addResource("crops", 2);
        NorthernEurope.addResource("wood", 4);
        NorthernEurope.addResource("oil", 2);
        NorthernEurope.addResource("gas", 2);
        NorthernEurope.addResource("iron", 3);
        NorthernEurope.setLandAccess(true);
        NorthernEurope.setWaterAccess(true);
        regions.add(NorthernEurope);

        Region NorthernAtlantic = new Region(bundle.get("northernAtlantic"), bundle.get("eurasia"));
        NorthernAtlantic.setCoordinates(-192, 670, -74, 1021);
        NorthernAtlantic.setPopulation(0);
        NorthernAtlantic.addResource("fish", 3);
        NorthernAtlantic.setWaterAccess(true);
        NorthernAtlantic.setLandAccess(true);
        regions.add(NorthernAtlantic);

        Region SouthEasternEurope = new Region(bundle.get("southEasternEurope"),
                bundle.get("eurasia"));
        SouthEasternEurope.setCoordinates(98, 395, 187, 568);
        SouthEasternEurope.setPopulation(27815000);
        SouthEasternEurope.addResource("fish", 2);
        SouthEasternEurope.addResource("cattle", 4);
        SouthEasternEurope.addResource("crops", 5);
        SouthEasternEurope.addResource("wood", 4);
        SouthEasternEurope.addResource("oil", 1);
        SouthEasternEurope.addResource("gas", 1);
        SouthEasternEurope.addResource("coal", 5);
        SouthEasternEurope.addResource("aluminum", 3);
        SouthEasternEurope.setLandAccess(true);
        SouthEasternEurope.setWaterAccess(true);
        regions.add(SouthEasternEurope);

        Region BlackSea = new Region(bundle.get("blackSea"), bundle.get("eurasia"));
        BlackSea.setCoordinates(186, 395, 325, 528);
        BlackSea.setPopulation(34512000);
        BlackSea.addResource("fish", 5);
        BlackSea.addResource("cattle", 4);
        BlackSea.addResource("crops", 4);
        BlackSea.addResource("oil", 2);
        BlackSea.addResource("coal", 3);
        BlackSea.addResource("wood", 2);
        BlackSea.setLandAccess(true);
        BlackSea.setWaterAccess(true);
        regions.add(BlackSea);

        Region CaspianSea = new Region(bundle.get("caspianSea"), bundle.get("eurasia"));
        CaspianSea.setCoordinates(324, 395, 456, 528);
        CaspianSea.setPopulation(17615000);
        CaspianSea.addResource("fish", 4);
        CaspianSea.addResource("cattle", 4);
        CaspianSea.addResource("crops", 3);
        CaspianSea.addResource("oil", 1);
        CaspianSea.addResource("coal", 4);
        CaspianSea.addResource("wood", 2);
        CaspianSea.setWaterAccess(true);
        CaspianSea.setLandAccess(true);
        regions.add(CaspianSea);

        Region CentralRussia = new Region(bundle.get("centralRussia"), bundle.get("eurasia"));
        CentralRussia.setCoordinates(185, 529, 454, 823);
        CentralRussia.setPopulation(58913000);
        CentralRussia.addResource("fish", 2);
        CentralRussia.addResource("cattle", 4);
        CentralRussia.addResource("crops", 5);
        CentralRussia.addResource("wood", 5);
        CentralRussia.addResource("oil", 1);
        CentralRussia.addResource("coal", 3);
        CentralRussia.addResource("iron", 1);
        CentralRussia.addResource("aluminum", 1);
        CentralRussia.setWaterAccess(true);
        CentralRussia.setLandAccess(true);
        regions.add(CentralRussia);

        Region Spitsbergen = new Region(bundle.get("spitsbergen"), bundle.get("eurasia"));
        Spitsbergen.setCoordinates(-71, 827, 183, 1021);
        Spitsbergen.setPopulation(2500);
        Spitsbergen.addResource("fish", 4);
        Spitsbergen.addResource("oil", 1);
        Spitsbergen.setLandAccess(true);
        Spitsbergen.setWaterAccess(true);
        regions.add(Spitsbergen);

        Region WesternSiberia = new Region(bundle.get("westernSiberia"), bundle.get("eurasia"));
        WesternSiberia.setCoordinates(456, 530, 732, 824);
        WesternSiberia.setPopulation(10134000);
        WesternSiberia.addResource("fish", 2);
        WesternSiberia.addResource("cattle", 1);
        WesternSiberia.addResource("crops", 3);
        WesternSiberia.addResource("wood", 5);
        WesternSiberia.addResource("oil", 3);
        WesternSiberia.addResource("gas", 4);
        WesternSiberia.addResource("coal", 3);
        WesternSiberia.addResource("iron", 2);
        WesternSiberia.setWaterAccess(true);
        WesternSiberia.setLandAccess(true);
        regions.add(WesternSiberia);

        Region CentralAsia = new Region(bundle.get("centralAsia"), bundle.get("eastasia"));
        CentralAsia.setCoordinates(455, 395, 827, 528);
        CentralAsia.setPopulation(14567000);
        CentralAsia.addResource("fish", 1);
        CentralAsia.addResource("cattle", 4);
        CentralAsia.addResource("crops", 4);
        CentralAsia.addResource("wood", 3);
        CentralAsia.addResource("oil", 2);
        CentralAsia.addResource("gas", 1);
        CentralAsia.addResource("coal", 3);
        CentralAsia.setWaterAccess(true);
        CentralAsia.setLandAccess(true);
        regions.add(CentralAsia);

        Region SouthernAsia = new Region(bundle.get("southernAsia"), bundle.get("eastasia"));
        SouthernAsia.setCoordinates(488, 32, 823, 390);
        SouthernAsia.setPopulation(537541000);
        SouthernAsia.addResource("fish", 3);
        SouthernAsia.addResource("cattle", 4);
        SouthernAsia.addResource("crops", 5);
        SouthernAsia.addResource("wood", 2);
        SouthernAsia.addResource("oil", 1);
        SouthernAsia.addResource("gas", 1);
        SouthernAsia.addResource("coal", 1);
        SouthernAsia.addResource("iron", 1);
        SouthernAsia.addResource("aluminum", 3);
        SouthernAsia.setWaterAccess(true);
        SouthernAsia.setLandAccess(true);
        regions.add(SouthernAsia);

        Region EasternSiberia = new Region(bundle.get("easternSiberia"), bundle.get("eurasia"));
        EasternSiberia.setCoordinates(732, 529, 1195, 823);
        EasternSiberia.setPopulation(6717000);
        EasternSiberia.addResource("fish", 2);
        EasternSiberia.addResource("cattle", 1);
        EasternSiberia.addResource("crops", 2);
        EasternSiberia.addResource("wood", 5);
        EasternSiberia.addResource("oil", 3);
        EasternSiberia.addResource("gas", 3);
        EasternSiberia.addResource("coal", 3);
        EasternSiberia.addResource("iron", 1);
        EasternSiberia.setWaterAccess(true);
        EasternSiberia.setLandAccess(true);
        regions.add(EasternSiberia);

        Region FarEast = new Region(bundle.get("farEast"), bundle.get("eurasia"));
        FarEast.setCoordinates(1195, 529, 1760, 823);
        FarEast.setPopulation(5419000);
        FarEast.addResource("fish", 4);
        FarEast.addResource("cattle", 1);
        FarEast.addResource("wood", 4);
        FarEast.addResource("oil", 2);
        FarEast.addResource("gas", 2);
        FarEast.addResource("coal", 2);
        FarEast.addResource("iron", 1);
        FarEast.setLandAccess(true);
        FarEast.setWaterAccess(true);
        regions.add(FarEast);

        Region NorthOfIndianOcean = new Region(bundle.get("northOfIndianOcean"),
                bundle.get("eastasia"));
        NorthOfIndianOcean.setCoordinates(488, -242, 823, 30);
        NorthOfIndianOcean.setPopulation(0);
        NorthOfIndianOcean.addResource("fish", 2);
        NorthOfIndianOcean.setWaterAccess(true);
        NorthOfIndianOcean.setLandAccess(false);
        regions.add(NorthOfIndianOcean);

        Region SouthOfIndianOcean = new Region(bundle.get("southOfIndianOcean"),
                bundle.get("eastasia"));
        SouthOfIndianOcean.setCoordinates(488, -441, 823, -244);
        SouthOfIndianOcean.setPopulation(0);
        SouthOfIndianOcean.addResource("fish", 2);
        SouthOfIndianOcean.setLandAccess(false);
        SouthOfIndianOcean.setWaterAccess(true);
        regions.add(SouthOfIndianOcean);

        Region NorthernChina = new Region(bundle.get("northernChina"), bundle.get("eastasia"));
        NorthernChina.setCoordinates(827, 377, 1101, 529);
        NorthernChina.setPopulation(386583000);
        NorthernChina.addResource("fish", 5);
        NorthernChina.addResource("cattle", 5);
        NorthernChina.addResource("crops", 4);
        NorthernChina.addResource("wood", 3);
        NorthernChina.addResource("oil", 2);
        NorthernChina.addResource("gas", 1);
        NorthernChina.addResource("coal", 1);
        NorthernChina.setWaterAccess(true);
        NorthernChina.setLandAccess(true);
        regions.add(NorthernChina);

        Region SouthernChina = new Region(bundle.get("southernChina"), bundle.get("eastasia"));
        SouthernChina.setCoordinates(826, 187, 1101, 373);
        SouthernChina.setPopulation(327471000);
        SouthernChina.addResource("fish", 4);
        SouthernChina.addResource("cattle", 5);
        SouthernChina.addResource("crops", 4);
        SouthernChina.addResource("wood", 3);
        SouthernChina.addResource("oil", 2);
        SouthernChina.addResource("gas", 1);
        SouthernChina.addResource("coal", 2);
        SouthernChina.addResource("iron", 1);
        SouthernChina.setWaterAccess(true);
        SouthernChina.setLandAccess(true);
        regions.add(SouthernChina);

        Region SouthEastAsia = new Region(bundle.get("southeastAsia"), bundle.get("eastasia"));
        SouthEastAsia.setCoordinates(825, -156, 1281, 185);
        SouthEastAsia.setPopulation(244317000);
        SouthEastAsia.addResource("fish", 3);
        SouthEastAsia.addResource("cattle", 2);
        SouthEastAsia.addResource("crops", 3);
        SouthEastAsia.addResource("wood", 5);
        SouthEastAsia.addResource("oil", 1);
        SouthEastAsia.addResource("gas", 3);
        SouthEastAsia.addResource("coal", 2);
        SouthEastAsia.setLandAccess(true);
        SouthEastAsia.setWaterAccess(true);
        regions.add(SouthEastAsia);

        Region WestOfIndianOcean = new Region(bundle.get("westOfIndianOcean"),
                bundle.get("eastasia"));
        WestOfIndianOcean.setCoordinates(823, -443, 947, -161);
        WestOfIndianOcean.setPopulation(0);
        WestOfIndianOcean.addResource("fish", 2);
        WestOfIndianOcean.setWaterAccess(true);
        WestOfIndianOcean.setLandAccess(false);
        regions.add(WestOfIndianOcean);

        Region Australia = new Region(bundle.get("australia"), bundle.get("oceania"));
        Australia.setCoordinates(945, -685, 1435, -160);
        Australia.setPopulation(10280000);
        Australia.addResource("fish", 2);
        Australia.addResource("cattle", 3);
        Australia.addResource("crops", 2);
        Australia.addResource("wood", 2);
        Australia.addResource("gas", 2);
        Australia.addResource("oil", 1);
        Australia.addResource("iron", 4);
        Australia.addResource("coal", 2);
        Australia.addResource("aluminum", 4);
        Australia.setLandAccess(true);
        Australia.setWaterAccess(true);
        regions.add(Australia);

        Region EastAsia = new Region(bundle.get("eastAsia"), bundle.get("eastasia"));
        EastAsia.setCoordinates(1102, 327, 1366, 529);
        EastAsia.setPopulation(198216000);
        EastAsia.addResource("fish", 5);
        EastAsia.addResource("cattle", 3);
        EastAsia.addResource("crops", 2);
        EastAsia.addResource("wood", 4);
        EastAsia.addResource("iron", 2);
        EastAsia.addResource("coal", 1);
        EastAsia.setWaterAccess(true);
        EastAsia.setLandAccess(true);
        regions.add(EastAsia);

        Region EastChinaSea = new Region(bundle.get("eastChinaSea"), bundle.get("eastasia"));
        EastChinaSea.setCoordinates(1102, 188, 1365, 323);
        EastChinaSea.setPopulation(0);
        EastChinaSea.addResource("fish", 3);
        EastChinaSea.setLandAccess(false);
        EastChinaSea.setWaterAccess(true);
        regions.add(EastChinaSea);

        Region WestOfArcticOcean = new Region(bundle.get("westOfArctic"), bundle.get("eurasia"));
        WestOfArcticOcean.setCoordinates(183, 827, 736, 1021);
        WestOfArcticOcean.setPopulation(3000);
        WestOfArcticOcean.addResource("fish", 1);
        WestOfArcticOcean.addResource("oil", 3);
        WestOfArcticOcean.setWaterAccess(true);
        WestOfArcticOcean.setLandAccess(true);
        regions.add(WestOfArcticOcean);

        Region NorthernSiberian = new Region(bundle.get("northernSiberian"), bundle.get("eurasia"));
        NorthernSiberian.setCoordinates(737, 827, 1013, 1021);
        NorthernSiberian.setPopulation(32000);
        NorthernSiberian.addResource("fish", 1);
        NorthernSiberian.setWaterAccess(true);
        NorthernSiberian.setLandAccess(true);
        regions.add(NorthernSiberian);

        Region EastOfArctic = new Region(bundle.get("eastOfArctic"), bundle.get("eurasia"));
        EastOfArctic.setCoordinates(1013, 827, 1757, 1021);
        EastOfArctic.setPopulation(1000);
        EastOfArctic.addResource("fish", 1);
        EastOfArctic.setWaterAccess(true);
        EastOfArctic.setLandAccess(true);
        regions.add(EastOfArctic);

        Region NorthOfPacific = new Region(bundle.get("northOfPacific"), bundle.get("eurasia"));
        NorthOfPacific.setCoordinates(1366, 189, 1756, 529);
        NorthOfPacific.setPopulation(0);
        NorthOfPacific.addResource("fish", 2);
        NorthOfPacific.setWaterAccess(true);
        NorthOfPacific.setLandAccess(false);
        regions.add(NorthOfPacific);

        Region PapuaNewGuinea = new Region(bundle.get("papuaNewGuinea"), bundle.get("oceania"));
        PapuaNewGuinea.setCoordinates(1281, -157, 1503,184);
        PapuaNewGuinea.setPopulation(2256000);
        PapuaNewGuinea.addResource("fish", 3);
        PapuaNewGuinea.addResource("wood", 5);
        PapuaNewGuinea.addResource("gas", 1);
        PapuaNewGuinea.setWaterAccess(true);
        PapuaNewGuinea.setLandAccess(true);
        regions.add(PapuaNewGuinea);

        Region CentralPacific = new Region(bundle.get("centralPacific"), bundle.get("oceania"));
        CentralPacific.setCoordinates(1503, -157, 1755, 184);
        CentralPacific.setPopulation(0);
        CentralPacific.addResource("fish", 2);
        CentralPacific.setLandAccess(false);
        CentralPacific.setWaterAccess(true);
        regions.add(CentralPacific);

        Region SouthEastOfMelanesia = new Region(bundle.get("southeastOfMelanesia"),
                bundle.get("oceania"));
        SouthEastOfMelanesia.setCoordinates(1436, -363, 1756, -159);
        SouthEastOfMelanesia.setPopulation(112000);
        SouthEastOfMelanesia.addResource("fish", 2);
        SouthEastOfMelanesia.addResource("wood", 4);
        SouthEastOfMelanesia.setWaterAccess(true);
        SouthEastOfMelanesia.setLandAccess(true);
        regions.add(SouthEastOfMelanesia);

        Region NewZealand = new Region(bundle.get("newZealand"), bundle.get("oceania"));
        NewZealand.setCoordinates(1435, -683, 1753, -368);
        NewZealand.setPopulation(2273000);
        NewZealand.addResource("fish", 2);
        NewZealand.addResource("cattle", 3);
        NewZealand.addResource("wood", 2);
        NewZealand.setWaterAccess(true);
        NewZealand.setLandAccess(true);
        regions.add(NewZealand);
    }
}
