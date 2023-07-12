package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 本类是元素管理器，专门存储所有元素，同时，提供方法给予视图和控制获取数据
 * @author cgl
 * @问题一：存储所有元素数据，怎么存放
 * @问题二：管理器是视图和控制要访问，管理器就必须有一个，单例模式
 */
public class ElementManager {
    /**
     * list存放元素基类
     * 所有元素都可以放在map中，显示模块只需要获取到这个map就可以显示界面需要的元素
     * (调用元素基类的show)
     */
    private Map<GameElement, List<ElementObj>> gameElements;

    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    /**
     * 单例模式：内存中有且只有一个实例
     * 饿汉模式-启动就自动加载模式
     * 饱汉模式-需要使用的时候才加载实例
     * 编写方式：
     * 1. 需要一个静态属性（定义一个常量）单例的引用
     * 2， 提供一个静态的方法（返回这个实例）return单例的引用
     * 3. 一般防止其他人自己使用（类是可以实例化），所以会私有化构造方法
     * ELementManager em - new ElementManager();
     */
    private  static ElementManager EM=null;
//    synchronized线程锁，保证本方法执行中只有一个线程

    public static synchronized ElementManager getManager(){
        if(EM == null){
            EM = new ElementManager();
        }
        return EM;
    }
    public void reinit(){
        gameElements.clear();
        init();
    }
    //添加元素（多半由加载器调用
    public void addElement(ElementObj obj, GameElement ge){
        gameElements.get(ge).add(obj); // 添加对象到集合中
    }

//    依据key返回list集合，去除某一元素
    public List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }

    private ElementManager(){
        init();
    }

    public void init(){ //实例化在此完成，因为继承不能继承构造方法，未来功能拓展可以通过重写init方法实现
//        hashMap hash散列
        gameElements = new HashMap<GameElement, List<ElementObj>>();
//        将每种元素结合放入map
        for(GameElement ge:GameElement.values()){
            gameElements.put(ge, new ArrayList<ElementObj>());
        }
    }

}
