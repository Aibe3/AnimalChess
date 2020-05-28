package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * privateやprotected等のフィールドやメソッドに対してテストを行いたい時の為のクラス
 */
public class InaccessibleMemberUtil {
    
    private Class<?> targetClass;
    
    private Object instance;
    
    /**
     * スタティックなメソッド等の場合に使用
     * @param targetClass 対象のメソッドやフィールドをもつクラス
     */
    public InaccessibleMemberUtil(Class<?> targetClass) {
        this.targetClass = targetClass;
    }
    
    /**
     * インスタンスのメソッド等の場合に使用
     * @param targetClass 対象のメソッドやフィールドをもつクラス
     * @param instance 対象のインスタンス
     * @throws IllegalArgumentException 
     */
    public InaccessibleMemberUtil(Class<?> targetClass, Object instance) throws IllegalArgumentException {
        this.targetClass = targetClass;
        this.instance = instance;
        if(!targetClass.equals(instance.getClass()))
            throw new IllegalArgumentException("クラスとインスタンスのクラスは同一のものにしてください。");
    }
    
    /**
     * コンストラクタで渡されたクラス・インスタンスから指定されたメソッドを実行し戻り値を返す
     * スタティック・インスタンス、どちらのメソッドでも使用可能
     * @param name メソッド名
     * @param paramTypes メソッドの引数の型
     * @param parameters メソッドの引数
     * @return 実行結果
     */
    public Optional<?> invokeMethod(String name, Class<?>[] paramTypes, Object[] parameters) {
        Optional<?> result = Optional.empty();
        try {
            Method m = targetClass.getMethod(name, paramTypes);
            m.setAccessible(true);
            result = Optional.of(m.invoke(this.instance, parameters));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * コンストラクタで渡されたクラスから指定されたフィールドを取得し返す
     * スタティック・インスタンス、どちらのフィールドでも使用可能
     * @param name フィールド名
     * @return 指定されたフィールドの値
     */
    public Optional<?> getFieldValue(String name) {
        Optional<?> result = Optional.empty();
        try {
            Field f = this.targetClass.getDeclaredField(name);
            f.setAccessible(true);
            result = Optional.of(f.get(this.instance));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * コンストラクタで渡されたクラスが持つフィールドに値を設定する
     * スタティック・インスタンス、どちらのフィールドでも使用可能
     * @param name フィールド名
     * @param value 設定する値
     */
    public void setFieldValue(String name, Object value) {
        try {
            Field f = this.targetClass.getDeclaredField(name);
            f.setAccessible(true);
            f.set(this.instance, value);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
