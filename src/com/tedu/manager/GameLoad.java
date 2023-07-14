package com.tedu.manager;

import com.tedu.element.*;
import com.tedu.element.Action;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @说明 加载器（工具，用户读取配置文件的工具）工具类，大多数提供的是static
 * @author cgl
 */

public class GameLoad {
    private static ElementManager em = ElementManager.getManager();
//    public static Map<ImgState, ImageIcon> imgMap = new HashMap<>();
    public static Map<Action, List<ImageIcon>> imgMaps = new HashMap<>();
    public static Map<String, ImageIcon> Maps = new HashMap<>();

    // 用户读取文件的类
    private static Properties pro = new Properties();
    /**
     * @说明 传入地图id用加载方法根据文件规则自动产生地图文件名称，加载文件
     */
    public static void MapLoad(int stage){
        String texturl = "com/tedu/text/"+stage+".map";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        pro.clear();
        try{
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String url = pro.getProperty(o.toString());

                Maps.put(o.toString(), new ImageIcon(url));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ElementObj element = new MapBg().createElement(Maps.get("backimage1").toString());
        em.addElement(element, GameElement.MAPBG);
    }
    public static void loadImg() {
        String texturl = "com/tedu/text/ImgSrc.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        pro.clear();
        try{
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String url = pro.getProperty(o.toString());
                System.out.println(o);
                imgMaps.put(Action.valueOf(o.toString()), loadImagesFromFolder(url));
                System.out.println(o);
                System.out.println(loadImagesFromFolder(url));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static List<ImageIcon> loadImagesFromFolder(String folderPath) {
        List<ImageIcon> imageList = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                Arrays.sort(files, Comparator.comparing(File::getName));
                for (File file : files) {
                    if (file.isFile()) {
                        String path = folderPath+"/"+file.getName();
                        ImageIcon imageIcon = new ImageIcon(path);
                        imageList.add(imageIcon);
                    }
                }
            }
        }

        return imageList;
    }
    public static void loadPlay(int stage){
//        String playStr = "500,500,up";
//        ElementObj play = getObj("play").createElement(playStr);
//        em.addElement(play, GameElement.PLAY);
        String texturl = "com/tedu/text/game"+stage+".pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        pro.clear();
        try{
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String url = pro.getProperty(o.toString());
                String tclass = o.toString().split("_")[0];
                ElementObj obj = getObj(tclass).createElement(url);
                em.addElement(obj, GameElement.valueOf(tclass));
            }
            ElementObj panel = new Panel(0);
            em.addElement(panel, GameElement.PANEL);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Map<String, Class<?>> objMap = new HashMap<>();
    public static ElementObj getObj(String str){
        try{
            System.out.println(str);
            Class<?> class1 = objMap.get(str);
//            System.out.println(class1);
            Object newInstance = class1.newInstance();
            if(newInstance instanceof ElementObj){
                return (ElementObj)newInstance;
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void loadObj(){
        String texturl = "com/tedu/text/obj.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        pro.clear();
        try{
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for (Object o:set){
                String classUrl = pro.getProperty(o.toString());
                Class<?> forName = Class.forName(classUrl);
                objMap.put(o.toString(), forName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadEnemy(int stage) {
//        String texturl = "com/tedu/text/game"+stage+".pro";
        String texturl = "com/tedu/text/game1"+".pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        pro.clear();
        try{
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String url = pro.getProperty(o.toString());
                String tclass = o.toString().split("_")[0];

                ElementObj obj = getObj(tclass).createElement(url);
                em.addElement(obj, GameElement.valueOf(tclass));
            }
            ElementObj panel = new Panel(0);
            em.addElement(panel, GameElement.PANEL);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        MapLoad(5);
//    }
}
