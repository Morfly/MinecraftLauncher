package com.mcbox.pesdk.mcfloat.func;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.mcbox.pesdk.C1687R;
import com.mcbox.pesdk.mcfloat.model.EnchantItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DtEnchant {
    private static final String XML_TAG_ITEM = "item";
    public static List<EnchantItem> enchantDataList = new ArrayList();
    public static Map<Short, List<EnchantItem>> enchantIdItems = new LinkedHashMap();
    public static Map<Short, EnchantItem> enchantItems = new LinkedHashMap();
    public static List<EnchantItem> enchantList = new ArrayList();
    public static Map<Short, String> itemEnchantMap = new HashMap();
    private static boolean loaded = false;

    public static List<EnchantItem> getEnchantDataByItemId(Short sh) {
        if (enchantIdItems.get(sh) == null && itemEnchantMap.get(sh) != null) {
            String[] split = ((String) itemEnchantMap.get(sh)).split(",");
            List arrayList = new ArrayList();
            for (String str : split) {
                if (str != null && str.trim().length() > 0) {
                    EnchantItem enchantItem = (EnchantItem) enchantItems.get(Short.valueOf(str));
                    if (enchantItem != null) {
                        arrayList.add(enchantItem);
                    }
                }
            }
            if (arrayList.size() > 0) {
                enchantIdItems.put(sh, arrayList);
            }
        }
        return (List) enchantIdItems.get(sh);
    }

    public static String getEnchantItemName(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null && str.indexOf(",") > -1) {
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i += 2) {
                short shortValue = Short.valueOf(split[i].trim()).shortValue();
                if (enchantItems.get(Short.valueOf(shortValue)) != null) {
                    if (i != 0) {
                        stringBuffer.append("\t\n");
                    }
                    stringBuffer.append(((EnchantItem) enchantItems.get(Short.valueOf(shortValue))).chName + ",Lv." + split[i + 1]);
                }
            }
        }
        return stringBuffer.toString();
    }

    public static void loadAll(Context context) throws IOException, XmlPullParserException {
        if (!loaded || enchantItems.size() <= 0 || itemEnchantMap.size() <= 0) {
            loadData(context);
            loadMapData(context);
            loadEnchantList(context);
        }
    }

    public static void loadData(Context context) throws IOException, XmlPullParserException {
        enchantItems.clear();
        enchantDataList.clear();
        XmlResourceParser xml = context.getResources().getXml(C1687R.xml.enchant_data);
        while (xml.next() != 1) {
            if (XML_TAG_ITEM.equals(xml.getName())) {
                EnchantItem enchantItem = new EnchantItem();
                int attributeCount = xml.getAttributeCount();
                for (int i = 0; i < attributeCount; i++) {
                    String attributeName = xml.getAttributeName(i);
                    String attributeValue = xml.getAttributeValue(i);
                    if (attributeName != null) {
                        if (attributeName.equals("id")) {
                            enchantItem.id = Short.parseShort(attributeValue);
                        } else if (attributeName.equals("name")) {
                            enchantItem.name = attributeValue;
                        } else {
                            try {
                                if (attributeName.equals("chName")) {
                                    enchantItem.chName = attributeValue;
                                } else if (attributeName.equals("level")) {
                                    enchantItem.level = Short.parseShort(attributeValue);
                                }
                            } catch (Exception e) {
                                try {
                                    e.printStackTrace();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                } finally {
                                    xml.close();
                                }
                            }
                        }
                    }
                }
                if (enchantItem.id >= (short) 0) {
                    enchantDataList.add(enchantItem);
                    enchantItems.put(Short.valueOf(enchantItem.id), enchantItem);
                }
            }
        }
    }

    public static List<EnchantItem> loadEnchantList(Context context) throws IOException, XmlPullParserException {
        enchantList.clear();
        XmlResourceParser xml = context.getResources().getXml(C1687R.xml.enchant_list);
        while (xml.next() != 1) {
            try {
                if (XML_TAG_ITEM.equals(xml.getName())) {
                    EnchantItem enchantItem = new EnchantItem();
                    int attributeCount = xml.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        String attributeName = xml.getAttributeName(i);
                        String attributeValue = xml.getAttributeValue(i);
                        if (attributeName != null) {
                            if (attributeName.equals("id")) {
                                enchantItem.id = Short.parseShort(attributeValue);
                            } else if (attributeName.equals("name")) {
                                enchantItem.name = attributeValue;
                            }
                        }
                    }
                    if (enchantItem.id >= (short) 0) {
                        enchantList.add(enchantItem);
                    }
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                } finally {
                    xml.close();
                }
            }
        }
        return enchantList;
    }

    public static void loadMapData(Context context) throws IOException, XmlPullParserException {
        itemEnchantMap.clear();
        XmlResourceParser xml = context.getResources().getXml(C1687R.xml.item_enchant_data);
        while (xml.next() != 1) {
            try {
                Object obj = null;
                try {
                    if (XML_TAG_ITEM.equals(xml.getName())) {
                        int attributeCount = xml.getAttributeCount();
                        int i = 0;
                        short s = (short) 0;
                        while (i < attributeCount) {
                            short s2;
                            String attributeName = xml.getAttributeName(i);
                            String attributeValue = xml.getAttributeValue(i);
                            if (attributeName == null) {
                                s2 = s;
                            } else if (attributeName.equals("id")) {
                                s2 = Short.parseShort(attributeValue);
                            } else if (attributeName.equals("enchant")) {
                                String str = attributeValue;
                                s2 = s;
                            } else {
                                s2 = s;
                            }
                            i++;
                            s = s2;
                        }
                        itemEnchantMap.put(Short.valueOf(s), (String) obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                xml.close();
            }
        }
    }
}
